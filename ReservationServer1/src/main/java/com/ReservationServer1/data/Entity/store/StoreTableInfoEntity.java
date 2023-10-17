package com.ReservationServer1.data.Entity.store;

import java.sql.Blob;
import org.springframework.beans.BeanUtils;
import com.ReservationServer1.data.DTO.store.StoreTableInfoDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
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
      StoreTableInfoEntity.builder().tableId((short) -1).storeId((short) -1).count((byte) 0).tableImage(null).build();

  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private short tableId;

  @NotNull
  private short storeId;
  
  @NotNull
  private int count;

  @Lob
  private byte[] tableImage;


  // StoreTableInfoDTO -> StoreTableInfoEntity
  public StoreTableInfoEntity(StoreTableInfoDTO storeTableInfoDTO) {
    BeanUtils.copyProperties(storeTableInfoDTO, this);
  }

  // Get Sample
  public static StoreTableInfoEntity sample() {
    return sample;
  }

}
