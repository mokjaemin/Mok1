package com.ReservationServer1.data.Entity.POR;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
  
  @NotNull
  private int amount;
  
  @Column(length = 40)
  private String comment;
  
  @Column(length = 40)
  private String bigComment;
  
}
