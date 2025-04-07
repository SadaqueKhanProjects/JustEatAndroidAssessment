package com.sadaquekhan.justeatassessment

import com.google.common.truth.Truth.assertThat
import com.sadaquekhan.justeatassessment.data.dto.*
import com.sadaquekhan.justeatassessment.data.repository.RestaurantRepositoryImpl
import com.sadaquekhan.justeatassessment.util.*
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Test
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException

/**
 * Unit tests for RestaurantRepositoryImpl.
 * Uses FakeApiService, FakeLogger, and FakeRestaurantMapper to isolate testing.
 */
class RestaurantRepositoryImplTest {

    @Test
    fun `WHEN API call is successful THEN returns mapped restaurants`() = runTest {
        // Arrange: mock successful API response
        val fakeApi = FakeApiService().apply {
            mockResponse = Response.success(
                RestaurantResponseDto(
                    listOf(
                        RestaurantDto(
                            id = "1",
                            name = "Burger Joint",
                            cuisines = listOf(CuisineDto("American")),
                            rating = RatingDto(4.0),
                            address = AddressDto("1 High St", "London", "N1 9GU")
                        )
                    )
                )
            )
        }
        val repo = RestaurantRepositoryImpl(fakeApi, FakeRestaurantMapper(), FakeLogger())

        // Act
        val result = repo.getRestaurants("N1 9GU")

        // Assert
        assertThat(result).hasSize(1)
        assertThat(result[0].name).isEqualTo("Burger Joint")
        assertThat(fakeApi.lastRequestedPostcode).isEqualTo("N1%209GU") // %20 encoding for space
    }

    @Test
    fun `WHEN API returns 404 THEN throws IOException with proper message`() = runTest {
        // Arrange: mock 404 error response
        val fakeApi = FakeApiService().apply {
            mockResponse = Response.error(
                404, "Not Found".toResponseBody("application/json".toMediaType())
            )
        }
        val logger = FakeLogger()
        val repo = RestaurantRepositoryImpl(fakeApi, FakeRestaurantMapper(), logger)

        // Act & Assert
        try {
            repo.getRestaurants("SW1A 1AA")
            throw AssertionError("Expected IOException but got none")
        } catch (e: IOException) {
            assertThat(e.message).contains("404")
            val errors = logger.getLogs().filter { it.isError }
            assertThat(errors).isNotEmpty()
            assertThat(errors[0].message).contains("API error 404")
        }
    }

    @Test
    fun `WHEN network is down THEN throws IOException with message`() = runTest {
        // Arrange: simulate IOException (e.g., no internet)
        val fakeApi = FakeApiService().apply {
            shouldReturnError = true
            errorToThrow = IOException("Network down")
        }
        val logger = FakeLogger()
        val repo = RestaurantRepositoryImpl(fakeApi, FakeRestaurantMapper(), logger)

        // Act & Assert
        try {
            repo.getRestaurants("E1 6AN")
            throw AssertionError("Expected IOException due to network failure")
        } catch (e: IOException) {
            assertThat(e.message).contains("No internet connection")
            assertThat(logger.getLogs().any { it.isError && "Network error" in it.message }).isTrue()
        }
    }

    @Test
    fun `WHEN timeout occurs THEN throws SocketTimeoutException`() = runTest {
        // Arrange: simulate timeout
        val fakeApi = FakeApiService().apply {
            shouldReturnError = true
            errorToThrow = SocketTimeoutException("Timeout")
        }
        val logger = FakeLogger()
        val repo = RestaurantRepositoryImpl(fakeApi, FakeRestaurantMapper(), logger)

        // Act & Assert
        try {
            repo.getRestaurants("W1A 0AX")
            throw AssertionError("Expected SocketTimeoutException due to timeout")
        } catch (e: SocketTimeoutException) {
            assertThat(e.message).contains("timeout")
            assertThat(logger.getLogs().any { it.isError && "Timeout" in it.message }).isTrue()
        }
    }

    @Test
    fun `WHEN API returns null THEN throws generic Exception`() = runTest {
        // Arrange: simulate null body from API (malformed or empty)
        val fakeApi = FakeApiService().apply {
            mockResponse = Response.success(null)
        }
        val logger = FakeLogger()
        val repo = RestaurantRepositoryImpl(fakeApi, FakeRestaurantMapper(), logger)

        // Act & Assert
        try {
            repo.getRestaurants("EC2A 3AR")
            throw AssertionError("Expected generic exception but got none")
        } catch (e: Exception) {
            assertThat(e.message).contains("Something went wrong")
            assertThat(logger.getLogs().any { it.isError && "Unexpected error" in it.message }).isTrue()
        }
    }

    @Test
    fun `WHEN postcode has space THEN ensure it is URL encoded as %20`() = runTest {
        // Arrange
        val fakeApi = FakeApiService()
        val repo = RestaurantRepositoryImpl(fakeApi, FakeRestaurantMapper(), FakeLogger())

        // Act
        repo.getRestaurants("W1D 3QF")

        // Assert: space should be encoded as %20 not +
        assertThat(fakeApi.lastRequestedPostcode).isEqualTo("W1D%203QF")
    }
}
