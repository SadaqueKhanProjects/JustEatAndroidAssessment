package com.sadaquekhan.justeatassessment.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RestaurantDto(
    @Json(name = "Name") val name: String,
    @Json(name = "RatingStars") val rating: Double,
    @Json(name = "Address") val address: AddressDto,
    @Json(name = "CuisineTypes") val cuisineTypes: List<CuisineDto>
)