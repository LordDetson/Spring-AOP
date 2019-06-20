package by.babanin.logger;

import by.babanin.model.Event;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingAspect {

    public static final int MAX_ALLOWED = 4;
    private StatisticsAspect statisticsAspect;
    private EventLogger eventLogger;

    public LoggingAspect(StatisticsAspect statisticsAspect, EventLogger fileLogger) {
        this.statisticsAspect = statisticsAspect;
        this.eventLogger = fileLogger;
    }

    @Pointcut("execution(* *.logEvent(..))")
    public void allLogEventMethods() {}

    @Pointcut("execution(* ConsoleLogger.logEvent(..))")
    public void consoleLoggerMethods() {}

    @Before("allLogEventMethods()")
    public void logBefore(JoinPoint joinPoint) {
        System.out.println("BEFORE: " +
                joinPoint.getTarget().getClass().getSimpleName() + " " +
                joinPoint.getSignature().getName());
    }

    @AfterReturning(pointcut="allLogEventMethods()",
            returning="retVal")
    public void logAfter(Object retVal) {
        System.out.println("AFTER_RET: " + retVal);
    }

    @AfterThrowing(pointcut="allLogEventMethods()",
            throwing="ex")
    public void logAfterThrow(Throwable ex) {
        System.out.println("AFTER_THR: " + ex);
    }

    @Around("consoleLoggerMethods() && args(evt)")
    public void aroundLogEvent(ProceedingJoinPoint proceedingJoinPoint, Object evt) throws Throwable {
        Integer integer = statisticsAspect.getCounter().get(ConsoleLogger.class);
        if (integer == null) integer = 0;
        if (integer < MAX_ALLOWED) {
            proceedingJoinPoint.proceed(new Object[]{evt});
        } else {
            eventLogger.logEvent((Event) evt);
        }
    }
}
