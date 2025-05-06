package com.sadaquekhan.justeatassessment

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.sadaquekhan.justeatassessment.ui.screen.RestaurantScreen
import com.sadaquekhan.justeatassessment.util.fake.FakeRestaurantFactory
import com.sadaquekhan.justeatassessment.util.fake.FakeRestaurantViewModel
import org.junit.Rule
import org.junit.Test

class RestaurantScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val fakeViewModel = FakeRestaurantViewModel()

    @Test
    fun testEmptyState_showsNoRestaurantsFound() {
        fakeViewModel.setEmptyState()

        composeTestRule.setContent {
            RestaurantScreen(viewModel = fakeViewModel)
        }

        composeTestRule.onNodeWithText("No restaurants found.").assertIsDisplayed()
    }

    @Test
    fun testLoadingState_showsProgressBar() {
        fakeViewModel.setLoadingState()

        composeTestRule.setContent {
            RestaurantScreen(viewModel = fakeViewModel)
        }

        composeTestRule.onNodeWithTag("loading_indicator").assertIsDisplayed()
    }

    @Test
    fun testSuccessState_showsRestaurants() {
        fakeViewModel.setSuccessState(
            listOf(
                FakeRestaurantFactory.make(name = "Pizza Place"),
                FakeRestaurantFactory.make(name = "Burger Joint")
            )
        )

        composeTestRule.setContent {
            RestaurantScreen(viewModel = fakeViewModel)
        }

        composeTestRule.onNodeWithText("Pizza Place").assertIsDisplayed()
        composeTestRule.onNodeWithText("Burger Joint").assertIsDisplayed()
    }

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
