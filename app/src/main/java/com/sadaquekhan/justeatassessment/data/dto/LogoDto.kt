package com.sadaquekhan.justeatassessment.data.dto

import com.squareup.moshi.JsonClass

/**
 * DTO representing the logo object inside metadata.
 *
 * @property standard Optional URL for the standard resolution logo
 */
@JsonClass(generateAdapter = true)
data class LogoDto(
    val standard: String?
)
