package com.example.hmipuresandbox.viewmodel

import androidx.lifecycle.ViewModel
import com.example.hmipuresandbox.model.DashboardUiState
import com.example.hmipuresandbox.repository.DashboardRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class DashboardViewModel(
    private val repository: DashboardRepository = DashboardRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    init {
        loadDashboard()
    }

    private fun loadDashboard() {
        _uiState.value = repository.getDashboardData()
    }
}