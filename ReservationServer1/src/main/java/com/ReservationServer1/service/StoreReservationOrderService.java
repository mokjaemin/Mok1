package com.ReservationServer1.service;

import com.ReservationServer1.data.DTO.ReservationOrder.OrderDTO;
import com.ReservationServer1.data.DTO.ReservationOrder.ReservationDTO;
import com.ReservationServer1.data.Entity.ReservationAndOrder.StoreReservationEntity;

public interface StoreReservationOrderService {
  
  String registerReservation(ReservationDTO reservationDTO, String userId);
  String updateReservation(ReservationDTO reservationDTO, String userId);
  StoreReservationEntity getReservation(String storeName, String userId);
  String deleteReservation(String storeName, String userId);
  
  String registerOrder(OrderDTO orderDTO, String userId);
  String updateOrder(OrderDTO orderDTO, String userId);
  String deleteOrder(String storeName, String foodName, String userId);

}
