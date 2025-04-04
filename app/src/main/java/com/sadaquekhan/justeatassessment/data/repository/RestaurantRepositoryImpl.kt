package com.sadaquekhan.justeatassessment.data.repository

import android.util.Log
import com.sadaquekhan.justeatassessment.data.mapper.RestaurantMapper
import com.sadaquekhan.justeatassessment.domain.model.Restaurant
import com.sadaquekhan.justeatassessment.network.api.RestaurantApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URLEncoder
import javax.inject.Inject

/**
 * Concrete implementation of the `RestaurantRepository` interface.
 *
 * Handles:
 * - Making API calls to Just Eat UK
 * - Mapping network DTOs to domain models
 * - Threading via `Dispatchers.IO`
 *
 * Follows Repository pattern and integrates with Clean Architecture.
 *
 * @constructor Injected with API service and mapper.
 */
class RestaurantRepositoryImpl @Inject constructor(
    private val apiService: RestaurantApiService,
    private val mapper: RestaurantMapper
) : RestaurantRepository {

    /**
     * Fetches a list of restaurants based on the given UK postcode.
     *
     * @param postcode Raw user-input postcode
     * @return List of cleaned `Restaurant` domain models
     */
    override suspend fun getRestaurants(postcode: String): List<Restaurant> {
        return withContext(Dispatchers.IO) {
            try {
                val encodedPostcode = URLEncoder.encode(postcode.trim(), "UTF-8")
                Log.d("RestaurantRepository", "API call started for postcode: $encodedPostcode")

                val response = apiService.getRestaurantsByPostcode(encodedPostcode)

                if (response.isSuccessful) {
                    val dtoList = response.body()?.restaurants ?: emptyList()
                    Log.d("RestaurantRepository", "API success. Restaurants fetched: ${dtoList.size}")
                    dtoList.map { mapper.mapToDomainModel(it) }
                } else {
                    Log.e("RestaurantRepository", "API failure: ${response.code()} ${response.message()}")
                    emptyList()
                }
            } catch (e: Exception) {
                Log.e("RestaurantRepository", "Exception: ${e.localizedMessage}")
                emptyList()
            }
        }
    }
}
