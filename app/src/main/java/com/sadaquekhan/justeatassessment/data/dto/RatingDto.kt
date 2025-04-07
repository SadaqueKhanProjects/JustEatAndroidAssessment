package com.sadaquekhan.justeatassessment.data.dto

import com.squareup.moshi.JsonClass

/**
 * API rating representation.
 *
 * @property starRating Nullable Double because:
 *                     - Some restaurants may not have ratings
 *                     - 0.0 is a valid poor rating
 *                     - Missing in API = null
 */
@JsonClass(generateAdapter = true)
data class RatingDto(
    val starRating: Double?
)