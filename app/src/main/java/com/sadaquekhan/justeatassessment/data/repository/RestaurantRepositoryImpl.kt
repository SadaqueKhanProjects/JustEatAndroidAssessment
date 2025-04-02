package com.sadaquekhan.justeatassessment.data.repository

import android.util.Log
import com.sadaquekhan.justeatassessment.domain.model.Restaurant
import com.sadaquekhan.justeatassessment.network.api.RestaurantApiService
import com.sadaquekhan.justeatassessment.data.remote.dto.mapper.toDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URLEncoder
import javax.inject.Inject

/**
 * Implementation of the RestaurantRepository interface.
 * Handles API communication and mapping to domain models.
 */
class RestaurantRepositoryImpl @Inject constructor(
    private val apiService: RestaurantApiService
) : RestaurantRepository {

    override suspend fun getRestaurants(postcode: String): List<Restaurant> {
        return withContext(Dispatchers.IO) {
            try {
                // Encode postcode to make it URL-safe
                val encodedPostcode = URLEncoder.encode(postcode.trim(), "UTF-8")
                Log.d("RestaurantRepository", "API call started for postcode: $encodedPostcode")

                // Make the API call
                val response = apiService.getRestaurantsByPostcode(encodedPostcode)

                if (response.isSuccessful) {
                    val dtoList = response.body()?.restaurants ?: emptyList()
                    Log.d(
                        "RestaurantRepository",
                        "API success. Restaurants fetched: ${dtoList.size}"
                    )

                    // Use extension function to map each DTO to domain model
                    dtoList.map { it.toDomain() }
                } else {
                    Log.e(
                        "RestaurantRepository",
                        "API call failed. Code: ${response.code()} Message: ${response.message()}"
                    )
                    emptyList()
                }
            } catch (e: Exception) {
                Log.e("RestaurantRepository", "Exception during API call: ${e.localizedMessage}")
                emptyList()
            }
        }
    }
}