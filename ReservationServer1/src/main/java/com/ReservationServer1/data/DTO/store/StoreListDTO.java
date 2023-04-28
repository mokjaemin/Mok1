package com.ReservationServer1.data.DTO.store;

import java.io.Serializable;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@RedisHash(value = "storeList", timeToLive = 60*10) // 10분
public class StoreListDTO implements Serializable {

    private static final long serialVersionUID = -214490344996507077L;

    @Id
    private String Address;

    private List<String> storeList;

}

