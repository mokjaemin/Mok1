package com.ReservationServer1.data.DTO.store.cache;

import java.util.HashMap;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
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
@RedisHash(value = "storeList", timeToLive = 60 * 10) // 10분
public class StoreListDTO {

  private static HashMap<String, Integer> testStoreList = new HashMap<>();
  private static StoreListDTO sample = StoreListDTO.builder().Address("testAddress").build();

  @Id
  private String Address;

  // Name, Id
  private HashMap<String, Integer> storeList;

  public static StoreListDTO sample() {
    if(testStoreList.size() == 0) {
      testStoreList.put("Name1", 1);
    }
    if(sample.getStoreList() == null) {
      sample.setStoreList(testStoreList);
    }
    return sample;
  }

}

