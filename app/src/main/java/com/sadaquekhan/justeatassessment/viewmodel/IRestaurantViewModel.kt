package com.sadaquekhan.justeatassessment.viewmodel

import kotlinx.coroutines.flow.StateFlow

/**
 * Contract for a ViewModel that powers the RestaurantScreen UI.
 *
 * Exposes UI state and allows triggering data fetch by postcode.
 */
interface IRestaurantViewModel {

    /**
     * The current state of the restaurant UI (loading, data, error, etc.)
     */
    val uiState: StateFlow<RestaurantUiState>

    /**
     * Triggers a restaurant search based on the user-entered postcode.
     *
     * @param postcode Raw UK postcode string entered by the user
     */
    fun loadRestaurants(postcode: String)
}
