package applicationCore.aop;

import applicationCore.aop.annotations.annotationLoggers.LoggerMark;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;



@Component
@Aspect
public class HandlerLogger {
    //"src", "main", "resources", "applicationCoreLogs", "serviceLogs", "serviceLogs.txt"
    private final Logger log = LoggerFactory.getLogger(HandlerLogger.class);

    @Around("@annotation(loggerMark)")
    public Object logger(ProceedingJoinPoint joinPoint, LoggerMark loggerMark) throws Throwable {
        try{
            log.info("Вызван метод: {}", joinPoint.getSignature().getName());
            long startTime = System.currentTimeMillis();
            Object result = joinPoint.proceed();
            long workTime = System.currentTimeMillis() - startTime;
            log.info("Успешно завершает работу метод: {}. Время его работы: {}. {}",
                    joinPoint.getSignature().getName(),
                    workTime,
                    loggerMark.getMessage());
            return result;
        } catch(Throwable e){
            log.error("Выброшено исключение {} в методе {} по причине {}",
                    e.toString(),
                    joinPoint.getSignature().getName(),
                    e.getMessage());
            throw e;
        }
    }
}
