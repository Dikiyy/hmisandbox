package com.example.hmipuresandbox.feature.hvac.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Thermostat
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
 * HVAC (Heating, Ventilation, Air Conditioning) control screen.
 *
 * --- Status: PLACEHOLDER — Phase 2 implementation ---
 *
 * --- What this will contain in Phase 2 ---
 *   - Dual-zone temperature controls (driver / passenger)
 *   - Fan speed control
 *   - A/C toggle
 *   - Airflow direction selector
 *   - Seat heating controls
 *   - Rear window defrost toggle
 *
 * --- Why HVAC is important for AAOS interviews ---
 * HVAC is one of the most commonly discussed subsystems in AAOS interviews because:
 *   1. It uses MULTIPLE VehicleProperty IDs (HVAC_TEMPERATURE_SET, HVAC_AC_ON,
 *      HVAC_FAN_SPEED, HVAC_FAN_DIRECTION, HVAC_SEAT_TEMPERATURE...)
 *   2. It demonstrates zone-aware property access (areaId: DRIVER vs PASSENGER zone)
 *   3. It requires both READ (display current state) and WRITE (user changes setting)
 *      operations on CarPropertyManager — more complex than read-only properties
 *   4. It exercises UX Write restrictions (some HVAC writes are blocked while driving)
 *
 * --- AAOS VehicleProperty IDs for HVAC ---
 *   VehiclePropertyIds.HVAC_TEMPERATURE_SET     — target temperature per zone
 *   VehiclePropertyIds.HVAC_TEMPERATURE_CURRENT — actual measured temperature
 *   VehiclePropertyIds.HVAC_AC_ON               — A/C compressor on/off
 *   VehiclePropertyIds.HVAC_FAN_SPEED           — blower fan speed level
 *   VehiclePropertyIds.HVAC_FAN_DIRECTION       — airflow direction (face/feet/windshield)
 *   VehiclePropertyIds.HVAC_AUTO_ON             — full auto climate control on/off
 *   VehiclePropertyIds.HVAC_SEAT_TEMPERATURE    — seat heating/cooling level
 *   VehiclePropertyIds.HVAC_DEFROSTER           — front/rear defrost on/off
 *
 * --- Spring analogy for Write operations ---
 * carPropertyManager.setProperty(Float::class.java, HVAC_TEMPERATURE_SET, areaId, 22.0f)
 * is equivalent to a REST PUT /hvac/temperature { "zone": "driver", "value": 22.0 }
 * The CarPropertyManager IS your HTTP client calling the car's "microservice".
 *
 * TODO: Phase 2 — implement HvacViewModel + HvacState + HvacRepositoryImpl
 */
@Composable
fun HvacScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.Thermostat,
            contentDescription = "HVAC",
            modifier = Modifier.size(64.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Climate Control",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Coming in Phase 2",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HvacScreenPreview() {
    HmiPureSandboxTheme { HvacScreen() }
}
