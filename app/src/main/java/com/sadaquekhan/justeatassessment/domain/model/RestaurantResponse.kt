package com.sadaquekhan.justeatassessment.domain.model

data class RestaurantResponse(
    val restaurants: List<RestaurantDto>
)

data class RestaurantDto(
    val id: String,
    val name: String,
    val rating: Float,
    val cuisineType: String,
    val address: String
) {
}
