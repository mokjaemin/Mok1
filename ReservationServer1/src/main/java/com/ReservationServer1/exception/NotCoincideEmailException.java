package com.ReservationServer1.exception;

import org.springframework.http.HttpStatus;

public class NotCoincideEmailException extends RuntimeException implements ReservationException {

	private static final long serialVersionUID = 5867172506387382921L;
	private static final String code = "400";
	private static final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
	
	public NotCoincideEmailException() {
		super("잘못된 이메일입니다.");
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
