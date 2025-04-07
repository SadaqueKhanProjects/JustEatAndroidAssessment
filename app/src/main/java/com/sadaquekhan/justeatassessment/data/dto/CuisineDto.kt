package com.sadaquekhan.justeatassessment.data.dto

import com.squareup.moshi.JsonClass

/**
 * DTO representing a single cuisine type offered by a restaurant.
 *
 * Used within the `cuisines` list in [RestaurantDto]. Each entry
 * describes one cuisine category like "Sushi", "Pizza", or "Halal".
 *
 * @property name The name of the cuisine (e.g., "Indian", "Italian", "Vegan")
 */
@JsonClass(generateAdapter = true)
data class CuisineDto(
    val name: String
)
