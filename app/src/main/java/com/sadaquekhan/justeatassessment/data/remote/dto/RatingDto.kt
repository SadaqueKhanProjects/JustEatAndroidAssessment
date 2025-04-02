package com.sadaquekhan.justeatassessment.data.remote.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RatingDto(
    val starRating: Double
)