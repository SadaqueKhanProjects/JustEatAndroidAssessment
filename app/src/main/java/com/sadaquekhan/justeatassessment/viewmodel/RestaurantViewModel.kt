package com.sadaquekhan.justeatassessment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sadaquekhan.justeatassessment.data.repository.IRestaurantRepository
import com.sadaquekhan.justeatassessment.util.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.SocketTimeoutException
import javax.inject.Inject

/**
 * ViewModel responsible for managing restaurant data and screen state.
 *
 * Responsibilities:
 * - Validates postcodes before querying
 * - Fetches restaurants from repository layer
 * - Exposes [RestaurantUiState] to the UI via [StateFlow]
 * - Handles loading, error, and empty result conditions
 *
 * @param repository Data source for fetching restaurants (injected via Hilt)
 * @param logger Platform-safe logging abstraction
 */
@HiltViewModel
class RestaurantViewModel @Inject constructor(
    private val repository: IRestaurantRepository,
    private val logger: Logger
) : ViewModel(), IRestaurantViewModel {

    private val _uiState = MutableStateFlow(RestaurantUiState())
    override val uiState: StateFlow<RestaurantUiState> = _uiState

    /**
     * Represents possible outcomes of postcode validation.
     */
    sealed class ValidationResult {
        object Empty : ValidationResult()
        object InvalidFormat : ValidationResult()
        data class Valid(val sanitizedPostcode: String) : ValidationResult()
    }

    /**
     * Public entry point for postcode search, triggered by the UI.
     * First validates the input, then initiates data loading.
     *
     * @param rawPostcode Unprocessed user input
     */
    override fun loadRestaurants(rawPostcode: String) {
        when (val result = validatePostcode(rawPostcode)) {
            is ValidationResult.Empty -> {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "Please enter a postcode",
                    hasSearched = true
                )
            }

            is ValidationResult.InvalidFormat -> {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "Invalid UK postcode format",
                    hasSearched = true
                )
            }

            is ValidationResult.Valid -> {
                loadValidatedRestaurants(result.sanitizedPostcode)
            }
        }
    }

    /**
     * Validates a user-entered postcode and transforms it to a standard UK format.
     * - Uppercases
     * - Reduces multiple spaces to one
     * - Ensures it matches UK postcode regex
     *
     * @return A [ValidationResult] indicating whether input is usable
     */
    private fun validatePostcode(rawPostcode: String): ValidationResult {
        if (rawPostcode.isBlank()) return ValidationResult.Empty

        val sanitized = rawPostcode
            .trim()
            .replace("\\s+".toRegex(), " ")
            .uppercase()

        return if (isValidPostcode(sanitized)) {
            ValidationResult.Valid(sanitized)
        } else {
            ValidationResult.InvalidFormat
        }
    }

    /**
     * Performs the actual API call via repository and updates the UI state accordingly.
     * Called only when postcode passes validation.
     *
     * @param sanitizedPostcode A cleaned, properly formatted UK postcode
     */
    private fun loadValidatedRestaurants(sanitizedPostcode: String) {
        logger.debug("RestaurantViewModel", "Loading restaurants for postcode: $sanitizedPostcode")

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                errorMessage = null,
                hasSearched = true
            )

            try {
                val restaurants = repository.getRestaurants(sanitizedPostcode)

                _uiState.value = _uiState.value.copy(
                    restaurants = restaurants,
                    isLoading = false,
                    isEmpty = restaurants.isEmpty()
                )

            } catch (e: SocketTimeoutException) {
                handleError("Server timeout. Please try again shortly.", e)
            } catch (e: IOException) {
                handleError("No internet connection. Please check your network.", e)
            } catch (e: Exception) {
                handleError("Something went wrong. Please try again later.", e)
            }
        }
    }

    /**
     * Centralized error handler that logs and updates the UI state.
     *
     * @param message User-facing error message
     * @param e The exception that caused the failure
     */
    private fun handleError(message: String, e: Exception) {
        logger.error("RestaurantViewModel", "${e.javaClass.simpleName}: ${e.message}")
        _uiState.value = _uiState.value.copy(
            isLoading = false,
            errorMessage = message
        )
    }

    /**
     * Regex-based UK postcode format validation.
     * Supports formats like:
     * - EC1A 1BB
     * - W1A 0AX
     * - M1 1AE
     */
    private fun isValidPostcode(postcode: String): Boolean {
        return postcode.matches("^[A-Z]{1,2}[0-9][0-9A-Z]? ?[0-9][A-Z]{2}$".toRegex())
    }
}
