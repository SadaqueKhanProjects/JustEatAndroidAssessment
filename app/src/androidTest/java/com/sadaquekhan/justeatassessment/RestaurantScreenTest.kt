package com.sadaquekhan.justeatassessment

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.sadaquekhan.justeatassessment.ui.screen.RestaurantScreen
import com.sadaquekhan.justeatassessment.viewmodel.RestaurantViewModel
import com.sadaquekhan.justeatassessment.domain.model.Restaurant
import com.sadaquekhan.justeatassessment.viewmodel.RestaurantUiState
import com.sadaquekhan.justeatassessment.domain.model.Address
import com.sadaquekhan.justeatassessment.ui.components.RestaurantItem
import com.sadaquekhan.justeatassessment.ui.components.SearchBar
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.whenever
import kotlinx.coroutines.flow.MutableStateFlow
import org.mockito.kotlin.mock

class RestaurantScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val mockViewModel = mock<RestaurantViewModel>()
    private val mockUiStateFlow = MutableStateFlow(RestaurantUiState()) // MutableStateFlow to simulate the state

    @Test
    fun testRestaurantScreen_loadingState() {
        // Given: ViewModel state is loading
        val loadingState = RestaurantUiState(isLoading = true)  // Pass all properties including isLoading
        whenever(mockViewModel.uiState).thenReturn(mockUiStateFlow)

        // Simulate the loading state
        mockUiStateFlow.value = loadingState

        composeTestRule.setContent {
            RestaurantScreen()
        }

        // Then: Assert loading indicator is displayed
        composeTestRule.onNodeWithTag("loading_indicator").assertIsDisplayed()
    }

    @Test
    fun testRestaurantScreen_emptyState() {
        // Given: ViewModel state is empty
        val emptyState = RestaurantUiState(isEmpty = true)  // Pass all properties including isEmpty
        whenever(mockViewModel.uiState).thenReturn(mockUiStateFlow)

        // Simulate the empty state
        mockUiStateFlow.value = emptyState

        composeTestRule.setContent {
            RestaurantScreen()
        }

        // Then: Assert empty state message is displayed
        composeTestRule.onNodeWithText("No restaurants found.").assertIsDisplayed()
    }

    @Test
    fun testRestaurantScreen_successState() {
        // Given: ViewModel state with a list of restaurants
        val mockRestaurants = listOf(
            Restaurant(
                id = "1",
                name = "Pizza Place",
                cuisines = listOf("Italian", "Vegan"),
                rating = 4.5,
                address = Address(firstLine = "123 Main Street", city = "London", postalCode = "EC1A1BB")
            ),
            Restaurant(
                id = "2",
                name = "Burger Joint",
                cuisines = listOf("American"),
                rating = 4.0,
                address = Address(firstLine = "456 Burger St.", city = "London", postalCode = "EC1A2BB")
            )
        )

        // Given: Full success state with restaurants
        val successState = RestaurantUiState(
            restaurants = mockRestaurants,
            isLoading = false,
            isEmpty = false,
            hasSearched = true
        )

        // Simulate success state
        mockUiStateFlow.value = successState
        whenever(mockViewModel.uiState).thenReturn(mockUiStateFlow)

        composeTestRule.setContent {
            RestaurantScreen()
        }

        // Then: Assert that restaurant names are displayed
        composeTestRule.onNodeWithText("Pizza Place").assertIsDisplayed()
        composeTestRule.onNodeWithText("Burger Joint").assertIsDisplayed()
    }

    @Test
    fun testSearchBar_input() {
        // Given: A SearchBar composable
        composeTestRule.setContent {
            SearchBar(
                value = "EC1A1BB",
                onValueChange = { /* no-op */ },
                onSearch = { /* mock search */ }
            )
        }

        // When: User types a search query
        composeTestRule.onNodeWithTag("search_bar").performTextInput("Pizza")

        // Then: Assert that the correct query is used
        composeTestRule.onNodeWithText("Pizza").assertIsDisplayed()
    }

    @Test
    fun testRestaurantItem_display() {
        // Given: A single restaurant item with name and rating
        val restaurant = Restaurant(
            id = "1",
            name = "Pizza Place",
            cuisines = listOf("Italian"),
            rating = 4.5,
            address = Address(firstLine = "123 Main Street", city = "London", postalCode = "EC1A1BB")
        )

        composeTestRule.setContent {
            RestaurantItem(restaurant = restaurant)
        }

        // Then: Assert that restaurant name and rating are displayed correctly
        composeTestRule.onNodeWithText("Pizza Place").assertIsDisplayed()
        composeTestRule.onNodeWithText("4.5").assertIsDisplayed()
    }
}