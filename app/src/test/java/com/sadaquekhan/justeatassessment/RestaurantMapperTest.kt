package com.sadaquekhan.justeatassessment

import com.google.common.truth.Truth.assertThat
import com.sadaquekhan.justeatassessment.data.dto.*
import com.sadaquekhan.justeatassessment.data.remote.dto.mapper.RestaurantMapper
import org.junit.Before
import org.junit.Test

/**
 * Unit tests for the RestaurantMapper class.
 * Verifies correct DTO to Domain transformation and validation logic.
 */
class RestaurantMapperTest {

    private lateinit var mapper: RestaurantMapper

    @Before
    fun setup() {
        mapper = RestaurantMapper()
    }

    @Test
    fun `GIVEN valid RestaurantDto WHEN mapped THEN returns correct Restaurant domain model`() {
        val dto = createSampleRestaurantDto()
        val domain = mapper.mapToDomainModel(dto)

        assertThat(domain.id).isEqualTo("123")
        assertThat(domain.name).isEqualTo("Pizza Place")
        assertThat(domain.rating).isEqualTo(4.5)
        assertThat(domain.address.postalCode).isEqualTo("EC1A 1BB")
        assertThat(domain.cuisines).containsExactly("Italian")
    }

    @Test
    fun `GIVEN malformed postcode WHEN mapped THEN returns formatted postcode`() {
        val dto = createSampleRestaurantDto().copy(
            address = AddressDto("Street", "City", "ec1a1bb")
        )

        val domain = mapper.mapToDomainModel(dto)

        assertThat(domain.address.postalCode).isEqualTo("EC1A 1BB")
    }

    @Test
    fun `GIVEN unknown cuisine WHEN mapped THEN filters it out`() {
        val dto = createSampleRestaurantDto().copy(
            cuisines = listOf(CuisineDto("Martian"), CuisineDto("Italian"))
        )

        val domain = mapper.mapToDomainModel(dto)

        assertThat(domain.cuisines).containsExactly("Italian")
    }

    @Test
    fun `GIVEN empty cuisine list WHEN mapped THEN returns empty domain cuisines`() {
        val dto = createSampleRestaurantDto().copy(
            cuisines = emptyList()
        )

        val domain = mapper.mapToDomainModel(dto)

        assertThat(domain.cuisines).isEmpty()
    }


    // --- Helpers ---

    private fun createSampleRestaurantDto(): RestaurantDto {
        return RestaurantDto(
            id = "123",
            name = "Pizza Place",
            cuisines = listOf(CuisineDto("Italian")),
            rating = RatingDto(4.5),
            address = AddressDto(
                firstLine = "1 Main Street",
                city = "London",
                postalCode = "EC1A1BB"
            )
        )
    }
}