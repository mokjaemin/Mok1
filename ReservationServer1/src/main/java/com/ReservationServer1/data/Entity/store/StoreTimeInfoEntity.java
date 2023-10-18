package com.ReservationServer1.data.Entity.store;

import java.util.Set;
import org.springframework.beans.BeanUtils;
import com.ReservationServer1.data.DTO.store.StoreTimeInfoDTO;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
@ToString(exclude = "breakTime")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "breakTime")
@Builder
@Table(name = "StoreTimeInfo")
public class StoreTimeInfoEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int timesId;

	@Column(length = 5)
	@NotNull
	private String startTime;

	@Column(length = 5)
	@NotNull
	private String endTime;

	@Column(length = 5)
	@NotNull
	private String intervalTime;

	@NotNull
	private short storeId;

	@OneToMany(mappedBy = "storeTimeInfoEntity", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private Set<StoreTimeInfoMapEntity> breakTime;

	public StoreTimeInfoEntity(StoreTimeInfoDTO storeTimeInfoDTO) {
		BeanUtils.copyProperties(storeTimeInfoDTO, this);
	}

}
