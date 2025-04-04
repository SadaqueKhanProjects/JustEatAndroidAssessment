package com.sadaquekhan.justeatassessment.network.api

import com.sadaquekhan.justeatassessment.data.dto.RestaurantResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Retrofit API interface that defines the endpoint to fetch restaurants by postcode.
 */
interface RestaurantApiService {

    /**
     * Retrieves a list of enriched restaurant details from the Just Eat API.
     *
     * @param postcode UK postcode to search restaurants by (URL-safe)
     * @return A Retrofit Response wrapping RestaurantResponseDto
     */
    @GET("discovery/uk/restaurants/enriched/bypostcode/{postcode}")
    suspend fun getRestaurantsByPostcode(
        @Path("postcode") postcode: String
    ): Response<RestaurantResponseDto>
}
