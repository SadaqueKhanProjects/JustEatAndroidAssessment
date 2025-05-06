package com.sadaquekhan.justeatassessment.util.fake

import com.sadaquekhan.justeatassessment.data.dto.AddressDto
import com.sadaquekhan.justeatassessment.data.dto.CuisineDto
import com.sadaquekhan.justeatassessment.data.dto.RatingDto
import com.sadaquekhan.justeatassessment.data.dto.RestaurantDto


object FakeRestaurantDtoFactory {

    fun make(
        id: String = "1",
        name: String = "DTO Restaurant",
        cuisines: List<CuisineDto> = listOf(CuisineDto("Thai")),
        rating: RatingDto = RatingDto(4.0),
        address: AddressDto = AddressDto(
            firstLine = "12 Example St",
            city = "London",
            postalCode = "E1 6AN"
        )
    ): RestaurantDto {
        return RestaurantDto(
            id = id,
            name = name,
            cuisines = cuisines,
            rating = rating,
            address = address
        )
    }

    fun list(count: Int = 3): List<RestaurantDto> =
        List(count) { index ->
            make(id = (index + 1).toString(), name = "DTO Restaurant #$index")
        }
}
