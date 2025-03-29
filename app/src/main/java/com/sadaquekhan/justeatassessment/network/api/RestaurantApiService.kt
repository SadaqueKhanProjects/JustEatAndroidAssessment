package com.sadaquekhan.justeatassessment.network.api

import com.sadaquekhan.justeatassessment.domain.model.RestaurantResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RestaurantApiService {
    @GET("restaurants")
    suspend fun getRestaurants(@Query("postcode") postcode: String): RestaurantResponse
}
