package com.ReservationServer1.data.DTO.member;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
@EqualsAndHashCode
public class LoginDTO {

	private static final LoginDTO sample = LoginDTO.builder().userId("testId").userPwd("testPwd").build();

	@NotNull
	@Size(min = 3, max = 20)
	private String userId;

	@NotNull
	@Size(min = 3, max = 20)
	private String userPwd;

	public static LoginDTO sample() {
		return sample;
	}
}
