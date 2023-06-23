package com.ReservationServer1.service;

import com.ReservationServer1.data.DTO.ReservationOrder.ReservationDTO;

public interface StoreReservationOrderService {
  
  String registerReservation(ReservationDTO reservationDTO, String userId);
  String updateReservation(ReservationDTO reservationDTO, String userId);
  ReservationDTO getReservation(String storeName, String userId);
  String deleteReservation(String storeName, String userId);

}
