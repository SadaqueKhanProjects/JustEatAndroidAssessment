// RestaurantRepository.kt
package com.sadaquekhan.justeatassessment.data.repository

import com.sadaquekhan.justeatassessment.domain.model.Restaurant

interface RestaurantRepository {
    suspend fun getRestaurants(postcode: String): List<Restaurant>
}