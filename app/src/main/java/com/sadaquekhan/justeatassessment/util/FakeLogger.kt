package com.sadaquekhan.justeatassessment.util

/**
 * Fake logger for use in unit tests.
 * Captures logs internally for assertions and inspection.
 */
class FakeLogger : Logger {

    private val logs = mutableListOf<LogEntry>()

    /**
     * Represents a single log entry.
     *
     * @property tag Logging tag
     * @property message Message content
     * @property isError True if this was an error log
     */
    data class LogEntry(
        val tag: String,
        val message: String,
        val isError: Boolean
    )

    override fun debug(tag: String, message: String) {
        logs.add(LogEntry(tag, message, isError = false))
    }

    override fun error(tag: String, message: String) {
        val formatted = if (message.isBlank()) "Unknown error" else message
        logs.add(LogEntry(tag, formatted, isError = true))
    }

    fun getLogs(): List<LogEntry> = logs.toList()

    fun clear() = logs.clear()
}
