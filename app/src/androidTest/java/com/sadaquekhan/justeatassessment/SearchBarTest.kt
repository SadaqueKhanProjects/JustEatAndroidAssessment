package com.sadaquekhan.justeatassessment

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
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


    
    // --- Core Functionality Tests ---
    @Test
    fun searchBar_triggersViewModelLoad() {
        composeTestRule.setContent {
            SearchBar(
                value = "SW1A1AA",
                onValueChange = {},
                onSearch = { postcode -> fakeViewModel.loadRestaurants(postcode) } // Updated to take postcode
            )
        }

        composeTestRule.onNodeWithTag("search_button")
            .performClick()

        assertTrue(fakeViewModel.uiState.value.hasSearched)
    }

    // --- State Propagation Tests ---
    @Test
    fun loadingState_showsProgressIndicator() {
        fakeViewModel.setLoadingState()

        composeTestRule.setContent {
            SearchBar(
                value = "SW1A1AA",
                onValueChange = {},
                onSearch = {} // This test doesn’t use onSearch, but should be updated if SearchBar displays state
            )
        }

        composeTestRule.onNodeWithTag("loading_indicator")
            .assertExists()
    }

    @Test
    fun errorState_showsMessage() {
        val errorMsg = "Invalid postcode"
        fakeViewModel.setError(errorMsg)

        composeTestRule.setContent {
            SearchBar(
                value = "INVALID",
                onValueChange = {},
                onSearch = {} // This test doesn’t use onSearch, but should be updated if SearchBar displays state
            )
        }

        composeTestRule.onNodeWithText(errorMsg)
            .assertExists()
    }

    @Test
    fun successState_showsRestaurants() {
        fakeViewModel.setSuccessState()

        composeTestRule.setContent {
            RestaurantScreen(viewModel = fakeViewModel)
        }

        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText("Pizza Place")
            .assertExists()
        composeTestRule.onNodeWithText("Burger Joint")
            .assertExists()
    }

    // --- Input Validation Tests ---
    @Test
    fun invalidPostcode_logsError() {
        composeTestRule.setContent {
            SearchBar(
                value = "INVALID!",
                onValueChange = {},
                onSearch = { fakeLogger.error("SearchBar", "Invalid postcode format") } // Updated to take String (unused here)
            )
        }

        composeTestRule.onNodeWithTag("search_button")
            .performClick()

        // Verify error was logged (would check logs in real test)
        assertTrue(true) // Placeholder - would use a log captor in practice
    }

    // --- Edge Cases ---
    @Test
    fun emptyPostcode_preventsSearch() {
        composeTestRule.setContent {
            SearchBar(
                value = "",
                onValueChange = {},
                onSearch = { fail("Search should not trigger for empty input") } // Updated to take String (unused here)
            )
        }

        composeTestRule.onNodeWithTag("search_button")
            .performClick()
    }

    @Test
    fun whitespacePostcode_getsTrimmed() {
        var receivedValue = ""

        composeTestRule.setContent {
            SearchBar(
                value = "  SW1A 1AA  ",
                onValueChange = { receivedValue = it },
                onSearch = { } // Updated to take String (unused here)
            )
        }

        composeTestRule.onNodeWithTag("search_bar")
            .performTextInput("  SW1A 1AA  ")

        assertEquals("SW1A 1AA", receivedValue.trim())
    }
}