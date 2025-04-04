package com.sadaquekhan.justeatassessment.data.dto

import com.squareup.moshi.JsonClass

/**
 * Root DTO for Just Eat's UK API response.
 *
 * The API returns a list of restaurants, each represented by a `RestaurantDto`.
 * This is the top-level structure returned by the backend.
 *
 * @property restaurants List of restaurant entries
 *
 * @see RestaurantDto – nested structure representing each restaurant.
 */
@JsonClass(generateAdapter = true)
data class RestaurantResponseDto(
    val restaurants: List<RestaurantDto>
)
