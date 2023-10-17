package com.ReservationServer1.data.Entity.POR;

import org.springframework.beans.BeanUtils;
import com.ReservationServer1.data.DTO.POR.ReservationDTO;
import com.ReservationServer1.data.Entity.BaseEntity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
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
  private int reservationId;
  
  @Expose
  @Column(length = 20)
  @NotNull
  private String userId;
  
  @Expose
  @NotNull
  private short storeId;
  
  @Expose
  @Column(length = 8)
  @NotNull
  private String date;
  
  @Expose
  @Column(length = 4)
  @NotNull
  private String time;
  
  @Expose
  @Column(length = 4)
  private String storeTable;

  @OneToOne(mappedBy = "storeReservationEntity", fetch = FetchType.LAZY,
      cascade = CascadeType.REMOVE)
  @Expose
  private StoreOrdersEntity child;
  
  
  
  public StoreReservationEntity(StoreReservationEntity other) {
    this.reservationId = other.reservationId;
    this.userId = other.userId;
    this.storeId = other.storeId;
    this.date = other.date;
    this.time = other.time;
    this.storeTable = other.storeTable;
    this.child = other.child;
  }


  public StoreReservationEntity(ReservationDTO reservationDTO) {
    BeanUtils.copyProperties(reservationDTO, this);
  }

  public static Gson createGsonInstance() {
    return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
  }

}
