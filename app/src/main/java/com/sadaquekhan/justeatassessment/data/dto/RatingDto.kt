package com.sadaquekhan.justeatassessment.data.dto

import com.squareup.moshi.JsonClass

/**
 * DTO representing the rating details of a restaurant.
 *
 * Parsed from the API and passed into domain models/UI for displaying customer satisfaction.
 *
 * @property starRating Average customer star rating (e.g., 4.5)
 *
 * @see RestaurantDto – uses this DTO to show restaurant ratings.
 */
@JsonClass(generateAdapter = true)
data class RatingDto(
    val starRating: Double
)
