package com.sadaquekhan.justeatassessment.data.dto

import com.squareup.moshi.JsonClass

/**
 * DTO for rating information returned by the Just Eat API.
 *
 * Not all restaurants are rated, so `starRating` is nullable.
 * Only the average numeric rating is captured in this DTO.
 *
 * @property starRating The average rating (e.g., 4.2) or null if not available
 */
@JsonClass(generateAdapter = true)
data class RatingDto(
    val starRating: Double?
)
