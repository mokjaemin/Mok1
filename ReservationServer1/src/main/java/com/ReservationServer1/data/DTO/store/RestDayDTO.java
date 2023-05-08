package com.ReservationServer1.data.DTO.store;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RestDayDTO implements Serializable{
  
  private String storeName;
  
  @NotNull
  private Map<String, String> date;
  

  public RestDayDTO(String storeName, Map<String, String> date) {
    this.storeName = storeName;
    this.date = date;
  }

  public static RestDayDTO sample() {
    Map<String, String> sampleDate = new HashMap<String, String>();
    sampleDate.put("date1", "date1");
    sampleDate.put("date2", "date2");
    RestDayDTO sample = new RestDayDTO("sampleName", sampleDate);
    return sample;
  }
}

