package com.ReservationServer1.data.Entity.store;

import java.util.LinkedHashSet;
import java.util.Set;
import com.ReservationServer1.data.Entity.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Table(name = "StoreRestDays", indexes = {@Index(name = "idx_storeName", columnList = "storeName")})
public class StoreRestDaysEntity extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long daysId;
  private String storeName;
  
  @OneToMany(mappedBy = "storeRestDaysEntity", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
  private Set<StoreRestDaysMapEntity> childSet = new LinkedHashSet<>();

  
  public StoreRestDaysEntity(String storeName) {
    this.storeName = storeName;
  }
}
