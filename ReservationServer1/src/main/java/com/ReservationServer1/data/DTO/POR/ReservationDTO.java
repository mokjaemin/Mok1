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

  private static final ReservationDTO sample =
      ReservationDTO.builder().storeId(0).date("1월1일").time("1시").storeTable("1번").build();

  @NotNull
  private int storeId;

  @NotNull
  private String date;

  @NotNull
  private String time;

  @NotNull
  private String storeTable;

  public static ReservationDTO sample() {
    return sample;
  }


}
