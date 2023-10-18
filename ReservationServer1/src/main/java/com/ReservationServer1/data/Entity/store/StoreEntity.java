package com.ReservationServer1.data.Entity.store;

import org.springframework.beans.BeanUtils;
import com.ReservationServer1.data.StoreType;
import com.ReservationServer1.data.DTO.store.StoreDTO;
import com.ReservationServer1.data.Entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "Store")
public class StoreEntity extends BaseEntity {

	private static final StoreEntity sample = StoreEntity.builder().storeId((short) -1).storeName("storeName")
			.ownerId("ownerId").country("country").city("city").dong("dong").type(StoreType.KOREAN)
			.couponInfo("couponInfo").build();

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private short storeId;

	@Column(length = 20)
	@NotNull
	private String storeName;

	@Column(length = 20)
	@NotNull
	private String ownerId;

	@Column(length = 20)
	@NotNull
	private String country;

	@Column(length = 5)
	@NotNull
	private String city;

	@Column(length = 5)
	@NotNull
	private String dong;

	@Enumerated(EnumType.STRING)
	@NotNull
	private StoreType type;

	@Column(length = 30)
	private String couponInfo;

	// StoreDTO -> StoreEntity
	public StoreEntity(StoreDTO storeDTO) {
		BeanUtils.copyProperties(storeDTO, this);
	}

	// StoreEntity -> StoreDTO
	public StoreDTO toStoreDTO() {
		StoreDTO storeDTO = StoreDTO.builder().storeName(this.storeName).ownerId(this.ownerId).country(this.country)
				.city(this.city).dong(this.dong).type(this.type).couponInfo(this.couponInfo).build();
		return storeDTO;
	}

	// Get Sample
	public static StoreEntity sample() {
		return sample;
	}

}
