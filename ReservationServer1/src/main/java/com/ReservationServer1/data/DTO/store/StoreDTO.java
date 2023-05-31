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
public class StoreDTO {

  private static final StoreDTO sample =
      StoreDTO.builder().storeName("storeName").ownerId("ownerId").country("country").city("city")
          .dong("dong").type("type").couponInfo("couponInfo").build();



  private String ownerId;

  @NotNull
  private String storeName;

  @NotNull
  private String country;

  @NotNull
  private String city;

  @NotNull
  private String dong;

  @NotNull
  private String type;

  @NotNull
  private String couponInfo;


  public static StoreDTO sample() {
    return sample;
  }
}
