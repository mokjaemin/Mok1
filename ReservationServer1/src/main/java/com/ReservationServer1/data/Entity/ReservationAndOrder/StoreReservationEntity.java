package com.ReservationServer1.data.Entity.ReservationAndOrder;

import org.springframework.beans.BeanUtils;
import com.ReservationServer1.data.DTO.ReservationOrder.ReservationDTO;
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
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Reservation")
@EqualsAndHashCode(callSuper = false)
public class StoreReservationEntity extends BaseEntity{
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long reservationId;
  private String userId;
  private String storeName;
  private String date;
  private String time;
  private String storeTable;
  
  public StoreReservationEntity(ReservationDTO reservationDTO) {
    BeanUtils.copyProperties(reservationDTO, this);
  }
  
}
