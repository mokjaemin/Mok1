package com.ReservationServer1.data.Entity.store;



import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "StoreRestDaysMap")
public class StoreRestDaysMapEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long dayId;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "days_id")
  @JsonBackReference
  private StoreRestDaysEntity storeRestDaysEntity;
  
  private String date;
  
  public StoreRestDaysMapEntity(String date, StoreRestDaysEntity storeRestDaysEntity) {
    this.date = date;
    this.storeRestDaysEntity = storeRestDaysEntity;;
  }
}
