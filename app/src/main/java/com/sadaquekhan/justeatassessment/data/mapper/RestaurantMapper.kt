package com.sadaquekhan.justeatassessment.data.remote.dto.mapper

import com.sadaquekhan.justeatassessment.data.dto.RestaurantDto
import com.sadaquekhan.justeatassessment.domain.mapper.IRestaurantMapper
import com.sadaquekhan.justeatassessment.domain.model.Address
import com.sadaquekhan.justeatassessment.domain.model.Restaurant
import javax.inject.Inject

/**
 * Concrete implementation of IRestaurantMapper.
 * Responsible for mapping and sanitizing restaurant data for domain usage.
 */
class RestaurantMapper @Inject constructor() : IRestaurantMapper {

    // Whitelist of valid cuisines allowed to display in the UI
    private val knownCuisines = setOf(
        "Indian", "Chinese", "Japanese", "Thai", "Pizza", "Burgers",
        "Italian", "Kebab", "Greek", "Turkish", "Halal", "Korean",
        "Vietnamese", "Mexican", "Breakfast", "Salads", "American",
        "British", "Spanish", "Caribbean", "Persian", "BBQ", "Vegan",
        "Vegetarian", "French", "Seafood", "Sushi", "Sandwiches"
    )

    /**
     * Maps a RestaurantDto to a domain Restaurant, performing sanitation.
     */
    override fun mapToDomainModel(dto: RestaurantDto): Restaurant {
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
     * Sanitizes the restaurant name by removing social handles, bracketed labels,
     * and tokens that are also found in the address. Falls back to a basic-cleaned
     * name if all tokens are stripped out.
     */
    private fun cleanRestaurantName(originalName: String, address: Address): String {
        // Step 1: Strip social handles and bracketed text
        val cleaned = originalName
            .replace(Regex("@\\S+"), "")         // Remove handles like @PizzaPlace
            .replace(Regex("\\(.*?\\)"), "")     // Remove anything in brackets
            .trim()

        // Step 2: Generate a lowercase set of address-related tokens
        val addressTokens = listOf(address.firstLine, address.city, address.postalCode)
            .flatMap { it.split(" ", ",", "-", "–") }
            .map { it.lowercase().trim() }
            .filter { it.isNotBlank() }
            .toSet()

        // Step 3: Tokenize cleaned name and remove anything matching address tokens
        val tokens = cleaned
            .split(" ", "-", "–", ",")
            .map { it.trim() }
            .filter { it.isNotBlank() }

        val filtered = tokens.filter { it.lowercase() !in addressTokens }

        // Step 4: Fallback to first token that actually contains letters (to avoid postcode fallback)
        val result = if (filtered.isNotEmpty()) {
            filtered.joinToString(" ")
        } else {
            tokens.firstOrNull { it.any(Char::isLetter) } ?: cleaned
        }

        // Step 5: Final cleanup of leading/trailing punctuation and spacing
        return result
            .replace(Regex("[-–,]+\\s*$"), "")
            .replace(Regex("^\\s*[-–,]+"), "")
            .replace(Regex("\\s{2,}"), " ")
            .trim()
    }
    /**
     * Filters valid cuisine names against the whitelist.
     */
    private fun filterValidCuisines(dto: RestaurantDto): List<String> {
        return dto.cuisines
            .map { it.name.trim() }
            .filter { it in knownCuisines }
    }

    /**
     * Sanitizes and formats the address components.
     */
    private fun sanitizeAndFormatAddress(dto: RestaurantDto): Address {
        // Step 1: Normalize input components
        val rawFirstLine = dto.address.firstLine.trim()
        val rawCity = dto.address.city.trim()
        val rawPostcode = dto.address.postalCode.trim().uppercase()

        // Step 2: Clean up casing and characters
        val lineNormalized = normalizeAddressComponent(rawFirstLine)
        val cityNormalized = normalizeAddressComponent(rawCity)

        // Step 3: Format the postcode to UK standard (e.g., EC1A1BB → EC1A 1BB)
        val postcodeFormatted = rawPostcode
            .replace(Regex("\\s+"), "")
            .replace(Regex("([A-Z]{1,2}[0-9][A-Z0-9]?)\\s*([0-9][A-Z]{2})"), "$1 $2")

        // Step 4: Remove city/postcode tokens from the first line
        val addressTokens = setOf(cityNormalized.lowercase(), postcodeFormatted.lowercase())

        val cleanedLine = lineNormalized
            .split(",", " ")
            .filter { it.isNotBlank() && it.lowercase() !in addressTokens }
            .joinToString(" ") { it.trim().replaceFirstChar { c -> c.uppercaseChar() } }

        // Step 5: Return Address model
        return Address(
            firstLine = cleanedLine,
            city = cityNormalized.replaceFirstChar { it.uppercaseChar() },
            postalCode = postcodeFormatted
        )
    }

    /**
     * Standardizes the formatting of address fields.
     */
    private fun normalizeAddressComponent(raw: String): String {
        return raw
            .replace(Regex("[,\\-–]{2,}"), ",") // Collapse multiple dashes/commas
            .replace(Regex("\\s{2,}"), " ")    // Remove extra spaces
            .replace(Regex(",\\s*,"), ",")     // Fix double commas
            .replace(Regex("[\\s,]+\$"), "")   // Remove trailing punctuation
            .replace(Regex("^\\s*,*"), "")     // Remove leading commas
            .split(" ")
            .joinToString(" ") {
                it.lowercase().replaceFirstChar { c -> c.uppercaseChar() }
            }
            .trim()
    }
}