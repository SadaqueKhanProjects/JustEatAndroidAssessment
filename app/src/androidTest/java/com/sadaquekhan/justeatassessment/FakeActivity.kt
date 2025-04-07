/**
 * A fake Activity used for testing purposes in the Just Eat Assessment app.
 *
 * This provides an empty Compose container that can be used as a test host for UI components
 * without requiring a real Activity. It's particularly useful for instrumentation tests.
 */
package com.sadaquekhan.justeatassessment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier

class FakeActivity : ComponentActivity() {
    /**
     * Creates an empty Compose Surface that fills the entire screen.
     * This serves as a container for testing individual UI components in isolation.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(modifier = Modifier.fillMaxSize()) {
                // Empty container for tests
            }
        }
    }
}