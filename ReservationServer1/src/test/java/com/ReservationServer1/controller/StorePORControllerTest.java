package com.ReservationServer1.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.HashMap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.ReservationServer1.data.DTO.POR.OrderDTO;
import com.ReservationServer1.data.DTO.POR.PayDTO;
import com.ReservationServer1.data.DTO.POR.ReservationDTO;
import com.ReservationServer1.data.Entity.POR.StoreReservationEntity;
import com.ReservationServer1.service.StorePORService;
import com.google.gson.Gson;

@WebMvcTest(StorePORController.class)
public class StorePORControllerTest {


  @MockBean
  private StorePORService storePORService;

  @Autowired
  private MockMvc mockMvc;

  // 1. Register Store Reservation
  @Test
  @DisplayName("가게 예약 등록 성공")
  @WithMockUser(username = "0")
  void registerReservationSuccess() throws Exception {
    // given
    ReservationDTO sample = ReservationDTO.sample();
    String response = "success";
    doReturn(response).when(storePORService).registerReservation(any(ReservationDTO.class),
        anyString());

    // when
    ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/por/reservation")
        .with(csrf()).contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(sample)));

    // then
    resultActions.andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().string(equalTo(response)));

    // verify
    verify(storePORService, times(1)).registerReservation(any(ReservationDTO.class), anyString());
    verify(storePORService).registerReservation(sample, String.valueOf(0));
  }


  // 2. Modify Store Reservation
  @Test
  @DisplayName("가게 예약 수정 성공")
  @WithMockUser(username = "0")
  void modReservationSuccess() throws Exception {
    // given
    ReservationDTO sample = ReservationDTO.sample();
    String response = "success";
    doReturn(response).when(storePORService).updateReservation(any(ReservationDTO.class),
        anyString());

    // when
    ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.put("/por/reservation")
        .with(csrf()).contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(sample)));

    // then
    resultActions.andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().string(equalTo(response)));

    // verify
    verify(storePORService, times(1)).updateReservation(any(ReservationDTO.class), anyString());
    verify(storePORService).updateReservation(sample, String.valueOf(0));
  }


  // 3. Get Store Reservation
  @Test
  @DisplayName("가게 예약 출력 성공")
  @WithMockUser(username = "0")
  void getReservationSuccess() throws Exception {
    // given
    int storeId = 0;
    StoreReservationEntity response = new StoreReservationEntity(ReservationDTO.sample());
    response.setUserId("userId");
    doReturn(response).when(storePORService).getReservation(anyInt(), anyString());

    // when
    ResultActions resultActions =
        mockMvc.perform(MockMvcRequestBuilders.get("/por/reservation").with(csrf())
            .contentType(MediaType.APPLICATION_JSON).param("storeId", String.valueOf(storeId)));

    // then
    resultActions.andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.content()
            .json(StoreReservationEntity.createGsonInstance().toJson(response)));

    // verify
    verify(storePORService, times(1)).getReservation(anyInt(), anyString());
    verify(storePORService).getReservation(storeId, "0");
  }


  // 4. Delete Store Reservation
  @Test
  @DisplayName("가게 예약 삭제 성공")
  @WithMockUser(username = "0")
  void deleteReservationSuccess() throws Exception {
    // given
    int storeId = 0;
    String response = "success";
    doReturn(response).when(storePORService).deleteReservation(anyInt(), anyString());

    // when
    ResultActions resultActions =
        mockMvc.perform(MockMvcRequestBuilders.delete("/por/reservation").with(csrf())
            .contentType(MediaType.APPLICATION_JSON).param("storeId", String.valueOf(storeId)));

    // then
    resultActions.andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().string(equalTo(response)));

    // verify
    verify(storePORService, times(1)).deleteReservation(anyInt(), anyString());
    verify(storePORService).deleteReservation(storeId, "0");
  }


  // 5. Register Store Order
  @Test
  @DisplayName("가게 주문 등록 성공")
  @WithMockUser(username = "0")
  void registerOrderSuccess() throws Exception {
    // given
    OrderDTO sample = OrderDTO.sample();
    String response = "success";
    doReturn(response).when(storePORService).registerOrder(any(OrderDTO.class), anyString());

    // when
    ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/por/order")
        .with(csrf()).contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(sample)));

    // then
    resultActions.andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().string(equalTo(response)));

    // verify
    verify(storePORService, times(1)).registerOrder(any(OrderDTO.class), anyString());
    verify(storePORService).registerOrder(sample, String.valueOf(0));
  }

  // 6. Modify Store Order
  @Test
  @DisplayName("가게 주문 수정 성공")
  @WithMockUser(username = "0")
  void modOrderSuccess() throws Exception {
    // given
    OrderDTO sample = OrderDTO.sample();
    String response = "success";
    doReturn(response).when(storePORService).updateOrder(any(OrderDTO.class), anyString());

    // when
    ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.put("/por/order")
        .with(csrf()).contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(sample)));

    // then
    resultActions.andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().string(equalTo(response)));

    // verify
    verify(storePORService, times(1)).updateOrder(any(OrderDTO.class), anyString());
    verify(storePORService).updateOrder(sample, String.valueOf(0));
  }


  // 7. Delete Store Order
  @Test
  @DisplayName("가게 주문 삭제 성공")
  @WithMockUser(username = "0")
  void deleteOrderSuccess() throws Exception {
    // given
    int storeId = 0;
    String foodName = "foodName1";
    String response = "success";
    doReturn(response).when(storePORService).deleteOrder(anyInt(), anyString(), anyString());

    // when
    ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.delete("/por/order")
        .with(csrf()).contentType(MediaType.APPLICATION_JSON)
        .param("storeId", String.valueOf(storeId)).param("foodName", foodName));

    // then
    resultActions.andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().string(equalTo(response)));

    // verify
    verify(storePORService, times(1)).deleteOrder(anyInt(), anyString(), anyString());
    verify(storePORService).deleteOrder(storeId, foodName, "0");
  }

  // 8. Register Store Pay
  @Test
  @DisplayName("가게 결제 등록 성공")
  @WithMockUser(username = "0")
  void registerPaySuccess() throws Exception {
    // given
    PayDTO sample = PayDTO.sample();
    String response = "success";
    doReturn(response).when(storePORService).registerPay(any(PayDTO.class));

    // when
    ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/por/pay")
        .with(csrf()).contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(sample)));

    // then
    resultActions.andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().string(equalTo(response)));

    // verify
    verify(storePORService, times(1)).registerPay(any(PayDTO.class));
    verify(storePORService).registerPay(sample);
  }


  @Test
  @DisplayName("가게 결제 등록 실패 : 권한 없음")
  @WithMockUser(username = "1")
  void registerPayFail() throws Exception {
    // given
    PayDTO sample = PayDTO.sample();
    String response = "권한이 없습니다.";

    // when
    ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/por/pay")
        .with(csrf()).contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(sample)));

    // then
    resultActions.andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().string(equalTo(response)));

    // verify
    verify(storePORService, never()).registerPay(any(PayDTO.class));
  }

  // 9. Delete Store Pay
  @Test
  @DisplayName("가게 결제 삭제 성공")
  @WithMockUser(username = "0")
  void deletePaySuccess() throws Exception {
    // given
    int storeId = 0;
    Long reservationId = 0L;
    String response = "success";
    doReturn(response).when(storePORService).deletePay(anyLong());

    // when
    ResultActions resultActions =
        mockMvc.perform(MockMvcRequestBuilders.delete("/por/pay").with(csrf())
            .contentType(MediaType.APPLICATION_JSON).param("storeId", String.valueOf(storeId))
            .param("reservationId", String.valueOf(reservationId)));

    // then
    resultActions.andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().string(equalTo(response)));

    // verify
    verify(storePORService, times(1)).deletePay(anyLong());
    verify(storePORService).deletePay(reservationId);
  }


  // 10. Register Store Pay Comment
  @Test
  @DisplayName("가게 결제 고객 댓글 등록 성공")
  @WithMockUser(username = "0")
  void registerPayCommentSuccess() throws Exception {
    // given
    long reservationId = 0L;
    String comment = "{\"comment\": \"comment\"}";
    String response = "success";
    doReturn(response).when(storePORService).registerComment(anyLong(), anyString(), anyString());


    // when
    ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/por/pay/comment")
        .with(csrf()).contentType(MediaType.APPLICATION_JSON).content(comment)
        .param("reservationId", String.valueOf(reservationId)));

    // then
    resultActions.andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().string(equalTo(response)));

    // verify
    verify(storePORService, times(1)).registerComment(anyLong(), anyString(), anyString());
    verify(storePORService).registerComment(reservationId, "comment", "0");
  }


  // 11. Delete Store Pay Comment
  @Test
  @DisplayName("가게 결제 고객 댓글 삭제 성공")
  @WithMockUser(username = "0")
  void deletePayCommentSuccess() throws Exception {
    // given
    long reservationId = 0L;
    String response = "success";
    doReturn(response).when(storePORService).deleteComment(anyLong(), anyString());


    // when
    ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.delete("/por/pay/comment")
        .with(csrf()).contentType(MediaType.APPLICATION_JSON)
        .param("reservationId", String.valueOf(reservationId)));

    // then
    resultActions.andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().string(equalTo(response)));

    // verify
    verify(storePORService, times(1)).deleteComment(anyLong(), anyString());
    verify(storePORService).deleteComment(reservationId, "0");
  }

  // 12. Register Store Pay Big Comment
  @Test
  @DisplayName("가게 결제 사장 댓글 등록 성공")
  @WithMockUser(username = "0")
  void registerPayBigCommentSuccess() throws Exception {
    // given
    long reservationId = 0L;
    String bigcomment = "{\"bigcomment\": \"bigcomment\"}";
    String response = "success";
    doReturn(response).when(storePORService).registerBigComment(anyLong(), anyString(), anyInt());


    // when
    ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/por/pay/bigcomment")
        .with(csrf()).contentType(MediaType.APPLICATION_JSON).content(bigcomment)
        .param("reservationId", String.valueOf(reservationId)));

    // then
    resultActions.andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().string(equalTo(response)));

    // verify
    verify(storePORService, times(1)).registerBigComment(anyLong(), anyString(), anyInt());
    verify(storePORService).registerBigComment(reservationId, "bigcomment", 0);
  }


  // 13. Delete Store Pay Big Comment
  @Test
  @DisplayName("가게 결제 사장 댓글 삭제 성공")
  @WithMockUser(username = "0")
  void deletePayBigCommentSuccess() throws Exception {
    // given
    long reservationId = 0L;
    String response = "success";
    doReturn(response).when(storePORService).deleteBigComment(anyLong(), anyInt());


    // when
    ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
        .delete("/por/pay/bigcomment").with(csrf()).contentType(MediaType.APPLICATION_JSON)
        .param("reservationId", String.valueOf(reservationId)));

    // then
    resultActions.andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().string(equalTo(response)));

    // verify
    verify(storePORService, times(1)).deleteBigComment(anyLong(), anyInt());
    verify(storePORService).deleteBigComment(reservationId, 0);
  }

  // 14. Get Client Coupon
  @Test
  @DisplayName("가게 고객 쿠폰 수 출력 성공")
  @WithMockUser(username = "0")
  void getClientCouponSuccess() throws Exception {
    // given
    int storeId = 0;
    int response = 0;
    doReturn(response).when(storePORService).getCouponClient(anyInt(), anyString());


    // when
    ResultActions resultActions =
        mockMvc.perform(MockMvcRequestBuilders.get("/por/coupon/client").with(csrf())
            .contentType(MediaType.APPLICATION_JSON).param("storeId", String.valueOf(storeId)));

    // then
    resultActions.andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().string(equalTo(String.valueOf(response))));

    // verify
    verify(storePORService, times(1)).getCouponClient(anyInt(), anyString());
    verify(storePORService).getCouponClient(storeId, "0");
  }


  // 14. Get Client Coupon
  @Test
  @DisplayName("가게 가게별 쿠폰 수 출력 성공")
  @WithMockUser(username = "0")
  void getOwenerCouponSuccess() throws Exception {
    // given
    HashMap<String, Integer> response = new HashMap<>();
    response.put("id", 0);
    doReturn(response).when(storePORService).getCouponOwner(anyInt());


    // when
    ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/por/coupon/owner")
        .with(csrf()).contentType(MediaType.APPLICATION_JSON));

    // then
    resultActions.andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(0));


    // verify
    verify(storePORService, times(1)).getCouponOwner(anyInt());
    verify(storePORService).getCouponOwner(0);
  }

}
