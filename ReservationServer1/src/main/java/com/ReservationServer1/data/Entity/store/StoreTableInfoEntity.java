package com.ReservationServer1.data.Entity.store;

import org.springframework.beans.BeanUtils;
import com.ReservationServer1.data.DTO.store.StoreTableInfoDTO;
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
@EqualsAndHashCode
@Builder
@Table(name = "StoreTableInfo")
public class StoreTableInfoEntity {

  private static final StoreTableInfoEntity sample =
      StoreTableInfoEntity.builder().tableId(-1L).storeId(-1).count(0).imageURL("url").build();

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long tableId;

  private int storeId;

  private int count;

  private String imageURL;


  public StoreTableInfoEntity(StoreTableInfoDTO storeTableInfoDTO) {
    BeanUtils.copyProperties(storeTableInfoDTO, this);
  }

  public static StoreTableInfoEntity sample() {
    return sample;
  }

}
