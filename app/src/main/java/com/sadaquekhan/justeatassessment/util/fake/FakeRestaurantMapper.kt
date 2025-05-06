package com.sadaquekhan.justeatassessment.util.fake

import com.sadaquekhan.justeatassessment.data.dto.RestaurantDto
import com.sadaquekhan.justeatassessment.domain.model.Address
import com.sadaquekhan.justeatassessment.domain.model.Restaurant
import com.sadaquekhan.justeatassessment.domain.mapper.IRestaurantMapper

/**
 * Simplified [IRestaurantMapper] for unit testing.
 *
 * Bypasses sanitization logic and provides predictable output for test validation.
 */
class FakeRestaurantMapper : IRestaurantMapper {

    override fun mapToDomainModel(dto: RestaurantDto): Restaurant {
        return Restaurant(
            id = dto.id,
            name = dto.name, // No name cleaning or sanitation
            cuisines = dto.cuisines.map { it.name },
            rating = dto.rating?.starRating
                ?: 0.0, // Null ratings default to 0.0 for test stability
            address = Address(
                firstLine = dto.address.firstLine,
                city = dto.address.city,
                postalCode = formatPostcode(dto.address.postalCode)
            )
        )
    }

    /**
     * Normalizes UK postcode format.
     * - e.g., "ec1a1bb" → "EC1A 1BB"
     */
    fun formatPostcode(postcode: String): String {
        return postcode
            .replace(Regex("\\s+"), "")
            .uppercase()
            .replace(Regex("([A-Z]{1,2}[0-9][A-Z0-9]?)([0-9][A-Z]{2})"), "$1 $2")
    }
}
