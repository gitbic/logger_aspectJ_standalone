package ru.clevertec.aspects;


import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import ru.clevertec.annotations.LogThisMethod;
import ru.clevertec.beans.JSong;
import ru.clevertec.constants.Constants;
import ru.clevertec.enums.LoggingLevel;

import java.time.temporal.ChronoUnit;


@Aspect
public class LoggingAspect {
    private final static Logger LOGGER = LogManager.getLogger(LoggingAspect.class);


    @Pointcut("execution(@ru.clevertec.annotations.* * *(..))")
    private void methodToBeLogging() {
    }

    @AfterReturning(pointcut = "methodToBeLogging()", returning = "resultOfMethodInvocation")
    public void logMethodInvocation(JoinPoint joinPoint, Object resultOfMethodInvocation) throws IllegalAccessException {

        JSong jSong = new JSong();
//        jSong.setPrettyString(true);

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = signature.getName();
        String className = joinPoint.getTarget().getClass().getName();


//        LoggingLevel loggingLevel = signature.getMethod().getAnnotation(LogThisMethod.class).loggingLevel();
        LOGGER.setLevel(Level.INFO);

        System.Logger.Level loggerLevel = signature
                .getMethod()
                .getAnnotation(LogThisMethod.class)
                .loggingLevel()
                .getLoggerLevel();

        LoggingLevel loggingLevel = signature
                .getMethod()
                .getAnnotation(LogThisMethod.class)
                .loggingLevel();


        System.Logger.Level level1 = System.Logger.Level.valueOf("INFO");
        org.slf4j.event.Level level2 = org.slf4j.event.Level.valueOf("DEBUG");

//        LOGGER.setLevel(level1);
//        LOGGER.setLevel(level2);
//        LOGGER.setLevel();


        Object[] args = joinPoint.getArgs();
        String inputArguments = Constants.NO_ARGUMENTS;
        if (args.length != 0) {
            inputArguments = jSong.setProcessedObject(args).serialize();
        }

        String returningResult = Constants.NO_RESULTS;
        if (resultOfMethodInvocation != null) {
            returningResult = jSong.setProcessedObject(resultOfMethodInvocation).serialize();
        }

        writeToLog(loggerLevel, className, methodName, Constants.STR_ARGUMENTS + inputArguments);
        writeToLog(loggerLevel, className, methodName, Constants.STR_RESULT + returningResult);

    }

    private void writeToLog(System.Logger.Level level, String className, String methodName, String jSonString) {
        LOGGER.log(Level.INFO, String.format(Constants.FSTRING_LOG_MSG,
                java.time.LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS),
                className,
                methodName,
                jSonString));

    }


}
