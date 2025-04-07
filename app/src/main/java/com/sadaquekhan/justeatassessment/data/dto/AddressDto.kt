package com.sadaquekhan.justeatassessment.data.dto

import com.squareup.moshi.JsonClass

/**
 * Data Transfer Object (DTO) representing the address of a restaurant.
 *
 * This model is nested inside the restaurant object returned by the Just Eat API,
 * and includes key address fields used for display and sanitation.
 *
 * @property firstLine Street-level address or building identifier (e.g., "45 Oxford Street")
 * @property city The city where the restaurant is located (e.g., "London")
 * @property postalCode Standard UK postal code (e.g., "W1D 2DZ")
 */
@JsonClass(generateAdapter = true)
data class AddressDto(
    val firstLine: String,
    val city: String,
    val postalCode: String
)
