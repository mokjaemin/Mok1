package com.ReservationServer1.data.Entity.store;

import java.util.Set;
import com.ReservationServer1.data.Entity.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Entity
@Getter
@Setter
@ToString(exclude = "childSet")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false, exclude="childSet")
@Table(name = "StoreRestDays")
public class StoreRestDaysEntity extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int daysId;
  
  @NotNull
  private short storeId;
  
  @OneToMany(mappedBy = "storeRestDaysEntity", fetch = FetchType.LAZY,  cascade = CascadeType.REMOVE)
  private Set<StoreRestDaysMapEntity> childSet;

  
  public StoreRestDaysEntity(short storeId) {
    this.storeId = storeId;
  }
}
