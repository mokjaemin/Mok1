package com.ReservationServer1.data.DTO.store;

import java.util.HashSet;
import java.util.Set;
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
public class StoreRestDayDTO {


  @NotNull
  private String storeName;

  @NotNull
  private Set<String> date;


  public static StoreRestDayDTO sample() {
    Set<String> sampleDate = new HashSet<>();
    sampleDate.add("date1");
    return new StoreRestDayDTO("sampleName", sampleDate);
  }
}

