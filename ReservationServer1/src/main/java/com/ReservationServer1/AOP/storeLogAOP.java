package com.ReservationServer1.AOP;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class storeLogAOP {

  private final Logger logger = LoggerFactory.getLogger(storeLogAOP.class);

  // Store Controller
  @Around("execution(* com.ReservationServer1.controller.StoreController.registerStore(..))")
  public Object registerStoreController(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StoreController] registerStore(가게 등록) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StoreController] registerStore(가게 등록) 종료");
    return result;
  }

  @Around("execution(* com.ReservationServer1.controller.StoreController.getStoreList(..))")
  public Object getStoreListController(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StoreController] registerStore(가게 리스트 출력) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StoreController] registerStore(가게 리스트 출력) 종료");
    return result;
  }

  @Around("execution(* com.ReservationServer1.controller.StoreController.loginStore(..))")
  public Object loginStoreController(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StoreController] loginStore(가게 로그인) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StoreController] loginStore(가게 로그인) 종료");
    return result;
  }

  
  
  // Store Service
  @Around("execution(* com.ReservationServer1.service.Impl.StoreServiceImpl.registerStore(..))")
  public Object registerStoreService(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StoreService] registerStore(가게 등록) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StoreService] registerStore(가게 등록) 종료");
    return result;
  }

  @Around("execution(* com.ReservationServer1.service.Impl.StoreServiceImpl.getStoreList(..))")
  public Object getStoreListService(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StoreService] registerStore(가게 리스트 출력) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StoreService] registerStore(가게 리스트 출력) 종료");
    return result;
  }

  @Around("execution(* com.ReservationServer1.service.Impl.StoreServiceImpl.loginStore(..))")
  public Object loginStoreService(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StoreService] loginStore(가게 로그인) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StoreService] loginStore(가게 로그인) 종료");
    return result;
  }
  
  
  

  // Store DAO
  @Around("execution(* com.ReservationServer1.DAO.Impl.StoreDAOImpl.registerStore(..))")
  public Object registerStoreDAO(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StoreDAO] registerStore(가게 등록) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StoreDAO] registerStore(가게 등록) 종료");
    return result;
  }

  @Around("execution(* com.ReservationServer1.DAO.Impl.StoreDAOImpl.getStoreList(..))")
  public Object getStoreListDAO(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StoreDAO] registerStore(가게 리스트 출력) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StoreDAO] registerStore(가게 리스트 출력) 종료");
    return result;
  }

  @Around("execution(* com.ReservationServer1.DAO.Impl.StoreDAOImpl.loginStore(..))")
  public Object loginStoreDAO(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StoreDAO] loginStore(가게 로그인) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StoreDAO] loginStore(가게 로그인) 종료");
    return result;
  }

}
