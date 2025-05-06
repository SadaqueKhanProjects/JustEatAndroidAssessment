package com.sadaquekhan.justeatassessment.network.api

import com.sadaquekhan.justeatassessment.data.dto.RestaurantResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Retrofit service interface for the Just Eat API.
 *
 * Defines endpoint for retrieving restaurant data by UK postcode.
 */
interface RestaurantApiService {

    /**
     * Fetches a list of enriched restaurant details for the given postcode.
     *
     * @param postcode URL-encoded UK postcode (e.g., "EC4M 7RF")
     * @return A [Response] wrapping [RestaurantResponseDto]
     */
    @GET("discovery/uk/restaurants/enriched/bypostcode/{postcode}")
    suspend fun getRestaurantsByPostcode(
        @Path("postcode") postcode: String
    ): Response<RestaurantResponseDto>
}
