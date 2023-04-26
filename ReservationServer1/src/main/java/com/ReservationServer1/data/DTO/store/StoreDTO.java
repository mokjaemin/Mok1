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
  private String storeAddress;
  
  @NotNull
  private String couponInfo;
  
  
  public StoreDTO(String storeName, String storeAddress, String couponInfo) {
    super();
    this.storeName = storeName;
    this.storeAddress = storeAddress;
    this.couponInfo = couponInfo;
  }
  
  public static StoreDTO sample() {
    return new StoreDTO("storeName", "storeAddress", "couponInfo");
  }
}
