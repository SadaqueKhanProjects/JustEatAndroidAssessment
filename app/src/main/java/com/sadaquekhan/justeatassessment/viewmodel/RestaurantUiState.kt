package com.sadaquekhan.justeatassessment.viewmodel

import com.sadaquekhan.justeatassessment.domain.model.Restaurant

/**
 * Represents the UI state of the restaurant list screen.
 *
 * Holds the list of restaurants and a loading flag for progress indication.
 *
 * @property restaurants List of domain-level Restaurant objects
 * @property isLoading True when loading data from the repository
 */
data class RestaurantUiState(
    val restaurants: List<Restaurant> = emptyList(),
    val isLoading: Boolean = false
)
