package applicationCore.aop;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class ExceptionHandler {
    @Around("@annotation(applicationCore.aop.annotations.annotationExceptions.HandleExceptions)")
    public Object handleException(ProceedingJoinPoint joinPoint){
        try{
            return joinPoint.proceed();
        } catch (Throwable e){
            System.out.println(e.getMessage());
            return null;
        }
    }
}
