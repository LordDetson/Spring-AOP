package by.babanin.logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Aspect
public class StatisticsAspect {
    private Map<Class<?>, Integer> counter;

    public StatisticsAspect() {
        counter = new HashMap<>();
    }

    @AfterReturning("execution(* *.logEvent(..))")
    public void count(JoinPoint joinPoint) {
        Class<?> aClass = joinPoint.getTarget().getClass();
        if (!counter.containsKey(aClass)) counter.put(aClass, 0);
        counter.put(aClass, counter.get(aClass) + 1);
    }

    public Map<Class<?>, Integer> getCounter() {
        return counter;
    }
}
