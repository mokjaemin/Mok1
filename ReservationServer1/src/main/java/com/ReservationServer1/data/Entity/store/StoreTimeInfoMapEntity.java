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
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "StoreTimeInfoMap")
public class StoreTimeInfoMapEntity {


  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long timeId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "timesId", referencedColumnName = "timesId")
  @JsonBackReference
  private StoreTimeInfoEntity storeTimeInfoEntity;
  
  private String time;
}
