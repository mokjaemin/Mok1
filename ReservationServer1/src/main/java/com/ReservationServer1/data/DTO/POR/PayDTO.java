package com.ReservationServer1.data.DTO.POR;

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
public class PayDTO {

	private static final PayDTO sample = PayDTO.builder().reservationId(0).storeId((short) 0).amount(0).build();

	@NotNull
	private int reservationId;

	@NotNull
	private short storeId;

	@NotNull
	private int amount;

	public static PayDTO sample() {
		return sample;
	}

}
