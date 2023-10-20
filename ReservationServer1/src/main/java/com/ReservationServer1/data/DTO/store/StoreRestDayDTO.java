package com.ReservationServer1.data.DTO.store;

import java.util.HashSet;
import java.util.Set;
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
public class StoreRestDayDTO {

	private static Set<String> sampleDate = new HashSet<>();
	private static StoreRestDayDTO sample = StoreRestDayDTO.builder().storeId((short) 0).build();

	@NotNull
	private short storeId;

	@NotNull
	private Set<String> date;

	public static StoreRestDayDTO sample() {
		if (sampleDate.size() == 0) {
			sampleDate.add("date1");
		}
		if (sample.getDate() == null) {
			sample.setDate(sampleDate);
		}
		return sample;
	}
}
