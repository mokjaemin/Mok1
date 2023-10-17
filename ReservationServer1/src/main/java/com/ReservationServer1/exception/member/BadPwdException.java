package com.ReservationServer1.exception.member;

import org.springframework.http.HttpStatus;
import com.ReservationServer1.exception.ReservationException;

public class BadPwdException extends RuntimeException implements ReservationException{

  
  private static final long serialVersionUID = 5867172506387382920L;
  private final String message = "잘못된 비밀번호입니다.";
  private final String code = "400";
  private final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

  
  @Override
  public String getCode() {
    return this.code;
  }

  @Override
  public String getMessage() {
    return this.message;
  }
  
  @Override
  public HttpStatus getHttpStatus() {
    return this.httpStatus;
  }
  
}
