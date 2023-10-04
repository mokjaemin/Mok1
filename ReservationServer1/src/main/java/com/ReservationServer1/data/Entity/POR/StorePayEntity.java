package com.ReservationServer1.data.Entity.POR;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
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
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@Table(name = "StorePay")
public class StorePayEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long paymentId;
  
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "ordersId")
  @JsonBackReference
  private StoreOrdersEntity storeOrdersEntity;
  
  private int amount;
  
  private String comment;
  
  private String bigComment;
}
