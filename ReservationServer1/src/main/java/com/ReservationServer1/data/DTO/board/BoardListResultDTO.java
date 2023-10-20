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
public class BoardListResultDTO {

	private static final BoardListResultDTO sample = BoardListResultDTO.builder().title("title").boardId(0).build();

	private String title;

	private int boardId;

	public static BoardListResultDTO getSample() {
		return sample;
	}

}
