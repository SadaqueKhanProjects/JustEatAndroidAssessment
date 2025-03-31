package com.sadaquekhan.justeatassessment.viewmodel

import com.sadaquekhan.justeatassessment.domain.model.Restaurant

// 📦 Represents all UI state for the restaurant screen
data class RestaurantUiState(
    val restaurants: List<Restaurant> = emptyList(), // Main restaurant list
    val isLoading: Boolean = false,                  // Show loading spinner
    val errorMessage: String? = null                 // Display error message if any
)