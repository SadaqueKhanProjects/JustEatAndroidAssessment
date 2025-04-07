package com.sadaquekhan.justeatassessment.util

import com.sadaquekhan.justeatassessment.domain.model.Address
import com.sadaquekhan.justeatassessment.domain.model.Restaurant
import com.sadaquekhan.justeatassessment.data.repository.IRestaurantRepository
import kotlinx.coroutines.delay
import java.io.IOException

/**
 * Fake repository used for unit testing.
 * Simulates various scenarios: success, timeout, and general exceptions.
 */
class FakeRestaurantRepository : IRestaurantRepository {

    var mockRestaurants: List<Restaurant> = emptyList()
    var shouldReturnError: Boolean = false
    var errorToThrow: Exception = IOException("Mock network error")
    var lastRequestedPostcode: String? = null
    var delayMillis: Long = 0L // Optional simulation of network delay

    override suspend fun getRestaurants(postcode: String): List<Restaurant> {
        lastRequestedPostcode = postcode.trim().replace("\\s+".toRegex(), "").uppercase()

        if (shouldReturnError) {
            throw errorToThrow
        }

        delay(delayMillis) // Optional for simulating network latency

        return mockRestaurants
    }
}
