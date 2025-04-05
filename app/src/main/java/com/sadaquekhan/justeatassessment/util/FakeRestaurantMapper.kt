package com.sadaquekhan.justeatassessment.util

import com.sadaquekhan.justeatassessment.data.dto.RestaurantDto
import com.sadaquekhan.justeatassessment.domain.model.Address
import com.sadaquekhan.justeatassessment.domain.model.Restaurant
import com.sadaquekhan.justeatassessment.domain.mapper.IRestaurantMapper

/**
 * Fake implementation of RestaurantMapper for unit testing.
 * This bypasses the actual sanitization logic and returns predictable data.
 */
class FakeRestaurantMapper : IRestaurantMapper {

    /**
     * Returns a simplified domain Restaurant object without applying cleanup.
     * This isolates mapping logic during unit tests.
     */
    override fun mapToDomainModel(dto: RestaurantDto): Restaurant {
        return Restaurant(
            id = dto.id,
            name = dto.name,
            cuisines = dto.cuisines.map { it.name },
            rating = dto.rating.starRating,
            address = Address(
                firstLine = dto.address.firstLine,
                city = dto.address.city,
                postalCode = formatPostcode(dto.address.postalCode)
            )
        )
    }

    /**
     * Formats UK postcode to standard format, e.g., "EC1A1BB" → "EC1A 1BB".
     */
    private fun formatPostcode(postcode: String): String {
        return postcode
            .replace(Regex("\\s+"), "")
            .replace(Regex("([A-Z]{1,2}[0-9][A-Z0-9]?)\\s*([0-9][A-Z]{2})"), "$1 $2")
            .trim()
    }
}