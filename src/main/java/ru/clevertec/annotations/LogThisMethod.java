package ru.clevertec.annotations;

import ru.clevertec.enums.LoggingLevel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = ElementType.METHOD)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface LogThisMethod {
    LoggingLevel loggingLevel() default LoggingLevel.INFO;
}
