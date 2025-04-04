package com.sadaquekhan.justeatassessment.data.dto

import com.squareup.moshi.JsonClass

/**
 * DTO for the 'rating' block in Just Eat's UK API response.
 *
 * Used strictly for network parsing and mapped to a domain model before business/UI use.
 * Moshi's `@JsonClass` enables efficient, safe deserialization.
 *
 * @property starRating Numeric star rating of the restaurant (e.g., 4.5)
 *
 * @see RestaurantDto – contains this as a nested object.
 */
@JsonClass(generateAdapter = true)
data class RatingDto(
    val starRating: Double
)
