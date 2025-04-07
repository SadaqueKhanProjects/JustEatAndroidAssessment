/**
 * Custom AndroidJUnitRunner that initializes Hilt for instrumentation tests.
 *
 * This test runner replaces the default Application with HiltTestApplication
 * to enable dependency injection in UI tests.
 */
package com.sadaquekhan.justeatassessment

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import dagger.hilt.android.testing.HiltTestApplication

class HiltTestRunner : AndroidJUnitRunner() {
    /**
     * Overrides the default application creation to use Hilt's test application.
     *
     * @param cl The class loader to use
     * @param name The name of the application class (ignored)
     * @param context The context to use
     * @return The created HiltTestApplication instance
     */
    override fun newApplication(
        cl: ClassLoader?,
        name: String?,
        context: Context?
    ): Application {
        return super.newApplication(cl, HiltTestApplication::class.java.name, context)
    }
}