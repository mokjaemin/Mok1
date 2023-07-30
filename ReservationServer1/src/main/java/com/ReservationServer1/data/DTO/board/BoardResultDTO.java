package com.ReservationServer1.data.DTO.board;

import org.springframework.web.multipart.MultipartFile;
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
@EqualsAndHashCode
public class BoardResultDTO {

  private Long boardId;

  private int storeId;

  private String userId;

  private String title;

  private String content;

  private String comment;

  private double rating;

  private String foodImage;


  public static BoardResultDTO sample() {
    return BoardResultDTO.builder().boardId(0L).userId("userId").title("title").content("content")
        .comment("comment").rating(5.0).foodImage("foodImage").build();
  }
}
