package com.ReservationServer1.exception.store;

import org.springframework.http.HttpStatus;

import com.ReservationServer1.exception.ReservationException;

public class NoInformationException extends RuntimeException implements ReservationException {

	private static final long serialVersionUID = 5867172506387382920L;
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
		return NoInformationException.code;
	}

	@Override
	public HttpStatus getHttpStatus() {
		return NoInformationException.httpStatus;
	}

}
