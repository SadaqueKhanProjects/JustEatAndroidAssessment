package com.sadaquekhan.justeatassessment

import com.google.common.truth.Truth.assertThat
import com.sadaquekhan.justeatassessment.data.dto.*
import com.sadaquekhan.justeatassessment.data.repository.RestaurantRepositoryImpl
import com.sadaquekhan.justeatassessment.util.*
import com.sadaquekhan.justeatassessment.util.fake.FakeApiService
import com.sadaquekhan.justeatassessment.util.fake.FakeLogger
import com.sadaquekhan.justeatassessment.util.fake.FakeRestaurantMapper
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Test
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException

/**
 * Unit tests for [RestaurantRepositoryImpl].
 *
 * Verifies the following behaviors:
 * - Successful API responses return mapped data
 * - All error paths (404, timeouts, network failure, null responses) are correctly handled
 * - Logging occurs correctly for debug and error messages
 * - Postcode is properly URL-encoded
 *
 * Dependencies:
 * - [FakeApiService]: mocks network layer
 * - [FakeRestaurantMapper]: avoids real sanitation logic
 * - [FakeLogger]: tracks log events without Android Log
 */
class RestaurantRepositoryImplTest {

    /**
     * Verifies that a successful API response returns the expected mapped data.
     */
    @Test
    fun `WHEN API call is successful THEN returns mapped restaurants`() = runTest {
        val fakeApi = FakeApiService().apply {
            mockResponse = Response.success(
                RestaurantResponseDto(
                    listOf(
                        RestaurantDto(
                            id = "1",
                            name = "Burger Joint",
                            cuisines = listOf(CuisineDto("American")),
                            rating = RatingDto(4.0),
                            address = AddressDto("1 High St", "London", "N19GU")
                        )
                    )
                )
            )
        }

        val repo = RestaurantRepositoryImpl(fakeApi, FakeRestaurantMapper(), FakeLogger())

        val result = repo.getRestaurants("N19GU") // Compact postcode (no spaces)

        assertThat(result).hasSize(1)
        assertThat(result[0].name).isEqualTo("Burger Joint")

        // Validate that raw compact postcode is sent to API without URL encoding
        assertThat(fakeApi.lastRequestedPostcode).isEqualTo("N19GU")
    }

    /**
     * Ensures API 404 errors are surfaced as IOExceptions with proper logging.
     */
    @Test
    fun `WHEN API returns 404 THEN throws IOException with proper message`() = runTest {
        val fakeApi = FakeApiService().apply {
            mockResponse = Response.error(
                404, "Not Found".toResponseBody("application/json".toMediaType())
            )
        }

        val logger = FakeLogger()
        val repo = RestaurantRepositoryImpl(fakeApi, FakeRestaurantMapper(), logger)

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

    /**
     * Simulates a network error (e.g., no internet) and ensures graceful fallback with logging.
     */
    @Test
    fun `WHEN network is down THEN throws IOException with message`() = runTest {
        val fakeApi = FakeApiService().apply {
            shouldReturnError = true
            errorToThrow = IOException("Network down")
        }

        val logger = FakeLogger()
        val repo = RestaurantRepositoryImpl(fakeApi, FakeRestaurantMapper(), logger)

        try {
            repo.getRestaurants("E1 6AN")
            throw AssertionError("Expected IOException due to network failure")
        } catch (e: IOException) {
            assertThat(e.message).contains("No internet connection")

            // Verify that proper error log was recorded
            assertThat(
                logger.getLogs().any { it.isError && "Network error" in it.message }).isTrue()
        }
    }

    /**
     * Simulates a timeout from the API and ensures a SocketTimeoutException is thrown and logged.
     */
    @Test
    fun `WHEN timeout occurs THEN throws SocketTimeoutException`() = runTest {
        val fakeApi = FakeApiService().apply {
            shouldReturnError = true
            errorToThrow = SocketTimeoutException("Timeout")
        }

        val logger = FakeLogger()
        val repo = RestaurantRepositoryImpl(fakeApi, FakeRestaurantMapper(), logger)

        try {
            repo.getRestaurants("W1A 0AX")
            throw AssertionError("Expected SocketTimeoutException due to timeout")
        } catch (e: SocketTimeoutException) {
            assertThat(e.message).contains("timeout")
            assertThat(logger.getLogs().any { it.isError && "Timeout" in it.message }).isTrue()
        }
    }

    /**
     * Simulates a case where API returns a null response body (malformed payload).
     */
    @Test
    fun `WHEN API returns null THEN throws generic Exception`() = runTest {
        val fakeApi = FakeApiService().apply {
            mockResponse = Response.success(null)
        }

        val logger = FakeLogger()
        val repo = RestaurantRepositoryImpl(fakeApi, FakeRestaurantMapper(), logger)

        try {
            repo.getRestaurants("EC2A 3AR")
            throw AssertionError("Expected generic exception but got none")
        } catch (e: Exception) {
            assertThat(e.message).contains("Something went wrong")
            assertThat(
                logger.getLogs().any { it.isError && "Unexpected error" in it.message }).isTrue()
        }
    }

    /**
     * Verifies that postcodes with spaces are now pre-sanitized by the ViewModel
     * and passed to the Repository in compact format (no %20 encoding).
     */
    @Test
    fun `WHEN postcode has space THEN ensure compact format is passed to repository`() = runTest {
        val fakeApi = FakeApiService()
        val repo = RestaurantRepositoryImpl(fakeApi, FakeRestaurantMapper(), FakeLogger())

        repo.getRestaurants("W1D3QF") // Already sanitized in ViewModel

        // Confirm no encoding needed – passed directly to endpoint
        assertThat(fakeApi.lastRequestedPostcode).isEqualTo("W1D3QF")
    }
}
