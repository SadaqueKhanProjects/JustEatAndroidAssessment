package com.sadaquekhan.justeatassessment.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sadaquekhan.justeatassessment.data.repository.RestaurantRepository
import com.sadaquekhan.justeatassessment.domain.model.Restaurant
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
    private val repository: RestaurantRepository
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

        Log.d("RestaurantViewModel", "Loading restaurants for postcode: $sanitized")

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
                Log.e("RestaurantViewModel", "TimeoutException: ${e.localizedMessage}")
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "Server timeout. Please try again shortly.",
                    isEmpty = false
                )
            } catch (e: IOException) {
                Log.e("RestaurantViewModel", "IOException: ${e.localizedMessage}")
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "No internet connection. Please check your network.",
                    isEmpty = false
                )
            } catch (e: Exception) {
                Log.e("RestaurantViewModel", "Unhandled error: ${e.localizedMessage}")
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "Something went wrong. Please try again later.",
                    isEmpty = false
                )
            }
        }
    }
}