package com.ReservationServer1.exception;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;



@RestControllerAdvice
public class ErrorHandler {

  private final Logger LOGGER = LoggerFactory.getLogger(ErrorHandler.class);


  // Basic Exception Handler
  @ExceptionHandler(value = Exception.class)
  public ResponseEntity<Map<String, String>> BasicExceptionHandler(Exception e) {
    
    LOGGER.error("Exception 발생, 원인 : {}", e.getCause());
    LOGGER.error("Exception 발생, 메시지 : {}", e.getMessage());

    
    HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.add(HttpHeaders.CONTENT_TYPE, "application/json");


    Map<String, String> map = new HashMap<>();
    HttpStatus httpStatus;

    // 일반 RuntimeException
    if (!(e instanceof ReservationException)) {
      httpStatus = HttpStatus.BAD_REQUEST;
      map.put("error type", httpStatus.getReasonPhrase());
      map.put("message", "오류발생");
      map.put("code", "400");
    }

    // ReservationException일 경우
    else {
      ReservationException new_e = (ReservationException) e;
      httpStatus = new_e.getHttpStatus();
      map.put("error type", httpStatus.getReasonPhrase());
      map.put("message", new_e.getMessage());
      map.put("code", new_e.getCode());
    }

    return new ResponseEntity<>(map, responseHeaders, httpStatus);
  }



}
