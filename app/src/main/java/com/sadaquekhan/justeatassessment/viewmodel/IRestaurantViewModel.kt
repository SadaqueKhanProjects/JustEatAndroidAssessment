package com.sadaquekhan.justeatassessment.viewmodel

import kotlinx.coroutines.flow.StateFlow

/**
 * IRestaurantViewModel is an interface defining the contract for a Restaurant ViewModel.
 * It exposes the UI state and a method to load restaurants based on a postcode.
 */
interface IRestaurantViewModel {
    // Exposes the current UI state of the screen
    val uiState: StateFlow<RestaurantUiState>

    // Loads the restaurant data based on the given postcode
    fun loadRestaurants(postcode: String)
}