package com.sadaquekhan.justeatassessment.viewmodel

import com.sadaquekhan.justeatassessment.domain.model.Restaurant

/**
 * Represents the complete state of the restaurant screen UI.
 * Updated via StateFlow and consumed by the composable.
 *
 * @property restaurants List of restaurants fetched from the API
 * @property isLoading True when data is being fetched
 * @property errorMessage Non-null if an error occurred during fetch
 * @property isEmpty True if the API returned an empty list after a valid search
 * @property hasSearched True if a search has been triggered (used to gate empty state)
 */
data class RestaurantUiState(
    val restaurants: List<Restaurant> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isEmpty: Boolean = false,
    val hasSearched: Boolean = false
)
