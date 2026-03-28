package com.example.hmipuresandbox.feature.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hmipuresandbox.domain.model.VehicleState
import com.example.hmipuresandbox.domain.repository.IVehicleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for the Dashboard screen.
 *
 * --- Spring analogy ---
 * ViewModel is the Android equivalent of a Spring @RestController method —
 * it receives data from the service/repository layer, processes it,
 * and exposes it in a form the "view" (Composable) can consume.
 *
 * But unlike a Controller, ViewModel is STATEFUL and survives configuration changes
 * (e.g. screen rotation). Spring controllers are stateless per-request.
 * The closest Spring concept is actually a @SessionScoped bean: it lives across
 * multiple requests (user interactions) within one user session (screen visit).
 *
 * --- @HiltViewModel ---
 * Tells Hilt to generate a factory for this ViewModel so it can be injected with
 * constructor parameters. Without this, Android's default ViewModel factory only
 * supports no-arg constructors.
 * Spring equivalent: @Service with @Autowired constructor — the framework creates
 * and injects the instance for you.
 *
 * --- StateFlow vs MutableStateFlow ---
 * _uiState is private and mutable — only the ViewModel modifies it.
 * uiState is public and read-only — the UI subscribes to it.
 * Spring analogy: a private setter on a field vs a public getter.
 * This is the UDF (Unidirectional Data Flow) pattern:
 *   Repository → ViewModel (processes) → StateFlow → UI (renders)
 * Data flows in ONE direction. UI never writes directly to the model.
 *
 * --- viewModelScope ---
 * A coroutine scope tied to the ViewModel's lifecycle. When the ViewModel is
 * cleared (screen destroyed), all coroutines in this scope are cancelled automatically.
 * Spring equivalent: a try-with-resources block that cleans up background tasks
 * when the request/session ends — but automatic.
 *
 * --- AAOS note ---
 * In a real AAOS application, this ViewModel would also handle:
 * - CarPropertyManager permission errors → emitting an error state
 * - Vehicle mode restrictions (e.g. speed-limited UI in Drive mode)
 * - UX Write restrictions (AAOS blocks certain interactions while vehicle is moving)
 */
@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val vehicleRepository: IVehicleRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(VehicleState())
    val uiState: StateFlow<VehicleState> = _uiState.asStateFlow()

    init {
        observeVehicleState()
    }

    private fun observeVehicleState() {
        viewModelScope.launch {
            vehicleRepository.observeVehicleState()
                .catch { exception ->
                    // TODO: emit error state when Result<T> is fully wired in Phase 2
                    // For now: log and keep last known state
                    exception.printStackTrace()
                }
                .collect { vehicleState ->
                    _uiState.value = vehicleState
                }
        }
    }
}
