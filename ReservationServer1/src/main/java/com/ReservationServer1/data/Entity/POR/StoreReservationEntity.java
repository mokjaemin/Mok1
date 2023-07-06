package com.ReservationServer1.data.Entity.POR;

import org.springframework.beans.BeanUtils;
import com.ReservationServer1.data.DTO.POR.ReservationDTO;
import com.ReservationServer1.data.Entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
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
@ToString(exclude="child")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Reservation")
public class StoreReservationEntity extends BaseEntity{
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long reservationId;
  private String userId;
  private int storeId;
  private String date;
  private String time;
  private String storeTable;
  
  @OneToOne(mappedBy = "storeReservationEntity", fetch = FetchType.LAZY)
  private StoreOrdersEntity child;
  
  
  public StoreReservationEntity(ReservationDTO reservationDTO) {
    BeanUtils.copyProperties(reservationDTO, this);
  }
  
}
