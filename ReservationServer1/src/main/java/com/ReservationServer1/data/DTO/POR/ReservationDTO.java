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
public class ReservationDTO {
  
  @NotNull
  private int storeId;
  
  @NotNull
  private String date;
  
  @NotNull
  private String time;
  
  @NotNull
  private String storeTable;
  

}
