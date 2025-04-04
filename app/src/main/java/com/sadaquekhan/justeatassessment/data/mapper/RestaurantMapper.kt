package com.sadaquekhan.justeatassessment.data.mapper

import com.sadaquekhan.justeatassessment.data.dto.RestaurantDto
import com.sadaquekhan.justeatassessment.domain.model.Address
import com.sadaquekhan.justeatassessment.domain.model.Restaurant
import javax.inject.Inject

class RestaurantMapper @Inject constructor() {

    // ✅ Whitelist of valid cuisine types allowed to display in the UI
    private val knownCuisines = setOf(
        "Indian", "Chinese", "Japanese", "Thai", "Pizza", "Burgers",
        "Italian", "Kebab", "Greek", "Turkish", "Halal", "Korean",
        "Vietnamese", "Mexican", "Breakfast", "Salads", "American",
        "British", "Spanish", "Caribbean", "Persian", "BBQ", "Vegan",
        "Vegetarian", "French", "Seafood", "Sushi", "Sandwiches"
    )

    /**
     * Converts a RestaurantDto into a sanitized domain Restaurant model.
     * This ensures UI content is clean, user-friendly, and consistent.
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
     * Cleans the restaurant name for clean display:
     * - Removes handles (@PizzaPlace), bracketed tags (e.g., (Halal))
     * - Removes address fragments (e.g., city/postcode) found in name
     * - Removes leftover punctuation and dash artifacts after filtering
     */
    private fun cleanRestaurantName(originalName: String, address: Address): String {
        var name = originalName
            .replace(Regex("@\\S+"), "")             // Remove social handles
            .replace(Regex("\\(.*?\\)"), "")         // Remove anything in brackets
            .trim()

        // Tokenize and clean address components
        val addressTokens = listOf(
            address.firstLine,
            address.city,
            address.postalCode
        ).flatMap { it.split(" ", ",", "-", "–") } // Also split on hyphens
            .map { it.lowercase().trim() }
            .filter { it.isNotBlank() }

        // Remove tokens from name that are part of the address
        name = name.split(" ", "-", "–", ",") // Split on typical separators
            .filterNot { it.lowercase() in addressTokens }
            .joinToString(" ")

        // Remove dangling punctuation like trailing hyphens or commas
        name = name
            .replace(Regex("[-–,]+\\s*$"), "")    // Trailing punctuation
            .replace(Regex("^\\s*[-–,]+"), "")    // Leading punctuation
            .replace(Regex("\\s{2,}"), " ")       // Extra spaces
            .trim()

        return name
    }

    /**
     * Filters only known/valid cuisine types from the API response.
     * Returns an empty list if none are valid.
     */
    private fun filterValidCuisines(dto: RestaurantDto): List<String> {
        return dto.cuisines
            .map { it.name.trim() }
            .filter { it in knownCuisines }
    }

    /**
     * Sanitizes address fields according to UK standards:
     * - Proper casing (e.g., Kilburn High Road)
     * - Normalized whitespace and punctuation
     * - Proper postcode format (e.g., SW1A 1AA)
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
     * Normalizes casing, whitespace, and symbols within a given address string.
     * Ensures proper sentence-style formatting for both city and firstLine.
     */
    private fun normalizeAddressComponent(raw: String): String {
        return raw
            .replace(Regex("[,\\-–]{2,}"), ",")       // Normalize repeated commas/dashes
            .replace(Regex("\\s{2,}"), " ")           // Collapse multiple spaces
            .replace(Regex(",\\s*,"), ",")            // Fix duplicate commas
            .replace(Regex("[\\s,]+\$"), "")          // Remove trailing comma/space
            .replace(Regex("^\\s*,*"), "")            // Remove leading comma/space
            .split(" ")
            .joinToString(" ") { it.lowercase().replaceFirstChar { c -> c.uppercaseChar() } }
            .trim()
    }
}