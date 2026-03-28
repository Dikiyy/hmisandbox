package com.example.hmipuresandbox.core.common

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 * Wraps coroutine dispatchers so they can be injected and replaced in tests.
 *
 * --- Spring analogy ---
 * This is the Android equivalent of a Spring @Configuration class that exposes
 * an Executor or TaskScheduler bean:
 *
 *   @Bean fun taskExecutor(): ThreadPoolTaskExecutor { ... }
 *
 * In Spring, you inject the executor to control which thread pool a @Async method uses.
 * In Android, you inject AppDispatchers to control which coroutine dispatcher a
 * suspend function or Flow runs on.
 *
 * --- Why this matters ---
 * Hardcoding `Dispatchers.IO` inside a UseCase or Repository makes it UNTESTABLE —
 * you cannot control or replace the dispatcher in unit tests.
 * By injecting AppDispatchers, a test can provide TestDispatchers.Main / UnconfinedTestDispatcher,
 * making coroutine-based tests deterministic and fast.
 *
 * --- AAOS note ---
 * CarPropertyManager callbacks arrive on the car executor thread (hardware binder thread).
 * You must switch to Dispatchers.Main before updating UI state. AppDispatchers.main
 * is the correct dispatcher for that final step.
 *
 * Usage:
 *   viewModelScope.launch(dispatchers.io) { /* read vehicle data */ }
 *   withContext(dispatchers.main) { _uiState.value = newState }
 */
data class AppDispatchers(
    val main: CoroutineDispatcher = Dispatchers.Main,
    val io: CoroutineDispatcher = Dispatchers.IO,
    val default: CoroutineDispatcher = Dispatchers.Default
)
