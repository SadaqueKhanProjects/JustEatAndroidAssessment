package com.sadaquekhan.justeatassessment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sadaquekhan.justeatassessment.data.repository.RestaurantRepository
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
    private val repository: RestaurantRepository,
    private val logger: Logger // Inject logger for platform-agnostic logging
) : ViewModel() {

    private val _uiState = MutableStateFlow(RestaurantUiState())
    val uiState: StateFlow<RestaurantUiState> = _uiState

    private fun isValidPostcode(postcode: String): Boolean {
        val trimmed = postcode.replace("\\s".toRegex(), "").uppercase()
        return trimmed.length in 5..8 && trimmed.matches("^[A-Z0-9]+$".toRegex())
    }

    fun loadRestaurants(rawPostcode: String) {
        val sanitized = rawPostcode.replace("\\s".toRegex(), "").uppercase()

        if (!isValidPostcode(sanitized)) {
            _uiState.value = _uiState.value.copy(
                isLoading = false,
                errorMessage = "Invalid UK postcode. Only 5–8 letters/numbers allowed.",
                isEmpty = false,
                hasSearched = true
            )
            return
        }

        logger.debug("RestaurantViewModel", "Loading restaurants for postcode: $sanitized")

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                errorMessage = null,
                isEmpty = false,
                hasSearched = true
            )

            try {
                val restaurants: List<Restaurant> = repository.getRestaurants(sanitized)

                _uiState.value = _uiState.value.copy(
                    restaurants = restaurants,
                    isLoading = false,
                    isEmpty = restaurants.isEmpty(),
                    errorMessage = null
                )
            } catch (e: SocketTimeoutException) {
                logger.error("RestaurantViewModel", "TimeoutException: ${e.localizedMessage}")
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "Server timeout. Please try again shortly.",
                    isEmpty = false
                )
            } catch (e: IOException) {
                logger.error("RestaurantViewModel", "IOException: ${e.localizedMessage}")
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "No internet connection. Please check your network.",
                    isEmpty = false
                )
            } catch (e: Exception) {
                logger.error("RestaurantViewModel", "Unhandled error: ${e.localizedMessage}")
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "Something went wrong. Please try again later.",
                    isEmpty = false
                )
            }
        }
    }
}