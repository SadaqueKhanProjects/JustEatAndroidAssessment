package com.sadaquekhan.justeatassessment.data.dto

import com.squareup.moshi.JsonClass

/**
 * DTO for a single cuisine type in Just Eat's UK API response.
 *
 * Used strictly for network parsing and mapped to a domain model before business/UI use.
 * Moshi's `@JsonClass` enables efficient, safe deserialization.
 *
 * @property name Name of the cuisine (e.g., "Italian", "Chinese", "Indian")
 *
 * @see RestaurantDto – contains a list of this DTO as part of its structure.
 */
@JsonClass(generateAdapter = true)
data class CuisineDto(
    val name: String
)
