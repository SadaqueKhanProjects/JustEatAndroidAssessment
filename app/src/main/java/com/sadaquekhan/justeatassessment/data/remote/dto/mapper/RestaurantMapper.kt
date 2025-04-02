package com.sadaquekhan.justeatassessment.data.remote.dto.mapper

import com.sadaquekhan.justeatassessment.data.remote.dto.RestaurantDto
import com.sadaquekhan.justeatassessment.domain.model.Address
import com.sadaquekhan.justeatassessment.domain.model.Restaurant
import javax.inject.Inject

class RestaurantMapper @Inject constructor() {

    // Whitelist of valid cuisine types
    private val knownCuisines = setOf(
        "Indian", "Chinese", "Japanese", "Thai", "Pizza", "Burgers",
        "Italian", "Kebab", "Greek", "Turkish", "Halal", "Korean",
        "Vietnamese", "Mexican", "Breakfast", "Salads", "American",
        "British", "Spanish", "Caribbean", "Persian", "BBQ", "Vegan",
        "Vegetarian", "French", "Seafood", "Sushi", "Sandwiches"
    )

    fun toDomain(dto: RestaurantDto): Restaurant {
        // Filter and sanitize cuisines
        val filteredCuisines = dto.cuisines.map { it.name.trim() }.filter {
            knownCuisines.contains(it)
        }

        val finalCuisines = if (filteredCuisines.isNotEmpty()) {
            filteredCuisines
        } else {
            dto.cuisines.map { it.name.trim() }
        }

        // Sanitize address fields
        val firstLineSanitized = capitalizeWords(dto.address.firstLine.trim())
        val citySanitized = capitalizeWords(dto.address.city.trim())
        val postalCodeSanitized = dto.address.postalCode.trim().uppercase()

        // Remove repeated fields (e.g., "London, London")
        val uniqueAddressParts = linkedSetOf(firstLineSanitized, citySanitized, postalCodeSanitized)
            .filter { it.isNotBlank() }

        return Restaurant(
            id = dto.id,
            name = dto.name,
            cuisines = finalCuisines,
            rating = dto.rating.starRating,
            address = Address(
                firstLine = firstLineSanitized,
                city = citySanitized,
                postalCode = postalCodeSanitized
            )
        )
    }

    // Capitalizes the first letter of each word: "london road" -> "London Road"
    private fun capitalizeWords(input: String): String {
        return input.lowercase()
            .split(" ")
            .filter { it.isNotBlank() }
            .joinToString(" ") { word ->
                word.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
            }
    }
}