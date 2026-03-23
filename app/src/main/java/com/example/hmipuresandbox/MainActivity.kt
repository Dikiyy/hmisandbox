package com.example.hmipuresandbox

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.hmipuresandbox.ui.DashboardScreen
import com.example.hmipuresandbox.ui.theme.HmiPureSandboxTheme
import com.example.hmipuresandbox.viewmodel.DashboardViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            HmiPureSandboxTheme {
                val dashboardViewModel: DashboardViewModel = viewModel()
                val uiState by dashboardViewModel.uiState.collectAsStateWithLifecycle()

                DashboardScreen(uiState = uiState)
            }
        }
    }
}