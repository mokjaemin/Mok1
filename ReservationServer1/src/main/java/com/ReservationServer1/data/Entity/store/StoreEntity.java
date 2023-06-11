package com.ReservationServer1.data.Entity.store;



import org.springframework.beans.BeanUtils;
import com.ReservationServer1.data.DTO.store.StoreDTO;
import com.ReservationServer1.data.Entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode(callSuper = false)
@Table(name = "Store", indexes = {@Index(name = "idx_city", columnList = "city"),
    @Index(name = "idx_dong", columnList = "dong")})
public class StoreEntity extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int storeId;
  private String storeName;
  private String ownerId;
  private String country;
  private String city;
  private String dong;
  private String type;
  private String couponInfo;

  public StoreEntity(StoreDTO storeDTO) {
    BeanUtils.copyProperties(storeDTO, this);
  }


  public StoreDTO toStoreDTO() {
    StoreDTO storeDTO =
        StoreDTO.builder().storeName(this.storeName).ownerId(this.ownerId).country(this.country)
            .city(this.city).dong(this.dong).type(this.type).couponInfo(this.couponInfo).build();
    return storeDTO;
  }
}
