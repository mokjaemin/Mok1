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
public class BoardResultDTO {

	private static final BoardResultDTO sample = BoardResultDTO.builder().boardId(0).userId("userId").title("title")
			.content("content").comment("comment").rating(5.0).encoded_boardImage("image").build();

	private int boardId;

	private short storeId;

	private String userId;

	private String title;

	private String content;

	private String comment;

	private double rating;

	private int views;
	
	private String encoded_boardImage;
	

	public static BoardResultDTO getSample() {
		return sample;
	}
}
