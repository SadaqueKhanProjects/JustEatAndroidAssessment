package com.sadaquekhan.justeatassessment

import com.google.common.truth.Truth.assertThat
import com.sadaquekhan.justeatassessment.domain.model.Address
import com.sadaquekhan.justeatassessment.domain.model.Restaurant
import com.sadaquekhan.justeatassessment.util.FakeLogger
import com.sadaquekhan.justeatassessment.util.FakeRestaurantRepository
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
 * Unit tests for [RestaurantViewModel].
 *
 * Validates the ViewModel’s ability to:
 * - Handle valid and invalid postcodes
 * - Manage API state transitions (loading, success, error)
 * - Log appropriate messages on success/failure
 * - Maintain UI state consistency between requests
 *
 * Dependencies used:
 * - [FakeRestaurantRepository] to simulate data layer
 * - [FakeLogger] to verify log outputs
 */
@OptIn(ExperimentalCoroutinesApi::class)
class RestaurantViewModelTest {

    private lateinit var viewModel: RestaurantViewModel
    private lateinit var fakeRepository: FakeRestaurantRepository
    private val fakeLogger = FakeLogger()
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

    /**
     * Verifies that the initial state is empty and not yet searched.
     */
    @Test
    fun `initial state has not searched`() = runTest {
        val initialState = viewModel.uiState.first()

        assertThat(initialState.hasSearched).isFalse()
        assertThat(initialState.restaurants).isEmpty()
    }

    /**
     * Verifies that entering an empty postcode triggers a validation error.
     */
    @Test
    fun `WHEN empty postcode entered THEN shows validation error`() = runTest {
        viewModel.loadRestaurants("")

        val state = viewModel.uiState.first()
        assertThat(state.errorMessage).contains("Please enter")
    }

    /**
     * Ensures that invalid postcode formats are flagged and blocked.
     */
    @Test
    fun `WHEN invalid postcode format THEN shows validation error`() = runTest {
        viewModel.loadRestaurants("INVALID")

        val state = viewModel.uiState.first()
        assertThat(state.errorMessage).contains("Invalid UK postcode")
    }

    /**
     * Verifies that valid postcodes with irregular spacing are sanitized correctly.
     */
    @Test
    fun `WHEN valid postcode with spaces THEN sanitizes it`() = runTest {
        fakeRepository.mockRestaurants = listOf(
            Restaurant(
                id = "1",
                name = "Test",
                cuisines = listOf("Pizza"),
                rating = 4.5,
                address = Address("1 Main St", "London", "EC1A1BB")
            )
        )

        viewModel.loadRestaurants(" ec1a 1bb ")

        // Wait until loading finishes
        val state = viewModel.uiState.first { !it.isLoading }

        assertThat(state.restaurants).hasSize(1)
        assertThat(fakeRepository.lastRequestedPostcode).isEqualTo("EC1A1BB")
    }

    /**
     * Simulates a SocketTimeoutException and ensures the ViewModel shows error and logs it.
     */
    @Test
    fun `WHEN timeout occurs THEN shows proper error and logs`() = runTest {
        fakeRepository.shouldReturnError = true
        fakeRepository.errorToThrow = SocketTimeoutException()

        viewModel.loadRestaurants("EC1A1BB")
        val state = viewModel.uiState.first { !it.isLoading }

        assertThat(state.errorMessage).contains("timeout")

        val logs = fakeLogger.getLogs().filter { it.isError }
        assertThat(logs).isNotEmpty()
        assertThat(logs[0].message.lowercase()).contains("sockettimeoutexception")
    }

    /**
     * Verifies that the debug logger is triggered for successful requests.
     */
    @Test
    fun `WHEN valid request THEN logs debug messages`() = runTest {
        fakeRepository.mockRestaurants = listOf(
            Restaurant(
                id = "1",
                name = "Test",
                cuisines = listOf("Pizza"),
                rating = 4.5,
                address = Address("1 Main St", "London", "EC1A1BB")
            )
        )

        viewModel.loadRestaurants("EC1A1BB")
        viewModel.uiState.first { !it.isLoading }

        val logs = fakeLogger.getLogs().filter { !it.isError }
        assertThat(logs).isNotEmpty()
        assertThat(logs[0].message).contains("Loading restaurants")
    }

    /**
     * Ensures that if two API requests are made in succession, both trigger loading indicators.
     */
    @Test
    fun `WHEN consecutive requests THEN shows loading between them`() = runTest {
        fakeRepository.mockRestaurants = listOf(
            Restaurant(
                id = "1",
                name = "Test",
                cuisines = listOf("Pizza"),
                rating = 4.5,
                address = Address("1 Main St", "London", "EC1A1BB")
            )
        )
        fakeRepository.delayMillis = 100 // Simulate artificial delay

        // First request
        viewModel.loadRestaurants("EC1A1BB")
        assertThat(viewModel.uiState.value.isLoading).isTrue()
        advanceUntilIdle() // Complete coroutine work

        // Second request
        viewModel.loadRestaurants("N19GU")
        assertThat(viewModel.uiState.value.isLoading).isTrue()
    }
}
