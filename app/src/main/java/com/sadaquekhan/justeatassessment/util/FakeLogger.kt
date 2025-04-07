package com.sadaquekhan.justeatassessment.util

/**
 * Fake logger that captures logs for verification in tests.
 * Replaces Android's Log class in test environments.
 */
class FakeLogger : Logger {
    private val logs = mutableListOf<LogEntry>()

    data class LogEntry(
        val tag: String,
        val message: String,
        val isError: Boolean
    )

    override fun debug(tag: String, message: String) {
        logs.add(LogEntry(tag, message, false))
    }

    override fun error(tag: String, message: String) {
        // Fallback to exception class name if message is null
        val formatted = if (message.isBlank()) "Unknown error" else message
        logs.add(LogEntry(tag, formatted, true))
    }

    fun getLogs() = logs.toList()
    fun clear() = logs.clear()
}
