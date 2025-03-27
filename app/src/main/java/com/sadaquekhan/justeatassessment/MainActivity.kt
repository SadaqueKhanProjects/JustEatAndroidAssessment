package com.sadaquekhan.justeatassessment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sadaquekhan.justeatassessment.ui.screen.RestaurantScreen
import com.sadaquekhan.justeatassessment.ui.theme.JustEatAndroidAssessmentTheme
import com.sadaquekhan.justeatassessment.viewmodel.RestaurantViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JustEatAndroidAssessmentTheme {
                val viewModel: RestaurantViewModel = viewModel()
                RestaurantScreen(viewModel = viewModel)
            }
        }
    }
}
