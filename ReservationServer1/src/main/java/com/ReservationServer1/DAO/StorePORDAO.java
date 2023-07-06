package com.ReservationServer1.DAO;

import java.util.HashMap;
import com.ReservationServer1.data.DTO.POR.OrderDTO;
import com.ReservationServer1.data.DTO.POR.PayDTO;
import com.ReservationServer1.data.DTO.POR.ReservationDTO;
import com.ReservationServer1.data.Entity.POR.StoreReservationEntity;

public interface StorePORDAO {
  String registerReservation(ReservationDTO reservationDTO, String userId);

  String updateReservation(ReservationDTO reservationDTO, String userId);

  StoreReservationEntity getReservation(int storeId, String userId);

  String deleteReservation(int storeId, String userId);

  String registerOrder(OrderDTO orderDTO, String userId);

  String updateOrder(OrderDTO orderDTO, String userId);

  String deleteOrder(int storeId, String foodName, String userId);

  String registerPay(PayDTO payDTO);

  String deletePay(Long reservationId);

  String registerComment(long reservationId, String comment, String userId);

  String deleteComment(Long reservationId, String userId);

  String registerBigComment(long reservationId, String bigcomment, int storeId);

  String deleteBigComment(Long reservationId, int storeId);

  int getCouponClient(int storeId, String userId);

  HashMap<String, Integer> getCouponOwner(int storeId);
}
