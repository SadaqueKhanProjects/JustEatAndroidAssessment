package com.sadaquekhan.justeatassessment.data.dto

import com.squareup.moshi.JsonClass

/**
 * DTO for the 'address' block in Just Eat's UK API response.
 *
 * Used strictly for network parsing and mapped to a domain model before business/UI use.
 * Moshi's `@JsonClass` enables efficient, safe deserialization.
 *
 * @property firstLine Building number and street (e.g., "123 High Street")
 * @property city City of the restaurant (e.g., "London")
 * @property postalCode UK postcode (e.g., "SW1A 1AA")
 *
 * @see RestaurantDto – contains this as part of its structure.
 */
@JsonClass(generateAdapter = true)
data class AddressDto(
    val firstLine: String,
    val city: String,
    val postalCode: String
)