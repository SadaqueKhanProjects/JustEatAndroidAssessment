package com.sadaquekhan.justeatassessment

import com.google.common.truth.Truth.assertThat
import com.sadaquekhan.justeatassessment.data.dto.RestaurantResponseDto
import com.sadaquekhan.justeatassessment.data.repository.RestaurantRepositoryImpl
import com.sadaquekhan.justeatassessment.domain.result.RestaurantResult
import com.sadaquekhan.justeatassessment.util.fake.*
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Test
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException

class RestaurantRepositoryImplTest {

    @Test
    fun `WHEN API call is successful THEN returns mapped restaurants`() = runTest {
        val dto = FakeRestaurantFactory.makeDto(
            id = "1",
            name = "Burger Joint",
            cuisines = listOf("American"),
            rating = 4.0,
            firstLine = "1 High St",
            city = "London",
            postalCode = "N19GU"
        )

        val fakeApi = FakeApiService().apply {
            mockResponse = Response.success(RestaurantResponseDto(listOf(dto)))
        }

        val repo = RestaurantRepositoryImpl(fakeApi, FakeRestaurantMapper(), FakeLogger())
        val result = repo.getRestaurants("N19GU")

        if (result is RestaurantResult.Success) {
            assertThat(result.restaurants).hasSize(1)
            assertThat(result.restaurants[0].name).isEqualTo("Burger Joint")
        } else {
            throw AssertionError("Expected Success but got $result")
        }

        assertThat(fakeApi.lastRequestedPostcode).isEqualTo("N19GU")
    }

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
            assertThat(logger.getLogs().any { it.isError && "Network error" in it.message }).isTrue()
        }
    }

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
            assertThat(logger.getLogs().any { it.isError && "Unexpected error" in it.message }).isTrue()
        }
    }

    @Test
    fun `WHEN postcode has space THEN ensure compact format is passed to repository`() = runTest {
        val fakeApi = FakeApiService()
        val repo = RestaurantRepositoryImpl(fakeApi, FakeRestaurantMapper(), FakeLogger())

        repo.getRestaurants("W1D3QF")

        assertThat(fakeApi.lastRequestedPostcode).isEqualTo("W1D3QF")
    }
}
