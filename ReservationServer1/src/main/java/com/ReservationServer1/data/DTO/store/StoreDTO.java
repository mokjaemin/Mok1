package com.ReservationServer1.data.DTO.store;

import com.ReservationServer1.data.Identification;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;



@Data
public class StoreDTO extends Identification{
  
  public StoreDTO(String storeId) {
    super(storeId);
  }
  
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
  
  
  
  public StoreDTO(String storeName, String country, String city, String dong, String type, String couponInfo) {
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
