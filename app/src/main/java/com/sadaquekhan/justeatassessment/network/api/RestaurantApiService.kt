package com.sadaquekhan.justeatassessment.network.api

import com.sadaquekhan.justeatassessment.domain.model.RestaurantResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Retrofit service interface for fetching restaurant data.
 * This service hits the Just Eat UK API to retrieve a list of restaurants
 * based on the user’s postcode.
 */
interface RestaurantApiService {

    /**
     * GET request to fetch restaurant data.
     *
     * @param postcode UK postcode used to filter restaurants.
     * @return RestaurantResponse containing a list of restaurants.
     */
    @GET("restaurants")
    suspend fun getRestaurants(
        @Query("postcode") postcode: String
    ): RestaurantResponse
}