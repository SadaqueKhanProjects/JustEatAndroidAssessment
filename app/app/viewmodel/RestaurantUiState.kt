package com.justorder.app.viewmodel

import com.justorder.app.domain.model.Restaurant

data class RestaurantUiState(
    val isLoading: Boolean = false,
    val restaurants: List<Restaurant> = emptyList(),
    val errorMessage: String? = null
)
