package com.sadaquekhan.justeatassessment.network.api

import com.sadaquekhan.justeatassessment.data.dto.RestaurantResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Retrofit API interface for fetching restaurant data from Just Eat's UK backend.
 *
 * Provides a suspend function for retrieving enriched restaurant listings based on postcode.
 *
 * @see RestaurantResponseDto – DTO representing the API response.
 */
interface RestaurantApiService {

    /**
     * Performs a GET request to Just Eat's enriched restaurant endpoint.
     *
     * @param postcode A valid UK postcode (URL-encoded if needed).
     * @return A Retrofit [Response] containing a parsed list of restaurants.
     */
    @GET("discovery/uk/restaurants/enriched/bypostcode/{postcode}")
    suspend fun getRestaurantsByPostcode(
        @Path("postcode") postcode: String
    ): Response<RestaurantResponseDto>
}
