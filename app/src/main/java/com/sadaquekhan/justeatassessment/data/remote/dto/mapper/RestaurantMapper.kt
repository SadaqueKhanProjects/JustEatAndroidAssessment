package com.sadaquekhan.justeatassessment.data.remote.dto.mapper

import com.sadaquekhan.justeatassessment.data.remote.dto.RestaurantDto
import com.sadaquekhan.justeatassessment.domain.model.Address
import com.sadaquekhan.justeatassessment.domain.model.Restaurant
import javax.inject.Inject

class RestaurantMapper @Inject constructor() {

    // Whitelist of valid cuisine types to display in the UI
    private val knownCuisines = setOf(
        "Indian", "Chinese", "Japanese", "Thai", "Pizza", "Burgers",
        "Italian", "Kebab", "Greek", "Turkish", "Halal", "Korean",
        "Vietnamese", "Mexican", "Breakfast", "Salads", "American",
        "British", "Spanish", "Caribbean", "Persian", "BBQ", "Vegan",
        "Vegetarian", "French", "Seafood", "Sushi", "Sandwiches"
    )

    /**
     * Maps a RestaurantDto to a clean Restaurant domain model.
     */
    fun mapToDomainModel(dto: RestaurantDto): Restaurant {
        val sanitizedAddress = sanitizeAndFormatAddress(dto)

        return Restaurant(
            id = dto.id,
            name = cleanRestaurantName(dto.name, sanitizedAddress),
            cuisines = filterValidCuisines(dto),
            rating = dto.rating.starRating,
            address = sanitizedAddress
        )
    }

    /**
     * Cleans restaurant name by:
     * - Removing @handles, bracketed tags
     * - Removing address fragments from name
     */
    private fun cleanRestaurantName(originalName: String, address: Address): String {
        var name = originalName
            .replace(Regex("@\\S+"), "")           // Remove handles like "@BensCafe"
            .replace(Regex("\\(.*?\\)"), "")       // Remove anything in brackets
            .trim()

        // Tokenize address to filter from name
        val addressTokens = listOf(
            address.firstLine,
            address.city,
            address.postalCode
        ).flatMap { it.split(" ", ",") }
            .map { it.lowercase().trim() }
            .filter { it.isNotBlank() }

        // Remove address-like fragments from restaurant name
        name = name.split(" ")
            .filterNot { it.lowercase() in addressTokens }
            .joinToString(" ")

        return name.trim()
    }

    /**
     * Filters and returns only known cuisine types.
     * Returns empty list if none match the whitelist.
     */
    private fun filterValidCuisines(dto: RestaurantDto): List<String> {
        return dto.cuisines
            .map { it.name.trim() }
            .filter { it in knownCuisines }
    }

    /**
     * Sanitizes the address fields:
     * - Capitalizes first letter of line and city
     * - Converts postal code to uppercase
     */
    private fun sanitizeAndFormatAddress(dto: RestaurantDto): Address {
        return Address(
            firstLine = dto.address.firstLine.trim().replaceFirstChar { it.uppercaseChar() },
            city = dto.address.city.trim().replaceFirstChar { it.uppercaseChar() },
            postalCode = dto.address.postalCode.trim().uppercase()
        )
    }
}