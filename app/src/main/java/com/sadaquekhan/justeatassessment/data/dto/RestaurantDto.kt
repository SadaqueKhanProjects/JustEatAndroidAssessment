package com.sadaquekhan.justeatassessment.data.dto

import com.squareup.moshi.JsonClass

/**
 * DTO for a restaurant entry in Just Eat's UK API response.
 *
 * Contains nested DTOs for address, rating, and list of cuisines.
 * Parsed from the network and mapped to a domain model before UI usage.
 *
 * @property id Unique restaurant identifier
 * @property name Name of the restaurant
 * @property cuisines List of cuisine types offered
 * @property rating Restaurant rating details
 * @property address Full address details of the restaurant
 *
 * @see RestaurantResponseDto – contains a list of this DTO.
 */
@JsonClass(generateAdapter = true)
data class RestaurantDto(
    val id: String,
    val name: String,
    val cuisines: List<CuisineDto>,
    val rating: RatingDto,
    val address: AddressDto
)
