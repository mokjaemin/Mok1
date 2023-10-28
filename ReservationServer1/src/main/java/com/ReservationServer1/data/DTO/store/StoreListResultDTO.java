package com.ReservationServer1.data.DTO.store;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class StoreListResultDTO {

	private String storeName;

	private short storeId;

}
