package com.sadaquekhan.justeatassessment.data.repository

import com.sadaquekhan.justeatassessment.domain.model.Restaurant

/**
 * Repository interface defining how restaurant data is fetched.
 *
 * Abstracts away API details from domain/business logic.
 * Used to allow easy mocking and testing.
 */
interface RestaurantRepository {

    /**
     * Retrieves a list of restaurants by UK postcode.
     *
     * @param postcode Postcode string input by the user
     * @return List of domain-level `Restaurant` objects
     */
    suspend fun getRestaurants(postcode: String): List<Restaurant>
}
