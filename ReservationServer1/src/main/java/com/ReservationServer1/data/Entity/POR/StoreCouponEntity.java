package com.ReservationServer1.data.Entity.POR;

import jakarta.persistence.Entity;
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
@Table(name = "StoreCoupon")
@EqualsAndHashCode
public class StoreCouponEntity {

	// Sample
	private static final StoreCouponEntity sample = StoreCouponEntity.builder().couponId(-1).storeId((short) -1)
			.userId("userId").amount(0).build();

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int couponId;

	@NotNull
	private short storeId;

	@NotNull
	private String userId;

	@NotNull
	private int amount;

	// Get Sample
	public static StoreCouponEntity sample() {
		return sample;
	}

}
