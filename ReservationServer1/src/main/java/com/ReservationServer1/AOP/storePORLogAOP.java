package com.ReservationServer1.AOP;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class storePORLogAOP {
  
  

  private final Logger logger = LoggerFactory.getLogger(storePORLogAOP.class);
  
  // Controller
  @Around("execution(* com.ReservationServer1.controller.StorePORController.registerReservation(..))")
  public Object registerReservationController(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StorePORController] registerReservation(가게 예약 등록) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StorePORController] registerReservation(가게 예약 등록) 종료");
    return result;
  }
  
  @Around("execution(* com.ReservationServer1.controller.StorePORController.updateReservation(..))")
  public Object updateReservationController(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StorePORController] updateReservation(가게 예약 수정) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StorePORController] updateReservation(가게 예약 수정) 종료");
    return result;
  }
  
  @Around("execution(* com.ReservationServer1.controller.StorePORController.getReservation(..))")
  public Object getReservationController(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StorePORController] getReservation(가게 예약 출력) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StorePORController] getReservation(가게 예약 출력) 종료");
    return result;
  }
  
  @Around("execution(* com.ReservationServer1.controller.StorePORController.deleteReservation(..))")
  public Object deleteReservationController(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StorePORController] deleteReservation(가게 예약 삭제) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StorePORController] deleteReservation(가게 예약 삭제) 종료");
    return result;
  }
  
  @Around("execution(* com.ReservationServer1.controller.StorePORController.registerOrder(..))")
  public Object registerOrderController(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StorePORController] registerOrder(가게 주문 등록) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StorePORController] registerOrder(가게 주문 등록) 종료");
    return result;
  }
  
  @Around("execution(* com.ReservationServer1.controller.StorePORController.updateOrder(..))")
  public Object updateOrderController(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StorePORController] updateOrder(가게 주문 수정) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StorePORController] updateOrder(가게 주문 수정) 종료");
    return result;
  }
  
  @Around("execution(* com.ReservationServer1.controller.StorePORController.deleteOrder(..))")
  public Object deleteOrderController(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StorePORController] deleteOrder(가게 주문 삭제) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StorePORController] deleteOrder(가게 주문 삭제) 종료");
    return result;
  }
  
  
  @Around("execution(* com.ReservationServer1.controller.StorePORController.registerPay(..))")
  public Object registerPayController(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StorePORController] registerPay(가게 주문 등록) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StorePORController] registerPay(가게 주문 등록) 종료");
    return result;
  }
  
  @Around("execution(* com.ReservationServer1.controller.StorePORController.deletePay(..))")
  public Object deletePayController(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StorePORController] deletePay(가게 주문 삭제) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StorePORController] deletePay(가게 주문 삭제) 종료");
    return result;
  }
  

  @Around("execution(* com.ReservationServer1.controller.StorePORController.registerComment(..))")
  public Object registerCommentController(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StorePORController] registerComment(가게 댓글 등록) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StorePORController] registerComment(가게 댓글 등록) 종료");
    return result;
  }
  
  @Around("execution(* com.ReservationServer1.controller.StorePORController.deleteComment(..))")
  public Object deleteCommentController(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StorePORController] deleteComment(가게 댓글 삭제) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StorePORController] deleteComment(가게 댓글 삭제) 종료");
    return result;
  }
  

  @Around("execution(* com.ReservationServer1.controller.StorePORController.registerBigComment(..))")
  public Object registerBigCommentController(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StorePORController] registerBigComment(가게 대댓글 등록) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StorePORController] registerBigComment(가게 대댓글 등록) 종료");
    return result;
  }
  
  @Around("execution(* com.ReservationServer1.controller.StorePORController.deleteBigComment(..))")
  public Object deleteBigCommentController(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StorePORController] deleteBigComment(가게 대댓글 삭제) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StorePORController] deleteBigComment(가게 대댓글 삭제) 종료");
    return result;
  }
  
  
  @Around("execution(* com.ReservationServer1.controller.StorePORController.getCouponClient(..))")
  public Object getCouponClientController(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StorePORController] getCouponClient(회원별 쿠폰 출력) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StorePORController] getCouponClient(회원별 쿠폰 출력) 종료");
    return result;
  }
  
  @Around("execution(* com.ReservationServer1.controller.StorePORController.getCouponOwner(..))")
  public Object getCouponOwnerController(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StorePORController] getCouponOwner(가게별 쿠폰 출력) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StorePORController] getCouponOwner(가게별 쿠폰 출력) 종료");
    return result;
  }
  
  
  
  
  
  
  
  // Service
  @Around("execution(* com.ReservationServer1.service.Impl.StorePORServiceImpl.registerReservation(..))")
  public Object registerReservationService(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StorePORService] registerReservation(가게 예약 등록) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StorePORService] registerReservation(가게 예약 등록) 종료");
    return result;
  }
  
  @Around("execution(* com.ReservationServer1.service.Impl.StorePORServiceImpl.updateReservation(..))")
  public Object updateReservationService(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StorePORService] updateReservation(가게 예약 수정) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StorePORService] updateReservation(가게 예약 수정) 종료");
    return result;
  }
  
  @Around("execution(* com.ReservationServer1.service.Impl.StorePORServiceImpl.getReservation(..))")
  public Object getReservationService(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StorePORService] getReservation(가게 예약 출력) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StorePORService] getReservation(가게 예약 출력) 종료");
    return result;
  }
  
  @Around("execution(* com.ReservationServer1.service.Impl.StorePORServiceImpl.deleteReservation(..))")
  public Object deleteReservationService(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StorePORService] deleteReservation(가게 예약 삭제) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StorePORService] deleteReservation(가게 예약 삭제) 종료");
    return result;
  }
  
  @Around("execution(* com.ReservationServer1.service.Impl.StorePORServiceImpl.registerOrder(..))")
  public Object registerOrderService(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StorePORService] registerOrder(가게 주문 등록) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StorePORService] registerOrder(가게 주문 등록) 종료");
    return result;
  }

  @Around("execution(* com.ReservationServer1.service.Impl.StorePORServiceImpl.updateOrder(..))")
  public Object updateOrderService(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StorePORService] updateOrder(가게 주문 수정) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StorePORService] updateOrder(가게 주문 수정) 종료");
    return result;
  }

  @Around("execution(* com.ReservationServer1.service.Impl.StorePORServiceImpl.deleteOrder(..))")
  public Object deleteOrderService(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StorePORService] deleteOrder(가게 주문 삭제) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StorePORService] deleteOrder(가게 주문 삭제) 종료");
    return result;
  }
  
  @Around("execution(* com.ReservationServer1.service.Impl.StorePORServiceImpl.registerPay(..))")
  public Object registerPayService(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StorePORService] registerPay(가게 주문 등록) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StorePORService] registerPay(가게 주문 등록) 종료");
    return result;
  }
  
  @Around("execution(* com.ReservationServer1.service.Impl.StorePORServiceImpl.deletePay(..))")
  public Object deletePayService(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StorePORService] deletePay(가게 주문 삭제) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StorePORService] deletePay(가게 주문 삭제) 종료");
    return result;
  }
  
  @Around("execution(* com.ReservationServer1.service.Impl.StorePORServiceImpl.registerComment(..))")
  public Object registerCommentService(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StorePORService] registerComment(가게 댓글 등록) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StorePORService] registerComment(가게 댓글 등록) 종료");
    return result;
  }
  
  @Around("execution(* com.ReservationServer1.service.Impl.StorePORServiceImpl.deleteComment(..))")
  public Object deleteCommentService(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StorePORService] deleteComment(가게 댓글 삭제) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StorePORService] deleteComment(가게 댓글 삭제) 종료");
    return result;
  }
  
  @Around("execution(* com.ReservationServer1.service.Impl.StorePORServiceImpl.registerBigComment(..))")
  public Object registerBigCommentService(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StorePORService] registerBigComment(가게 대댓글 등록) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StorePORService] registerBigComment(가게 대댓글 등록) 종료");
    return result;
  }
  
  @Around("execution(* com.ReservationServer1.service.Impl.StorePORServiceImpl.deleteBigComment(..))")
  public Object deleteBigCommentService(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StorePORService] deleteBigComment(가게 대댓글 삭제) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StorePORService] deleteBigComment(가게 대댓글 삭제) 종료");
    return result;
  }
  
  @Around("execution(* com.ReservationServer1.service.Impl.StorePORServiceImpl.getCouponClient(..))")
  public Object getCouponClientService(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StorePORService] getCouponClient(회원별 쿠폰 출력) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StorePORService] getCouponClient(회원별 쿠폰 출력) 종료");
    return result;
  }
  
  @Around("execution(* com.ReservationServer1.service.Impl.StorePORServiceImpl.getCouponOwner(..))")
  public Object getCouponOwnerService(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StorePORService] getCouponOwner(가게별 쿠폰 출력) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StorePORService] getCouponOwner(가게별 쿠폰 출력) 종료");
    return result;
  }
  
  
  
  
  
  
  
  
  
  // DAO
  @Around("execution(* com.ReservationServer1.DAO.Impl.StorePORDAOImpl.registerReservation(..))")
  public Object registerReservationDAO(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StorePORDAO] registerReservation(가게 예약 등록) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StorePORDAO] registerReservation(가게 예약 등록) 종료");
    return result;
  }
  
  @Around("execution(* com.ReservationServer1.DAO.Impl.StorePORDAOImpl.updateReservation(..))")
  public Object updateReservationDAO(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StorePORDAO] updateReservation(가게 예약 수정) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StorePORDAO] updateReservation(가게 예약 수정) 종료");
    return result;
  }
  
  @Around("execution(* com.ReservationServer1.DAO.Impl.StorePORDAOImpl.getReservation(..))")
  public Object getReservationDAO(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StorePORDAO] getReservation(가게 예약 출력) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StorePORDAO] getReservation(가게 예약 출력) 종료");
    return result;
  }
  
  @Around("execution(* com.ReservationServer1.DAO.Impl.StorePORDAOImpl.deleteReservation(..))")
  public Object deleteReservationDAO(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StorePORDAO] deleteReservation(가게 예약 삭제) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StorePORDAO] deleteReservation(가게 예약 삭제) 종료");
    return result;
  }
  
  @Around("execution(* com.ReservationServer1.DAO.Impl.StorePORDAOImpl.registerOrder(..))")
  public Object registerOrderDAO(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StorePORDAO] registerOrder(가게 주문 등록) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StorePORDAO] registerOrder(가게 주문 등록) 종료");
    return result;
  }

  @Around("execution(* com.ReservationServer1.DAO.Impl.StorePORDAOImpl.updateOrder(..))")
  public Object updateOrderDAO(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StorePORDAO] updateOrder(가게 주문 수정) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StorePORDAO] updateOrder(가게 주문 수정) 종료");
    return result;
  }

  @Around("execution(* com.ReservationServer1.DAO.Impl.StorePORDAOImpl.deleteOrder(..))")
  public Object deleteOrderDAO(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StorePORDAO] deleteOrder(가게 주문 삭제) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StorePORDAO] deleteOrder(가게 주문 삭제) 종료");
    return result;
  }
  
  @Around("execution(* com.ReservationServer1.DAO.Impl.StorePORDAOImpl.registerPay(..))")
  public Object registerPayDAO(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StorePORDAO] registerPay(가게 주문 등록) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StorePORDAO] registerPay(가게 주문 등록) 종료");
    return result;
  }
  
  @Around("execution(* com.ReservationServer1.DAO.Impl.StorePORDAOImpl.deletePay(..))")
  public Object deletePayDAO(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StorePORDAO] deletePay(가게 주문 삭제) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StorePORDAO] deletePay(가게 주문 삭제) 종료");
    return result;
  }
  
  @Around("execution(* com.ReservationServer1.DAO.Impl.StorePORDAOImpl.registerComment(..))")
  public Object registerCommentDAO(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StorePORDAO] registerComment(가게 댓글 등록) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StorePORDAO] registerComment(가게 댓글 등록) 종료");
    return result;
  }
  
  @Around("execution(* com.ReservationServer1.DAO.Impl.StorePORDAOImpl.deleteComment(..))")
  public Object deleteCommentDAO(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StorePORDAO] deleteComment(가게 댓글 삭제) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StorePORDAO] deleteComment(가게 댓글 삭제) 종료");
    return result;
  }
  
  @Around("execution(* com.ReservationServer1.DAO.Impl.StorePORDAOImpl.registerBigComment(..))")
  public Object registerBigCommentDAO(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StorePORDAO] registerBigComment(가게 대댓글 등록) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StorePORDAO] registerBigComment(가게 대댓글 등록) 종료");
    return result;
  }
  
  @Around("execution(* com.ReservationServer1.DAO.Impl.StorePORDAOImpl.deleteBigComment(..))")
  public Object deleteBigCommentDAO(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StorePORDAO] deleteBigComment(가게 대댓글 삭제) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StorePORDAO] deleteBigComment(가게 대댓글 삭제) 종료");
    return result;
  }
  
  @Around("execution(* com.ReservationServer1.DAO.Impl.StorePORDAOImpl.getCouponClient(..))")
  public Object getCouponClientDAO(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StorePORDAO] getCouponClient(회원별 쿠폰 출력) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StorePORDAO] getCouponClient(회원별 쿠폰 출력) 종료");
    return result;
  }
  
  @Around("execution(* com.ReservationServer1.DAO.Impl.StorePORDAOImpl.getCouponOwner(..))")
  public Object getCouponOwnerDAO(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StorePORDAO] getCouponOwner(가게별 쿠폰 출력) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StorePORDAO] getCouponOwner(가게별 쿠폰 출력) 종료");
    return result;
  }
  
  
  
}
