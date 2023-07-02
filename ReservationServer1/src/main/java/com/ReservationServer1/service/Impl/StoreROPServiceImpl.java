package com.ReservationServer1.service.Impl;

import org.springframework.stereotype.Service;
import com.ReservationServer1.DAO.StoreROPDAO;
import com.ReservationServer1.data.DTO.ROP.OrderDTO;
import com.ReservationServer1.data.DTO.ROP.PayDTO;
import com.ReservationServer1.data.DTO.ROP.ReservationDTO;
import com.ReservationServer1.data.Entity.ROP.StoreReservationEntity;
import com.ReservationServer1.service.StoreROPService;


@Service
public abstract class StoreROPServiceImpl implements StoreROPService{

  private final StoreROPDAO storeReservationOrderDAO;
  
  public StoreROPServiceImpl(StoreROPDAO storeReservationOrderDAO) {
    this.storeReservationOrderDAO = storeReservationOrderDAO;
  }
  
  @Override
  public String registerReservation(ReservationDTO reservationDTO, String userId) {
    return storeReservationOrderDAO.registerReservation(reservationDTO, userId);
  }

  @Override
  public String updateReservation(ReservationDTO reservationDTO, String userId) {
    return storeReservationOrderDAO.updateReservation(reservationDTO, userId);
  }

  @Override
  public StoreReservationEntity getReservation(String storeName, String userId) {
    return storeReservationOrderDAO.getReservation(storeName, userId);
  }

  @Override
  public String deleteReservation(String storeName, String userId) {
    return storeReservationOrderDAO.deleteReservation(storeName, userId);
  }

  @Override
  public String registerOrder(OrderDTO orderDTO, String userId) {
    return storeReservationOrderDAO.registerOrder(orderDTO, userId);
  }

  @Override
  public String updateOrder(OrderDTO orderDTO, String userId) {
    return storeReservationOrderDAO.updateOrder(orderDTO, userId);
  }

  @Override
  public String deleteOrder(String storeName, String foodName, String userId) {
    return storeReservationOrderDAO.deleteOrder(storeName, foodName, userId);
  }

  @Override
  public String registerPay(PayDTO payDTO) {
    return storeReservationOrderDAO.registerPay(payDTO);
  }

  @Override
  public String deletePay(Long reservationId) {
    return storeReservationOrderDAO.deletePay(reservationId);
  }

}
