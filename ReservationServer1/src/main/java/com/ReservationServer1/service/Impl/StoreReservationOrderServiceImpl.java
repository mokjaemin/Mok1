package com.ReservationServer1.service.Impl;

import org.springframework.stereotype.Service;
import com.ReservationServer1.DAO.StoreReservationOrderDAO;
import com.ReservationServer1.data.DTO.ReservationOrder.ReservationDTO;
import com.ReservationServer1.service.StoreReservationOrderService;

@Service
public class StoreReservationOrderServiceImpl implements StoreReservationOrderService{

  private final StoreReservationOrderDAO storeReservationOrderDAO;
  
  public StoreReservationOrderServiceImpl(StoreReservationOrderDAO storeReservationOrderDAO) {
    this.storeReservationOrderDAO = storeReservationOrderDAO;
  }
  
  @Override
  public String registerReservation(ReservationDTO reservationDTO, String userId) {
    return storeReservationOrderDAO.registerReservation(reservationDTO, userId);
  }

}
