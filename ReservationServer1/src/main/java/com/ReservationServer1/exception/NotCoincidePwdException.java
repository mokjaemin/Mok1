package com.ReservationServer1.exception;

import org.springframework.http.HttpStatus;

public class NotCoincidePwdException extends RuntimeException implements ReservationException {

	private static final long serialVersionUID = 5867172506387382922L;
	private static final String code = "400";
	private static final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
	
	public NotCoincidePwdException() {
		super("잘못된 비밀번호입니다.");
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
