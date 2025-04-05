package com.sadaquekhan.justeatassessment.viewmodel

import com.sadaquekhan.justeatassessment.domain.model.Restaurant

/**

 * Represents the full state of the RestaurantScreen UI.
 * Observed via StateFlow in the ViewModel.
 *
 * @property restaurants List of fetched restaurants
 * @property isLoading Flag to indicate ongoing API call
 * @property errorMessage Optional error message if an error occurred
 * @property isEmpty True if the API returned an empty list
 * @property hasSearched Tracks whether a search has ever been performed
 */
data class RestaurantUiState(
    val restaurants: List<Restaurant> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isEmpty: Boolean = false,
    val hasSearched: Boolean = false
)