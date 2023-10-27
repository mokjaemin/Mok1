package com.ReservationServer1.data.DTO.member;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberTokenResultDTO {
	
	private final String accessToken;
	private final String refreshToken;
	
	public MemberTokenResultDTO(String accessToken, String refreshToken){
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}
	
}
