package com.example.hmipuresandbox.navigation

/**
 * All navigation route strings defined in one place.
 *
 * --- Spring analogy ---
 * These constants are the Android equivalent of @RequestMapping path values.
 * In Spring you'd have:
 *   @GetMapping("/dashboard")
 *   @GetMapping("/hvac")
 *
 * In Compose Navigation, each screen is a "destination" identified by a route string.
 * NavController.navigate("dashboard") is conceptually identical to an HTTP GET /dashboard.
 *
 * --- Why constants? ---
 * Typos in route strings cause runtime crashes, not compile errors.
 * Centralising them here gives you one place to rename a route safely —
 * the same reason you define API path constants in Spring rather than inline strings.
 *
 * --- Future: typed routes ---
 * Navigation 2.8+ supports type-safe routes using @Serializable data classes.
 * We start with strings to keep complexity low. Migrate to typed routes in Phase 3+.
 */
object NavDestinations {
    const val DASHBOARD = "dashboard"
    const val HVAC = "hvac"
    const val SETTINGS = "settings"
}
