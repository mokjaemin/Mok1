package com.ReservationServer1.DAO;

import com.ReservationServer1.data.DTO.ReservationOrder.ReservationDTO;

public interface StoreReservationOrderDAO {

  String registerReservation(ReservationDTO reservationDTO, String userId);
}
