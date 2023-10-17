package com.ReservationServer1.data.DTO.board;

import org.springframework.web.multipart.MultipartFile;
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
public class BoardDTO {

  private static final BoardDTO sample =
      BoardDTO.builder().storeId((short) 0).title("title").content("content").rating(5.0).build();

  @NotNull
  private short storeId;

  @NotNull
  @Size(min=1, max=20)
  private String title;

  @NotNull
  @Size(min=3, max=50)
  private String content;

  @NotNull
  private double rating;

  private MultipartFile foodImage;

  public static BoardDTO sample() {
    return sample;
  }

}
