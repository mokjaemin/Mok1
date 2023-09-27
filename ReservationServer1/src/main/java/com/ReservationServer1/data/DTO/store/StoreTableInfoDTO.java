package com.ReservationServer1.data.DTO.store;

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
public class StoreTableInfoDTO {

  private static final StoreTableInfoDTO sample =
      StoreTableInfoDTO.builder().storeId(0).count(1).tableImage(null).build();

  @NotNull
  private int storeId;

  @NotNull
  private int count;

  private MultipartFile tableImage;

  public static StoreTableInfoDTO sample() {
    return sample;
  }

}
