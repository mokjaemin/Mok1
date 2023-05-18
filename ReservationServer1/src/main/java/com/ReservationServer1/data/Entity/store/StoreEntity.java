package com.ReservationServer1.data.Entity.store;




import org.springframework.beans.BeanUtils;
import com.ReservationServer1.data.DTO.store.StoreDTO;
import com.ReservationServer1.data.Entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
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
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Store", indexes = {
    @Index(name = "idx_city", columnList = "city"),
    @Index(name = "idx_dong", columnList = "dong")
})
public class StoreEntity extends BaseEntity{
  
  @Id
  private String storeId;
  private String storeName;
  private String ownerId;
  private String country;
  private String city;
  private String dong;
  private String type;
  private String couponInfo;
  
  //DTO를 Entity로 변환
  public StoreEntity(StoreDTO storeDTO) {
      // DTO의 프로퍼티들을 Entity의 프로퍼티들로 매핑
      BeanUtils.copyProperties(storeDTO, this);
  }
  
  // Entity를 다시 DTO로 변환 
  public StoreDTO toDomain() {
      StoreDTO storeDTO = new StoreDTO(this.storeName, this.country, this.city, this.dong, this.type, this.couponInfo);
      storeDTO.setId(this.storeId);
      storeDTO.setOwnerId(this.ownerId);
      return storeDTO;
  }
}
