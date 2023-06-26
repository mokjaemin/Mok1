package com.ReservationServer1.data.Entity.ReservationAndOrder;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
@Table(name = "StoreOrdersMap")
@EqualsAndHashCode(callSuper=false)
public class StoreOrdersMapEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long orderId;
  
  private String foodName;
  
  private int foodCount;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "orders_id")
  @JsonBackReference
  private StoreOrdersEntity storeOrdersEntity;
  
  public StoreOrdersMapEntity(String foodName, int foodCount, StoreOrdersEntity storeOrdersEntity) {
    this.foodName = foodName;
    this.foodCount = foodCount;
    this.storeOrdersEntity = storeOrdersEntity;
  }
}
