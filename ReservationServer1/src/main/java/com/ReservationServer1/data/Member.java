package com.ReservationServer1.data;


import java.io.Serializable;
import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Member implements Serializable { 
	
	
	private String userId;
	private String userPwd;
	private String userName;
	private String userNumber;
	private String userAddress;
	private String userEmail;
	private LocalDate foundationTime;
	
	
	public Member(String userId, String userPwd, String userName, String userNumber, String userAddress, String userEmail) {
		this.userId = userId;
		this.userPwd = userPwd;
		this.userName = userName;
		this.userNumber = userNumber;
		this.userAddress = userAddress;
		this.userEmail = userEmail;
		this.foundationTime = LocalDate.now();
	}
	
	public static Member sample() {
		
		String userId = "testId";
		String userPwd = "testPwd";
		String userName = "testName";
		String userNumber = "testNumber";
		String userAddress = "testAddress";
		String userEmail = "testEmail";

		return new Member(userId, userPwd, userName, userNumber, userAddress, userEmail);
	}

	
}
