package com.ReservationServer1.DAO;

import com.ReservationServer1.data.DTO.ROP.OrderDTO;
import com.ReservationServer1.data.DTO.ROP.PayDTO;
import com.ReservationServer1.data.DTO.ROP.ReservationDTO;
import com.ReservationServer1.data.Entity.ROP.StoreReservationEntity;

public interface StoreROPDAO {

  String registerReservation(ReservationDTO reservationDTO, String userId);
  String updateReservation(ReservationDTO reservationDTO, String userId);
  StoreReservationEntity getReservation(String storeName, String userId);
  String deleteReservation(String storeName, String userId);
  
  String registerOrder(OrderDTO orderDTO, String userId);
  String updateOrder(OrderDTO orderDTO, String userId);
  String deleteOrder(String storeName, String foodName, String userId);
  
  
  String registerPay(PayDTO payDTO);
  String deletePay(Long reservationId);
}
