package com.ReservationServer1.data.Entity.store;

import java.util.Set;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
@Table(name = "StoreTimeInfo")
public class StoreTimeInfoEntity {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long timesId;
  private String startTime;
  private String endTime;
  private String intervalTime;
  private int storeId;
  
  @OneToMany(mappedBy = "storeTimeInfoEntity", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
  private Set<StoreTimeInfoMapEntity> breakTime;
    

}
