package ru.clevertec.aspects;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import ru.clevertec.annotations.LogThisMethod;
import ru.clevertec.beans.JSong;
import ru.clevertec.constants.Constants;
import ru.clevertec.enums.LoggingLevel;


@Aspect
public class LoggingAspect {

    @Pointcut("execution(@ru.clevertec.annotations.* * *(..))")
    private void loggedMethod() {
    }

    @AfterReturning(pointcut = "loggedMethod()", returning = "resultOfMethodInvocation")
    public void logAfterReturning(JoinPoint joinPoint, Object resultOfMethodInvocation) throws IllegalAccessException {

        JSong jSong = new JSong();
        jSong.setPrettyString(true);

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = signature.getName();
        String className = joinPoint.getTarget().getClass().getName();

        LoggingLevel loggingLevel = signature
                .getMethod()
                .getAnnotation(LogThisMethod.class)
                .loggingLevel();


        Object[] args = joinPoint.getArgs();
        String inputArguments = Constants.NO_ARGUMENTS;
        if (args.length != 0) {
            inputArguments = jSong.setProcessedObject(args).serialize();
        }

        String message = String.format(Constants.FSTRING_LOG_MSG,
                className,
                methodName,
                Constants.STR_ARGUMENTS + inputArguments);

        loggingLevel.log(message);


        String returningResult = Constants.NO_RESULTS;
        if (resultOfMethodInvocation != null) {
            returningResult = jSong.setProcessedObject(resultOfMethodInvocation).serialize();
        }

        message = String.format(Constants.FSTRING_LOG_MSG,
                className,
                methodName,
                Constants.STR_RESULT + returningResult);

        loggingLevel.log(message);

    }

    @AfterThrowing(value = "loggedMethod()", throwing = "e")
    public void logException(JoinPoint joinPoint, Exception e) {
        LoggingLevel.ERROR.log(e.getMessage());
    }

}
