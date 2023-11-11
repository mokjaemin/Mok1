package com.ReservationServer1.exception;

import org.springframework.http.HttpStatus;

public class UploadPhotoException extends RuntimeException implements ReservationException {

	
	private static final long serialVersionUID = 5867172506387382989L;
	private static final String code = "400";
	private static final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
	
	public UploadPhotoException() {
		super("해당 사진은 업로드가 불가능합니다.");
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
