package com.ReservationServer1.data.DTO.store;

import java.util.ArrayList;
import java.util.List;
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
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class StoreTimeInfoDTO {

  private static List<String> breakTimeSample = new ArrayList<>();
  private static StoreTimeInfoDTO sample = StoreTimeInfoDTO.builder().startTime("startTime")
      .endTime("endTime").intervalTime("intervalTime").storeId(1).build();

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
    if (breakTimeSample.size() == 0) {
      breakTimeSample.add("breakTime");
    }
    if (sample.getBreakTime() == null) {
      sample.setBreakTime(breakTimeSample);
    }
    return sample;
  }


}
