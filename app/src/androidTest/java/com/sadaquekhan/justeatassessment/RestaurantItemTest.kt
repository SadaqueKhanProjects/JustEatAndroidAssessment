package com.sadaquekhan.justeatassessment

import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.sadaquekhan.justeatassessment.ui.components.RestaurantItem
import com.sadaquekhan.justeatassessment.util.fake.FakeRestaurantFactory
import org.junit.Rule
import org.junit.Test

class RestaurantItemTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val testRestaurant = FakeRestaurantFactory.make(
        id = "test_123",
        name = "Test Restaurant",
        cuisines = listOf("Italian", "Vegan"),
        rating = 4.5,
        firstLine = "123 Test Street",
        city = "London",
        postalCode = "SW1A 1AA"
    )

    @Test
    fun displaysAllRestaurantInformation() {
        composeTestRule.setContent {
            RestaurantItem(restaurant = testRestaurant)
        }

        composeTestRule.onNodeWithTag("restaurant_item_test_123").assertExists()
        composeTestRule.onNodeWithTag("restaurant_name_test_123").assertTextEquals("Test Restaurant")
        composeTestRule.onNodeWithTag("restaurant_rating_test_123").assertTextEquals("Rating: 4.5")
        composeTestRule.onNodeWithTag("restaurant_cuisines_test_123").assertTextEquals("Cuisines: Italian, Vegan")
        composeTestRule.onNodeWithTag("restaurant_address_test_123").assertTextEquals("Address: 123 Test Street, London, SW1A 1AA")
    }

    @Test
    fun handlesMissingOptionalFields() {
        val minimalRestaurant = testRestaurant.copy(
            cuisines = emptyList(),
            address = testRestaurant.address.copy(firstLine = "")
        )

        composeTestRule.setContent {
            RestaurantItem(restaurant = minimalRestaurant)
        }

        composeTestRule.onNodeWithTag("restaurant_cuisines_test_123").assertDoesNotExist()
        composeTestRule.onNodeWithTag("restaurant_address_test_123").assertTextEquals("Address: London, SW1A 1AA")
    }
}
