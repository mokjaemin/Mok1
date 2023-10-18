package com.ReservationServer1.data.DTO.POR;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class ReservationDTO {

	private static final ReservationDTO sample = ReservationDTO.builder().storeId((short) 0).date("1월1일").time("1시")
			.storeTable("1번").build();

	@NotNull
	private short storeId;

	@NotNull
	@Size(min = 1, max = 8)
	private String date;

	@NotNull
	@Size(min = 1, max = 4)
	private String time;

	@NotNull
	@Size(min = 1, max = 4)
	private String storeTable;

	public static ReservationDTO sample() {
		return sample;
	}

}
