package com.ReservationServer1.data.Entity.store;

import java.util.Map;
import org.springframework.beans.BeanUtils;
import com.ReservationServer1.data.DTO.store.RestDayDTO;
import com.ReservationServer1.data.Entity.BaseEntity;
import jakarta.persistence.ElementCollection;
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
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "StoreRestDay", indexes = {@Index(name = "idx_storeName", columnList = "storeName")})
public class StoreRestDayEntity extends BaseEntity {

  @Id
  private String dayId;
  private String storeName;
  @ElementCollection
  private Map<String, String> date;


  // DTO를 Entity로 변환
  public StoreRestDayEntity(RestDayDTO restDayDTO) {
    // DTO의 프로퍼티들을 Entity의 프로퍼티들로 매핑
    BeanUtils.copyProperties(restDayDTO, this);
  }

  // Entity를 다시 DTO로 변환
  public RestDayDTO toDomain() {
      String check = "";
      RestDayDTO restDayDTO = new RestDayDTO(check, this.date);
      restDayDTO.setId(dayId);
      return restDayDTO;
  }
}
