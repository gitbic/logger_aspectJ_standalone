package ru.clevertec.enums;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.clevertec.aspects.LoggingAspect;

public enum LoggingLevel {

    ERROR {
        @Override
        public void log(String message) {
            LOGGER.error(message);
        }
    },
    WARN {
        @Override
        public void log(String message) {
            LOGGER.warn(message);
        }
    },
    INFO {
        @Override
        public void log(String message) {
            LOGGER.info(message);
        }
    },
    DEBUG {
        @Override
        public void log(String message) {
            LOGGER.debug(message);
        }
    },
    TRACE {
        @Override
        public void log(String message) {
            LOGGER.trace(message);
        }
    };

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class.getName());

    public abstract void log(String message);

}
