package com.ReservationServer1.exception;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;




@RestControllerAdvice
public class ErrorHandler {

	private final Logger LOGGER = LoggerFactory.getLogger(ErrorHandler.class);
	
	
	// Basic Exception Handler
	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<Map<String, String>> BasicExceptionHandler(Exception e) {
		HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add(HttpHeaders.CONTENT_TYPE, "application/json");
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        LOGGER.error("ExceptionHandler 호출, {}, {}", e.getCause(), e.getMessage());

        Map<String, String> map = new HashMap<>();
        map.put("error type", httpStatus.getReasonPhrase());
        map.put("code", "400");
        map.put("message", e.getMessage());

        return new ResponseEntity<>(map, responseHeaders, httpStatus);
    }
	
	
	
	// Exist Member Id Exception Handler
	@ExceptionHandler(value = MemberException.class)
	public ResponseEntity<Map<String, String>> ExistMemberExceptionHandler(Exception e) {
		HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add(HttpHeaders.CONTENT_TYPE, "application/json");
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        LOGGER.error("Member Exception, 원인 : {}", e.getMessage());

        Map<String, String> map = new HashMap<>();
        map.put("error type", httpStatus.getReasonPhrase());
        map.put("code", "400");
        map.put("message", e.getMessage());
        return new ResponseEntity<>(map, responseHeaders, httpStatus);
    }
	
	
}
