package com.example.hmipuresandbox

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Application entry point for the HMI Sandbox.
 *
 * @HiltAndroidApp triggers Hilt's code generation and creates the top-level DI component.
 *
 * --- Spring analogy ---
 * This class is the Android equivalent of @SpringBootApplication.
 * Just as Spring Boot scans the classpath and wires the ApplicationContext on startup,
 * @HiltAndroidApp generates a Hilt component graph that lives for the entire app lifetime.
 * The generated component is called HmiApplication_HiltComponents.SingletonC — it IS the
 * Android equivalent of Spring's ApplicationContext.
 *
 * --- AAOS note ---
 * In a real AAOS system app (privileged app shipped with the vehicle), this class would also
 * initialise CarServiceConnection and register lifecycle-level Car API callbacks.
 * In this sandbox, all car data comes from the mock data layer.
 */
@HiltAndroidApp
class HmiApplication : Application()
