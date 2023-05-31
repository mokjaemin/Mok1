package com.ReservationServer1.AOP;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class memberLogAOP {


  private final Logger logger = LoggerFactory.getLogger(memberLogAOP.class);

  
  // Member Controller
  @Around("execution(* com.ReservationServer1.controller.MemberController.registerMember(..))")
  public Object registerMemberController(ProceedingJoinPoint joinPoint) throws Throwable{
    logger.info("[MemberController] register(회원가입) 호출");
    Object result = joinPoint.proceed();
    logger.info("[MemberController] register(회원가입) 종료");
    return result;
  }
  
  @Around("execution(* com.ReservationServer1.controller.MemberController.loginMember(..))")
  public Object loginMemberController(ProceedingJoinPoint joinPoint) throws Throwable{
    logger.info("[MemberController] login(로그인) 호출");
    Object result = joinPoint.proceed();
    logger.info("[MemberController] login(로그인) 종료");
    return result;
  }
  
  @Around("execution(* com.ReservationServer1.controller.MemberController.findPwdMember(..))")
  public Object findPwdMemberController(ProceedingJoinPoint joinPoint) throws Throwable{
    logger.info("[MemberController] findPwd(비밀번호 찾기) 호출");
    Object result = joinPoint.proceed();
    logger.info("[MemberController] findPwd(비밀번호 찾기) 종료");
    return result;
  }
  
  @Around("execution(* com.ReservationServer1.controller.MemberController.modPwdMember(..))")
  public Object modPwdMemberController(ProceedingJoinPoint joinPoint) throws Throwable{
    logger.info("[MemberController] modPwd(비밀번호 수정) 호출");
    Object result = joinPoint.proceed();
    logger.info("[MemberController] modPwd(비밀번호 수정) 종료");
    return result;
  }
  
  @Around("execution(* com.ReservationServer1.controller.MemberController.modInfoMember(..))")
  public Object modInfoMemberController(ProceedingJoinPoint joinPoint) throws Throwable{
    logger.info("[MemberController] modInfo(회원정보 수정) 호출");
    Object result = joinPoint.proceed();
    logger.info("[MemberController] modInfo(회원정보 수정) 종료");
    return result;
  }
  
  @Around("execution(* com.ReservationServer1.controller.MemberController.delMember(..))")
  public Object delMemberController(ProceedingJoinPoint joinPoint) throws Throwable{
    logger.info("[MemberController] del(회원정보 삭제) 호출");
    Object result = joinPoint.proceed();
    logger.info("[MemberController] del(회원정보 삭제) 종료");
    return result;
  }
  
  
  
  
  // Member Service
  @Around("execution(* com.ReservationServer1.service.Impl.MemberServiceImpl.registerMember(..))")
  public Object registerMemberService(ProceedingJoinPoint joinPoint) throws Throwable{
    logger.info("[MemberService] register(회원가입) 호출");
    Object result = joinPoint.proceed();
    logger.info("[MemberService] register(회원가입) 종료");
    return result;
  }
  
  @Around("execution(* com.ReservationServer1.service.Impl.MemberServiceImpl.loginMember(..))")
  public Object loginMemberService(ProceedingJoinPoint joinPoint) throws Throwable{
    logger.info("[MemberService] login(로그인) 호출");
    Object result = joinPoint.proceed();
    logger.info("[MemberService] login(로그인) 종료");
    return result;
  }
  
  @Around("execution(* com.ReservationServer1.service.Impl.MemberServiceImpl.findPwdMember(..))")
  public Object findPwdMemberService(ProceedingJoinPoint joinPoint) throws Throwable{
    logger.info("[MemberService] findPwd(비밀번호 찾기) 호출");
    Object result = joinPoint.proceed();
    logger.info("[MemberService] findPwd(비밀번호 찾기) 종료");
    return result;
  }
  
  @Around("execution(* com.ReservationServer1.service.Impl.MemberServiceImpl.modPwdMember(..))")
  public Object modPwdMemberService(ProceedingJoinPoint joinPoint) throws Throwable{
    logger.info("[MemberService] modPwd(비밀번호 수정) 호출");
    Object result = joinPoint.proceed();
    logger.info("[MemberService] modPwd(비밀번호 수정) 종료");
    return result;
  }
  
  @Around("execution(* com.ReservationServer1.service.Impl.MemberServiceImpl.modInfoMember(..))")
  public Object modInfoMemberService(ProceedingJoinPoint joinPoint) throws Throwable{
    logger.info("[MemberService] modInfo(회원정보 수정) 호출");
    Object result = joinPoint.proceed();
    logger.info("[MemberService] modInfo(회원정보 수정) 종료");
    return result;
  }
  
  @Around("execution(* com.ReservationServer1.service.Impl.MemberServiceImpl.delMember(..))")
  public Object delMemberService(ProceedingJoinPoint joinPoint) throws Throwable{
    logger.info("[MemberService] del(회원정보 삭제) 호출");
    Object result = joinPoint.proceed();
    logger.info("[MemberService] del(회원정보 삭제) 종료");
    return result;
  }
  
  
  // Member DAO
  @Around("execution(* com.ReservationServer1.DAO.Impl.MemberDAOImpl.registerMember(..))")
  public Object registerMemberDAO(ProceedingJoinPoint joinPoint) throws Throwable{
    logger.info("[MemberDAO] register(회원가입) 호출");
    Object result = joinPoint.proceed();
    logger.info("[MemberDAO] register(회원가입) 종료");
    return result;
  }
  
  @Around("execution(* com.ReservationServer1.DAO.Impl.MemberDAOImpl.loginMember(..))")
  public Object loginMemberDAO(ProceedingJoinPoint joinPoint) throws Throwable{
    logger.info("[MemberDAO] login(로그인) 호출");
    Object result = joinPoint.proceed();
    logger.info("[MemberDAO] login(로그인) 종료");
    return result;
  }
  
  @Around("execution(* com.ReservationServer1.DAO.Impl.MemberDAOImpl.findPwdMember(..))")
  public Object findPwdMemberDAO(ProceedingJoinPoint joinPoint) throws Throwable{
    logger.info("[MemberDAO] findPwd(비밀번호 찾기) 호출");
    Object result = joinPoint.proceed();
    logger.info("MemberDAO] findPwd(비밀번호 찾기) 종료");
    return result;
  }
  
  @Around("execution(* com.ReservationServer1.DAO.Impl.MemberDAOImpl.modPwdMember(..))")
  public Object modPwdMemberDAO(ProceedingJoinPoint joinPoint) throws Throwable{
    logger.info("[MemberDAO] modPwd(비밀번호 수정) 호출");
    Object result = joinPoint.proceed();
    logger.info("[MemberDAO] modPwd(비밀번호 수정) 종료");
    return result;
  }
  
  @Around("execution(* com.ReservationServer1.DAO.Impl.MemberDAOImpl.modInfoMember(..))")
  public Object modInfoMemberDAO(ProceedingJoinPoint joinPoint) throws Throwable{
    logger.info("[MemberDAO] modInfo(회원정보 수정) 호출");
    Object result = joinPoint.proceed();
    logger.info("[MemberDAO] modInfo(회원정보 수정) 종료");
    return result;
  }
  
  @Around("execution(* com.ReservationServer1.DAO.Impl.MemberDAOImpl.delMember(..))")
  public Object delMemberDAO(ProceedingJoinPoint joinPoint) throws Throwable{
    logger.info("[MemberDAO] del(회원정보 삭제) 호출");
    Object result = joinPoint.proceed();
    logger.info("[MemberDAO] del(회원정보 삭제) 종료");
    return result;
  }
  
  
}
