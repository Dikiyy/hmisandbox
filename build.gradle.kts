// Top-level build file — applies plugins to all subprojects.
// Think of this like your Maven parent POM or Spring Boot BOM: declares versions, never applies logic directly.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    // Hilt requires its Gradle plugin declared here (applies annotation processor to all modules)
    alias(libs.plugins.hilt) apply false
    // KSP (Kotlin Symbol Processing) replaces kapt — faster, incremental, no separate JVM process
    alias(libs.plugins.ksp) apply false
}
