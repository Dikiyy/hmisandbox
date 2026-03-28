package com.example.hmipuresandbox.domain.repository

import com.example.hmipuresandbox.domain.model.VehicleState
import kotlinx.coroutines.flow.Flow

/**
 * Contract for accessing vehicle data.
 *
 * --- Spring analogy ---
 * This interface is the Android equivalent of a Spring @Repository interface.
 * In Spring, you define:
 *   interface UserRepository : JpaRepository<User, Long>
 * and Spring generates the implementation.
 *
 * Here, we define the contract in the DOMAIN layer (the interface lives in domain/).
 * The IMPLEMENTATION lives in data/ (VehicleRepositoryImpl).
 * This is the Dependency Inversion Principle:
 *   - domain/ does NOT depend on data/
 *   - data/ depends on domain/ (it implements the interface)
 *
 * This means we can swap the entire data layer without touching domain or feature modules.
 * Phase 2 swaps the mock data source for a real CarPropertyManager source —
 * zero changes to DashboardViewModel or DashboardScreen.
 *
 * --- AAOS integration path ---
 * Today:  VehicleRepositoryImpl returns mock Flow from MockCarPropertyDataSource
 * Phase 5: VehicleRepositoryImpl wraps real CarPropertyManager.registerCallback()
 *          and emits updates as a Flow — the interface stays identical.
 *
 * Why Flow<VehicleState> and not suspend fun VehicleState?
 * Vehicle signals are continuous — speed updates every ~100ms, gear changes on driver input.
 * Flow<T> is the correct model for a stream of values over time, exactly like a WebSocket
 * stream or Server-Sent Events in a Spring backend.
 */
interface IVehicleRepository {

    /**
     * Continuous stream of vehicle state updates.
     * Backed by mock data now; backed by CarPropertyManager callbacks in production.
     * Analogy: a Server-Sent Events endpoint — you subscribe and get updates pushed to you.
     */
    fun observeVehicleState(): Flow<VehicleState>
}
