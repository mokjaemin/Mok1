package com.ReservationServer1.exception.store;

import org.springframework.http.HttpStatus;
import com.ReservationServer1.exception.ReservationException;

public class NoInformationException extends RuntimeException implements ReservationException {

  private static final long serialVersionUID = 5867172506387382920L;
  private final String message = "해당 정보를 찾을 수 없습니다.";
  private final String code = "400";
  private final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

  @Override
  public String getMessage() {
    return this.message;
  }

  @Override
  public String getCode() {
    return this.code;
  }

  @Override
  public HttpStatus getHttpStatus() {
    return this.httpStatus;
  }

}
