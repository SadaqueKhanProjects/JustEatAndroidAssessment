package com.sadaquekhan.justeatassessment.data.repository

import com.sadaquekhan.justeatassessment.domain.model.Restaurant
import com.sadaquekhan.justeatassessment.network.api.RestaurantApiService


class RestaurantRepository(private val api: RestaurantApiService) {
    suspend fun fetchRestaurants(postcode: String): List<Restaurant> {
        val response = api.getRestaurants(postcode)
        return response.restaurants.map { dto ->
            Restaurant(
                name = dto.name,
                rating = dto.rating,
                cuisineType = dto.cuisineType,
                eta = dto.eta
            )
        }
    }

}
