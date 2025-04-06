package com.sadaquekhan.justeatassessment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.sadaquekhan.justeatassessment.ui.screen.RestaurantScreen
import com.sadaquekhan.justeatassessment.ui.theme.JustEatAndroidAssessmentTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * MainActivity is the entry point of the app.
 * It is an Hilt-injected activity that sets up the content view using Jetpack Compose.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    /**
     * onCreate is called when the activity is created.
     * It sets the content of the activity using Jetpack Compose.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Applying the theme to the app
            JustEatAndroidAssessmentTheme {
                // Set the RestaurantScreen composable as the UI
                RestaurantScreen()
            }
        }
    }
}