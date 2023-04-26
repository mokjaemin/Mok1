package com.ReservationServer1.data.DTO.member;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginDTO {
	
	@NotNull
	private String userId;
	
	@NotNull
	private String userPwd;
	
	public LoginDTO(String userId, String userPwd) {
		this.userId = userId;
		this.userPwd = userPwd;
	}
	
	
	public static LoginDTO sample() {
		String userId = "testId";
		String userPwd = "testPwd";
		return new LoginDTO(userId, userPwd);
	}
}
