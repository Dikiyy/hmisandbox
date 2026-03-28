plugins {
    alias(libs.plugins.android.application)
    // kotlin.android enables the Kotlin compiler for Android source sets.
    // Required for KSP / Hilt to process @HiltAndroidApp, @HiltViewModel etc.
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    // Hilt plugin generates component/module binding code at compile time (like Spring's @ComponentScan)
    alias(libs.plugins.hilt)
    // KSP processes Hilt annotations — replaces the older kapt annotation processor
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.example.hmipuresandbox"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.hmipuresandbox"
        minSdk = 28
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    // AndroidX Core
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    // Compose BOM — declares all Compose versions in one place, like a Spring BOM
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    // Extended icon set — includes automotive-relevant icons (Speed, AcUnit, etc.)
    implementation(libs.androidx.compose.material.icons.extended)

    // ViewModel + Compose integration
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)

    // Navigation Compose — the Android equivalent of Spring MVC @RequestMapping / Router
    implementation(libs.androidx.navigation.compose)

    // Hilt — Dependency Injection, equivalent to Spring's IoC container
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    // Allows hiltViewModel() inside Composables — bridges Hilt + Navigation
    implementation(libs.hilt.navigation.compose)

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}
