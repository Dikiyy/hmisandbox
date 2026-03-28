package com.example.hmipuresandbox.feature.settings.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.hmipuresandbox.ui.theme.HmiPureSandboxTheme

/**
 * Application settings screen.
 *
 * --- Status: PLACEHOLDER — Phase 3 implementation ---
 *
 * --- What this will contain in Phase 3 ---
 *   - User profile selector (driver 1 / driver 2)
 *   - Display settings (brightness, day/night mode)
 *   - Unit preferences (km/h vs mph, Celsius vs Fahrenheit)
 *   - Language selection
 *   - Connectivity preferences
 *   - About / version info
 *
 * --- Storage: Jetpack DataStore ---
 * Settings will be persisted using Jetpack DataStore (Preferences DataStore).
 * DataStore is the modern replacement for SharedPreferences — it's coroutine-native
 * and provides type-safe access via Protocol Buffers (Proto DataStore) or key-value pairs.
 *
 * --- Spring analogy ---
 * DataStore is equivalent to application.properties / application.yml backed by a
 * simple key-value store, but per-user and writable at runtime.
 * In Spring: @Value("${user.temperature.unit}") reads a config value.
 * In Android: dataStore.data.map { it[TEMP_UNIT_KEY] } reads a persisted preference.
 *
 * --- AAOS note ---
 * In real AAOS systems, settings are often managed at the system level by the
 * CarSettingsActivity (part of the privileged Settings app). Your app-level settings
 * should only contain HMI-specific preferences, not system/vehicle settings.
 * Vehicle settings (e.g. mirror positions, seat presets) go through VehicleProperty writes.
 *
 * TODO: Phase 3 — implement SettingsViewModel + DataStore preferences
 */
@Composable
fun SettingsScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.Settings,
            contentDescription = "Settings",
            modifier = Modifier.size(64.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Settings",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Coming in Phase 3",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SettingsScreenPreview() {
    HmiPureSandboxTheme { SettingsScreen() }
}
