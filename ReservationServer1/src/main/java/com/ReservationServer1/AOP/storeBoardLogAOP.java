package com.ReservationServer1.AOP;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class storeBoardLogAOP {

  private final Logger logger = LoggerFactory.getLogger(storeBoardLogAOP.class);


  // Board Controller
  @Around("execution(* com.ReservationServer1.controller.StoreBoardController.registerBoard(..))")
  public Object registerBoardController(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StoreBoardController] registerBoard(가게 게시판 등록) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StoreBoardController] registerBoard(가게 게시판 등록) 종료");
    return result;
  }

  @Around("execution(* com.ReservationServer1.controller.StoreBoardController.getBoard(..))")
  public Object getBoardController(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StoreBoardController] getBoard(가게 게시판 출력) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StoreBoardController] getBoard(가게 게시판 출력) 종료");
    return result;
  }

  @Around("execution(* com.ReservationServer1.controller.StoreBoardController.updateBoard(..))")
  public Object updateBoardController(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StoreBoardController] updateBoard(가게 게시판 수정) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StoreBoardController] updateBoard(가게 게시판 수정) 종료");
    return result;
  }

  @Around("execution(* com.ReservationServer1.controller.StoreBoardController.deleteBoard(..))")
  public Object delelteBoardController(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StoreBoardController] deleteBoard(가게 게시판 삭제) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StoreBoardController] deleteBoard(가게 게시판 삭제) 종료");
    return result;
  }
  
  @Around("execution(* com.ReservationServer1.controller.StoreBoardController.getBoardByUser(..))")
  public Object getBoardByUserController(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StoreBoardController] getBoardByUser(가게 게시판 개인별 출력) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StoreBoardController] getBoardByUser(가게 게시판 개인별 출력) 종료");
    return result;
  }
  
  @Around("execution(* com.ReservationServer1.controller.StoreBoardController.registerBoardComment(..))")
  public Object registerBoardCommentController(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StoreBoardController] registerBoardComment(가게 게시판 댓글 등록) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StoreBoardController] registerBoardComment(가게 게시판 댓글 등록) 종료");
    return result;
  }

  @Around("execution(* com.ReservationServer1.controller.StoreBoardController.updateBoardComment(..))")
  public Object updateBoardCommentController(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StoreBoardController] updateBoardComment(가게 게시판 댓글 수정) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StoreBoardController] updateBoardComment(가게 게시판 댓글 수정) 종료");
    return result;
  }

  @Around("execution(* com.ReservationServer1.controller.StoreBoardController.deleteBoardComment(..))")
  public Object delelteBoardCommentController(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StoreBoardController] deleteBoardComment(가게 게시판 댓글 삭제) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StoreBoardController] deleteBoardComment(가게 게시판 댓글 삭제) 종료");
    return result;
  }


  
  
  
  

  // Board Service
  @Around("execution(* com.ReservationServer1.service.Impl.StoreBoardServiceImpl.registerBoard(..))")
  public Object registerBoardService(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StoreBoardService] registerBoard(가게 게시판 등록) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StoreBoardService] registerBoard(가게 게시판 등록) 종료");
    return result;
  }

  @Around("execution(* com.ReservationServer1.service.Impl.StoreBoardServiceImpl.getBoard(..))")
  public Object getBoardService(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StoreBoardService] getBoard(가게 게시판 출력) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StoreBoardService] getBoard(가게 게시판 출력) 종료");
    return result;
  }

  @Around("execution(* com.ReservationServer1.service.Impl.StoreBoardServiceImpl.updateBoard(..))")
  public Object updateBoardService(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StoreBoardService] updateBoard(가게 게시판 수정) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StoreBoardService] updateBoard(가 게시판 수정) 종료");
    return result;
  }

  @Around("execution(* com.ReservationServer1.service.Impl.StoreBoardServiceImpl.deleteBoard(..))")
  public Object delelteBoardService(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StoreBoardService] deleteBoard(가게 게시판 삭제) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StoreBoardService] deleteBoard(가게 게시판 삭제) 종료");
    return result;
  }
  
  @Around("execution(* com.ReservationServer1.service.Impl.StoreBoardServiceImpl.getBoardByUser(..))")
  public Object getBoardByUserService(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StoreBoardService] getBoardByUser(가게 개인별 게시판 출력) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StoreBoardService] getBoardByUser(가게 개인별 게시판 출력) 종료");
    return result;
  }
  
  @Around("execution(* com.ReservationServer1.service.Impl.StoreBoardServiceImpl.registerBoardComment(..))")
  public Object registerBoardCommentService(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StoreBoardService] registerBoardComment(가게 게시판 댓글 등록) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StoreBoardService] registerBoardComment(가게 게시판 댓글 등록) 종료");
    return result;
  }

  @Around("execution(* com.ReservationServer1.service.Impl.StoreBoardServiceImpl.updateBoardComment(..))")
  public Object updateBoardCommentService(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StoreBoardService] updateBoardComment(가게 게시판 댓글 수정) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StoreBoardService] updateBoardComment(가게 게시판 댓글 수정) 종료");
    return result;
  }

  @Around("execution(* com.ReservationServer1.service.Impl.StoreBoardServiceImpl.deleteBoardComment(..))")
  public Object delelteBoardCommentService(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StoreBoardService] deleteBoardComment(가게 게시판 댓글 삭제) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StoreBoardService] deleteBoardComment(가게 게시판 댓글 삭제) 종료");
    return result;
  }

  
  

  
  

  // Board DAO
  @Around("execution(* com.ReservationServer1.DAO.Impl.StoreBoardDAOImpl.registerBoard(..))")
  public Object registerBoardDAO(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StoreBoardDAO] registerBoard(가게 게시판 등록) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StoreBoardDAO] registerBoard(가게 게시판 등록) 종료");
    return result;
  }

  @Around("execution(* com.ReservationServer1.DAO.Impl.StoreBoardDAOImpl.getBoard(..))")
  public Object getBoardDAO(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StoreBoardDAO] getBoard(가게 게시판 출력) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StoreBoardDAO] getBoard(가게 게시판 출력) 종료");
    return result;
  }

  @Around("execution(* com.ReservationServer1.DAO.Impl.StoreBoardDAOImpl.updateBoard(..))")
  public Object updateBoardDAO(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StoreBoardDAO] updateBoard(가게 게시판 수정) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StoreBoardDAO] updateBoard(가 게시판 수정) 종료");
    return result;
  }

  @Around("execution(* com.ReservationServer1.DAO.Impl.StoreBoardDAOImpl.deleteBoard(..))")
  public Object delelteBoardDAO(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StoreBoardDAO] deleteBoard(가게 게시판 삭제) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StoreBoardDAO] deleteBoard(가게 게시판 삭제) 종료");
    return result;
  }
  
  @Around("execution(* com.ReservationServer1.DAO.Impl.StoreBoardDAOImpl.getBoardByUser(..))")
  public Object getBoardByUserDAO(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StoreBoardDAO] getBoardByUser(가게 개인별 게시판 출력) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StoreBoardDAO] getBoardByUser(가게 개인별 게시판 출력) 종료");
    return result;
  }
  
  @Around("execution(* com.ReservationServer1.DAO.Impl.StoreBoardDAOImpl.registerBoardComment(..))")
  public Object registerBoardCommentDAO(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StoreBoardDAO] registerBoardComment(가게 게시판 댓글 등록) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StoreBoardDAO] registerBoardComment(가게 게시판 댓글 등록) 종료");
    return result;
  }

  @Around("execution(* com.ReservationServer1.DAO.Impl.StoreBoardDAOImpl.updateBoardComment(..))")
  public Object updateBoardCommentDAO(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StoreBoardDAO] updateBoardComment(가게 게시판 댓글 수정) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StoreBoardDAO] updateBoardComment(가게 게시판 댓글 수정) 종료");
    return result;
  }

  @Around("execution(* com.ReservationServer1.DAO.Impl.StoreBoardDAOImpl.deleteBoardComment(..))")
  public Object delelteBoardCommentDAO(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.info("[StoreBoardDAO] deleteBoardComment(가게 게시판 댓글 삭제) 호출");
    Object result = joinPoint.proceed();
    logger.info("[StoreBoardDAO] deleteBoardComment(가게 게시판 댓글 삭제) 종료");
    return result;
  }
  
  
}
