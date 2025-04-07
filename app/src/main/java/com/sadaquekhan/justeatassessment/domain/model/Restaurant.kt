package com.sadaquekhan.justeatassessment.domain.model

/**
 * Domain model representing a fully sanitized restaurant ready for UI display.
 *
 * @property id Unique identifier for the restaurant
 * @property name Cleaned and formatted restaurant name
 * @property cuisines Filtered list of valid cuisine types
 * @property rating Nullable Double where:
 *                 - null = No rating available
 *                 - 0.0 = Actual zero-star rating
 *                 - >0.0 = Positive star rating
 * @property address Formatted and standardized address components
 */
data class Restaurant(
    val id: String,
    val name: String,
    val cuisines: List<String>,
    val rating: Double?,
    val address: Address
) {
    /**
     * Formats address components into a single readable string.
     * Skips blank components and joins with commas.
     * Example: "123 Main St, London, EC1A 1BB"
     */
    val fullAddress: String
        get() = listOfNotNull(
            address.firstLine.takeIf { it.isNotBlank() },
            address.city.takeIf { it.isNotBlank() },
            address.postalCode.takeIf { it.isNotBlank() }
        ).joinToString(", ")
}

/**
 * Standardized address representation.
 * @property firstLine Street address (e.g., "123 Main St")
 * @property city City name (e.g., "London")
 * @property postalCode Formatted UK postcode (e.g., "EC1A 1BB")
 */
data class Address(
    val firstLine: String,
    val city: String,
    val postalCode: String
)