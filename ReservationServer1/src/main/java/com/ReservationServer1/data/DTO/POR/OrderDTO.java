package com.ReservationServer1.data.DTO.POR;

import java.util.HashMap;
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
public class OrderDTO {
  private static HashMap<String, Integer> testInfo = new HashMap<>();
  private static OrderDTO sample = OrderDTO.builder().storeId(0).build();

  @NotNull
  private int storeId;
  
  @NotNull
  private HashMap<String, Integer> orderInfo;
  
  public static OrderDTO sample() {
    if(testInfo.size() == 0) {
      testInfo.put("foodName1", 1);
      testInfo.put("foodName2", 2);
    }
    if(sample.getOrderInfo() == null) {
      sample.setOrderInfo(testInfo);
    }
    return sample;
  }
  
}
