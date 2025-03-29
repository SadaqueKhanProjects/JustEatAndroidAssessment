package com.sadaquekhan.justeatassessment.viewmodel



import com.sadaquekhan.justeatassessment.domain.model.Restaurant

data class RestaurantUiState(
    val isLoading: Boolean = false,
    val restaurants: List<Restaurant> = emptyList(),
    val errorMessage: String? = null
)
