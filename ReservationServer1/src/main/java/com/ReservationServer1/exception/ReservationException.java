package com.ReservationServer1.exception;

import org.springframework.http.HttpStatus;

public interface ReservationException {

  String getMessage();
  String getCode();
  HttpStatus getHttpStatus();
  
}
