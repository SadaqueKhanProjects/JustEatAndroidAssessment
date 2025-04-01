package com.sadaquekhan.justeatassessment.data.remote.dto

import com.squareup.moshi.JsonClass

/**
 * Root DTO for the restaurant API response
 */
@JsonClass(generateAdapter = true)
data class RestaurantResponseDto(
    val restaurants: List<RestaurantDto>
)