package com.sadaquekhan.justeatassessment

import com.google.common.truth.Truth.assertThat
import com.sadaquekhan.justeatassessment.util.fake.FakeLogger
import com.sadaquekhan.justeatassessment.util.fake.FakeRestaurantFactory
import com.sadaquekhan.justeatassessment.util.fake.FakeRestaurantRepository
import com.sadaquekhan.justeatassessment.viewmodel.RestaurantViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.net.SocketTimeoutException

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

    @Test
    fun `initial state has not searched`() = runTest {
        val initialState = viewModel.uiState.first()
        assertThat(initialState.hasSearched).isFalse()
        assertThat(initialState.restaurants).isEmpty()
    }

    @Test
    fun `WHEN empty postcode entered THEN shows validation error`() = runTest {
        viewModel.loadRestaurants("")
        val state = viewModel.uiState.first()
        assertThat(state.errorMessage).contains("Please enter")
    }

    @Test
    fun `WHEN invalid postcode format THEN shows validation error`() = runTest {
        viewModel.loadRestaurants("INVALID")
        val state = viewModel.uiState.first()
        assertThat(state.errorMessage).contains("Invalid UK postcode")
    }

    @Test
    fun `WHEN valid postcode with spaces THEN sanitizes it`() = runTest {
        fakeRepository.mockRestaurants = listOf(
            FakeRestaurantFactory.make(
                id = "1",
                name = "Test",
                cuisines = listOf("Pizza"),
                rating = 4.5,
                firstLine = "1 Main St",
                city = "London",
                postalCode = "EC1A1BB"
            )
        )

        viewModel.loadRestaurants(" ec1a 1bb ")
        val state = viewModel.uiState.first { !it.isLoading }

        assertThat(state.restaurants).isNotNull()
        assertThat(state.restaurants.size).isEqualTo(1)
        assertThat(fakeRepository.lastRequestedPostcode).isEqualTo("EC1A1BB")
    }

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


    @Test
    fun `WHEN valid request THEN logs debug messages`() = runTest {
        fakeRepository.mockRestaurants = listOf(
            FakeRestaurantFactory.make(
                id = "1",
                name = "Test",
                cuisines = listOf("Pizza"),
                rating = 4.5,
                firstLine = "1 Main St",
                city = "London",
                postalCode = "EC1A1BB"
            )
        )

        viewModel.loadRestaurants("EC1A1BB")
        viewModel.uiState.first { !it.isLoading }

        val logs = fakeLogger.getLogs().filter { !it.isError }
        assertThat(logs).isNotEmpty()
        assertThat(logs[0].message).contains("Loading restaurants")
    }

    @Test
    fun `WHEN consecutive requests THEN shows loading between them`() = runTest {
        fakeRepository.mockRestaurants = listOf(
            FakeRestaurantFactory.make(
                id = "1",
                name = "Test",
                cuisines = listOf("Pizza"),
                rating = 4.5,
                firstLine = "1 Main St",
                city = "London",
                postalCode = "EC1A1BB"
            )
        )
        fakeRepository.delayMillis = 100

        viewModel.loadRestaurants("EC1A1BB")
        assertThat(viewModel.uiState.value.isLoading).isTrue()
        advanceUntilIdle()

        viewModel.loadRestaurants("N19GU")
        assertThat(viewModel.uiState.value.isLoading).isTrue()
    }
}
