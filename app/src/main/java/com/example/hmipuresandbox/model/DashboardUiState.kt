package com.example.hmipuresandbox.model

data class DashboardUiState(
    val speed: Int = 0,
    val gear: String = "-",
    val temperature: Int = 0,
    val mediaTitle: String = "",
    val vehicleStatus: String = "Loading..."
)