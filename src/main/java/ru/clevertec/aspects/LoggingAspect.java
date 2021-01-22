package ru.clevertec.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import ru.clevertec.beans.JSong;
import ru.clevertec.constants.Constants;

import java.time.temporal.ChronoUnit;
import java.util.logging.Logger;


@Aspect
public class LoggingAspect {
    private static final Logger LOGGER = Logger.getLogger(LoggingAspect.class.getName());

    @Pointcut("execution(@ru.clevertec.annotations.* * *(..))")
    private void methodToBeLogging() {
    }

    @AfterReturning(pointcut = "methodToBeLogging()", returning = "resultOfMethodInvocation")
    public void logMethodInvocation(JoinPoint joinPoint, Object resultOfMethodInvocation) throws IllegalAccessException{
        System.out.println("AFTER RETURNINGGGGGGGGGGGGGGGGG");

        JSong jSong = new JSong();
        jSong.setPrettyString(true);


        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getName();
        //        String className = joinPoint.getSignature().getClass().getName();

        MethodSignature signature = (MethodSignature) joinPoint.getSignature(); // попробовать сверху поставить
//        LoggingLevel loggingLevel = signature.getMethod().getAnnotation(LogThisMethod.class).loggingLevel(); // из аннотации


        Object[] args = joinPoint.getArgs();
        String inputArguments = Constants.NO_ARGUMENTS;
        if (args != null) {
            jSong.setProcessedObject(args);
            inputArguments = jSong.serialize();
        }

        String returningResult = Constants.NO_RESULTS;
        if (resultOfMethodInvocation != null) {
            jSong.setProcessedObject(resultOfMethodInvocation);
            returningResult = jSong.serialize();
        }

        // фактори, а может System level
        LOGGER.info(String.format(Constants.FSTRING_LOG_MSG,
                java.time.LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS),
                className,
                methodName,
                Constants.STR_ARGUMENTS + inputArguments));

        LOGGER.info(String.format(Constants.FSTRING_LOG_MSG,
                java.time.LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS),
                className,
                methodName,
                Constants.STR_RESULT + returningResult));


//        System.out.println("+++++++++++++++");
//        if (signature.getMethod().isAnnotationPresent(CostMetric.class)) {
//            CostMetric costMetricAnnotation = signature.getMethod().getAnnotation(CostMetric.class);
//            System.out.println("-------------");
//        }

    }


}
