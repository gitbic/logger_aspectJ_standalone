package ru.clevertec.enums;

public enum LoggingLevel {
    OFF,
    FATAL,
    ERROR,
    WARN,
    INFO,
    DEBUG,
    TRACE;

    public System.Logger.Level getLoggerLevel() {
        return System.Logger.Level.valueOf(this.toString());
    }
}
