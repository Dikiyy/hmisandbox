package com.example.hmipuresandbox.data.vehicle

import com.example.hmipuresandbox.domain.model.VehicleState
import com.example.hmipuresandbox.domain.repository.IVehicleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Concrete implementation of IVehicleRepository.
 *
 * Currently returns a static snapshot of vehicle state via [flowOf].
 *
 * --- Spring analogy ---
 * This is like a Spring @Repository implementation. In Spring you might have:
 *   @Repository
 *   class UserRepositoryImpl(private val jdbcTemplate: JdbcTemplate) : IUserRepository
 *
 * Here, @Inject constructor() is Hilt's equivalent of @Autowired constructor injection.
 * @Singleton is Hilt's equivalent of @Scope("singleton") / default Spring bean scope.
 *
 * --- Dependency Inversion in practice ---
 * This class is in the DATA layer (data/).
 * It implements an interface from the DOMAIN layer (domain/).
 * The feature layer (feature/dashboard/) only sees IVehicleRepository, never this class.
 * Hilt wires them together via AppModule — exactly like Spring wires @Repository impls
 * to interfaces via @Autowired.
 *
 * --- AAOS integration path ---
 * Phase 2: Inject MockCarPropertyDataSource and emit a Flow that ticks values over time.
 * Phase 5: Inject real Car instance (via CarConnectionManager), call
 *           carPropertyManager.registerCallback(VehiclePropertyIds.PERF_VEHICLE_SPEED, ...)
 *           and convert the callback stream into a Flow using callbackFlow { }.
 *
 * TODO: AAOS Phase 2 — replace flowOf with MockCarPropertyDataSource.vehicleStateFlow
 * TODO: AAOS Phase 5 — replace with real CarPropertyManager callback via callbackFlow
 */
@Singleton
class VehicleRepositoryImpl @Inject constructor() : IVehicleRepository {

    override fun observeVehicleState(): Flow<VehicleState> = flowOf(
        VehicleState(
            speed = 0,
            gear = "P",
            engineTemperature = 90,
            cabinTemperature = 21.0f,
            mediaTitle = "FM 101.2",
            vehicleStatus = "Parked",
            isEngineRunning = false,
            fuelLevel = 80,
            batteryLevel = 100
        )
    )
}
