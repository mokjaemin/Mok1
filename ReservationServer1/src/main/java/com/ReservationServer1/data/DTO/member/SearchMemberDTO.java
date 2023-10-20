package com.ReservationServer1.data.DTO.member;

import jakarta.validation.constraints.Email;
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
public class SearchMemberDTO {

	private static final SearchMemberDTO sample = SearchMemberDTO.builder().userId("userId").userName("name")
			.userNumber("01234567891").userAddress("userAddress").userEmail("userEmail@naver").build();

	@Size(min = 3, max = 20)
	private String userId;

	@Size(min = 2, max = 7)
	private String userName;

	@Size(min = 11, max = 11)
	private String userNumber;

	private String userAddress;

	@Email
	private String userEmail;
	
	public static SearchMemberDTO getSample() {
		return sample;
	}

}
