package com.sadaquekhan.justeatassessment

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.sadaquekhan.justeatassessment.domain.model.Address
import com.sadaquekhan.justeatassessment.domain.model.Restaurant
import com.sadaquekhan.justeatassessment.ui.components.SearchBar
import com.sadaquekhan.justeatassessment.ui.screen.RestaurantScreen
import com.sadaquekhan.justeatassessment.util.FakeLogger
import com.sadaquekhan.justeatassessment.util.FakeRestaurantViewModel
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SearchBarTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var fakeViewModel: FakeRestaurantViewModel
    private lateinit var fakeLogger: FakeLogger

    @Before
    fun setup() {
        fakeViewModel = FakeRestaurantViewModel()
        fakeLogger = FakeLogger()
    }

    // 1. Basic Functionality Tests
    @Test
    fun searchBar_displaysCorrectInitialState() {
        composeTestRule.setContent {
            SearchBar(
                value = "",
                onValueChange = {},
                onSearch = {}
            )
        }

        // Verify the search bar exists and shows placeholder
        composeTestRule.onNodeWithTag("search_bar")
            .assertExists()

        // Verify placeholder text is shown
        composeTestRule.onNodeWithText("Enter UK postcode")
            .assertExists()

        // Verify the search button state
        composeTestRule.onNodeWithTag("search_button")
            .assertExists()
            .assertIsNotEnabled()
    }

    @Test
    fun typingUpdatesSearchBarValue() {
        var currentValue = ""
        composeTestRule.setContent {
            SearchBar(
                value = currentValue,
                onValueChange = { currentValue = it },
                onSearch = {}
            )
        }

        composeTestRule.onNodeWithTag("search_bar")
            .performTextInput("SW1A")

        assertEquals("SW1A", currentValue)
    }

    // 2. Search Trigger Tests
    @Test
    fun buttonClick_triggersSearchWithValidInput() {
        var receivedValue = ""
        composeTestRule.setContent {
            SearchBar(
                value = "SW1A1AA",
                onValueChange = {},
                onSearch = { receivedValue = it }
            )
        }

        composeTestRule.onNodeWithTag("search_button")
            .performClick()

        assertEquals("SW1A1AA", receivedValue)
    }


    @Test
    fun keyboardAction_doesNotTriggerWithEmptyInput() {
        var searchTriggered = false

        composeTestRule.setContent {
            SearchBar(
                value = "",
                onValueChange = {},
                onSearch = { searchTriggered = true }
            )
        }

        composeTestRule.onNodeWithTag("search_bar")
            .performImeAction()

        assertFalse(searchTriggered)
    }

    // 3. Input Validation Tests
    @Test
    fun emptyInput_disablesSearchButton() {
        composeTestRule.setContent {
            SearchBar(
                value = "",
                onValueChange = {},
                onSearch = {}
            )
        }

        composeTestRule.onNodeWithTag("search_button")
            .assertIsNotEnabled()
    }

    @Test
    fun whitespaceInput_trimsValueBeforeSearch() {
        var receivedValue = ""
        composeTestRule.setContent {
            SearchBar(
                value = "  SW1A 1AA  ",
                onValueChange = {},
                onSearch = { receivedValue = it }
            )
        }

        composeTestRule.onNodeWithTag("search_button")
            .performClick()

        assertEquals("SW1A 1AA", receivedValue)
    }

    // 4. Integration with ViewModel Tests
    @Test
    fun search_triggersViewModelLoad() {
        composeTestRule.setContent {
            SearchBar(
                value = "W1J7NT",
                onValueChange = {},
                onSearch = { fakeViewModel.loadRestaurants(it) }
            )
        }

        composeTestRule.onNodeWithTag("search_button")
            .performClick()

        composeTestRule.waitUntil(5000) {
            fakeViewModel.uiState.value.hasSearched
        }

        assertTrue(fakeViewModel.uiState.value.hasSearched)
    }

    // 5. UI State Tests
    @Test
    fun loadingState_displaysIndicator() {
        fakeViewModel.setLoadingState()
        composeTestRule.setContent {
            RestaurantScreen(viewModel = fakeViewModel)
        }

        composeTestRule.onNodeWithTag("loading_indicator")
            .assertExists()
    }

    @Test
    fun errorState_displaysMessage() {
        val errorMsg = "Network error"
        fakeViewModel.setError(errorMsg)
        composeTestRule.setContent {
            RestaurantScreen(viewModel = fakeViewModel)
        }

        composeTestRule.onNodeWithText(errorMsg)
            .assertExists()
    }

    @Test
    fun successState_displaysRestaurants() {
        val testRestaurants = listOf(
            Restaurant(
                id = "1",
                name = "Test Place",
                rating = 4.2,
                cuisines = listOf("British"),
                address = Address("1 Test Rd", "London", "SW1A1AA")
            )
        )
        fakeViewModel.setSuccessState(testRestaurants)

        composeTestRule.setContent {
            RestaurantScreen(viewModel = fakeViewModel)
        }

        composeTestRule.onNodeWithText("Test Place")
            .assertExists()
    }

    // 6. Edge Cases
    @Test
    fun veryLongInput_handlesCorrectly() {
        var receivedValue = ""
        val longInput = "A".repeat(100)

        composeTestRule.setContent {
            SearchBar(
                value = longInput,
                onValueChange = {},
                onSearch = { receivedValue = it }
            )
        }

        composeTestRule.onNodeWithTag("search_button")
            .performClick()

        assertEquals(longInput, receivedValue)
    }

    @Test
    fun specialCharacters_invalidInput() {
        composeTestRule.setContent {
            SearchBar(
                value = "SW1@1AA",
                onValueChange = {},
                onSearch = { fakeViewModel.loadRestaurants(it) }
            )
        }

        composeTestRule.onNodeWithTag("search_button")
            .performClick()

        composeTestRule.waitUntil(5000) {
            fakeViewModel.uiState.value.errorMessage != null
        }

        assertNotNull(fakeViewModel.uiState.value.errorMessage)
    }

    @Test
    fun keyboardAction_triggersSearchWithValidInput() {
        var receivedValue = ""
        composeTestRule.setContent {
            SearchBar(
                value = "SW1A1AA",
                onValueChange = {},
                onSearch = { receivedValue = it }
            )
        }

        composeTestRule.onNodeWithTag("search_bar")
            .performImeAction()

        assertEquals("SW1A1AA", receivedValue)
    }
}