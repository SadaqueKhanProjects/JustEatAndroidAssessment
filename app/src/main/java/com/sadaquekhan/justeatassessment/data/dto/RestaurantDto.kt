package com.sadaquekhan.justeatassessment.data.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RestaurantDto(
    val id: String,
    val name: String,
    val cuisines: List<CuisineDto>,
    val rating: RatingDto,
    val address: AddressDto
)