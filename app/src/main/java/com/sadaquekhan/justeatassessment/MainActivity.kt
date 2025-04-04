package com.sadaquekhan.justeatassessment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import com.sadaquekhan.justeatassessment.ui.screen.RestaurantScreen
import com.sadaquekhan.justeatassessment.ui.theme.JustEatAndroidAssessmentTheme
import com.sadaquekhan.justeatassessment.viewmodel.RestaurantViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * Main entry point of the app, launched on startup.
 *
 * Sets up Hilt-based dependency injection and renders the main Composable content.
 * Uses Jetpack Compose and Hilt to initialize the `RestaurantViewModel` via `hiltViewModel()`.
 *
 * Responsibilities:
 * - Inject and bind the ViewModel
 * - Set the app theme and content layout
 * - Pass ViewModel to the `RestaurantScreen` Composable
 *
 * @see RestaurantScreen – The UI rendered inside the content
 * @see RestaurantViewModel – ViewModel driving the UI state
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Compose UI setup
        setContent {
            JustEatAndroidAssessmentTheme {
                // Hilt-provided ViewModel scoped to this activity
                val viewModel: RestaurantViewModel = hiltViewModel()

                // Launch the screen with injected ViewModel
                RestaurantScreen(viewModel = viewModel)
            }
        }
    }
}
