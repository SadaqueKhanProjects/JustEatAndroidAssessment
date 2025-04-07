package com.sadaquekhan.justeatassessment.util

import com.sadaquekhan.justeatassessment.data.dto.RestaurantResponseDto
import com.sadaquekhan.justeatassessment.network.api.RestaurantApiService
import retrofit2.Response

/**
 * Fake API service that returns predefined responses.
 * Allows testing without real network calls.
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