package com.ReservationServer1.exception.store;

import org.springframework.http.HttpStatus;
import com.ReservationServer1.exception.ReservationException;

public class NoAuthorityException extends RuntimeException implements ReservationException {

	private static final long serialVersionUID = 5867172506387382920L;
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
		return NoAuthorityException.code;
	}

	@Override
	public HttpStatus getHttpStatus() {
		return NoAuthorityException.httpStatus;
	}

}