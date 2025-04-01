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

    fun loadRestaurants(postcode: String) {
        viewModelScope.launch {
            Log.d("RestaurantViewModel", "Loading restaurants for postcode: $postcode")
            _uiState.value = _uiState.value.copy(isLoading = true)

            val restaurants: List<Restaurant> = repository.getRestaurants(postcode)

            Log.d("RestaurantViewModel", "Fetched ${restaurants.size} restaurants from repository")

            _uiState.value = _uiState.value.copy(
                restaurants = restaurants,
                isLoading = false
            )
        }
    }
}