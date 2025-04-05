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

    private fun cleanRestaurantName(originalName: String, address: Address): String {
        var name = originalName
            .replace(Regex("@\\S+"), "")
            .replace(Regex("\\(.*?\\)"), "")
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

    private fun filterValidCuisines(dto: RestaurantDto): List<String> {
        return dto.cuisines.map { it.name.trim() }.filter { it in knownCuisines }
    }

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