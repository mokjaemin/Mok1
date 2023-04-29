package com.ReservationServer1.data.DTO.store;

import java.util.HashMap;
import java.util.Map;
import com.ReservationServer1.data.Identification;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RestDayDTO extends Identification{
  
  private String storeName;
  
  @NotNull
  private Map<String, String> date;
  

  public RestDayDTO(String check, Map<String, String> date) {
    super();
    this.date = date;
  }

  public static RestDayDTO sample() {
    Map<String, String> sampleDate = new HashMap<String, String>();
    sampleDate.put("date1", "date1");
    sampleDate.put("date2", "date2");
    RestDayDTO sample = new RestDayDTO(null, sampleDate);
    return sample;
  }
}

