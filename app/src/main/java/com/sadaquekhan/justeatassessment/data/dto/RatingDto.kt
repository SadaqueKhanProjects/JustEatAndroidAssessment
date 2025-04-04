package com.sadaquekhan.justeatassessment.data.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RatingDto(
    val starRating: Double
)