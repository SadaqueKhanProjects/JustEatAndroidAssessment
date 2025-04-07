package com.sadaquekhan.justeatassessment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sadaquekhan.justeatassessment.data.repository.IRestaurantRepository
import com.sadaquekhan.justeatassessment.domain.model.Restaurant
import com.sadaquekhan.justeatassessment.util.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.SocketTimeoutException
import javax.inject.Inject

/**
 * ViewModel for the RestaurantScreen.
 * Handles:
 * - Triggering API calls via RestaurantRepository
 * - Managing and exposing UI state (RestaurantUiState)
 * - Handling error messages, loading, and empty states
 */
@HiltViewModel
class RestaurantViewModel @Inject constructor(
    private val repository: IRestaurantRepository,
    private val logger: Logger
) : ViewModel(), IRestaurantViewModel {

    private val _uiState = MutableStateFlow(RestaurantUiState())
    override val uiState: StateFlow<RestaurantUiState> = _uiState

    // Represents different outcomes of postcode validation
    sealed class ValidationResult {
        object Empty : ValidationResult()
        object InvalidFormat : ValidationResult()
        data class Valid(val sanitizedPostcode: String) : ValidationResult()
    }

    /**
     * Validates raw postcode input and returns appropriate result.
     * Properly formats the postcode for UK standard (uppercase with a single space).
     */
    private fun validatePostcode(rawPostcode: String): ValidationResult {
        if (rawPostcode.isBlank()) return ValidationResult.Empty

        val sanitized = rawPostcode
            .trim() // Remove leading/trailing whitespace
            .replace("\\s+".toRegex(), " ") // Replace multiple spaces with a single space
            .uppercase() // Convert to uppercase

        return if (isValidPostcode(sanitized)) {
            ValidationResult.Valid(sanitized)
        } else {
            ValidationResult.InvalidFormat
        }
    }

    /**
     * Triggered by the UI. Initiates validation and, if valid, loads restaurants.
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
     * Launches coroutine to load restaurants from repository and update UI state.
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
     * Handles errors and updates UI state with the provided message.
     */
    private fun handleError(message: String, e: Exception) {
        logger.error("RestaurantViewModel", "${e.javaClass.simpleName}: ${e.message}")
        _uiState.value = _uiState.value.copy(
            isLoading = false,
            errorMessage = message
        )
    }

    /**
     * Validates UK postcode format using a realistic pattern.
     * This matches formats like EC1A 1BB, W1A 0AX, M1 1AE, etc.
     */
    private fun isValidPostcode(postcode: String): Boolean {
        return postcode.matches("^[A-Z]{1,2}[0-9][0-9A-Z]? ?[0-9][A-Z]{2}$".toRegex())
    }
}
