package com.sadaquekhan.justeatassessment.domain.model

/**
 * Domain model representing a sanitized restaurant entity.
 * Used in UI and business logic layers.
 *
 * @property id Unique restaurant identifier
 * @property name Display-ready restaurant name
 * @property cuisines Valid cuisine types (filtered)
 * @property rating Nullable numeric rating:
 *                  - null: no rating
 *                  - 0.0: lowest possible
 *                  - > 0.0: valid customer rating
 * @property address Cleaned UK-style address object
 */
data class Restaurant(
    val id: String,
    val name: String,
    val cuisines: List<String>,
    val rating: Double?,
    val address: Address
) {
    /**
     * Combines address components into one formatted string.
     * Useful for display in UI (e.g., "45 Oxford St, London, W1D 2DZ")
     */
    val fullAddress: String
        get() = listOfNotNull(
            address.firstLine.takeIf { it.isNotBlank() },
            address.city.takeIf { it.isNotBlank() },
            address.postalCode.takeIf { it.isNotBlank() }
        ).joinToString(", ")
}

/**
 * Represents a UK-style postal address for a restaurant.
 *
 * @property firstLine Street-level identifier (e.g., "Unit 4, Baker Street")
 * @property city City of operation (e.g., "London")
 * @property postalCode UK postcode formatted to standard (e.g., "W1U 6RS")
 */
data class Address(
    val firstLine: String,
    val city: String,
    val postalCode: String
)
