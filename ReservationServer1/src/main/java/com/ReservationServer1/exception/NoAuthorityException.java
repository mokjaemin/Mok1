package com.ReservationServer1.exception;

import org.springframework.http.HttpStatus;

public class NoAuthorityException extends RuntimeException implements ReservationException {

	private static final long serialVersionUID = 5867172506387382924L;
	private static final String code = "400";
	private static final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
	
	public NoAuthorityException() {
		super("해당 정보에 접근할 권한이 없습니다.");
	}

	@Override
	public String getMessage() {
		return super.getMessage();
	}

	@Override
	public String getCode() {
		return code;
	}

	@Override
	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

}