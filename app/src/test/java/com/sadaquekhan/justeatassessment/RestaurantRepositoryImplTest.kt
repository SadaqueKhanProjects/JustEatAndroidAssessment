package com.sadaquekhan.justeatassessment

import com.google.common.truth.Truth.assertThat
import com.sadaquekhan.justeatassessment.data.repository.RestaurantRepositoryImpl
import com.sadaquekhan.justeatassessment.domain.model.Restaurant
import com.sadaquekhan.justeatassessment.network.api.RestaurantApiService
import com.sadaquekhan.justeatassessment.util.FakeLogger
import com.sadaquekhan.justeatassessment.util.FakeRestaurantMapper
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.IOException
import java.net.HttpURLConnection

/**
 * Unit tests for RestaurantRepositoryImpl using a MockWebServer and a FakeRestaurantMapper.
 * Injects FakeLogger to avoid android.util.Log crashes during JVM-based tests.
 */
class RestaurantRepositoryImplTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var apiService: RestaurantApiService
    private lateinit var repository: RestaurantRepositoryImpl

    // Logger used to bypass Android logging during test
    private val fakeLogger = FakeLogger()

    // Fake mapper avoids sanitization logic to make assertions more predictable
    private val fakeMapper = FakeRestaurantMapper()

    @Before
    fun setup() {
        // Start a mock server to simulate API responses
        mockWebServer = MockWebServer()
        mockWebServer.start()

        // Set up Retrofit to point to the mock server
        apiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(RestaurantApiService::class.java)

        // Inject mock dependencies into the repository
        repository = RestaurantRepositoryImpl(
            apiService = apiService,
            mapper = fakeMapper,
            logger = fakeLogger
        )
    }

    @After
    fun tearDown() {
        // Shut down the mock server after each test
        mockWebServer.shutdown()
    }

    @Test
    fun `GIVEN valid response WHEN getRestaurants THEN returns correct mapped data`() = runTest {
        val mockJson = """
            {
              "restaurants": [
                {
                  "id": "123",
                  "name": "Pizza House",
                  "cuisines": [{"name": "Italian"}],
                  "rating": {"starRating": 4.5},
                  "address": {
                    "firstLine": "1 Main Street",
                    "city": "London",
                    "postalCode": "EC1A1BB"
                  }
                }
              ]
            }
        """.trimIndent()

        // Enqueue mock successful response
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody(mockJson)
        )

        val result: List<Restaurant> = repository.getRestaurants("EC1A1BB")

        // Assertions to validate mapping logic
        assertThat(result).isNotEmpty()
        assertThat(result[0].name).isEqualTo("Pizza House")
        assertThat(result[0].rating).isEqualTo(4.5)
        assertThat(result[0].address.postalCode).isEqualTo("EC1A 1BB")
    }

    @Test
    fun `GIVEN server error WHEN getRestaurants THEN throws Exception`() = runTest {
        // Simulate a server failure (HTTP 500)
        mockWebServer.enqueue(MockResponse().setResponseCode(500))

        try {
            repository.getRestaurants("EC1A1BB")
            assert(false) { "Expected exception but none thrown" }
        } catch (e: Exception) {
            assertThat(e).isInstanceOf(Exception::class.java)
        }
    }

    @Test
    fun `GIVEN no internet WHEN getRestaurants THEN throws IOException`() = runTest {
        // Simulate no internet by stopping the server
        mockWebServer.shutdown()

        try {
            repository.getRestaurants("EC1A1BB")
            assert(false) { "Expected IOException but none thrown" }
        } catch (e: IOException) {
            assertThat(e).isInstanceOf(IOException::class.java)
        }
    }
}