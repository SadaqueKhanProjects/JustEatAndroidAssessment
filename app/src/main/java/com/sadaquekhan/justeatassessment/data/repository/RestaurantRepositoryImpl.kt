package com.sadaquekhan.justeatassessment.data.repository

import android.util.Log
import com.sadaquekhan.justeatassessment.data.remote.dto.mapper.RestaurantMapper
import com.sadaquekhan.justeatassessment.domain.model.Restaurant
import com.sadaquekhan.justeatassessment.network.api.RestaurantApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.URLEncoder
import javax.inject.Inject

/**
 * Repository implementation that handles API communication and maps data to domain models.
 * Includes exception handling for network and parsing failures.
 */
class RestaurantRepositoryImpl @Inject constructor(
    private val apiService: RestaurantApiService,
    private val mapper: RestaurantMapper
) : RestaurantRepository {

    /**
     * Fetches and maps restaurant data from Just Eat API for the given postcode.
     * Handles API errors gracefully and logs for debugging.
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
                    Log.e("RestaurantRepository", "API error ${response.code()}: ${response.message()}")
                    throw IOException("API error ${response.code()}: ${response.message()}")
                }
            } catch (e: SocketTimeoutException) {
                Log.e("RestaurantRepository", "Timeout error: ${e.localizedMessage}")
                throw SocketTimeoutException("Server timeout")
            } catch (e: IOException) {
                Log.e("RestaurantRepository", "Network error: ${e.localizedMessage}")
                throw IOException("No internet connection")
            } catch (e: Exception) {
                Log.e("RestaurantRepository", "Unexpected error: ${e.localizedMessage}")
                throw Exception("Something went wrong")
            }
        }
    }
}
