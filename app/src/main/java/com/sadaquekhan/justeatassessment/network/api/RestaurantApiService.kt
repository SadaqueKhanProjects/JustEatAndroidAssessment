package com.sadaquekhan.justeatassessment.network.api

import com.sadaquekhan.justeatassessment.domain.model.RestaurantResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Retrofit interface for communicating with the Just Eat API.
 */
interface RestaurantApiService {

    /**
     * Fetches restaurants by postcode using the GET /restaurants endpoint.
     *
     * @param postcode - The UK postcode used to filter results.
     * @return RestaurantResponse - contains a list of restaurants and related metadata.
     */
    @GET("restaurants")
    suspend fun getRestaurants(
        @Query("postcode") postcode: String
    ): RestaurantResponse
}