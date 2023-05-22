package com.ReservationServer1.data.DTO.member;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class LoginDTO {
	
	@NotNull
	private String userId;
	
	@NotNull
	private String userPwd;
	
	
	public static LoginDTO sample() {
		return new LoginDTO("testId", "testPwd");
	}
}
