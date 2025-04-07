package com.sadaquekhan.justeatassessment.util

import com.sadaquekhan.justeatassessment.domain.model.Restaurant
import com.sadaquekhan.justeatassessment.data.repository.IRestaurantRepository
import kotlinx.coroutines.delay
import java.io.IOException

/**
 * Fake repository to simulate API responses in unit tests.
 *
 * Supports:
 * - Delay simulation
 * - Custom error throwing
 * - Tracking of last requested postcode
 */
class FakeRestaurantRepository : IRestaurantRepository {

    var mockRestaurants: List<Restaurant> = emptyList()
    var shouldReturnError: Boolean = false
    var errorToThrow: Exception = IOException("Mock network error")
    var lastRequestedPostcode: String? = null
    var delayMillis: Long = 0L

    override suspend fun getRestaurants(postcode: String): List<Restaurant> {
        lastRequestedPostcode = postcode.trim().replace("\\s+".toRegex(), "").uppercase()

        if (shouldReturnError) {
            throw errorToThrow
        }

        delay(delayMillis)
        return mockRestaurants
    }
}
