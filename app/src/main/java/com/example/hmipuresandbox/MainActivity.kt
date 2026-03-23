package com.example.hmipuresandbox

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.hmipuresandbox.ui.theme.HmiPureSandboxTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            HmiPureSandboxTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    HmiHomeScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun HmiHomeScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF101216))
            .statusBarsPadding()
            .padding(20.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "HMI Sandbox",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Hello Android",
                style = MaterialTheme.typography.bodyLarge,
                color = Color(0xFFB8C0CC)
            )

            Spacer(modifier = Modifier.height(24.dp))

            StatusCard(
                title = "Vehicle Status",
                value = "Parked"
            )

            Spacer(modifier = Modifier.height(16.dp))

            ButtonRow()

            Spacer(modifier = Modifier.height(16.dp))

            StatusCard(
                title = "Media",
                value = "No source selected"
            )
        }
    }
}

@Composable
fun StatusCard(title: String, value: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1B1F26)
        )
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                text = title,
                color = Color(0xFF8E99A8),
                style = MaterialTheme.typography.labelLarge
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = value,
                color = Color.White,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
fun ButtonRow() {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        HmiButton(text = "Media")
        HmiButton(text = "Climate")
        HmiButton(text = "Navigation")
    }
}

@Composable
fun HmiButton(text: String) {
    Button(
        onClick = { },
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF2A6CF0),
            contentColor = Color.White
        )
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium
        )
    }
}