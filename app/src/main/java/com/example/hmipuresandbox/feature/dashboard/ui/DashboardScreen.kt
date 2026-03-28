package com.example.hmipuresandbox.feature.dashboard.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.LocalGasStation
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hmipuresandbox.domain.model.VehicleState
import com.example.hmipuresandbox.ui.theme.HmiPureSandboxTheme

/**
 * Dashboard screen — the HMI home screen showing primary vehicle telemetry.
 *
 * --- Architecture note ---
 * This Composable is a "stateless" UI function. It receives [VehicleState] as a parameter
 * and renders it — it does NOT know where the data comes from.
 *
 * This is the fundamental Android MVVM pattern and the exact equivalent of a
 * React/Vue component that receives props and renders them. The ViewModel above
 * is the "controller" that manages state; this function is a pure view function.
 *
 * --- Spring analogy ---
 * Think of this as a Thymeleaf / FreeMarker template:
 *   it receives a model object (VehicleState) and renders HTML (Compose UI).
 *   The @Controller prepares the model; the template renders it.
 *   Here: ViewModel prepares VehicleState; DashboardScreen renders it.
 *
 * --- AAOS note ---
 * In real AAOS, the Dashboard / Cluster display is often managed by a separate
 * system process (the Instrument Cluster app). Your in-cabin screen (IVI head unit)
 * shows a similar but distinct view. This screen represents the IVI head unit view.
 */
@Composable
fun DashboardScreen(
    uiState: VehicleState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        DashboardHeader(uiState)
        SpeedGearCard(uiState)
        HorizontalDivider()
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            TelemetryCard(
                modifier = Modifier.weight(1f),
                icon = Icons.Default.Thermostat,
                label = "Cabin",
                value = "${"%.1f".format(uiState.cabinTemperature)}°C"
            )
            TelemetryCard(
                modifier = Modifier.weight(1f),
                icon = Icons.Default.LocalGasStation,
                label = "Fuel",
                value = "${uiState.fuelLevel}%"
            )
            TelemetryCard(
                modifier = Modifier.weight(1f),
                icon = Icons.Default.Speed,
                label = "Engine",
                value = "${uiState.engineTemperature}°C"
            )
        }
        MediaCard(uiState.mediaTitle)
    }
}

@Composable
private fun DashboardHeader(uiState: VehicleState) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Dashboard",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .background(
                        color = if (uiState.isEngineRunning) Color(0xFF4CAF50) else Color(0xFFBDBDBD),
                        shape = CircleShape
                    )
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = uiState.vehicleStatus,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun SpeedGearCard(uiState: VehicleState) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "${uiState.speed}",
                    fontSize = 64.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    lineHeight = 64.sp
                )
                Text(
                    text = "km/h",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
                )
            }
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.primary
            ) {
                Text(
                    text = uiState.gear,
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp),
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}

@Composable
private fun TelemetryCard(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    label: String,
    value: String
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun MediaCard(mediaTitle: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(
                imageVector = Icons.Default.MusicNote,
                contentDescription = "Media",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(24.dp)
            )
            Column {
                Text(
                    text = "Now Playing",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = mediaTitle,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 400)
@Composable
private fun DashboardScreenPreview() {
    HmiPureSandboxTheme {
        DashboardScreen(
            uiState = VehicleState(
                speed = 87,
                gear = "D",
                engineTemperature = 92,
                cabinTemperature = 22.5f,
                mediaTitle = "FM 101.2 — Morning Drive",
                vehicleStatus = "Driving",
                isEngineRunning = true,
                fuelLevel = 73,
                batteryLevel = 100
            )
        )
    }
}
