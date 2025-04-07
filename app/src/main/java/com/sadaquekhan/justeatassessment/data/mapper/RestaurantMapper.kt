package com.sadaquekhan.justeatassessment.data.remote.dto.mapper

import com.sadaquekhan.justeatassessment.data.dto.RestaurantDto
import com.sadaquekhan.justeatassessment.domain.mapper.IRestaurantMapper
import com.sadaquekhan.justeatassessment.domain.model.Address
import com.sadaquekhan.justeatassessment.domain.model.Restaurant
import javax.inject.Inject

/**
 * Converts raw API restaurant data (RestaurantDto) into sanitized domain models (Restaurant).
 * Performs critical data cleaning and formatting including:
 * - Name sanitization (removes social handles, redundant address info)
 * - Cuisine validation (whitelist filter)
 * - Address standardization (UK postcode formatting, component cleaning)
 * - Proper rating nullability handling
 */
class RestaurantMapper @Inject constructor() : IRestaurantMapper {

    // Approved list of cuisine types that will be displayed in the UI
    private val knownCuisines = setOf(
        "Indian", "Chinese", "Japanese", "Thai", "Pizza", "Burgers",
        "Italian", "Kebab", "Greek", "Turkish", "Halal", "Korean",
        "Vietnamese", "Mexican", "Breakfast", "Salads", "American",
        "British", "Spanish", "Caribbean", "Persian", "BBQ", "Vegan",
        "Vegetarian", "French", "Seafood", "Sushi", "Sandwiches"
    )

    override fun mapToDomainModel(dto: RestaurantDto): Restaurant {
        val sanitizedAddress = sanitizeAndFormatAddress(dto)

        return Restaurant(
            id = dto.id,
            name = cleanRestaurantName(dto.name),
            cuisines = filterValidCuisines(dto),
            rating = dto.rating?.starRating,
            address = sanitizedAddress
        )
    }

    /**
     * Cleans restaurant names by removing:
     * - Social media handles (@PizzaPlace)
     * - Text in brackets/parentheses
     */
    private fun cleanRestaurantName(originalName: String): String {
        return originalName
            .replace(Regex("@\\S+"), "") // Remove social handles
            .replace(Regex("\\(.*?\\)"), "") // Remove text in parentheses
            .trim()
    }

    private fun filterValidCuisines(dto: RestaurantDto): List<String> {
        return dto.cuisines
            .map { it.name.trim() }
            .filter { it in knownCuisines }
    }

    private fun sanitizeAndFormatAddress(dto: RestaurantDto): Address {
        // Normalize components
        val rawFirstLine = dto.address.firstLine.trim()
        val rawCity = dto.address.city.trim()
        val rawPostcode = dto.address.postalCode.trim().uppercase()

        // Format postcode to UK standard
        val postcodeFormatted = rawPostcode
            .replace(Regex("\\s+"), "")
            .replace(Regex("([A-Z]{1,2}[0-9][A-Z0-9]?)([0-9][A-Z]{2})"), "$1 $2")

        // Clean first line by removing city/postcode duplicates
        val addressTokens = setOf(
            normalizeAddressComponent(rawCity).lowercase(),
            postcodeFormatted.lowercase()
        )

        val cleanedLine = normalizeAddressComponent(rawFirstLine)
            .split(",", " ")
            .filter { it.isNotBlank() && it.lowercase() !in addressTokens }
            .joinToString(" ") { it.trim().capitalize() }

        return Address(
            firstLine = cleanedLine,
            city = normalizeAddressComponent(rawCity).capitalize(),
            postalCode = postcodeFormatted
        )
    }

    private fun normalizeAddressComponent(raw: String): String {
        return raw
            .replace(Regex("[,\\-–]{2,}"), ",")
            .replace(Regex("\\s{2,}"), " ")
            .replace(Regex(",\\s*,"), ",")
            .replace(Regex("[\\s,]+\$"), "")
            .replace(Regex("^\\s*,*"), "")
            .split(" ")
            .joinToString(" ") { it.lowercase().capitalize() }
            .trim()
    }
}