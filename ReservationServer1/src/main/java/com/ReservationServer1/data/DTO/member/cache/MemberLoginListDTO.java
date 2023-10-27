package com.ReservationServer1.data.DTO.member.cache;

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
@RedisHash(value = "memberLoginList", timeToLive = 60 * 30) // 30분
public class MemberLoginListDTO {
	
	@Id
	private String userId;

	private String accessToken;

}
