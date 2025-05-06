package com.sadaquekhan.justeatassessment.data.dto

import com.squareup.moshi.JsonClass

/**
 * DTO for logo image returned in metadata.
 *
 * @property standard URL of the standard-sized logo
 */
@JsonClass(generateAdapter = true)
data class LogoDto(
    val standard: String?
)
