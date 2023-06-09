package com.ReservationServer1.data.Entity.POR;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString(exclude={"childSet", "payment"})
@NoArgsConstructor
@Table(name = "StoreOrders")
public class StoreOrdersEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long ordersId;
  
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "reservation_id")
  @JsonBackReference
  private StoreReservationEntity storeReservationEntity;
  
  @OneToMany(mappedBy = "storeOrdersEntity", fetch = FetchType.LAZY)
  private List<StoreOrdersMapEntity> childSet;
  
  
  @OneToOne(mappedBy = "storeOrdersEntity", fetch = FetchType.LAZY)
  private StorePayEntity payment;
  
  public StoreOrdersEntity(StoreReservationEntity storeReservationEntity) {
    this.storeReservationEntity = storeReservationEntity;
  }
}
