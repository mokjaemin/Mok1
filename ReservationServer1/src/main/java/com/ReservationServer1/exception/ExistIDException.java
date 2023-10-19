package com.ReservationServer1.exception;

import org.springframework.http.HttpStatus;

public class ExistIDException extends RuntimeException implements ReservationException {

	private static final long serialVersionUID = 5867172506387382923L;
	private static final String code = "400";
	private static final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

	
	public ExistIDException() {
		super("해당 ID가 이미 존재합니다.");
	}

	@Override
	public String getCode() {
		return code;
	}

	@Override
	public String getMessage() {
		return super.getMessage();
	}

	@Override
	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
}
