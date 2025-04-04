package com.sadaquekhan.justeatassessment.domain.model

/**
 * Domain model representing a restaurant, used by the business and UI layers.
 *
 * This is mapped from RestaurantDto and sanitized before reaching the UI.
 *
 * @property id Unique identifier of the restaurant
 * @property name Cleaned name used for display
 * @property cuisines Filtered list of valid cuisine types
 * @property rating Numeric rating score (e.g., 4.2)
 * @property address Full address (first line, city, postcode)
 */
data class Restaurant(
    val id: String,
    val name: String,
    val cuisines: List<String>,
    val rating: Double,
    val address: Address
) {
    /**
     * Combines address fields into a single displayable string.
     * Used directly in the UI for presenting restaurant location.
     */
    val fullAddress: String
        get() = listOfNotNull(
            address.firstLine.takeIf { it.isNotBlank() },
            address.city.takeIf { it.isNotBlank() },
            address.postalCode.takeIf { it.isNotBlank() }
        ).joinToString(", ")
}

/**
 * Domain model for representing a physical address.
 *
 * Used inside the Restaurant model.
 *
 * @property firstLine Street or building name
 * @property city City portion of the address
 * @property postalCode Standard UK postcode
 */
data class Address(
    val firstLine: String,
    val city: String,
    val postalCode: String
)
