package com.sadaquekhan.justeatassessment.data.mapper

import com.sadaquekhan.justeatassessment.data.dto.RestaurantDto
import com.sadaquekhan.justeatassessment.domain.model.Address
import com.sadaquekhan.justeatassessment.domain.model.Restaurant
import javax.inject.Inject

/**
 * Responsible for mapping `RestaurantDto` into domain-level `Restaurant` models.
 * Ensures the app uses clean, sanitized, user-friendly data structures.
 *
 * Includes:
 * - Name cleanup
 * - Cuisine filtering
 * - UK address normalization
 */
class RestaurantMapper @Inject constructor() {

    // Whitelist of supported cuisines shown in the UI
    private val knownCuisines = setOf(
        "Indian", "Chinese", "Japanese", "Thai", "Pizza", "Burgers",
        "Italian", "Kebab", "Greek", "Turkish", "Halal", "Korean",
        "Vietnamese", "Mexican", "Breakfast", "Salads", "American",
        "British", "Spanish", "Caribbean", "Persian", "BBQ", "Vegan",
        "Vegetarian", "French", "Seafood", "Sushi", "Sandwiches"
    )

    /**
     * Maps a raw RestaurantDto to a clean Restaurant domain model.
     *
     * @param dto API-level restaurant data
     * @return Domain-level Restaurant model
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
     * Removes social handles, address fragments, and bracket tags from restaurant names.
     * @param originalName The raw name from the API
     * @param address The already cleaned address for reference
     */
    private fun cleanRestaurantName(originalName: String, address: Address): String {
        var name = originalName
            .replace(Regex("@\\S+"), "")             // Remove social handles
            .replace(Regex("\\(.*?\\)"), "")         // Remove tags in brackets
            .trim()

        val addressTokens = listOf(address.firstLine, address.city, address.postalCode)
            .flatMap { it.split(" ", ",", "-", "–") }
            .map { it.lowercase().trim() }
            .filter { it.isNotBlank() }

        name = name.split(" ", "-", "–", ",")
            .filterNot { it.lowercase() in addressTokens }
            .joinToString(" ")

        return name
            .replace(Regex("[-–,]+\\s*$"), "")
            .replace(Regex("^\\s*[-–,]+"), "")
            .replace(Regex("\\s{2,}"), " ")
            .trim()
    }

    /**
     * Filters only valid cuisine names based on a known whitelist.
     * @param dto RestaurantDto containing raw cuisine names
     */
    private fun filterValidCuisines(dto: RestaurantDto): List<String> {
        return dto.cuisines
            .map { it.name.trim() }
            .filter { it in knownCuisines }
    }

    /**
     * Sanitizes address fields according to UK formatting conventions.
     * Ensures sentence casing, trimmed spacing, and formatted postcode.
     */
    private fun sanitizeAndFormatAddress(dto: RestaurantDto): Address {
        val rawFirstLine = dto.address.firstLine.trim()
        val rawCity = dto.address.city.trim()
        val rawPostcode = dto.address.postalCode.trim().uppercase()

        val lineNormalized = normalizeAddressComponent(rawFirstLine)
        val cityNormalized = normalizeAddressComponent(rawCity)

        val postcodeFormatted = rawPostcode
            .replace(Regex("\\s+"), "")
            .replace(Regex("([A-Z]{1,2}[0-9][A-Z0-9]?)\\s*([0-9][A-Z]{2})"), "$1 $2")

        val addressTokens = setOf(cityNormalized.lowercase(), postcodeFormatted.lowercase())

        val cleanedLine = lineNormalized
            .split(",", " ")
            .filter { it.isNotBlank() && it.lowercase() !in addressTokens }
            .joinToString(" ") { it.trim().replaceFirstChar { c -> c.uppercaseChar() } }

        return Address(
            firstLine = cleanedLine,
            city = cityNormalized.replaceFirstChar { it.uppercaseChar() },
            postalCode = postcodeFormatted
        )
    }

    /**
     * Normalizes casing, whitespace, and symbols for address components.
     * @param raw Raw component (firstLine or city)
     */
    private fun normalizeAddressComponent(raw: String): String {
        return raw
            .replace(Regex("[,\\-–]{2,}"), ",")
            .replace(Regex("\\s{2,}"), " ")
            .replace(Regex(",\\s*,"), ",")
            .replace(Regex("[\\s,]+\$"), "")
            .replace(Regex("^\\s*,*"), "")
            .split(" ")
            .joinToString(" ") { it.lowercase().replaceFirstChar { c -> c.uppercaseChar() } }
            .trim()
    }
}
