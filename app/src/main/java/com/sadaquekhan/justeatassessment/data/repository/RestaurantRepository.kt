package com.sadaquekhan.justeatassessment.data.repository

import com.sadaquekhan.justeatassessment.domain.model.Restaurant

/**
 * Interface for fetching restaurants from a data source (e.g., remote API).
 * Used to decouple business logic from networking implementation.
 */
interface RestaurantRepository {
    /**
     * Returns a list of restaurants for a given UK postcode.
     *
     * @param postcode The postcode used for the restaurant search
     * @return List of domain Restaurant models
     */
    suspend fun getRestaurants(postcode: String): List<Restaurant>
}
