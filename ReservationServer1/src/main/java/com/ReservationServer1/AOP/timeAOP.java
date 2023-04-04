package com.ReservationServer1.AOP;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.ReservationServer1.controller.MemberController;

@Component
@Aspect
public class timeAOP {

    @Around("execution(* com.ReservationServer1..*(..))")
    public Object logPerf(ProceedingJoinPoint pjp) throws Throwable {
        long begin = System.currentTimeMillis();
        Object reVal = pjp.proceed();
//        System.out.println(System.currentTimeMillis() - begin); 
        return reVal;
    }
}
