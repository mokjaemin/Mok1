package com.ReservationServer1.DAO;

import com.ReservationServer1.data.DTO.ReservationOrder.ReservationDTO;

public interface StoreReservationOrderDAO {

  String registerReservation(ReservationDTO reservationDTO, String userId);
  String updateReservation(ReservationDTO reservationDTO, String userId);
  ReservationDTO getReservation(String storeName, String userId);
  String deleteReservation(String storeName, String userId);
}
