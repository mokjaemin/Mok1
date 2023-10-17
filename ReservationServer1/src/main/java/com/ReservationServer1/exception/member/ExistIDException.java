package com.ReservationServer1.exception.member;

import org.springframework.http.HttpStatus;
import com.ReservationServer1.exception.ReservationException;

public class ExistIDException extends RuntimeException implements ReservationException {

  
  private static final long serialVersionUID = 5867172506387382920L;
  private final String code = "400";
  private final String message1 = "해당 ID가 이미 존재합니다.";
  private final String message2 = "해당 ID가 존재하지 않습니다.";
  private final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
  private final Boolean Exist;

  
  public ExistIDException(Boolean Exist) {
    this.Exist = Exist;
  }

  
  @Override
  public String getCode() {
    return this.code;
  }

  
  @Override
  public String getMessage() {
    return (Exist) ? message1 : message2;
  }
  
  @Override
  public HttpStatus getHttpStatus() {
    return this.httpStatus;
  }
}
