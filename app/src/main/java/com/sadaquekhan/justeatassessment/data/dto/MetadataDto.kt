package com.sadaquekhan.justeatassessment.data.dto

import com.squareup.moshi.JsonClass

/**
 * DTO for metadata block containing nested logo data.
 *
 * @property logo The logo object, which may be null
 */
@JsonClass(generateAdapter = true)
data class MetadataDto(
    val logo: LogoDto?
)
