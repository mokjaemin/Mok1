package com.ReservationServer1.data.DTO.store;

import com.ReservationServer1.data.StoreType;
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
public class StoreDTO {

  private static final StoreDTO sample =
      StoreDTO.builder().storeName("storeName").ownerId("userId").country("country").city("city")
          .dong("dong").type(StoreType.KOREAN).couponInfo("couponInfo").build();


  private String ownerId;


  @NotNull
  @Size(min = 1, max = 20)
  private String storeName;

  @NotNull
  @Size(min = 1, max = 20)
  private String country;

  @NotNull
  @Size(min = 1, max = 5)
  private String city;

  @NotNull
  @Size(min = 1, max = 5)
  private String dong;

  @NotNull
  private StoreType type;

  @NotNull
  @Size(min = 0, max = 30)
  private String couponInfo;


  public void setType(String type) {
    this.type = StoreType.valueOf(type);
  }


  public static StoreDTO sample() {
    return sample;
  }
}
