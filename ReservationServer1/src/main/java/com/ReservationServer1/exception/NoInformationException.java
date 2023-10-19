package com.ReservationServer1.exception;

import org.springframework.http.HttpStatus;

public class NoInformationException extends RuntimeException implements ReservationException {

	private static final long serialVersionUID = 5867172506387382925L;
	private static final String code = "400";
	private static final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

	public NoInformationException() {
		super("해당 정보를 찾을 수 없습니다.");
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
