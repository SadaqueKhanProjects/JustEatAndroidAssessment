package com.sadaquekhan.justeatassessment.network.api

import com.sadaquekhan.justeatassessment.data.dto.RestaurantResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Retrofit API interface to fetch restaurants by postcode
 */
interface RestaurantApiService {

    /**
     * Fetches enriched restaurant data for a given UK postcode.
     *
     * @param postcode The UK postcode (must be URL-safe)
     * @return Response containing a list of restaurants
     */
    @GET("discovery/uk/restaurants/enriched/bypostcode/{postcode}")
    suspend fun getRestaurantsByPostcode(
        @Path("postcode") postcode: String
    ): Response<RestaurantResponseDto>
}