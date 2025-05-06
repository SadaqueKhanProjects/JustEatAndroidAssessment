package com.sadaquekhan.justeatassessment.util.fake

import com.sadaquekhan.justeatassessment.data.repository.IRestaurantRepository
import com.sadaquekhan.justeatassessment.domain.model.Restaurant
import com.sadaquekhan.justeatassessment.domain.result.RestaurantResult
import kotlinx.coroutines.delay

/**
 * Fake repository to simulate API responses in unit tests.
 *
 * Supports:
 * - Delay simulation
 * - Custom error types
 * - Tracking of last requested postcode
 */
class FakeRestaurantRepository : IRestaurantRepository {

    var mockRestaurants: List<Restaurant> = emptyList()
    var returnType: RestaurantResultType = RestaurantResultType.SUCCESS
    var lastRequestedPostcode: String? = null
    var delayMillis: Long = 0L

    override suspend fun getRestaurants(postcode: String): RestaurantResult {
        lastRequestedPostcode = postcode.trim().replace("\\s+".toRegex(), "").uppercase()
        delay(delayMillis)

        return when (returnType) {
            RestaurantResultType.SUCCESS -> RestaurantResult.Success(mockRestaurants)
            RestaurantResultType.NETWORK_ERROR -> RestaurantResult.NetworkError("Simulated network error")
            RestaurantResultType.TIMEOUT -> RestaurantResult.Timeout("Simulated timeout")
            RestaurantResultType.UNKNOWN_ERROR -> RestaurantResult.UnknownError("Simulated unknown error")
        }
    }

    enum class RestaurantResultType {
        SUCCESS,
        NETWORK_ERROR,
        TIMEOUT,
        UNKNOWN_ERROR
    }
}
