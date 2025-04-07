/**
 * Instrumentation tests for the RestaurantScreen Composable.
 *
 * Verifies the UI behavior for all possible states of the restaurant screen:
 * - Empty state (no restaurants found)
 * - Loading state (progress indicator)
 * - Success state (restaurants displayed)
 * - Error state (error message shown)
 */
package com.sadaquekhan.justeatassessment

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.sadaquekhan.justeatassessment.ui.screen.RestaurantScreen
import com.sadaquekhan.justeatassessment.util.FakeRestaurantViewModel
import org.junit.Rule
import org.junit.Test

class RestaurantScreenTest {

    // Compose test rule that provides a host for composable testing
    @get:Rule
    val composeTestRule = createComposeRule()

    // Fake ViewModel that allows controlled state manipulation for tests
    private val fakeViewModel = FakeRestaurantViewModel()

    /**
     * Verifies that the empty state displays the correct message
     * when no restaurants are found for the given search.
     */
    @Test
    fun testEmptyState_showsNoRestaurantsFound() {
        fakeViewModel.setEmptyState()

        composeTestRule.setContent {
            RestaurantScreen(viewModel = fakeViewModel)
        }

        composeTestRule.onNodeWithText("No restaurants found.").assertIsDisplayed()
    }

    /**
     * Verifies that the loading state displays a progress indicator
     * while restaurants are being fetched.
     */
    @Test
    fun testLoadingState_showsProgressBar() {
        fakeViewModel.setLoadingState()

        composeTestRule.setContent {
            RestaurantScreen(viewModel = fakeViewModel)
        }

        composeTestRule.onNodeWithTag("loading_indicator").assertIsDisplayed()
    }

    /**
     * Verifies that the success state correctly displays
     * the list of restaurants returned from the API.
     */
    @Test
    fun testSuccessState_showsRestaurants() {
        fakeViewModel.setSuccessState()

        composeTestRule.setContent {
            RestaurantScreen(viewModel = fakeViewModel)
        }

        // Verify sample restaurants from the fake ViewModel are displayed
        composeTestRule.onNodeWithText("Pizza Place").assertIsDisplayed()
        composeTestRule.onNodeWithText("Burger Joint").assertIsDisplayed()
    }

    /**
     * Verifies that the error state displays the correct error message
     * when restaurant fetching fails.
     */
    @Test
    fun testErrorState_showsErrorMessage() {
        val errorMessage = "No internet connection."
        fakeViewModel.setError(errorMessage)

        composeTestRule.setContent {
            RestaurantScreen(viewModel = fakeViewModel)
        }

        composeTestRule.onNodeWithText(errorMessage).assertIsDisplayed()
    }
}