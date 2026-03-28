package com.example.hmipuresandbox.core.common

/**
 * Generic operation result wrapper for the entire application.
 *
 * --- Spring analogy ---
 * This is the Android equivalent of ResponseEntity<T> combined with a custom ApiResponse<T> DTO
 * that most Spring backends define to wrap HTTP responses:
 *   data class ApiResponse<T>(val data: T?, val error: String?, val loading: Boolean)
 *
 * In Android we use a sealed class because the compiler enforces exhaustive when() branches,
 * the same way you'd use a typed union in a well-designed REST response model.
 *
 * --- Usage ---
 * Repository returns:   Flow<Result<VehicleState>>
 * ViewModel maps it:    when (result) { is Success -> ...; is Error -> ...; Loading -> ... }
 * UI observes:          uiState.isLoading, uiState.errorMessage, uiState.data
 *
 * --- AAOS note ---
 * When reading from CarPropertyManager, a property read can fail if:
 *   - The vehicle is in an unsupported drive mode
 *   - HAL returns STATUS_UNAVAILABLE
 *   - The app lacks the required car permission
 * Result<T> gives a clean, typed way to propagate these failures up to the UI.
 */
sealed class Result<out T> {

    /** Operation completed successfully. [data] holds the value. */
    data class Success<out T>(val data: T) : Result<T>()

    /**
     * Operation failed. [exception] is the root cause. [message] is a human-readable
     * fallback — useful when you want to show a generic UI string without leaking
     * internal exception messages to the user.
     */
    data class Error(
        val exception: Throwable,
        val message: String? = exception.localizedMessage
    ) : Result<Nothing>()

    /** Operation is in progress. No data available yet. */
    data object Loading : Result<Nothing>()
}

/** Convenience: true if this is a successful result. */
val <T> Result<T>.isSuccess: Boolean get() = this is Result.Success

/** Convenience: extract data or null. Mirrors Kotlin's runCatching().getOrNull(). */
fun <T> Result<T>.getOrNull(): T? = (this as? Result.Success)?.data
