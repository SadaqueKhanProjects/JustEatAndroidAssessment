// RestaurantDto.kt
package com.sadaquekhan.justeatassessment.network.dto


import com.squareup.moshi.Json

data class RestaurantDto(
    @Json(name = "Id") val id: String?,
    @Json(name = "Name") val name: String?,
    @Json(name = "RatingStars") val ratingStars: Double?,
    @Json(name = "CuisineTypes") val cuisineTypes: List<CuisineDto>?,
    @Json(name = "Address") val address: AddressDto?
)

data class CuisineDto(
    @Json(name = "Name") val name: String?
)

data class AddressDto(
    @Json(name = "FirstLine") val firstLine: String?
)