package com.sadaquekhan.justeatassessment.data.repository

import com.sadaquekhan.justeatassessment.domain.model.Restaurant

/**
 * Contract for fetching restaurant data from any data source (remote/local).
 *
 * This abstraction allows decoupling the domain/business logic from specific data implementations.
 */
interface IRestaurantRepository {

    /**
     * Fetches a list of restaurants based on the provided UK postcode.
     *
     * @param postcode The user-supplied postcode for restaurant search (e.g., "EC4M 7RF")
     * @return A list of sanitized [Restaurant] domain models ready for UI consumption
     */
    suspend fun getRestaurants(postcode: String): List<Restaurant>
}
