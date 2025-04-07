/**
 * Comprehensive tests for the SearchBar Composable.
 *
 * Covers all aspects of search bar functionality:
 * - Initial state and basic interaction
 * - Search triggering mechanisms
 * - Input validation and formatting
 * - Integration with ViewModel
 * - Edge case handling
 */
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

    // Compose test rule that provides a host for composable testing
    @get:Rule
    val composeTestRule = createComposeRule()

    // Test dependencies initialized before each test
    private lateinit var fakeViewModel: FakeRestaurantViewModel
    private lateinit var fakeLogger: FakeLogger

    /**
     * Initializes test dependencies before each test case.
     * Ensures clean state for every test.
     */
    @Before
    fun setup() {
        fakeViewModel = FakeRestaurantViewModel()
        fakeLogger = FakeLogger()
    }

    /* --------------------------
     * 1. Basic Functionality Tests
     * -------------------------- */

    /**
     * Verifies the initial state of the search bar:
     * - Search bar exists with correct placeholder
     * - Search button is disabled when empty
     */
    @Test
    fun searchBar_displaysCorrectInitialState() {
        composeTestRule.setContent {
            SearchBar(
                value = "",
                onValueChange = {},
                onSearch = {}
            )
        }

        composeTestRule.onNodeWithTag("search_bar")
            .assertExists()

        composeTestRule.onNodeWithText("Enter UK postcode")
            .assertExists()

        composeTestRule.onNodeWithTag("search_button")
            .assertExists()
            .assertIsNotEnabled()
    }

    /**
     * Verifies that text input updates the search bar value.
     */
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

    /* ----------------------
     * 2. Search Trigger Tests
     * ---------------------- */

    /**
     * Verifies that clicking the search button triggers
     * the search callback with the current input value.
     */
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

    /**
     * Verifies that IME (keyboard) action doesn't trigger search
     * when input is empty.
     */
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

    /* ----------------------------
     * 3. Input Validation Tests
     * ---------------------------- */

    /**
     * Verifies that empty input keeps the search button disabled.
     */
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

    /**
     * Verifies that whitespace in input is trimmed before
     * being passed to the search callback.
     */
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

    /* -----------------------------------
     * 4. Integration with ViewModel Tests
     * ----------------------------------- */

    /**
     * Verifies that search triggers the ViewModel's restaurant loading
     * and updates the UI state accordingly.
     */
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

        // Wait for async operation to complete
        composeTestRule.waitUntil(5000) {
            fakeViewModel.uiState.value.hasSearched
        }

        assertTrue(fakeViewModel.uiState.value.hasSearched)
    }

    /* -------------------
     * 5. UI State Tests
     * ------------------- */

    /**
     * Verifies that loading state displays the progress indicator.
     */
    @Test
    fun loadingState_displaysIndicator() {
        fakeViewModel.setLoadingState()
        composeTestRule.setContent {
            RestaurantScreen(viewModel = fakeViewModel)
        }

        composeTestRule.onNodeWithTag("loading_indicator")
            .assertExists()
    }

    /**
     * Verifies that error state displays the error message.
     */
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

    /**
     * Verifies that success state displays the list of restaurants.
     */
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

    /* -------------------
     * 6. Edge Cases
     * ------------------- */

    /**
     * Verifies that very long input is handled correctly
     * without truncation or errors.
     */
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

    /**
     * Verifies that invalid input (special characters)
     * results in an error state.
     */
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

    /**
     * Verifies that IME (keyboard) action triggers search
     * when input is valid.
     */
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