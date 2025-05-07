package com.sadaquekhan.justeatassessment.data.remote.dto.mapper

import android.util.Log
import com.sadaquekhan.justeatassessment.data.dto.RestaurantDto
import com.sadaquekhan.justeatassessment.domain.mapper.IRestaurantMapper
import com.sadaquekhan.justeatassessment.domain.model.Address
import com.sadaquekhan.justeatassessment.domain.model.Restaurant
import javax.inject.Inject

/**
 * Maps [RestaurantDto] to a clean [Restaurant] domain model.
 *
 * Performs all key sanitization and validation for presentation logic:
 * - Removes unwanted characters or social handles from names
 * - Filters only whitelisted cuisines
 * - Normalizes and formats address components to UK standards
 *
 * This logic helps keep the domain layer consistent and UI-ready.
 */
class RestaurantMapper @Inject constructor() : IRestaurantMapper {

    private val knownCuisines = setOf(
        "Indian", "Chinese", "Japanese", "Thai", "Pizza", "Burgers",
        "Italian", "Kebab", "Greek", "Turkish", "Halal", "Korean",
        "Vietnamese", "Mexican", "Breakfast", "Salads", "American",
        "British", "Spanish", "Caribbean", "Persian", "BBQ", "Vegan",
        "Vegetarian", "French", "Seafood", "Sushi", "Sandwiches"
    )

    override fun mapToDomainModel(dto: RestaurantDto): Restaurant {
        val sanitizedAddress = sanitizeAndFormatAddress(dto)
        val logoUrl = dto.logoUrl

        Log.d("RestaurantMapper", "Mapping logo URL: $logoUrl") // 🔍 LOG LINE

        return Restaurant(
            id = dto.id,
            name = cleanRestaurantName(dto.name),
            cuisines = filterValidCuisines(dto),
            rating = dto.rating?.starRating,
            address = sanitizedAddress,
            logoUrl = dto.logoUrl
        )
    }

    private fun cleanRestaurantName(originalName: String): String {
        return originalName
            .replace(Regex("@\\S+"), "")
            .replace(Regex("\\(.*?\\)"), "")
            .trim()
    }

    private fun filterValidCuisines(dto: RestaurantDto): List<String> {
        return dto.cuisines
            .map { it.name.trim() }
            .filter { it in knownCuisines }
    }

    private fun sanitizeAndFormatAddress(dto: RestaurantDto): Address {
        val rawFirstLine = dto.address.firstLine.trim()
        val rawCity = dto.address.city.trim()
        val rawPostcode = dto.address.postalCode.trim().uppercase()

        val postcodeFormatted = rawPostcode
            .replace(Regex("\\s+"), "")
            .replace(Regex("([A-Z]{1,2}[0-9][A-Z0-9]?)([0-9][A-Z]{2})"), "$1 $2")

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
            .joinToString(" ") { it.lowercase().replaceFirstChar(Char::uppercaseChar) }
            .trim()
    }
}
