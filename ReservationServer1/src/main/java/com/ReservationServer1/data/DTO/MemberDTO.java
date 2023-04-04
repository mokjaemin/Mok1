package com.ReservationServer1.data.DTO;


import java.io.Serializable;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class MemberDTO implements Serializable { 
	
	@NotNull
	private String userId;
	
	@NotNull
	private String userPwd;
	
	@NotNull
	private String userName;
	
	@NotNull
	private String userNumber;
	
	@NotNull
	private String userAddress;
	
	@NotNull
	private String userEmail;
	
	
	public MemberDTO(String userId, String userPwd, String userName, String userNumber, String userAddress, String userEmail) {
		this.userId = userId;
		this.userPwd = userPwd;
		this.userName = userName;
		this.userNumber = userNumber;
		this.userAddress = userAddress;
		this.userEmail = userEmail;
	} 
	
	public static MemberDTO sample() {
		
		String userId = "testId";
		String userPwd = "testPwd";
		String userName = "testName";
		String userNumber = "testNumber";
		String userAddress = "testAddress";
		String userEmail = "testEmail";
		return new MemberDTO(userId, userPwd, userName, userNumber, userAddress, userEmail);
	}

	
}
