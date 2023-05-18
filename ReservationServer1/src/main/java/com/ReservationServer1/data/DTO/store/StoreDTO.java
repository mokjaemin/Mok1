package com.ReservationServer1.data.DTO.store;

import com.ReservationServer1.data.Identification;
import com.ReservationServer1.data.DTO.member.FindPwdDTO;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;



@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder
public class StoreDTO extends Identification {


  @NotNull
  private String storeName;

  private String ownerId;

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

  public StoreDTO() {
    super();
  }

  public StoreDTO(String storeName, String country, String city, String dong, String type,
      String couponInfo) {
    super();
    this.storeName = storeName;
    this.country = country;
    this.city = city;
    this.dong = dong;
    this.type = type;
    this.couponInfo = couponInfo;
  }

  public static StoreDTO sample() {
    StoreDTO sample = new StoreDTO("name", "country", "city", "dong", "type", "couponInfo");
    sample.setOwnerId("ownerId");
    return sample;
  }
}
