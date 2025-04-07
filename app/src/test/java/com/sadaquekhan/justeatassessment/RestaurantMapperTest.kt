package com.sadaquekhan.justeatassessment

import com.google.common.truth.Truth.assertThat
import com.sadaquekhan.justeatassessment.data.dto.*
import com.sadaquekhan.justeatassessment.domain.model.Address
import com.sadaquekhan.justeatassessment.domain.model.Restaurant
import com.sadaquekhan.justeatassessment.data.remote.dto.mapper.RestaurantMapper
import org.junit.Test

/**
 * Unit tests for [RestaurantMapper].
 *
 * Validates key transformation rules between [RestaurantDto] and [Restaurant] domain model:
 * - Sanitization of names and addresses
 * - Cuisine filtering
 * - Rating handling
 * - Postcode formatting
 */
class RestaurantMapperTest {

    private val mapper = RestaurantMapper()

    @Test
    fun `maps valid restaurant with all fields`() {
        val dto = RestaurantDto(
            id = "1",
            name = "Pizza Place",
            cuisines = listOf(CuisineDto("Italian")),
            rating = RatingDto(4.5),
            address = AddressDto("1 Road", "London", "ec1a1bb")
        )

        val result = mapper.mapToDomainModel(dto)

        assertThat(result).isEqualTo(
            Restaurant(
                id = "1",
                name = "Pizza Place",
                cuisines = listOf("Italian"),
                rating = 4.5,
                address = Address("1 Road", "London", "EC1A 1BB")
            )
        )
    }

    @Test
    fun `handles null rating with default null`() {
        val dto = RestaurantDto(
            id = "1",
            name = "Pizza Place",
            cuisines = listOf(CuisineDto("Italian")),
            rating = null,
            address = AddressDto("1 Road", "London", "EC1A1BB")
        )

        val result = mapper.mapToDomainModel(dto)

        // Rating should remain null if not provided
        assertThat(result.rating).isNull()
    }

    @Test
    fun `WHEN name contains social handles THEN removes them`() {
        val dto = RestaurantDto(
            id = "1",
            name = "PizzaSlice @Italian (London)",
            cuisines = listOf(CuisineDto("Italian")),
            rating = RatingDto(4.5),
            address = AddressDto("1 Road", "London", "ec1a1bb")
        )

        val result = mapper.mapToDomainModel(dto)

        // @handle and parentheses should be stripped
        assertThat(result.name).isEqualTo("PizzaSlice")
    }

    @Test
    fun `WHEN cuisine not in whitelist THEN filters it out`() {
        val dto = RestaurantDto(
            id = "1",
            name = "Test",
            cuisines = listOf(CuisineDto("Italian"), CuisineDto("Unknown")),
            rating = null,
            address = AddressDto("1 Road", "London", "ec1a1bb")
        )

        val result = mapper.mapToDomainModel(dto)

        assertThat(result.cuisines).containsExactly("Italian")
    }

    @Test
    fun `WHEN address contains city in first line THEN removes duplicate`() {
        val dto = RestaurantDto(
            id = "1",
            name = "Test",
            cuisines = emptyList(),
            rating = null,
            address = AddressDto("123 London Road", "London", "ec1a1bb")
        )

        val result = mapper.mapToDomainModel(dto)

        // "London" should not appear twice in the address
        assertThat(result.address.firstLine).isEqualTo("123 Road")
    }

    @Test
    fun `formats postcodes consistently`() {
        val testCases = mapOf(
            "ec1a1bb" to "EC1A 1BB",
            "n1 9gu" to "N1 9GU",
            "SW1A1AA" to "SW1A 1AA",
            "wc2h9jq" to "WC2H 9JQ"
        )

        testCases.forEach { (input, expected) ->
            val dto = RestaurantDto(
                id = "1",
                name = "Test",
                cuisines = emptyList(),
                rating = null,
                address = AddressDto("1 Road", "London", input)
            )

            val result = mapper.mapToDomainModel(dto)

            assertThat(result.address.postalCode).isEqualTo(expected)
        }
    }
}
