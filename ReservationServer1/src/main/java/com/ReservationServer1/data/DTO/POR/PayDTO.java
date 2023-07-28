package com.ReservationServer1.data.DTO.POR;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class PayDTO {

  @NotNull
  private Long reservationId;
  
  @NotNull
  private int storeId;
  
  @NotNull
  private int amount;
  
  public static PayDTO sample() {
    return PayDTO.builder().reservationId(0L).storeId(0).amount(0).build();
  }
  
}
