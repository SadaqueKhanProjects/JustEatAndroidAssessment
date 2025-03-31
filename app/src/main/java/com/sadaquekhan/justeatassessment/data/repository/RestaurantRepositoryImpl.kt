// RestaurantRepositoryImpl.kt
package com.sadaquekhan.justeatassessment.data.repository
import com.sadaquekhan.justeatassessment.data.remote.dto.mapper.toDomain
import com.sadaquekhan.justeatassessment.domain.model.Restaurant
import com.sadaquekhan.justeatassessment.network.api.RestaurantApiService
import javax.inject.Inject


class RestaurantRepositoryImpl @Inject constructor(
    private val apiService: RestaurantApiService
) : RestaurantRepository {

    // Fetches restaurant data using the API service
    override suspend fun getRestaurants(postcode: String): List<Restaurant> {
        return try {
            // Map API response to domain model
            apiService.getRestaurants(postcode).restaurants.map { it.toDomain() }
        } catch (e: Exception) {
            // Log or handle error accordingly (e.g., show user-friendly message)
            emptyList() // fallback behavior
        }
    }
}
