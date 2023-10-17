package com.ReservationServer1.DAO;

import java.util.HashMap;
import com.ReservationServer1.data.DTO.POR.OrderDTO;
import com.ReservationServer1.data.DTO.POR.PayDTO;
import com.ReservationServer1.data.DTO.POR.ReservationDTO;
import com.ReservationServer1.data.Entity.POR.StoreReservationEntity;

public interface StorePORDAO {
  String registerReservation(ReservationDTO reservationDTO, String userId);

  String updateReservation(ReservationDTO reservationDTO, String userId);

  StoreReservationEntity getReservation(short storeId, String userId);

  String deleteReservation(short storeId, String userId);

  String registerOrder(OrderDTO orderDTO, String userId);

  String updateOrder(OrderDTO orderDTO, String userId);

  String deleteOrder(short storeId, String foodName, String userId);

  String registerPay(PayDTO payDTO);

  String deletePay(int reservationId);

  String registerComment(int reservationId, String comment, String userId);

  String deleteComment(int reservationId, String userId);

  String registerBigComment(int reservationId, String bigcomment, short storeId);

  String deleteBigComment(int reservationId, short storeId);

  int getCouponClient(short storeId, String userId);

  HashMap<String, Integer> getCouponOwner(short storeId);
}
