package com.justorder.app.network.api

import retrofit2.http.GET
import retrofit2.http.Query

interface RestaurantApiService {
    @GET("restaurants")
    suspend fun getRestaurants(@Query("postcode") postcode: String): RestaurantResponse
}
