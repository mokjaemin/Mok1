package com.ReservationServer1.AOP;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class logAOP {


  private final Logger logger = LoggerFactory.getLogger(logAOP.class);

  
  // 회원 가입
  @Around("execution(* com.ReservationServer1.controller.MemberController.registerMember(..))")
  public Object registerMemberController(ProceedingJoinPoint joinPoint) throws Throwable{
    logger.info("[MemberController] register(회원가입) 호출");
    Object result = joinPoint.proceed();
    logger.info("[MemberController] register(회원가입) 종료");
    return result;
  }
  
  @Around("execution(* com.ReservationServer1.service.Impl.MemberServiceImpl.registerMember(..))")
  public Object registerMemberService(ProceedingJoinPoint joinPoint) throws Throwable{
    logger.info("[MemberService] register(회원가입) 호출");
    Object result = joinPoint.proceed();
    logger.info("[MemberService] register(회원가입) 종료");
    return result;
  }
  
  @Around("execution(* com.ReservationServer1.DAO.Impl.MemberDAOImpl.registerMember(..))")
  public Object registerMemberDAO(ProceedingJoinPoint joinPoint) throws Throwable{
    logger.info("[MemberDAO] register(회원가입) 호출");
    Object result = joinPoint.proceed();
    logger.info("[MemberDAO] register(회원가입) 종료");
    return result;
  }
  
  
}
