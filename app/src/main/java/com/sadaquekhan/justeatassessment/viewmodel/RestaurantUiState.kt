package com.sadaquekhan.justeatassessment.viewmodel

import com.sadaquekhan.justeatassessment.domain.model.Restaurant

/**
 * Represents the UI state for the Restaurant screen.
 *
 * Includes:
 * - List of restaurants returned from the API
 * - Loading state flag
 * - Error message from API/network issues
 * - Flag for rendering "no results" message
 */
data class RestaurantUiState(
    val restaurants: List<Restaurant> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isEmpty: Boolean = false
)