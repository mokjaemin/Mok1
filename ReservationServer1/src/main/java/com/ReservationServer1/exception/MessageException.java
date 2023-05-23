package com.ReservationServer1.exception;



public class MessageException extends RuntimeException{

	private String message;
	private static final long serialVersionUID = 5867172506387382920L;
	
	
	public MessageException(String message) {
		this.message = message;
	}
	
	public String getMessage() {
        return this.message;
    }
	
}
