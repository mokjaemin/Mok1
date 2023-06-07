package com.ReservationServer1.data.DTO.store;

import java.util.HashMap;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class StoreTimeInfoDTO {
  @NotNull
  private String startTime;
  @NotNull
  private String endTime;
  @NotNull
  private HashMap<String, String> breakTime;
  @NotNull
  private String intervalTime;
  @NotNull
  private String storeName;

  public static StoreTimeInfoDTO sample() {
    HashMap<String, String> breakTimeSample = new HashMap<>();
    breakTimeSample.put("breakTimeKey", "breakTimeValue");
    StoreTimeInfoDTO sample = StoreTimeInfoDTO.builder().startTime("startTime").endTime("endTime")
        .breakTime(breakTimeSample).intervalTime("intervalTime").storeName("storeName").build();
    return sample;
  }


}
