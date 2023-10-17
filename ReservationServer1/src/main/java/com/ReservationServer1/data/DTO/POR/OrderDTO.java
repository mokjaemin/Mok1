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
  
  // Sample Data
  private static HashMap<String, Short> testInfo = new HashMap<>();
  private static OrderDTO sample = OrderDTO.builder().storeId((short) 0).build();

  @NotNull
  private short storeId;
  
  @NotNull
  private HashMap<String, Short> orderInfo;
  
  public static OrderDTO sample() {
    if(testInfo.size() == 0) {
      testInfo.put("foodName1", (short) 1);
      testInfo.put("foodName2", (short) 2);
    }
    if(sample.getOrderInfo() == null) {
      sample.setOrderInfo(testInfo);
    }
    return sample;
  }
  
}
