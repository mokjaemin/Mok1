package com.ReservationServer1.data.Entity.store;

import org.springframework.beans.BeanUtils;

import com.ReservationServer1.data.DTO.store.StoreFoodsInfoDTO;
import com.ReservationServer1.data.DTO.store.StoreFoodsInfoResultDTO;
import com.ReservationServer1.data.Entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@Table(name = "StoreFoodsInfo")
public class StoreFoodsInfoEntity extends BaseEntity {

	private static final StoreFoodsInfoEntity sample = StoreFoodsInfoEntity.builder().foodsId((short) -1)
			.storeId((short) -1).foodName("foodName").foodDescription("foodDescription").foodPrice(0)
			.foodImage(new byte[10]).build();

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private short foodsId;

	@NotNull
	private short storeId;

	@Column(length = 20)
	@NotNull
	private String foodName;

	@Column(length = 30)
	@NotNull
	private String foodDescription;

	@NotNull
	private int foodPrice;

	@Lob
	private byte[] foodImage;

	// StoreFoodsInfoDTO -> StoreFoodsInfoEntity
	public StoreFoodsInfoEntity(StoreFoodsInfoDTO storeFoodsInfoDTO) {
		BeanUtils.copyProperties(storeFoodsInfoDTO, this);
	}

	// StoreFoodsInfoEntity -> StoreFoodsInfoResultDTO
	public StoreFoodsInfoResultDTO toStoreFoodsInfoResultDTO() {
		return StoreFoodsInfoResultDTO.builder().storeId(this.storeId).foodName(this.foodName)
				.foodDescription(this.foodDescription).foodPrice(this.foodPrice).build();
	}

	public static StoreFoodsInfoEntity sample() {
		return sample;
	}

}
