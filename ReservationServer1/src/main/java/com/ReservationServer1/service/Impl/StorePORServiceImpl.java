package com.ReservationServer1.service.Impl;

import java.util.HashMap;
import org.springframework.stereotype.Service;
import com.ReservationServer1.DAO.StorePORDAO;
import com.ReservationServer1.data.DTO.POR.OrderDTO;
import com.ReservationServer1.data.DTO.POR.PayDTO;
import com.ReservationServer1.data.DTO.POR.ReservationDTO;
import com.ReservationServer1.data.Entity.POR.StoreReservationEntity;
import com.ReservationServer1.service.StorePORService;

@Service
public class StorePORServiceImpl implements StorePORService {
  
  private final StorePORDAO storePORDAO;

  public StorePORServiceImpl(StorePORDAO storePORDAO) {
    this.storePORDAO = storePORDAO;
  }

  @Override
  public String registerReservation(ReservationDTO reservationDTO, String userId) {
    return storePORDAO.registerReservation(reservationDTO, userId);
  }

  @Override
  public String updateReservation(ReservationDTO reservationDTO, String userId) {
    return storePORDAO.updateReservation(reservationDTO, userId);
  }

  @Override
  public StoreReservationEntity getReservation(int storeId, String userId) {
    return storePORDAO.getReservation(storeId, userId);
  }

  @Override
  public String deleteReservation(int storeId, String userId) {
    return storePORDAO.deleteReservation(storeId, userId);
  }

  @Override
  public String registerOrder(OrderDTO orderDTO, String userId) {
    return storePORDAO.registerOrder(orderDTO, userId);
  }

  @Override
  public String updateOrder(OrderDTO orderDTO, String userId) {
    return storePORDAO.updateOrder(orderDTO, userId);
  }

  @Override
  public String deleteOrder(int storeId, String foodName, String userId) {
    return storePORDAO.deleteOrder(storeId, foodName, userId);
  }

  @Override
  public String registerPay(PayDTO payDTO) {
    return storePORDAO.registerPay(payDTO);
  }

  @Override
  public String deletePay(Long reservationId) {
    return storePORDAO.deletePay(reservationId);
  }

  @Override
  public String registerComment(long reservationId, String comment, String userId) {
    return storePORDAO.registerComment(reservationId, comment, userId);
  }

  @Override
  public String deleteComment(Long reservationId, String userId) {
    return storePORDAO.deleteComment(reservationId, userId);
  }

  @Override
  public String registerBigComment(long reservationId, String bigcomment, int storeId) {
    return storePORDAO.registerBigComment(reservationId, bigcomment, storeId);
  }

  @Override
  public String deleteBigComment(Long reservationId, int storeId) {
    return storePORDAO.deleteBigComment(reservationId, storeId);
  }

  @Override
  public int getCouponClient(int storeId, String userId) {
    return storePORDAO.getCouponClient(storeId, userId);
  }

  @Override
  public HashMap<String, Integer> getCouponOwner(int storeId) {
    return storePORDAO.getCouponOwner(storeId);
  }
}
