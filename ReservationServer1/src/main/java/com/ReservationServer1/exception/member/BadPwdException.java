package com.ReservationServer1.exception.member;

import org.springframework.http.HttpStatus;
import com.ReservationServer1.exception.ReservationException;

public class BadPwdException extends RuntimeException implements ReservationException {

	private static final long serialVersionUID = 5867172506387382920L;
	private static final String code = "400";
	private static final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
	
	public BadPwdException() {
		super("잘못된 비밀번호입니다.");
	}

	@Override
	public String getCode() {
		return BadPwdException.code;
	}

	@Override
	public String getMessage() {
		return super.getMessage();
	}

	@Override
	public HttpStatus getHttpStatus() {
		return BadPwdException.httpStatus;
	}

}
