package com.ReservationServer1.data.Entity.POR;

import org.springframework.beans.BeanUtils;
import com.ReservationServer1.data.DTO.POR.ReservationDTO;
import com.ReservationServer1.data.Entity.BaseEntity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
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
@ToString(exclude = "child")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = "child", callSuper = false)
@Table(name = "Reservation")
public class StoreReservationEntity extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Expose
  private Long reservationId;
  @Expose
  private String userId;
  @Expose
  private int storeId;
  @Expose
  private String date;
  @Expose
  private String time;
  @Expose
  private String storeTable;

  @OneToOne(mappedBy = "storeReservationEntity", fetch = FetchType.LAZY,
      cascade = CascadeType.REMOVE)
  @Expose
  private StoreOrdersEntity child;


  public StoreReservationEntity(ReservationDTO reservationDTO) {
    BeanUtils.copyProperties(reservationDTO, this);
  }

  public static Gson createGsonInstance() {
    return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
  }

}
