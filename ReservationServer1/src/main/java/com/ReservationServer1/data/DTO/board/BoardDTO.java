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
public class BoardDTO {

  @NotNull
  private int storeId;

  @NotNull
  private String title;

  @NotNull
  private String content;

  @NotNull
  private double rating;
  
  private MultipartFile foodImage;
  
  public static BoardDTO sample() {
    return BoardDTO.builder().storeId(0).title("title").content("content").rating(5.0).build();
  }

}
