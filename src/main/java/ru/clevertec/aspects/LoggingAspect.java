package ru.clevertec.aspects;

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
import java.util.logging.Logger;


@Aspect
public class LoggingAspect {
    private static final Logger LOGGER = Logger.getLogger(LoggingAspect.class.getName());

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


        LoggingLevel loggingLevel = signature.getMethod().getAnnotation(LogThisMethod.class).loggingLevel();


        Object[] args = joinPoint.getArgs();
        String inputArguments = Constants.NO_ARGUMENTS;
        if (args.length != 0) {
            inputArguments = jSong.setProcessedObject(args).serialize();
        }

        String returningResult = Constants.NO_RESULTS;
        if (resultOfMethodInvocation != null) {
            returningResult = jSong.setProcessedObject(resultOfMethodInvocation).serialize();
        }

        writeToLog(className, methodName, Constants.STR_ARGUMENTS + inputArguments);
        writeToLog(className, methodName, Constants.STR_RESULT + returningResult);

    }

    private void writeToLog(String className, String methodName, String jSonString) {
        LOGGER.info(String.format(Constants.FSTRING_LOG_MSG,
                java.time.LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS),
                className,
                methodName,
                jSonString));
    }


}
