package com.sadaquekhan.justeatassessment.domain.model

/**
 * Domain model representing a restaurant after mapping and sanitization.
 * This model is safe for use in the UI layer.
 *
 * @property id Unique restaurant identifier
 * @property name Cleaned restaurant name
 * @property cuisines List of validated cuisine types
 * @property rating Star rating from customer reviews
 * @property address Formatted Address model
 */
data class Restaurant(
    val id: String,
    val name: String,
    val cuisines: List<String>,
    val rating: Double,
    val address: Address
) {
    /**
     * Combines address fields into a readable single-line address string.
     */
    val fullAddress: String
        get() = listOfNotNull(
            address.firstLine.takeIf { it.isNotBlank() },
            address.city.takeIf { it.isNotBlank() },
            address.postalCode.takeIf { it.isNotBlank() }
        ).joinToString(", ")
}

/**
 * Domain-level representation of a UK address.
 */
data class Address(
    val firstLine: String,
    val city: String,
    val postalCode: String
)
