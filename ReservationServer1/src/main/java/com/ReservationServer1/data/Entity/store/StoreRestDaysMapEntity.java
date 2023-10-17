package com.ReservationServer1.data.Entity.store;

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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "StoreRestDaysMap")
public class StoreRestDaysMapEntity {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int dayId;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "days_id")
  @JsonBackReference
  private StoreRestDaysEntity storeRestDaysEntity;
  
  @Column(length = 7)
  @NotNull
  private String date;
  
  public StoreRestDaysMapEntity(String date, StoreRestDaysEntity storeRestDaysEntity) {
    this.date = date;
    this.storeRestDaysEntity = storeRestDaysEntity;;
  }
}
