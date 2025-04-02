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

@HiltViewModel
class RestaurantViewModel @Inject constructor(
    private val repository: RestaurantRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RestaurantUiState())
    val uiState: StateFlow<RestaurantUiState> = _uiState

    fun loadRestaurants(rawPostcode: String) {
        // Sanitize the postcode: trim, remove spaces, uppercase
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