package com.sadaquekhan.justeatassessment.data.dto

import com.squareup.moshi.JsonClass

/**
 * Root DTO for the Just Eat API response.
 *
 * The response wraps a list of restaurant objects under the `restaurants` field.
 * This DTO is used directly by Retrofit to deserialize the full payload.
 *
 * @property restaurants List of raw [RestaurantDto] entries returned by the API
 */
@JsonClass(generateAdapter = true)
data class RestaurantResponseDto(
    val restaurants: List<RestaurantDto>
)
