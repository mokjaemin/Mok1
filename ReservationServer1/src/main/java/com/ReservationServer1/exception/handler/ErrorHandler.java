package com.ReservationServer1.exception.handler;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ReservationServer1.exception.ReservationException;

@RestControllerAdvice
public class ErrorHandler {

	private final Logger LOGGER = LoggerFactory.getLogger(ErrorHandler.class);

	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<Map<String, String>> BasicExceptionHandler(Exception e) {

		LOGGER.error("Exception 발생, 원인 : {}", e.getCause());
		LOGGER.error("Exception 발생, 메시지 : {}", e.getMessage());

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add(HttpHeaders.CONTENT_TYPE, "application/json");

		Map<String, String> map = new HashMap<>();
		HttpStatus httpStatus;

		// ReservationException 일 경우
		if (e instanceof ReservationException) {
			ReservationException new_e = (ReservationException) e;
			httpStatus = new_e.getHttpStatus();
			map.put("error type", httpStatus.getReasonPhrase());
			map.put("message", new_e.getMessage());
			map.put("code", new_e.getCode());

		}
		// @Valid 파생 오류
		else if (e instanceof MethodArgumentNotValidException) {
			httpStatus = HttpStatus.BAD_REQUEST;
			map.put("error type", httpStatus.getReasonPhrase());
			map.put("message", "잘못된 데이터 형식입니다.");
			map.put("code", "400");
		}

		// RuntimeException 일 경우
		else {
			httpStatus = HttpStatus.BAD_REQUEST;
			map.put("error type", httpStatus.getReasonPhrase());
			map.put("message", "오류발생");
			map.put("code", "400");
		}

		return new ResponseEntity<>(map, responseHeaders, httpStatus);
	}

}
