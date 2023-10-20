package com.ReservationServer1.data.DTO.board;

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
public class BoardCountResultDTO {

	private static final BoardCountResultDTO sample = BoardCountResultDTO.builder().userId("userId").count(0).build();

	private String userId;

	private long count;
	
	public static BoardCountResultDTO getSample() {
		return sample;
	}

}
