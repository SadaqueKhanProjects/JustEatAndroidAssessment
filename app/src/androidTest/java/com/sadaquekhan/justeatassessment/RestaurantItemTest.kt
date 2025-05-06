/**
 * Instrumentation tests for the RestaurantItem Composable.
 *
 * Verifies that restaurant information is displayed correctly and handles edge cases
 * like missing optional fields.
 */
package com.sadaquekhan.justeatassessment

import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.sadaquekhan.justeatassessment.domain.model.Address
import com.sadaquekhan.justeatassessment.domain.model.Restaurant
import com.sadaquekhan.justeatassessment.ui.components.RestaurantItem
import org.junit.Rule
import org.junit.Test

class RestaurantItemTest {

    // Compose test rule that provides a host for composable testing
    @get:Rule
    val composeTestRule = createComposeRule()

    // Test restaurant data used across multiple test cases
    private val testRestaurant = Restaurant(
        id = "test_123",
        name = "Test Restaurant",
        cuisines = listOf("Italian", "Vegan"),
        rating = 4.5,
        address = Address(
            firstLine = "123 Test Street",
            city = "London",
            postalCode = "SW1A 1AA"
        )
    )

    /**
     * Verifies that all restaurant information is displayed correctly:
     * - Name
     * - Rating (formatted)
     * - Cuisines (joined)
     * - Address (formatted)
     */
    @Test
    fun displaysAllRestaurantInformation() {
        composeTestRule.setContent {
            RestaurantItem(restaurant = testRestaurant)
        }

        // Verify container exists
        composeTestRule.onNodeWithTag("restaurant_item_test_123")
            .assertExists()

        // Verify name
        composeTestRule.onNodeWithTag("restaurant_name_test_123")
            .assertExists()
            .assertTextEquals("Test Restaurant")

        // Verify formatted rating
        composeTestRule.onNodeWithTag("restaurant_rating_test_123")
            .assertExists()
            .assertTextEquals("Rating: 4.5")

        // Verify cuisines
        composeTestRule.onNodeWithTag("restaurant_cuisines_test_123")
            .assertExists()
            .assertTextEquals("Cuisines: Italian, Vegan")

        // Verify formatted address
        composeTestRule.onNodeWithTag("restaurant_address_test_123")
            .assertExists()
            .assertTextEquals("Address: 123 Test Street, London, SW1A 1AA")
    }

    /**
     * Verifies behavior when optional fields are missing:
     * - Empty cuisines list should hide cuisines section
     * - Empty address line should be omitted from display
     */
    @Test
    fun handlesMissingOptionalFields() {
        val minimalRestaurant = testRestaurant.copy(
            cuisines = emptyList(),
            address = testRestaurant.address.copy(firstLine = "")
        )

        composeTestRule.setContent {
            RestaurantItem(restaurant = minimalRestaurant)
        }

        // Cuisines should not appear when empty
        composeTestRule.onNodeWithTag("restaurant_cuisines_test_123")
            .assertDoesNotExist()

        // Address should still show available parts
        composeTestRule.onNodeWithTag("restaurant_address_test_123")
            .assertTextEquals("Address: London, SW1A 1AA")
    }
}