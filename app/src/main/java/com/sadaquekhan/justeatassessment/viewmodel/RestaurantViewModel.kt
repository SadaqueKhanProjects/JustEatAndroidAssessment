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
import javax.inject.Inject

/**
 * ViewModel responsible for managing the restaurant search state.
 *
 * Uses Hilt for injecting the repository dependency.
 * Maintains a reactive UI state via Kotlin StateFlow.
 *
 * @property repository Repository that fetches and maps restaurant data
 * @property uiState Exposes loading + restaurant list state to the UI
 */
@HiltViewModel
class RestaurantViewModel @Inject constructor(
    private val repository: RestaurantRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RestaurantUiState())
    val uiState: StateFlow<RestaurantUiState> = _uiState

    /**
     * Loads restaurants using a user-provided postcode.
     *
     * Sanitizes the input and calls the repository. Automatically
     * updates loading state and fetched data in the UI state flow.
     *
     * @param rawPostcode Input from the user (can include spaces or lowercase)
     */
    fun loadRestaurants(rawPostcode: String) {
        val sanitized = rawPostcode.replace("\\s".toRegex(), "").uppercase()
        Log.d("RestaurantViewModel", "Loading restaurants for postcode: $sanitized")

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            val restaurants: List<Restaurant> = repository.getRestaurants(sanitized)

            _uiState.value = _uiState.value.copy(
                restaurants = restaurants,
                isLoading = false
            )
        }
    }
}
