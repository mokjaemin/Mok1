package com.ReservationServer1.data.Entity.store;

import org.springframework.beans.BeanUtils;
import com.ReservationServer1.data.DTO.store.StoreFoodsInfoDTO;
import com.ReservationServer1.data.DTO.store.StoreFoodsInfoResultDTO;
import com.ReservationServer1.data.DTO.store.StoreTableInfoDTO;
import com.ReservationServer1.data.Entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "StoreFoodsInfo")
public class StoreFoodsInfoEntity extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long tableId;

  private int storeId;

  private String foodName;

  private String foodDescription;

  private int foodPrice;

  private String imageURL;

  public StoreFoodsInfoEntity(StoreFoodsInfoDTO storeFoodsInfoDTO) {
    BeanUtils.copyProperties(storeFoodsInfoDTO, this);
  }

  public StoreFoodsInfoResultDTO toStoreFoodsInfoResultDTO() {
    return StoreFoodsInfoResultDTO.builder().storeId(this.storeId).foodName(this.foodName)
        .foodDescription(this.foodDescription).foodPrice(this.foodPrice).build();
  }

}


