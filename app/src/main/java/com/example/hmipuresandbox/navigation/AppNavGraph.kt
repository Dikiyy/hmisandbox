package com.example.hmipuresandbox.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.hmipuresandbox.feature.dashboard.DashboardViewModel
import com.example.hmipuresandbox.feature.dashboard.ui.DashboardScreen
import com.example.hmipuresandbox.feature.hvac.ui.HvacScreen
import com.example.hmipuresandbox.feature.settings.ui.SettingsScreen

/**
 * Defines the full navigation graph for the HMI application.
 *
 * --- Spring analogy ---
 * [NavHost] is the Android equivalent of a Spring DispatcherServlet or a Router.
 * Each [composable(route)] block is equivalent to a @GetMapping method on a @RestController.
 * [NavHostController] is the equivalent of calling HttpServletRequest.getRequestDispatcher()
 * — it decides which screen/controller handles the current "request".
 *
 * The key difference: navigation in Android is stateful and back-stack aware.
 * Spring is stateless HTTP; Android navigation maintains a stack of destinations
 * the user can pop by pressing Back. Think of it like browser history.
 *
 * --- hiltViewModel() ---
 * Called inside each composable() block to obtain a Hilt-injected ViewModel scoped
 * to that navigation destination. The ViewModel survives configuration changes
 * (screen rotation) but is destroyed when the destination leaves the back stack.
 * Spring analogy: @RequestScoped bean that lives for the duration of one screen visit.
 */
@Composable
fun AppNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = NavDestinations.DASHBOARD,
        modifier = modifier
    ) {
        composable(NavDestinations.DASHBOARD) {
            val viewModel: DashboardViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            DashboardScreen(uiState = uiState)
        }

        composable(NavDestinations.HVAC) {
            HvacScreen()
        }

        composable(NavDestinations.SETTINGS) {
            SettingsScreen()
        }
    }
}

// ---------------------------------------------------------------------------
// Bottom Navigation Bar
// ---------------------------------------------------------------------------

/**
 * Describes a single item in the bottom navigation bar.
 * Kept as a plain data class — no sealed class needed when items are static.
 */
data class BottomNavItem(
    val route: String,
    val label: String,
    val icon: ImageVector
)

private val bottomNavItems = listOf(
    BottomNavItem(NavDestinations.DASHBOARD, "Dashboard", Icons.Default.Speed),
    BottomNavItem(NavDestinations.HVAC, "Climate", Icons.Default.Thermostat),
    BottomNavItem(NavDestinations.SETTINGS, "Settings", Icons.Default.Settings)
)

/**
 * The HMI bottom navigation bar — the persistent navigation element visible on all screens.
 *
 * --- IVI note ---
 * In a real automotive HMI, persistent navigation is often a side rail or top bar,
 * not a bottom bar (because bottom areas may be blocked by climate controls or OEM overlays).
 * For this sandbox, bottom nav is used for simplicity. A real Skoda HMI would use
 * a system-level navigation bar managed by the Car Launcher, not the app itself.
 *
 * The navigate() call uses popUpTo + saveState + restoreState to preserve scroll
 * and ViewModel state when switching tabs — same pattern as most production apps.
 */
@Composable
fun HmiBottomNavBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar {
        bottomNavItems.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        // Pop back to the start destination so back-stack doesn't grow unbounded
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true  // avoid multiple copies of the same destination
                        restoreState = true     // restore ViewModel/scroll state on re-select
                    }
                },
                icon = { Icon(imageVector = item.icon, contentDescription = item.label) },
                label = { Text(text = item.label) }
            )
        }
    }
}
