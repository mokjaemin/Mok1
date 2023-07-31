package com.ReservationServer1.data.Entity.board;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
public class StoreBoardEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long boardId;

  private int storeId;

  private String userId;

  private String title;

  private String content;

  private String comment;

  private String imageURL;

  private double rating;

  public static StoreBoardEntity sample() {
    return StoreBoardEntity.builder().boardId(0L).userId("userId").title("title").content("content")
        .comment("comment").imageURL("url").rating(5.0).build();
  }

}
