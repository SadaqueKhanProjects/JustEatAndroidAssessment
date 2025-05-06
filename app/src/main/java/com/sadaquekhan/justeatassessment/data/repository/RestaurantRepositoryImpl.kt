package com.sadaquekhan.justeatassessment.data.repository

import com.sadaquekhan.justeatassessment.domain.mapper.IRestaurantMapper
import com.sadaquekhan.justeatassessment.domain.result.RestaurantResult
import com.sadaquekhan.justeatassessment.network.api.RestaurantApiService
import com.sadaquekhan.justeatassessment.util.logging.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.SocketTimeoutException
import javax.inject.Inject


/**
 * Default implementation of [IRestaurantRepository] responsible for:
 * - Fetching restaurants from the Just Eat API
 * - Handling API/network errors gracefully
 * - Mapping raw DTOs to domain models
 *
 * @property apiService Injected Retrofit API interface
 * @property mapper Maps raw API responses into clean domain models
 * @property logger Platform-safe logger used for both runtime and test contexts
 */
class RestaurantRepositoryImpl @Inject constructor(
    private val apiService: RestaurantApiService,
    private val mapper: IRestaurantMapper,
    private val logger: Logger
) : IRestaurantRepository {

    /**
     * Retrieves and maps a list of restaurants based on the provided UK postcode.
     * Handles API responses and returns a sealed [RestaurantResult] instead of throwing exceptions.
     */
    override suspend fun getRestaurants(postcode: String): RestaurantResult {
        return withContext(Dispatchers.IO) {
            try {
                logger.debug("RestaurantRepository", "API call started for postcode: $postcode")

                val response = apiService.getRestaurantsByPostcode(postcode)

                if (response.isSuccessful) {
                    val dtoList = response.body()?.restaurants
                    if (dtoList != null) {
                        logger.debug("RestaurantRepository", "Restaurants fetched: ${dtoList.size}")
                        RestaurantResult.Success(dtoList.map { mapper.mapToDomainModel(it) })
                    } else {
                        logger.error("RestaurantRepository", "Null response body")
                        RestaurantResult.UnknownError("Unexpected empty response from server")
                    }
                } else {
                    logger.error("RestaurantRepository", "API error ${response.code()}: ${response.message()}")
                    RestaurantResult.NetworkError("API error ${response.code()}")
                }

            } catch (e: SocketTimeoutException) {
                logger.error("RestaurantRepository", "Timeout error: ${e.localizedMessage}")
                RestaurantResult.Timeout("Server timeout")
            } catch (e: IOException) {
                logger.error("RestaurantRepository", "Network error: ${e.localizedMessage}")
                RestaurantResult.NetworkError("No internet connection")
            } catch (e: Exception) {
                logger.error("RestaurantRepository", "Unexpected error: ${e.localizedMessage}")
                RestaurantResult.UnknownError("Something went wrong")
            }
        }
    }
}
