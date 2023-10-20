package com.ReservationServer1.data.DTO.member;

import jakarta.validation.constraints.Email;
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
public class MemberDTO {

	private static final MemberDTO sample = MemberDTO.builder().userId("userId").userPwd("userPwd").userName("name")
			.userNumber("01234567891").userAddress("address").userEmail("test@email").build();

	@NotNull
	@Size(min = 3, max = 20)
	private String userId;

	@NotNull
	@Size(min = 3, max = 20)
	private String userPwd;

	@NotNull
	@Size(min = 2, max = 7)
	private String userName;

	@NotNull
	@Size(min = 11, max = 11)
	private String userNumber;

	@NotNull
	private String userAddress;

	@NotNull
	@Email
	private String userEmail;

	public MemberDTO(MemberDTO other) {
		this.userId = other.userId;
		this.userPwd = other.userPwd;
		this.userName = other.userName;
		this.userNumber = other.userNumber;
		this.userAddress = other.userAddress;
		this.userEmail = other.userEmail;
	}

	public static MemberDTO getSample() {
		return sample;
	}

}
