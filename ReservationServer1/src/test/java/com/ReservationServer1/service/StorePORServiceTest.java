package com.ReservationServer1.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import java.util.HashMap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.ReservationServer1.DAO.StorePORDAO;
import com.ReservationServer1.data.DTO.POR.OrderDTO;
import com.ReservationServer1.data.DTO.POR.PayDTO;
import com.ReservationServer1.data.DTO.POR.ReservationDTO;
import com.ReservationServer1.data.Entity.POR.StoreReservationEntity;
import com.ReservationServer1.service.Impl.StorePORServiceImpl;

@ExtendWith(MockitoExtension.class)
public class StorePORServiceTest {


  @InjectMocks
  private StorePORServiceImpl storePORServiceImpl;

  @Mock
  private StorePORDAO storePORDAO;

  // 1. Register Reservation
  @Test
  @DisplayName("가게 예약 등록 성공")
  void registerRservationSuccess() throws Exception {
    // given
    ReservationDTO sample = ReservationDTO.sample();
    String userId = "userId";
    String response = "success";
    doReturn(response).when(storePORDAO).registerReservation(any(ReservationDTO.class),
        anyString());

    // when
    String result = storePORServiceImpl.registerReservation(sample, userId);

    // then
    assertEquals(result, response);

    // verify
    verify(storePORDAO, times(1)).registerReservation(any(ReservationDTO.class), anyString());
    verify(storePORDAO).registerReservation(sample, userId);
  }

  // 2. Update Reservation
  @Test
  @DisplayName("가게 예약 수정 성공")
  void updateRservationSuccess() throws Exception {
    // given
    ReservationDTO sample = ReservationDTO.sample();
    String userId = "userId";
    String response = "success";
    doReturn(response).when(storePORDAO).updateReservation(any(ReservationDTO.class), anyString());

    // when
    String result = storePORServiceImpl.updateReservation(sample, userId);

    // then
    assertEquals(result, response);

    // verify
    verify(storePORDAO, times(1)).updateReservation(any(ReservationDTO.class), anyString());
    verify(storePORDAO).updateReservation(sample, userId);
  }


  // 3. Get Reservation
  @Test
  @DisplayName("가게 예약 출력 성공")
  void getRservationSuccess() throws Exception {
    // given
    int storeId = 0;
    String userId = "userId";
    StoreReservationEntity response = new StoreReservationEntity();
    doReturn(response).when(storePORDAO).getReservation(anyInt(), anyString());

    // when
    StoreReservationEntity result = storePORServiceImpl.getReservation(storeId, userId);

    // then
    assertEquals(result, response);

    // verify
    verify(storePORDAO, times(1)).getReservation(anyInt(), anyString());
    verify(storePORDAO).getReservation(storeId, userId);
  }


  // 4. Delete Reservation
  @Test
  @DisplayName("가게 예약 출력 성공")
  void deleteRservationSuccess() throws Exception {
    // given
    int storeId = 0;
    String userId = "userId";
    String response = "success";
    doReturn(response).when(storePORDAO).deleteReservation(anyInt(), anyString());

    // when
    String result = storePORServiceImpl.deleteReservation(storeId, userId);

    // then
    assertEquals(result, response);

    // verify
    verify(storePORDAO, times(1)).deleteReservation(anyInt(), anyString());
    verify(storePORDAO).deleteReservation(storeId, userId);
  }


  // 5. Register Order
  @Test
  @DisplayName("가게 주문 등록 성공")
  void registerOrderSuccess() throws Exception {
    // given
    OrderDTO sample = OrderDTO.sample();
    String userId = "userId";
    String response = "success";
    doReturn(response).when(storePORDAO).registerOrder(any(OrderDTO.class), anyString());

    // when
    String result = storePORServiceImpl.registerOrder(sample, userId);

    // then
    assertEquals(result, response);

    // verify
    verify(storePORDAO, times(1)).registerOrder(any(OrderDTO.class), anyString());
    verify(storePORDAO).registerOrder(sample, userId);
  }

  // 6. Update Order
  @Test
  @DisplayName("가게 주문 수정 성공")
  void updateOrderSuccess() throws Exception {
    // given
    OrderDTO sample = OrderDTO.sample();
    String userId = "userId";
    String response = "success";
    doReturn(response).when(storePORDAO).updateOrder(any(OrderDTO.class), anyString());

    // when
    String result = storePORServiceImpl.updateOrder(sample, userId);

    // then
    assertEquals(result, response);

    // verify
    verify(storePORDAO, times(1)).updateOrder(any(OrderDTO.class), anyString());
    verify(storePORDAO).updateOrder(sample, userId);
  }



  // 7. Delete Order
  @Test
  @DisplayName("가게 주문 삭제 성공")
  void deleteOrderSuccess() throws Exception {
    // given
    int storeId = 0;
    String foodName = "foodName";
    String userId = "userId";
    String response = "success";
    doReturn(response).when(storePORDAO).deleteOrder(anyInt(), anyString(), anyString());

    // when
    String result = storePORServiceImpl.deleteOrder(storeId, foodName, userId);

    // then
    assertEquals(result, response);

    // verify
    verify(storePORDAO, times(1)).deleteOrder(anyInt(), anyString(), anyString());
    verify(storePORDAO).deleteOrder(storeId, foodName, userId);
  }


  // 8. Register Pay
  @Test
  @DisplayName("가게 결제 등록 성공")
  void registerPaySuccess() throws Exception {
    // given
    PayDTO sample = PayDTO.sample();
    String response = "success";
    doReturn(response).when(storePORDAO).registerPay(any(PayDTO.class));

    // when
    String result = storePORServiceImpl.registerPay(sample);

    // then
    assertEquals(result, response);

    // verify
    verify(storePORDAO, times(1)).registerPay(any(PayDTO.class));
    verify(storePORDAO).registerPay(sample);
  }

  // 9. Delete Pay
  @Test
  @DisplayName("가게 결제 삭제 성공")
  void deletePaySuccess() throws Exception {
    // given
    long reservationId = 0L;
    String response = "success";
    doReturn(response).when(storePORDAO).deletePay(anyLong());

    // when
    String result = storePORServiceImpl.deletePay(reservationId);

    // then
    assertEquals(result, response);

    // verify
    verify(storePORDAO, times(1)).deletePay(anyLong());
    verify(storePORDAO).deletePay(reservationId);
  }


  // 10. Register Comment
  @Test
  @DisplayName("가게 개인 댓글 등록 성공")
  void registerCommentSuccess() throws Exception {
    // given
    long reservationId = 0L;
    String comment = "comment";
    String userId = "userId";
    String response = "success";
    doReturn(response).when(storePORDAO).registerComment(anyLong(), anyString(), anyString());

    // when
    String result = storePORServiceImpl.registerComment(reservationId, comment, userId);

    // then
    assertEquals(result, response);

    // verify
    verify(storePORDAO, times(1)).registerComment(anyLong(), anyString(), anyString());
    verify(storePORDAO).registerComment(reservationId, comment, userId);
  }


  // 11. Delete Comment
  @Test
  @DisplayName("가게 개인 댓글 삭제 성공")
  void deleteCommentSuccess() throws Exception {
    // given
    long reservationId = 0L;
    String userId = "userId";
    String response = "success";
    doReturn(response).when(storePORDAO).deleteComment(anyLong(), anyString());

    // when
    String result = storePORServiceImpl.deleteComment(reservationId, userId);

    // then
    assertEquals(result, response);

    // verify
    verify(storePORDAO, times(1)).deleteComment(anyLong(), anyString());
    verify(storePORDAO).deleteComment(reservationId, userId);
  }


  // 12. Register Big Comment
  @Test
  @DisplayName("가게 개인 대댓글 등록 성공")
  void registerBigCommentSuccess() throws Exception {
    // given
    long reservationId = 0L;
    String bigcomment = "bigcomment";
    int storeId = 0;
    String response = "success";
    doReturn(response).when(storePORDAO).registerBigComment(anyLong(), anyString(), anyInt());

    // when
    String result = storePORServiceImpl.registerBigComment(reservationId, bigcomment, storeId);

    // then
    assertEquals(result, response);

    // verify
    verify(storePORDAO, times(1)).registerBigComment(anyLong(), anyString(), anyInt());
    verify(storePORDAO).registerBigComment(reservationId, bigcomment, storeId);
  }


  // 13. Delete Big Comment
  @Test
  @DisplayName("가게 개인 대댓글 삭제 성공")
  void deleteBigCommentSuccess() throws Exception {
    // given
    long reservationId = 0L;
    int storeId = 0;
    String response = "success";
    doReturn(response).when(storePORDAO).deleteBigComment(anyLong(), anyInt());

    // when
    String result = storePORServiceImpl.deleteBigComment(reservationId, storeId);

    // then
    assertEquals(result, response);

    // verify
    verify(storePORDAO, times(1)).deleteBigComment(anyLong(), anyInt());
    verify(storePORDAO).deleteBigComment(reservationId, storeId);
  }

  // 14. Get Coupon Client
  @Test
  @DisplayName("가게 개인 쿠폰 출력 성공")
  void getCouponClientSuccess() throws Exception {
    // given
    int storeId = 0;
    String userId = "userId";
    int response = 0;
    doReturn(response).when(storePORDAO).getCouponClient(anyInt(), anyString());

    // when
    int result = storePORServiceImpl.getCouponClient(storeId, userId);

    // then
    assertThat(result, is(response));

    // verify
    verify(storePORDAO, times(1)).getCouponClient(anyInt(), anyString());
    verify(storePORDAO).getCouponClient(storeId, userId);
  }


  // 15. Get Coupon Store
  @Test
  @DisplayName("가게별 쿠폰 출력 성공")
  void getCouponStoreSuccess() throws Exception {
    // given
    int storeId = 0;
    HashMap<String, Integer> response = new HashMap<>();
    doReturn(response).when(storePORDAO).getCouponOwner(anyInt());

    // when
    HashMap<String, Integer> result = storePORServiceImpl.getCouponOwner(storeId);

    // then
    assertEquals(result, response);

    // verify
    verify(storePORDAO, times(1)).getCouponOwner(anyInt());
    verify(storePORDAO).getCouponOwner(storeId);
  }
}
