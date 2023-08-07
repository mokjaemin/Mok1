package com.ReservationServer1.data.Entity.POR;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "StoreCoupon")
public class StoreCouponEntity {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long couponId;
  
  private int storeId;
  
  private String userId;
  
  private int amount;
  
  public static StoreCouponEntity sample() {
    return StoreCouponEntity.builder().couponId(-1L).storeId(-1).userId("userId").amount(0).build();
  }

}
