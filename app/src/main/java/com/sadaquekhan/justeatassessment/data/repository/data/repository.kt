package com.sadaquekhan.justeatassessment.data.repository

import com.sadaquekhan.justeatassessment.data.repository.RestaurantRepository
import com.sadaquekhan.justeatassessment.domain.model.Restaurant
import com.sadaquekhan.justeatassessment.network.api.RestaurantApiService
import com.sadaquekhan.justeatassessment.RestaurantResponse
import com.sadaquekhan.justeatassessment.domain.model.RestaurantResponse

class DummyRepository : RestaurantRepository(
    api = object : RestaurantApiService {
        override suspend fun getRestaurants(postcode: String): RestaurantResponse {
            return RestaurantResponse(
                restaurants = listOf(
                    Restaurant(
                        id = "1",
                        name = "Sushi Heaven",
                        rating = 4.8f,
                        cuisineType = "Japanese",
                        eta = "20 mins"
                    ),
                    Restaurant(
                        id = "2",
                        name = "Pizza Palace",
                        rating = 4.5f,
                        cuisineType = "Italian",
                        eta = "25 mins"
                    ),
                    Restaurant(
                        id = "3",
                        name = "Curry Corner",
                        rating = 4.2f,
                        cuisineType = "Indian",
                        eta = "30 mins"
                    )
                )
            )
        }
    }
)
