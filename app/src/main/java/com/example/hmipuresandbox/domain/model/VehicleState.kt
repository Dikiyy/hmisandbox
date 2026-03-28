package com.example.hmipuresandbox.domain.model

/**
 * Core domain model representing the observable state of the vehicle.
 *
 * --- Spring analogy ---
 * This is the Domain Model / Entity layer. NOT a JPA entity (no database annotations).
 * It is a pure Kotlin data class — the equivalent of a clean domain object in DDD.
 * Compare to a Spring @Service layer's internal model, before it gets mapped to a DTO.
 *
 * In the MVVM pipeline:
 *   VehicleState (domain model)  →  mapped to →  DashboardUiState (UiState / DTO for UI)
 *
 * For now, VehicleState IS the UI state. When the app grows, a dedicated UiState per
 * screen will map only the fields that screen needs — keeping screens decoupled from
 * changes in the domain model (same principle as DTO isolation in REST APIs).
 *
 * --- AAOS mapping ---
 * Each field here corresponds to a real VehicleProperty in the AAOS framework:
 *
 *   speed               → VehiclePropertyIds.PERF_VEHICLE_SPEED        (float, m/s)
 *   gear                → VehiclePropertyIds.GEAR_SELECTION             (int, GearSelections enum)
 *   engineTemperature   → VehiclePropertyIds.ENGINE_COOLANT_TEMP        (float, Celsius)
 *   cabinTemperature    → VehiclePropertyIds.HVAC_TEMPERATURE_DISPLAY   (float, Celsius)
 *   isEngineRunning     → VehiclePropertyIds.ENGINE_RUNNING             (boolean)
 *   fuelLevel           → VehiclePropertyIds.FUEL_LEVEL                 (float, 0..1 range)
 *   batteryLevel        → VehiclePropertyIds.EV_BATTERY_LEVEL           (float, 0..1 range)
 *
 * TODO: AAOS Phase 2 — add odometer, door states, parkingBrake, lightStates
 */
data class VehicleState(
    val speed: Int = 0,
    val gear: String = "P",
    val engineTemperature: Int = 90,
    val cabinTemperature: Float = 21.0f,
    val mediaTitle: String = "No media",
    val vehicleStatus: String = "Parked",
    val isEngineRunning: Boolean = false,
    val fuelLevel: Int = 80,       // percentage 0-100
    val batteryLevel: Int = 100    // percentage 0-100 (EV/hybrid)
)
