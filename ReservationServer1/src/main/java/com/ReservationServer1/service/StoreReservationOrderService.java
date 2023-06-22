package com.ReservationServer1.service;

import com.ReservationServer1.data.DTO.ReservationOrder.ReservationDTO;

public interface StoreReservationOrderService {
  
  String registerReservation(ReservationDTO reservationDTO, String userId);

}
