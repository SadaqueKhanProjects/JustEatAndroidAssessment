package com.sadaquekhan.justeatassessment.util.fake

import com.sadaquekhan.justeatassessment.data.dto.AddressDto
import com.sadaquekhan.justeatassessment.data.dto.CuisineDto
import com.sadaquekhan.justeatassessment.data.dto.RatingDto
import com.sadaquekhan.justeatassessment.data.dto.RestaurantDto
import com.sadaquekhan.justeatassessment.domain.model.Address
import com.sadaquekhan.justeatassessment.domain.model.Restaurant

/**
 * Factory for generating test/fake Restaurant and RestaurantDto models
 * for unit and instrumentation tests.
 */
object FakeRestaurantFactory {

    fun make(
        id: String = "test_id",
        name: String = "Fake Restaurant",
        cuisines: List<String> = listOf("Italian"),
        rating: Double? = 4.0,
        firstLine: String = "123 Fake St",
        city: String = "London",
        postalCode: String = "SW1A1AA"
    ): Restaurant {
        return Restaurant(
            id = id,
            name = name,
            cuisines = cuisines,
            rating = rating,
            address = Address(
                firstLine = firstLine,
                city = city,
                postalCode = postalCode
            )
        )
    }

    fun makeDto(
        id: String = "test_id",
        name: String = "Fake Restaurant",
        cuisines: List<String> = listOf("Italian"),
        rating: Double? = 4.0,
        firstLine: String = "123 Fake St",
        city: String = "London",
        postalCode: String = "SW1A1AA"
    ): RestaurantDto {
        return RestaurantDto(
            id = id,
            name = name,
            cuisines = cuisines.map { CuisineDto(it) },
            rating = rating?.let { RatingDto(it) },
            address = AddressDto(
                firstLine = firstLine,
                city = city,
                postalCode = postalCode
            )
        )
    }
}
