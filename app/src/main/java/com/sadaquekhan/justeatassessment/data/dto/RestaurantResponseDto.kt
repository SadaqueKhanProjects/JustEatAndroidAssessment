package com.sadaquekhan.justeatassessment.data.dto

import com.squareup.moshi.JsonClass

/**
 * Root DTO for the Just Eat API response.
 *
 * This class wraps the top-level JSON response, which contains
 * a list of restaurant entries. It's used by Retrofit to deserialize
 * the full response payload.
 *
 * @property restaurants The list of restaurants returned for a given postcode search
 *
 * @see RestaurantDto – each item in the list represents one restaurant.
 */
@JsonClass(generateAdapter = true)
data class RestaurantResponseDto(
    val restaurants: List<RestaurantDto>
)
