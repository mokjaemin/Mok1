package com.ReservationServer1.data.Entity.board;

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
@Table(name = "StoreBoard")
@EqualsAndHashCode(callSuper = false)
public class StoreBoardEntity extends BaseEntity{

	private static final StoreBoardEntity sample = StoreBoardEntity.builder().boardId((short) 0).userId("userId")
			.title("title").content("content").comment("comment").boardImage(new byte[10]).rating(5.0).views(10)
			.build();

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int boardId;

	@NotNull
	private short storeId;

	@NotNull
	private String userId;

	@Column(length = 20)
	@NotNull
	private String title;

	@Column(length = 50)
	@NotNull
	private String content;

	@Column(length = 50)
	private String comment;

	@Lob
	private byte[] boardImage;

	@NotNull
	private double rating;

	private int views;

	public static StoreBoardEntity sample() {
		return sample;
	}

}
