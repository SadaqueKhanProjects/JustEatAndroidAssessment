package com.sadaquekhan.justeatassessment.data.dto

import com.squareup.moshi.JsonClass

/**
 * DTO representing a single cuisine type offered by a restaurant.
 *
 * Often returned as part of a list of cuisines in the RestaurantDto.
 *
 * @property name The name of the cuisine (e.g., "Indian", "Italian", "Sushi")
 *
 * @see RestaurantDto – contains a list of this DTO.
 */
@JsonClass(generateAdapter = true)
data class CuisineDto(
    val name: String
)
