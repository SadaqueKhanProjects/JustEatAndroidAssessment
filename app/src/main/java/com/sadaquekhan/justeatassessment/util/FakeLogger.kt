package com.sadaquekhan.justeatassessment.util

/**
 * Test-safe implementation of the Logger interface.
 * Prints to the console instead of relying on Android-specific logging.
 * Use this in JVM-based unit tests to avoid runtime exceptions.
 */
class FakeLogger : Logger {

    /**
     * Simulates debug-level logging by printing to standard output.
     */
    override fun debug(tag: String, message: String) {
        println("DEBUG: [$tag] $message")
    }

    /**
     * Simulates error-level logging by printing to standard output.
     */
    override fun error(tag: String, message: String) {
        println("ERROR: [$tag] $message")
    }
}