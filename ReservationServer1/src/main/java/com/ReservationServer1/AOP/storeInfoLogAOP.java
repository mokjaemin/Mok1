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
  
  @Around("execution(* com.ReservationServer1.controller.StoreInfoController.getTimeInfo(..))")
  public Object getTimeInfoController(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StoreInfoController] getTimeInfo(가게 운영시간 출력) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StoreInfoController] getTimeInfo(가게 운영시간 출력) 종료");
    return result;
  }
  
  @Around("execution(* com.ReservationServer1.controller.StoreInfoController.modTimeInfo(..))")
  public Object modTimeInfoController(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StoreInfoController] modTimeInfo(가게 운영시간 수정) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StoreInfoController] modTimeInfo(가게 운영시간 수정) 종료");
    return result;
  }

  @Around("execution(* com.ReservationServer1.controller.StoreInfoController.deleteTimeInfo(..))")
  public Object delelteTimeInfofController(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StoreInfoController] deleteTimeInfo(가게 운영시간 삭제) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StoreInfoController] deleteTimeInfo(가게 운영시간 삭제) 종료");
    return result;
  }
  
  @Around("execution(* com.ReservationServer1.controller.StoreInfoController.registerTableInfo(..))")
  public Object registerTableInfoController(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StoreInfoController] registerTableInfo(가게 테이블 정보 등록) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StoreInfoController] registerTableInfo(가게 테이블 정보 등록) 종료");
    return result;
  }
  
  @Around("execution(* com.ReservationServer1.controller.StoreInfoController.modTableInfo(..))")
  public Object modTableInfoController(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StoreInfoController] modTableInfo(가게 테이블 정보 수정) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StoreInfoController] modTableInfo(가게 테이블 정보 수정) 종료");
    return result;
  }
  
  @Around("execution(* com.ReservationServer1.controller.StoreInfoController.deleteTableInfo(..))")
  public Object deleteTableInfoController(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StoreInfoController] deleteTableInfo(가게 테이블 정보 삭제) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StoreInfoController] deleteTableInfo(가게 테이블 정보 삭제) 종료");
    return result;
  }
  
  @Around("execution(* com.ReservationServer1.controller.StoreInfoController.registerFoodsInfo(..))")
  public Object registerFoodsInfoController(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StoreInfoController] registerFoodsInfo(가게 음식정보 등록) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StoreInfoController] registerFoodsInfo(가게 음식정보 등록) 종료");
    return result;
  }
  
  @Around("execution(* com.ReservationServer1.controller.StoreInfoController.getFoodsInfo(..))")
  public Object getFoodsInfoController(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StoreInfoController] getFoodsInfo(가게 음식정보 출력) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StoreInfoController] getFoodsInfo(가게 음식정보 출력) 종료");
    return result;
  }
  
  @Around("execution(* com.ReservationServer1.controller.StoreInfoController.modFoodsInfo(..))")
  public Object modFoodsInfoController(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StoreInfoController] modFoodsInfo(가게 음식정보 수정) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StoreInfoController] modFoodsInfo(가게 음식정보 수정) 종료");
    return result;
  }

  @Around("execution(* com.ReservationServer1.controller.StoreInfoController.deleteFoodsInfo(..))")
  public Object delelteFoodsInfofController(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StoreInfoController] deleteFoodsInfo(가게 음식정보 삭제) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StoreInfoController] deleteFoodsInfo(가게 음식정보 삭제) 종료");
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
  
  @Around("execution(* com.ReservationServer1.service.Impl.StoreInfoServiceImpl.getTimeInfo(..))")
  public Object getTimeInfoService(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StoreInfoService] getTimeInfo(가게 운영시간 출력) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StoreInfoService] getTimeInfo(가게 운영시간 출력) 종료");
    return result;
  }
  
  @Around("execution(* com.ReservationServer1.service.Impl.StoreInfoServiceImpl.modTimeInfo(..))")
  public Object modTimeInfoService(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StoreInfoService] modTimeInfo(가게 운영시간 수정) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StoreInfoService] modTimeInfo(가게 운영시간 수정) 종료");
    return result;
  }
  
  @Around("execution(* com.ReservationServer1.service.Impl.StoreInfoServiceImpl.deleteTimeInfo(..))")
  public Object deleteTimeInfoService(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StoreInfoService] deleteTimeInfo(가게 운영시간 삭제) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StoreInfoService] deleteTimeInfo(가게 운영시간 삭제) 종료");
    return result;
  }

  @Around("execution(* com.ReservationServer1.service.Impl.StoreInfoServiceImpl.registerTableInfo(..))")
  public Object registerTableInfoService(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StoreInfoService] registerTableInfo(가게 테이블 정보 등록) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StoreInfoService] registerTableInfo(가게 테이블 정보 등록) 종료");
    return result;
  }
  
  @Around("execution(* com.ReservationServer1.service.Impl.StoreInfoServiceImpl.modTableInfo(..))")
  public Object modTableInfoService(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StoreInfoService] modTableInfo(가게 테이블 정보 수정) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StoreInfoService] modTableInfo(가게 테이블 정보 수정) 종료");
    return result;
  }
  
  @Around("execution(* com.ReservationServer1.service.Impl.StoreInfoServiceImpl.deleteTableInfo(..))")
  public Object deleteTableInfoService(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StoreInfoService] deleteTableInfo(가게 테이블 정보 삭제) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StoreInfoService] deleteTableInfo(가게 테이블 정보 삭제) 종료");
    return result;
  }
  
  @Around("execution(* com.ReservationServer1.service.Impl.StoreInfoServiceImpl.registerFoodsInfo(..))")
  public Object registerFoodsInfoService(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StoreInfoService] registerFoodsInfo(가게 음식정보 등록) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StoreInfoService] registerFoodsInfo(가게 음식정보 등록) 종료");
    return result;
  }
  
  @Around("execution(* com.ReservationServer1.service.Impl.StoreInfoServiceImpl.getFoodsInfo(..))")
  public Object getFoodsInfoService(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StoreInfoService] getFoodsInfo(가게 음식정보 출력) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StoreInfoService] getFoodsInfo(가게 음식정보 출력) 종료");
    return result;
  }
  
  @Around("execution(* com.ReservationServer1.service.Impl.StoreInfoServiceImpl.modFoodsInfo(..))")
  public Object modFoodsInfoService(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StoreInfoService] modFoodsInfo(가게 음식정보 수정) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StoreInfoService] modFoodsInfo(가게 음식정보 수정) 종료");
    return result;
  }
  
  @Around("execution(* com.ReservationServer1.service.Impl.StoreInfoServiceImpl.deleteFoodsInfo(..))")
  public Object deleteFoodsInfoService(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StoreInfoService] deleteFoodsInfo(가게 음식정보 삭제) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StoreInfoService] deleteFoodsInfo(가게 음식정보 삭제) 종료");
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
    logger.info("[StoreInfoDAO] registerTimeInfo(가게 운영시간 등록) 종료");
    return result;
  }
  
  @Around("execution(* com.ReservationServer1.DAO.Impl.StoreInfoDAOImpl.getTimeInfo(..))")
  public Object getTimeInfoDAO(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StoreInfoDAO] getTimeInfo(가게 운영시간 출력) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StoreInfoDAO] getTimeInfo(가게 운영시간 출력) 호출");
    return result;
  }
  
  @Around("execution(* com.ReservationServer1.DAO.Impl.StoreInfoDAOImpl.modTimeInfo(..))")
  public Object modTimeInfoDAO(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StoreInfoDAO] modTimeInfo(가게 운영시간 수정) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StoreInfoDAO] modTimeInfo(가게 운영시간 수정) 호출");
    return result;
  }
  
  @Around("execution(* com.ReservationServer1.DAO.Impl.StoreInfoDAOImpl.deleteTimeInfo(..))")
  public Object deleteTimeInfoDAO(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StoreInfoDAO] deleteTimeInfo(가게 운영시간 삭제) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StoreInfoDAO] deleteTimeInfo(가게 운영시간 삭제) 호출");
    return result;
  }
  
  @Around("execution(* com.ReservationServer1.DAO.Impl.StoreInfoDAOImpl.registerTableInfo(..))")
  public Object registerTableInfoDAO(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StoreInfoDAO] registerTableInfo(가게 테이블 정보 등록) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StoreInfoDAO] registerTableInfo(가게 테이블 정보 등록) 종료");
    return result;
  }
  
  @Around("execution(* com.ReservationServer1.DAO.Impl.StoreInfoDAOImpl.modTimeInfo(..))")
  public Object modTableInfoDAO(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StoreInfoDAO] modTableInfo(가게 테이블 정보 수정) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StoreInfoDAO] modTableInfo(가게 테이블 정보 수정) 호출");
    return result;
  }
  
  @Around("execution(* com.ReservationServer1.DAO.Impl.StoreInfoDAOImpl.deleteTableInfo(..))")
  public Object deleteTableInfoDAO(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StoreInfoDAO] deleteTableInfo(가게 테이블 정보 삭제) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StoreInfoDAO] deleteTableInfo(가게 테이블 정보 삭제) 호출");
    return result;
  }

  
  @Around("execution(* com.ReservationServer1.DAO.Impl.StoreInfoDAOImpl.registerFoodsInfo(..))")
  public Object registerFoodsInfoDAO(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StoreInfoDAO] registerFoodsInfo(가게 음식정보 등록) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StoreInfoDAO] registerFoodsInfo(가게 음식정보 등록) 종료");
    return result;
  }
  
  @Around("execution(* com.ReservationServer1.DAO.Impl.StoreInfoDAOImpl.getFoodsInfo(..))")
  public Object getFoodsInfoDAO(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StoreInfoDAO] getFoodsInfo(가게 음식정보 출력) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StoreInfoDAO] getFoodsInfo(가게 음식정보 출력) 호출");
    return result;
  }
  
  @Around("execution(* com.ReservationServer1.DAO.Impl.StoreInfoDAOImpl.modFoodsInfo(..))")
  public Object modFoodsInfoDAO(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StoreInfoDAO] modFoodsInfo(가게 음식정보 수정) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StoreInfoDAO] modFoodsInfo(가게 음식정보 수정) 호출");
    return result;
  }
  
  @Around("execution(* com.ReservationServer1.DAO.Impl.StoreInfoDAOImpl.deleteFoodsInfo(..))")
  public Object deleteFoodsInfoDAO(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StoreInfoDAO] deleteFoodsInfo(가게 음식정보 삭제) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StoreInfoDAO] deleteFoodsInfo(가게 음식정보 삭제) 호출");
    return result;
  }
}
