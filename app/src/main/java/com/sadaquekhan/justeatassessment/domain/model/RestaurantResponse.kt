package com.sadaquekhan.justeatassessment.domain.model

data class RestaurantResponse(
    val restaurants: List<RestaurantDto>
)

data class RestaurantDto(
    val name: String,
    val rating: Float,
    val cuisineType: String,
    val eta: String
)
