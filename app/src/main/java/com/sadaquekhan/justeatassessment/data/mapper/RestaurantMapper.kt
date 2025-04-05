package com.sadaquekhan.justeatassessment.data.remote.dto.mapper

import com.sadaquekhan.justeatassessment.data.dto.RestaurantDto
import com.sadaquekhan.justeatassessment.domain.model.Address
import com.sadaquekhan.justeatassessment.domain.model.Restaurant
import javax.inject.Inject

/**
 * Responsible for mapping RestaurantDto (from API) into Restaurant domain models (for UI).
 * Also performs sanitation and formatting of names, cuisines, and addresses.
 */
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
     * Ensures only valid and properly formatted content reaches the UI.
     *
     * @param dto Raw restaurant data from API
     * @return Restaurant model safe for presentation layer
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

        val addressTokens = listOf(
            address.firstLine,
            address.city,
            address.postalCode
        ).flatMap { it.split(" ", ",", "-", "–") }
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
     * Filters only known/valid cuisine types from the API response.
     *
     * @return A list of clean cuisine names safe to show in the UI
     */
    private fun filterValidCuisines(dto: RestaurantDto): List<String> {
        return dto.cuisines
            .map { it.name.trim() }
            .filter { it in knownCuisines }
    }

    /**
     * Sanitizes and formats the address fields:
     * - Proper casing and trimming
     * - Formats postcode to UK standard (e.g., "W1D4FA" → "W1D 4FA")
     * - Removes city/postcode repetition from first line
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
     * Normalizes casing, whitespace, and symbols within address strings.
     * Example: "  kilburn   high   ROAD ,,, " → "Kilburn High Road"
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
