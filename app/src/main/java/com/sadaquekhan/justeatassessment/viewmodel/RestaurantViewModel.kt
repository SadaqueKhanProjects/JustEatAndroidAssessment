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
 * ViewModel responsible for:
 * - Managing API interaction
 * - Tracking UI loading/error/empty state
 * - Exposing observable state to the UI
 */
@HiltViewModel
class RestaurantViewModel @Inject constructor(
    private val repository: RestaurantRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RestaurantUiState())
    val uiState: StateFlow<RestaurantUiState> = _uiState

    /**
     * Loads restaurants for a given postcode.
     * Sanitizes input and updates UI state based on outcome.
     */
    fun loadRestaurants(rawPostcode: String) {
        val sanitized = rawPostcode.replace("\\s".toRegex(), "").uppercase()
        Log.d("RestaurantViewModel", "Loading restaurants for postcode: $sanitized")

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                errorMessage = null,
                isEmpty = false
            )

            try {
                val restaurants: List<Restaurant> = repository.getRestaurants(sanitized)

                _uiState.value = _uiState.value.copy(
                    restaurants = restaurants,
                    isLoading = false,
                    isEmpty = restaurants.isEmpty()
                )
            } catch (e: IOException) {
                Log.e("RestaurantViewModel", "IOException: ${e.localizedMessage}")
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    isEmpty = true,
                    errorMessage = "No internet connection. Please check your network."
                )
            } catch (e: SocketTimeoutException) {
                Log.e("RestaurantViewModel", "TimeoutException: ${e.localizedMessage}")
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    isEmpty = true,
                    errorMessage = "Server timeout. Please try again shortly."
                )
            } catch (e: Exception) {
                Log.e("RestaurantViewModel", "Unhandled error: ${e.localizedMessage}")
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    isEmpty = true,
                    errorMessage = "Something went wrong. Please try again later."
                )
            }
        }
    }
}