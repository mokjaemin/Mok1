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

  @NotNull
  private int storeId;
  
  @NotNull
  private HashMap<String, Integer> orderInfo;
  
  public static OrderDTO sample() {
    HashMap<String, Integer> testInfo = new HashMap<>();
    testInfo.put("foodName1", 1);
    testInfo.put("foodName2", 2);
    return OrderDTO.builder().storeId(0).orderInfo(testInfo).build();
  }
  
}
