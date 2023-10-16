package com.ReservationServer1.exception.member;

import org.springframework.http.HttpStatus;
import com.ReservationServer1.exception.ReservationException;

public class BadEmailException extends RuntimeException implements ReservationException{

  private static final long serialVersionUID = 5867172506387382920L;
  private final String message = "잘못된 이메일입니다.";
  private final String code = "400";
  HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

  
  public String getCode() {
    return this.code;
  }

  public String getMessage() {
    return this.message;
  }
  
  public HttpStatus getHttpStatus() {
    return this.httpStatus;
  }
  
}
