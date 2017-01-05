package tk.zhangh.log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * 日志切面定义
 * Created by ZhangHao on 2017/1/4.
 */
@Aspect
public class LogAspect {
    private Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Pointcut("execution(* tk.zhangh.java..*.*(..))")
    private void selectAll() {
    }

    @Around("selectAll()")
    public Object doBasicProfiling(ProceedingJoinPoint pjp) throws Throwable {
        String className = pjp.getTarget().getClass().getName();
        String methodName = pjp.getSignature().getName();
        String signature = className + "." + methodName;
        Object object;
        try {
            logger.info("{} will do with {}", signature, Arrays.asList(pjp.getArgs()));
            object = pjp.proceed();
        } catch (Throwable throwable) {
            logger.info("{} has occurred exception:", throwable);
            throw throwable;
        }
        logger.info("{} has finished,result is\n{}", signature, object);
        return object;
    }
}
