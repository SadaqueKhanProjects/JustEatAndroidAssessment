package com.sadaquekhan.justeatassessment.data.dto

import com.squareup.moshi.JsonClass

/**
 * Primary DTO representing a restaurant entry in the API response.
 *
 * This model contains only the subset of fields required for the assignment:
 * name, cuisines, rating, and address. It acts as a raw data model used in
 * the data layer before conversion to domain models for display.
 *
 * @property id Unique restaurant identifier (e.g., "10234")
 * @property name Restaurant name as returned from the API (may require sanitation)
 * @property cuisines List of cuisine types offered by the restaurant
 * @property rating Nullable rating information containing the star rating
 * @property address Nested object containing the restaurant’s full address
 */
@JsonClass(generateAdapter = true)
data class RestaurantDto(
    val id: String,
    val name: String,
    val cuisines: List<CuisineDto>,
    val rating: RatingDto?,
    val address: AddressDto
)
