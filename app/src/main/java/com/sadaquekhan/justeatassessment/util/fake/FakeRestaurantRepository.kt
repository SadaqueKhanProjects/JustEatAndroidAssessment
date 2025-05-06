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
 * - Exception throwing for error testing
 * - Mocked return types via RestaurantResult
 * - Postcode tracking
 */
class FakeRestaurantRepository : IRestaurantRepository {

    var mockRestaurants: List<Restaurant> = emptyList()
    var returnType: RestaurantResultType = RestaurantResultType.SUCCESS
    var lastRequestedPostcode: String? = null
    var delayMillis: Long = 0L

    // New error simulation flags
    var shouldReturnError: Boolean = false
    var errorToThrow: Throwable = Exception("Simulated error")

    override suspend fun getRestaurants(postcode: String): RestaurantResult {
        lastRequestedPostcode = postcode.trim().replace("\\s+".toRegex(), "").uppercase()
        delay(delayMillis)

        // Throw error if flag is set
        if (shouldReturnError) {
            throw errorToThrow
        }

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
