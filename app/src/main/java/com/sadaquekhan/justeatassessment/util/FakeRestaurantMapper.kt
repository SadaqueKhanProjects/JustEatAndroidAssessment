package com.sadaquekhan.justeatassessment.util

import com.sadaquekhan.justeatassessment.data.dto.RestaurantDto
import com.sadaquekhan.justeatassessment.domain.model.Address
import com.sadaquekhan.justeatassessment.domain.model.Restaurant
import com.sadaquekhan.justeatassessment.domain.mapper.IRestaurantMapper

/**
 * Fake implementation of [IRestaurantMapper] for unit testing.
 * Features:
 * - Bypasses real sanitization logic
 * - Provides predictable outputs
 * - Handles null ratings safely
 * - Maintains consistent postcode formatting
 *
 * Usage: Inject this instead of real mapper to isolate tests from mapping logic
 */
class FakeRestaurantMapper : IRestaurantMapper {

    /**
     * Simplified mapping that:
     * 1. Preserves all DTO values exactly (no sanitization)
     * 2. Formats postcodes consistently
     * 3. Safely handles null ratings (defaults to 0.0)
     */
    override fun mapToDomainModel(dto: RestaurantDto): Restaurant {
        return Restaurant(
            id = dto.id,
            name = dto.name, // No name cleaning
            cuisines = dto.cuisines?.map { it.name } ?: emptyList(), // Null-safe
            rating = dto.rating?.starRating ?: 0.0, // Null-safe
            address = Address(
                firstLine = dto.address.firstLine,
                city = dto.address.city,
                postalCode = formatPostcode(dto.address.postalCode)
            )
        )
    }

    /**
     * Standardizes UK postcode format without validation.
     * Example transforms:
     * - "ec1a1bb" → "EC1A 1BB"
     * - "N1 9GU" → "N1 9GU" (no change)
     */
    fun formatPostcode(postcode: String): String {
        return postcode
            .replace(Regex("\\s+"), "")
            .uppercase()
            .replace(Regex("([A-Z]{1,2}[0-9][A-Z0-9]?)([0-9][A-Z]{2})"), "$1 $2")
    }
}