package com.ReservationServer1.data.DTO.store.cache;

import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import com.ReservationServer1.data.DTO.store.StoreDTO;
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
@RedisHash(value = "storeList", timeToLive = 60*10) // 10분
public class StoreListDTO{

    @Id
    private String Address;

    private List<String> storeList;

}

