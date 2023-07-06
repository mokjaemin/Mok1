package com.ReservationServer1.data.DTO.store;

import java.util.ArrayList;
import java.util.List;
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
  private List<String> breakTime;
  @NotNull
  private String intervalTime;
  @NotNull
  private int storeId;

  public static StoreTimeInfoDTO sample() {
    List<String> breakTimeSample = new ArrayList<>();
    breakTimeSample.add("breakTime");
    StoreTimeInfoDTO sample = StoreTimeInfoDTO.builder().startTime("startTime").endTime("endTime")
        .breakTime(breakTimeSample).intervalTime("intervalTime").storeId(1).build();
    return sample;
  }


}
