package com.sadaquekhan.justeatassessment.data.dto

import com.squareup.moshi.JsonClass

/**
 * Primary DTO representing a restaurant entry in the API response.
 *
 * @property id Unique identifier
 * @property name Raw restaurant name
 * @property cuisines List of cuisine DTOs
 * @property rating Nested rating DTO (nullable)
 * @property address Nested address DTO
 * @property metadata Optional metadata block (may contain logo)
 */
@JsonClass(generateAdapter = true)
data class RestaurantDto(
    val id: String,
    val name: String,
    val cuisines: List<CuisineDto>,
    val rating: RatingDto?,
    val address: AddressDto,
    val logoUrl: String? = null
)
