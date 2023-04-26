package com.ReservationServer1.data.Entity;

import org.springframework.beans.BeanUtils;
import com.ReservationServer1.data.DTO.store.StoreDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="Store")
public class StoreEntity extends BaseEntity{
  
  @Id
  private String storeId;
  private String storeName;
  private String ownerId;
  private String storeAddress;
  private String couponInfo;
  
  //DTO를 Entity로 변환
  public StoreEntity(StoreDTO storeDTO) {
      // DTO의 프로퍼티들을 Entity의 프로퍼티들로 매핑
      BeanUtils.copyProperties(storeDTO, this);
  }
  
  // Entity를 다시 DTO로 변환 
  public StoreDTO toDomain() {
      StoreDTO storeDTO = new StoreDTO(this.storeName, this.storeAddress, this.couponInfo);
      return storeDTO;
  }
}
