package com.sadaquekhan.justeatassessment

import com.google.common.truth.Truth.assertThat
import com.sadaquekhan.justeatassessment.data.repository.IRestaurantRepository
import com.sadaquekhan.justeatassessment.domain.model.Address
import com.sadaquekhan.justeatassessment.domain.model.Restaurant
import com.sadaquekhan.justeatassessment.util.FakeLogger
import com.sadaquekhan.justeatassessment.viewmodel.RestaurantViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.io.IOException
import java.net.SocketTimeoutException

/**
 * Unit test suite for the RestaurantViewModel.
 * Verifies logic using FakeRestaurantRepository and FakeLogger.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class RestaurantViewModelTest {

    private lateinit var viewModel: RestaurantViewModel
    private lateinit var fakeRepository: FakeRestaurantRepository

    // Using FakeLogger to avoid android.util.Log in tests
    private val fakeLogger = FakeLogger()

    // Unconfined dispatcher allows coroutines to run instantly
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        fakeRepository = FakeRestaurantRepository()
        viewModel = RestaurantViewModel(
            repository = fakeRepository,
            logger = fakeLogger
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadRestaurants with valid postcode returns restaurant list`() = runTest {
        viewModel.loadRestaurants("EC1A1BB")
        val result = viewModel.uiState.first()
        assertThat(result.restaurants).isNotEmpty()
        assertThat(result.errorMessage).isNull()
        assertThat(result.isEmpty).isFalse()
    }

    @Test
    fun `loadRestaurants with invalid postcode returns error`() = runTest {
        viewModel.loadRestaurants("!!!")
        val result = viewModel.uiState.first()
        assertThat(result.errorMessage).contains("Invalid UK postcode")
        assertThat(result.restaurants).isEmpty()
    }

    @Test
    fun `loadRestaurants returns empty list`() = runTest {
        fakeRepository.shouldReturnEmptyList = true
        viewModel.loadRestaurants("EC1A1BB")
        val result = viewModel.uiState.first()
        assertThat(result.restaurants).isEmpty()
        assertThat(result.isEmpty).isTrue()
    }

    @Test
    fun `loadRestaurants handles timeout exception`() = runTest {
        fakeRepository.shouldThrowTimeout = true
        viewModel.loadRestaurants("EC1A1BB")
        val result = viewModel.uiState.first()
        assertThat(result.errorMessage).contains("timeout")
    }

    @Test
    fun `loadRestaurants handles no internet`() = runTest {
        fakeRepository.shouldThrowIOException = true
        viewModel.loadRestaurants("EC1A1BB")
        val result = viewModel.uiState.first()
        assertThat(result.errorMessage).contains("No internet")
    }

    @Test
    fun `loadRestaurants handles generic exception`() = runTest {
        fakeRepository.shouldThrowGenericException = true
        viewModel.loadRestaurants("EC1A1BB")
        val result = viewModel.uiState.first()
        assertThat(result.errorMessage).contains("Something went wrong")
    }

    /**
     * A fake implementation of RestaurantRepository to simulate different test scenarios.
     */
    class FakeRestaurantRepository : IRestaurantRepository {

        var shouldReturnEmptyList = false
        var shouldThrowTimeout = false
        var shouldThrowIOException = false
        var shouldThrowGenericException = false

        override suspend fun getRestaurants(postcode: String): List<Restaurant> {
            if (shouldThrowTimeout) throw SocketTimeoutException("timeout")
            if (shouldThrowIOException) throw IOException("no connection")
            if (shouldThrowGenericException) throw Exception("unexpected")

            return if (shouldReturnEmptyList) emptyList() else listOf(
                Restaurant(
                    id = "1",
                    name = "Test Restaurant",
                    cuisines = listOf("Pizza"),
                    rating = 4.5,
                    address = Address(
                        firstLine = "1 Main Street",
                        city = "London",
                        postalCode = "EC1A1BB"
                    )
                )
            )
        }
    }
}