package com.sadaquekhan.justeatassessment.domain.model

data class Restaurant(
    val id: String,
    val name: String,
    val cuisines: List<String>,
    val rating: Double,
    val address: Address
) {
    // Combines address parts into a clean formatted string
    val fullAddress: String
        get() = listOfNotNull(
            address.firstLine.takeIf { it.isNotBlank() },
            address.city.takeIf { it.isNotBlank() },
            address.postalCode.takeIf { it.isNotBlank() }
        ).joinToString(", ")
}

data class Address(
    val firstLine: String,
    val city: String,
    val postalCode: String
)