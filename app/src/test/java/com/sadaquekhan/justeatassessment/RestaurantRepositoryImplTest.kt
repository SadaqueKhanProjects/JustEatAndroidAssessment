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
    fun `WHEN API returns 404 THEN returns NetworkError with proper message`() = runTest {
        val fakeApi = FakeApiService().apply {
            mockResponse = Response.error(
                404, "Not Found".toResponseBody("application/json".toMediaType())
            )
        }

        val logger = FakeLogger()
        val repo = RestaurantRepositoryImpl(fakeApi, FakeRestaurantMapper(), logger)

        val result = repo.getRestaurants("SW1A 1AA")

        assertThat(result).isInstanceOf(RestaurantResult.NetworkError::class.java)
        result as RestaurantResult.NetworkError
        assertThat(result.message).contains("404")
        assertThat(logger.getLogs().any { it.isError && "API error 404" in it.message }).isTrue()
    }


    @Test
    fun `WHEN network is down THEN returns NetworkError with proper message`() = runTest {
        val fakeApi = FakeApiService().apply {
            shouldReturnError = true
            errorToThrow = IOException("Network down")
        }

        val logger = FakeLogger()
        val repo = RestaurantRepositoryImpl(fakeApi, FakeRestaurantMapper(), logger)

        val result = repo.getRestaurants("E1 6AN")

        assertThat(result).isInstanceOf(RestaurantResult.NetworkError::class.java)
        result as RestaurantResult.NetworkError
        assertThat(result.message).contains("No internet connection")
        assertThat(logger.getLogs().any { it.isError && "Network error" in it.message }).isTrue()
    }


    @Test
    fun `WHEN timeout occurs THEN returns Timeout with proper message`() = runTest {
        val fakeApi = FakeApiService().apply {
            shouldReturnError = true
            errorToThrow = SocketTimeoutException("timeout")
        }

        val logger = FakeLogger()
        val repo = RestaurantRepositoryImpl(fakeApi, FakeRestaurantMapper(), logger)

        val result = repo.getRestaurants("W1A 0AX")

        assertThat(result).isInstanceOf(RestaurantResult.Timeout::class.java)
        result as RestaurantResult.Timeout
        assertThat(result.message.lowercase()).contains("timeout")
        assertThat(logger.getLogs().any { it.isError && "timeout" in it.message.lowercase() }).isTrue()
    }


    @Test
    fun `WHEN API returns null THEN returns UnknownError with proper message`() = runTest {
        val fakeApi = FakeApiService().apply {
            mockResponse = Response.success(null)
        }

        val logger = FakeLogger()
        val repo = RestaurantRepositoryImpl(fakeApi, FakeRestaurantMapper(), logger)

        val result = repo.getRestaurants("EC2A 3AR")

        assertThat(result).isInstanceOf(RestaurantResult.UnknownError::class.java)
        result as RestaurantResult.UnknownError
        assertThat(result.message).contains("empty response")

        assertThat(logger.getLogs().any { it.isError && "Null response body" in it.message }).isTrue()
    }


    @Test
    fun `WHEN postcode has space THEN ensure compact format is passed to repository`() = runTest {
        val fakeApi = FakeApiService()
        val repo = RestaurantRepositoryImpl(fakeApi, FakeRestaurantMapper(), FakeLogger())

        repo.getRestaurants("W1D3QF")

        assertThat(fakeApi.lastRequestedPostcode).isEqualTo("W1D3QF")
    }
}
