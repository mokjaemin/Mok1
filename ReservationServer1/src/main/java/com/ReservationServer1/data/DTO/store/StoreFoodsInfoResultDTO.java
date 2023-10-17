package com.ReservationServer1.data.DTO.store;

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
      StoreFoodsInfoResultDTO.builder().storeId((short) 0).foodName("foodName")
          .foodDescription("foodDescription").foodPrice(0).encoded_image("imageString").build();

  private short storeId;

  private String foodName;

  private String foodDescription;

  private int foodPrice;

  private String encoded_image;

  public static StoreFoodsInfoResultDTO sample() {
    return sample;
  }
}
