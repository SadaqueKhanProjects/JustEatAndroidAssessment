package com.sadaquekhan.justeatassessment

import com.google.common.truth.Truth.assertThat
import com.sadaquekhan.justeatassessment.data.remote.dto.mapper.RestaurantMapper
import com.sadaquekhan.justeatassessment.domain.model.Address
import com.sadaquekhan.justeatassessment.domain.model.Restaurant
import com.sadaquekhan.justeatassessment.util.fake.FakeRestaurantFactory
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
    fun `WHEN logo url is provided in metadata THEN it maps correctly to domain model`() {
        val testLogoUrl = "https://justeat-assets.com/logos/test_logo.png"

        val dto = FakeRestaurantFactory.makeDto(
            id = "logo_test_id",
            name = "Logo Test Restaurant",
            cuisines = listOf("Pizza"),
            rating = 4.2,
            firstLine = "123 Logo St",
            city = "London",
            postalCode = "W1D 3QF",
            logoUrl = testLogoUrl
        )

        val result = mapper.mapToDomainModel(dto)

        assertThat(result.logoUrl).isEqualTo(testLogoUrl)
        assertThat(result.id).isEqualTo("logo_test_id")
        assertThat(result.name).contains("Logo Test")
    }

    @Test
    fun `maps valid restaurant with all fields`() {
        val dto = FakeRestaurantFactory.makeDto(
            id = "1",
            name = "Pizza Place",
            cuisines = listOf("Italian"),
            rating = 4.5,
            firstLine = "1 Road",
            city = "London",
            postalCode = "ec1a1bb"
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
        val dto = FakeRestaurantFactory.makeDto(
            rating = null
        )

        val result = mapper.mapToDomainModel(dto)

        assertThat(result.rating).isNull()
    }

    @Test
    fun `WHEN name contains social handles THEN removes them`() {
        val dto = FakeRestaurantFactory.makeDto(
            name = "PizzaSlice @Italian (London)"
        )

        val result = mapper.mapToDomainModel(dto)

        assertThat(result.name).isEqualTo("PizzaSlice")
    }

    @Test
    fun `WHEN cuisine not in whitelist THEN filters it out`() {
        val dto = FakeRestaurantFactory.makeDto(
            cuisines = listOf("Italian", "Unknown")
        )

        val result = mapper.mapToDomainModel(dto)

        assertThat(result.cuisines).containsExactly("Italian")
    }

    @Test
    fun `WHEN address contains city in first line THEN removes duplicate`() {
        val dto = FakeRestaurantFactory.makeDto(
            firstLine = "123 London Road",
            city = "London",
            postalCode = "ec1a1bb"
        )

        val result = mapper.mapToDomainModel(dto)

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
            val dto = FakeRestaurantFactory.makeDto(
                postalCode = input
            )

            val result = mapper.mapToDomainModel(dto)

            assertThat(result.address.postalCode).isEqualTo(expected)
        }
    }
}
