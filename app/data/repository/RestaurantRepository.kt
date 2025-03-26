package com.justorder.app.data.repository

import com.justorder.app.network.api.RestaurantApiService
import com.justorder.app.domain.model.Restaurant

class RestaurantRepository(private val api: RestaurantApiService) {
    suspend fun fetchRestaurants(postcode: String): List<Restaurant> {
        // Mapping logic goes here — to be implemented after actual response is tested
        return emptyList()
    }
}
