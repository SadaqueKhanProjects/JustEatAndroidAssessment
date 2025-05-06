package com.sadaquekhan.justeatassessment.util

import com.sadaquekhan.justeatassessment.data.dto.RestaurantResponseDto
import com.sadaquekhan.justeatassessment.network.api.RestaurantApiService
import retrofit2.Response

/**
 * Fake implementation of [RestaurantApiService] for testing network interactions.
 *
 * Allows simulation of:
 * - Successful responses
 * - Thrown exceptions
 * - Controlled request tracking
 */
class FakeApiService : RestaurantApiService {

    var mockResponse: Response<RestaurantResponseDto> =
        Response.success(RestaurantResponseDto(emptyList()))

    var shouldReturnError = false
    var errorToThrow: Exception = RuntimeException("Test API error")
    var lastRequestedPostcode: String? = null

    override suspend fun getRestaurantsByPostcode(postcode: String): Response<RestaurantResponseDto> {
        lastRequestedPostcode = postcode
        return if (shouldReturnError) {
            throw errorToThrow
        } else {
            mockResponse
        }
    }
}
