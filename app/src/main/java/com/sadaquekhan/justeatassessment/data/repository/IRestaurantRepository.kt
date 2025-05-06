package com.sadaquekhan.justeatassessment.data.repository

import com.sadaquekhan.justeatassessment.domain.result.RestaurantResult

/**
 * Contract for fetching restaurant data from any data source (remote/local).
 *
 * This abstraction allows decoupling the domain/business logic from specific data implementations.
 */
interface IRestaurantRepository {

    /**
     * Fetches a list of restaurants based on the provided UK postcode.
     * Returns a sealed [RestaurantResult] representing either success or failure.
     *
     * @param postcode The user-supplied postcode for restaurant search (e.g., "EC4M7RF")
     * @return A [RestaurantResult] encapsulating either a list of domain models or an error
     */
    suspend fun getRestaurants(postcode: String): RestaurantResult
}
