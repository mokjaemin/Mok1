package com.ReservationServer1.data.DTO.store;

import java.util.HashMap;
import java.util.Map;
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
@EqualsAndHashCode(callSuper = false)
public class RestDayDTO{
  
  @NotNull
  private String storeName;
  
  @NotNull
  private Map<String, String> date;
  

  public static RestDayDTO sample() {
    Map<String, String> sampleDate = new HashMap<String, String>();
    sampleDate.put("date1", "date1");
    sampleDate.put("date2", "date2");
    return new RestDayDTO("sampleName", sampleDate);
  }
}

