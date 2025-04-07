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
     * - Handles HTTP errors, network failures, and unexpected null payloads
     * - Applies domain mapping before returning to the ViewModel
     *
     * @param postcode The user-entered UK postcode (e.g., "EC4M 7RF")
     * @return A list of sanitized [Restaurant] domain models
     * @throws IOException For network/API-related failures
     */
    override suspend fun getRestaurants(postcode: String): List<Restaurant> {
        return withContext(Dispatchers.IO) {
            try {
                // Encode postcode for safe HTTP transmission
                val encodedPostcode = URLEncoder.encode(postcode.trim(), "UTF-8")
                    .replace("+", "%20") // "+" is converted from spaces; must revert to "%20"

                logger.debug(
                    "RestaurantRepository",
                    "API call started for postcode: $encodedPostcode"
                )

                val response = apiService.getRestaurantsByPostcode(encodedPostcode)

                if (response.isSuccessful) {
                    // Defensive null check in case API returns no body
                    val dtoList = response.body()?.restaurants
                        ?: throw Exception("Unexpected null response body")

                    logger.debug(
                        "RestaurantRepository",
                        "API success. Restaurants fetched: ${dtoList.size}"
                    )

                    dtoList.map { mapper.mapToDomainModel(it) }

                } else {
                    // Log and throw descriptive error for HTTP failures
                    logger.error(
                        "RestaurantRepository",
                        "API error ${response.code()}: ${response.message()}"
                    )
                    throw IOException("API error ${response.code()}: ${response.message()}")
                }

            } catch (e: SocketTimeoutException) {
                // Specific case for server unavailability
                logger.error("RestaurantRepository", "Timeout error: ${e.localizedMessage}")
                throw SocketTimeoutException("Server timeout")

            } catch (e: IOException) {
                // Handles general network issues (e.g., airplane mode)
                if (e.message?.contains("API error") == false) {
                    logger.error("RestaurantRepository", "Network error: ${e.localizedMessage}")
                    throw IOException("No internet connection")
                } else {
                    throw e // Allow higher layers to handle API-specific codes
                }

            } catch (e: Exception) {
                // Fallback for unexpected internal or parsing errors
                logger.error("RestaurantRepository", "Unexpected error: ${e.localizedMessage}")
                throw Exception("Something went wrong")
            }
        }
    }
}
