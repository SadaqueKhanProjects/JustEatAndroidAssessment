package com.sadaquekhan.justeatassessment.data.repository

import com.sadaquekhan.justeatassessment.domain.mapper.IRestaurantMapper
import com.sadaquekhan.justeatassessment.domain.model.Restaurant
import com.sadaquekhan.justeatassessment.network.api.RestaurantApiService
import com.sadaquekhan.justeatassessment.util.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.URLEncoder
import javax.inject.Inject

/**
 * Repository implementation that handles API communication and maps data to domain models.
 * Includes exception handling and uses injected logger for platform-safe logging.
 */
class RestaurantRepositoryImpl @Inject constructor(
    private val apiService: RestaurantApiService,
    private val mapper: IRestaurantMapper,
    private val logger: Logger // ✅ Replaces direct android.util.Log
) : IRestaurantRepository {

    /**
     * Fetches and maps restaurant data from Just Eat API for the given postcode.
     * Handles API errors gracefully and logs for debugging.
     */
    override suspend fun getRestaurants(postcode: String): List<Restaurant> {
        return withContext(Dispatchers.IO) {
            try {
                val encodedPostcode = URLEncoder.encode(postcode.trim(), "UTF-8")
                logger.debug("RestaurantRepository", "API call started for postcode: $encodedPostcode")

                val response = apiService.getRestaurantsByPostcode(encodedPostcode)

                if (response.isSuccessful) {
                    val dtoList = response.body()?.restaurants ?: emptyList()
                    logger.debug("RestaurantRepository", "API success. Restaurants fetched: ${dtoList.size}")
                    dtoList.map { mapper.mapToDomainModel(it) }
                } else {
                    logger.error("RestaurantRepository", "API error ${response.code()}: ${response.message()}")
                    throw IOException("API error ${response.code()}: ${response.message()}")
                }
            } catch (e: SocketTimeoutException) {
                logger.error("RestaurantRepository", "Timeout error: ${e.localizedMessage}")
                throw SocketTimeoutException("Server timeout")
            } catch (e: IOException) {
                logger.error("RestaurantRepository", "Network error: ${e.localizedMessage}")
                throw IOException("No internet connection")
            } catch (e: Exception) {
                logger.error("RestaurantRepository", "Unexpected error: ${e.localizedMessage}")
                throw Exception("Something went wrong")
            }
        }
    }
}