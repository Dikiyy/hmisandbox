package com.example.hmipuresandbox

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.hmipuresandbox.navigation.AppNavGraph
import com.example.hmipuresandbox.navigation.HmiBottomNavBar
import com.example.hmipuresandbox.ui.theme.HmiPureSandboxTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * The single Activity of the HMI Sandbox application.
 *
 * --- @AndroidEntryPoint ---
 * Marks this class as a Hilt injection point. Hilt will inject any @Inject-annotated
 * fields in this Activity, and will also make hiltViewModel() work inside Composables
 * that are part of this Activity's composition.
 *
 * Spring analogy: this is the entry point equivalent of your @SpringBootApplication
 * main() method. Just as Spring Boot initialises the ApplicationContext before your
 * first @RestController handles a request, @AndroidEntryPoint ensures the Hilt
 * component graph is ready before your first Composable renders.
 *
 * --- Single Activity Architecture ---
 * Modern Android applications use a single Activity with Compose Navigation handling
 * all screen transitions internally. This is intentional:
 *   - Activities are expensive to create (full OS window allocation)
 *   - Compose navigation is lightweight and keeps the back stack in memory
 *   - The system handles deep links and back navigation via NavController
 *
 * Spring analogy: the single Activity is like your single Spring Boot application
 * running as a process. Multiple screens = multiple @RestController classes in that
 * process — you don't spin up a new JVM for each endpoint.
 *
 * --- Scaffold ---
 * Scaffold is the Material Design layout component that provides slots for:
 *   - topBar (app bar / toolbar)
 *   - bottomBar (bottom navigation)
 *   - content (the main screen content)
 *   - snackbarHost (system messages)
 * It automatically applies the correct padding to the content slot so UI is not
 * hidden behind the bars (inset handling).
 *
 * --- AAOS note ---
 * In a real AAOS application, the Activity may receive Car-specific intents
 * (e.g. android.car.intent.action.CAR_UI_TYPE_DAY_NIGHT) and must register a
 * CarLifecycleObserver to handle vehicle power state transitions. The HMI must
 * also handle driving restriction callbacks to disable certain UI elements
 * while the vehicle is in motion (UX Restrictions — AAOS compliance requirement).
 *
 * TODO: AAOS Phase 4 — register CarUxRestrictionsManager listener and apply
 *       driving-mode restrictions to interactive UI elements.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HmiPureSandboxTheme {
                HmiApp()
            }
        }
    }
}

/**
 * Root composable — owns the NavController and the top-level Scaffold.
 *
 * Extracted into its own function so it can be previewed and tested independently
 * of the Activity. NavController is created here (at the top of the composition tree)
 * and passed down to both [AppNavGraph] and [HmiBottomNavBar] so they share state.
 *
 * This pattern (hoist state to the lowest common ancestor) is the Compose equivalent
 * of keeping shared state in a parent Spring @Service rather than two sibling services.
 */
@Composable
private fun HmiApp() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            HmiBottomNavBar(navController = navController)
        }
    ) { innerPadding ->
        AppNavGraph(
            navController = navController,
            modifier = Modifier.padding(innerPadding)
        )
    }
}
