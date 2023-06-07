package com.ReservationServer1.AOP;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class storeInfoLogAOP {

  private final Logger logger = LoggerFactory.getLogger(storeInfoLogAOP.class);

  // StoreInfo Controller
  @Around("execution(* com.ReservationServer1.controller.StoreInfoController.registerDayOff(..))")
  public Object registerDayOffController(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StoreInfoController] registerDayOff(가게 쉬는날 등록) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StoreInfoController] registerDayOff(가게 쉬는날 등록) 종료");
    return result;
  }

  @Around("execution(* com.ReservationServer1.controller.StoreInfoController.getDayOff(..))")
  public Object getDayOffController(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StoreInfoController] getDayOff(가게 쉬는날 출력) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StoreInfoController] getDayOff(가게 쉬는날 출력) 종료");
    return result;
  }

  @Around("execution(* com.ReservationServer1.controller.StoreInfoController.deleteDayOff(..))")
  public Object delelteDayOffController(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StoreInfoController] deleteDayOff(가게 쉬는날 삭제) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StoreInfoController] deleteDayOff(가게 쉬는날 삭제) 종료");
    return result;
  }

  @Around("execution(* com.ReservationServer1.controller.StoreInfoController.registerTimeInfo(..))")
  public Object registerTimeInfoController(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StoreInfoController] registerTimeInfo(가게 운영시간 등록) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StoreInfoController] registerTimeInfo(가게 운영시간 등록) 종료");
    return result;
  }
  
  
  



  // StoreInfo Service
  @Around("execution(* com.ReservationServer1.service.Impl.StoreInfoServiceImpl.registerDayOff(..))")
  public Object registerDayOffService(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StoreInfoService] registerDayOff(가게 쉬는날 등록) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StoreInfoService] registerDayOff(가게 쉬는날 등록) 종료");
    return result;
  }

  @Around("execution(* com.ReservationServer1.service.Impl.StoreInfoServiceImpl.getDayOff(..))")
  public Object getDayOffService(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StoreInfoService] getDayOff(가게 쉬는날 출력) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StoreInfoService] getDayOff(가게 쉬는날 출력) 종료");
    return result;
  }


  @Around("execution(* com.ReservationServer1.service.Impl.StoreInfoServiceImpl.deleteDayOff(..))")
  public Object delelteDayOffService(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StoreInfoService] deleteDayOff(가게 쉬는날 삭제) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StoreInfoService] deleteDayOff(가게 쉬는날 삭제) 종료");
    return result;
  }

  @Around("execution(* com.ReservationServer1.service.Impl.StoreInfoServiceImpl.registerTimeInfo(..))")
  public Object registerTimeInfoService(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StoreInfoService] registerTimeInfo(가게 운영시간 등록) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StoreInfoService] registerTimeInfo(가게 운영시간 등록) 종료");
    return result;
  }


  
  
  
  

  // StoreInfo DAO
  @Around("execution(* com.ReservationServer1.DAO.Impl.StoreInfoDAOImpl.registerDayOff(..))")
  public Object registerDayOffDAO(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StoreInfoDAO] registerDayOff(가게 쉬는날 등록) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StoreInfoDAO] registerDayOff(가게 쉬는날 등록) 종료");
    return result;
  }

  @Around("execution(* com.ReservationServer1.DAO.Impl.StoreInfoDAOImpl.getDayOff(..))")
  public Object getDayOffDAO(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StoreInfoDAO] getDayOff(가게 쉬는날 출력) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StoreInfoDAO] getDayOff(가게 쉬는날 출력) 종료");
    return result;
  }

  @Around("execution(* com.ReservationServer1.DAO.Impl.StoreInfoDAOImpl.deleteDayOff(..))")
  public Object delelteDayOffDAO(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StoreInfoDAO] deleteDayOff(가게 쉬는날 삭제) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StoreInfoDAO] deleteDayOff(가게 쉬는날 삭제) 종료");
    return result;
  }
  
  @Around("execution(* com.ReservationServer1.DAO.Impl.StoreInfoDAOImpl.registerTimeInfo(..))")
  public Object registerTimeInfoDAO(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StoreInfoDAO] registerTimeInfo(가게 운영시간 등록) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StoreInfoDAO] registerTimeInfo(가게 운영시간 종료) 호출");
    return result;
  }

}
