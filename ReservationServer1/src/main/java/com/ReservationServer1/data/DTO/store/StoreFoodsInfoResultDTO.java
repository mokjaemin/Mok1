package com.ReservationServer1.data.DTO.store;

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
public class StoreFoodsInfoResultDTO {

  private static final StoreFoodsInfoResultDTO sample =
      StoreFoodsInfoResultDTO.builder().storeId(0).foodName("foodName")
          .foodDescription("foodDescription").foodPrice(0).encoded_image("imageString").build();

  @NotNull
  private int storeId;

  @NotNull
  private String foodName;

  @NotNull
  private String foodDescription;

  @NotNull
  private int foodPrice;

  private String encoded_image;

  public static StoreFoodsInfoResultDTO sample() {
    return sample;
  }
}
