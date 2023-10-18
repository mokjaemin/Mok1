package com.ReservationServer1.data.DTO.store;

import org.springframework.web.multipart.MultipartFile;
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
public class StoreFoodsInfoDTO {

	private static final StoreFoodsInfoDTO sample = StoreFoodsInfoDTO.builder().storeId((short) -1).foodName("foodName")
			.foodDescription("foodDescription").foodPrice(0).build();

	@NotNull
	private short storeId;

	@NotNull
	@Size(min = 1, max = 20)
	private String foodName;

	@NotNull
	@Size(min = 1, max = 30)
	private String foodDescription;

	@NotNull
	private int foodPrice;

	private MultipartFile foodImage;

	public static StoreFoodsInfoDTO sample() {
		return sample;
	}
}
