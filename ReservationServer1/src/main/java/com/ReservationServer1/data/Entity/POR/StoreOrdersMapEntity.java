package com.ReservationServer1.data.Entity.POR;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "StoreOrdersMap")
@EqualsAndHashCode
@AllArgsConstructor
@Builder
public class StoreOrdersMapEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int orderId;
  
  @Column(length = 20)
  @NotNull
  private String foodName;
  
  @NotNull
  private short foodCount;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "orders_id")
  @JsonBackReference
  private StoreOrdersEntity storeOrdersEntity;
  
  public StoreOrdersMapEntity(String foodName, short foodCount, StoreOrdersEntity storeOrdersEntity) {
    this.foodName = foodName;
    this.foodCount = foodCount;
    this.storeOrdersEntity = storeOrdersEntity;
  }
  
}
