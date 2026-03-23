package com.example.hmipuresandbox.repository

import com.example.hmipuresandbox.model.DashboardUiState

class DashboardRepository {

    fun getDashboardData(): DashboardUiState {
        return DashboardUiState(
            speed = 0,
            gear = "P",
            temperature = 21,
            mediaTitle = "FM 101.2",
            vehicleStatus = "Parked"
        )
    }
}