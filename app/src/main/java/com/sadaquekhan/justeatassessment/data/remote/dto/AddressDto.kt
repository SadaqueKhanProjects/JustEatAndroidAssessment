package com.sadaquekhan.justeatassessment.data.remote.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AddressDto(
    val firstLine: String,
    val city: String,
    val postalCode: String
)