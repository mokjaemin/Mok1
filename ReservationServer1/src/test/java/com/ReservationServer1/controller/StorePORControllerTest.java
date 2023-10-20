package com.ReservationServer1.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyShort;
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
		ReservationDTO sample = ReservationDTO.getSample();
		String response = "success";
		doReturn(response).when(storePORService).registerReservation(any(ReservationDTO.class), anyString());

		// when
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/por/reservation").with(csrf())
				.contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(sample)));

		// then
		resultActions.andExpect(status().isOk()).andExpect(MockMvcResultMatchers.content().string(equalTo(response)));

		// verify
		verify(storePORService, times(1)).registerReservation(any(ReservationDTO.class), anyString());
		verify(storePORService).registerReservation(sample, String.valueOf(0));
	}

	// 2. Modify Store Reservation
	@Test
	@DisplayName("가게 예약 수정 성공")
	@WithMockUser(username = "0")
	void updateReservationSuccess() throws Exception {
		// given
		ReservationDTO sample = ReservationDTO.getSample();
		String response = "success";
		doReturn(response).when(storePORService).updateReservation(any(ReservationDTO.class), anyString());

		// when
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.put("/por/reservation").with(csrf())
				.contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(sample)));

		// then
		resultActions.andExpect(status().isOk()).andExpect(MockMvcResultMatchers.content().string(equalTo(response)));

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
		short storeId = 0;
		StoreReservationEntity response = new StoreReservationEntity(ReservationDTO.getSample());
		response.setUserId("userId");
		doReturn(response).when(storePORService).getReservation(anyShort(), anyString());

		// when
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/por/reservation").with(csrf())
				.contentType(MediaType.APPLICATION_JSON).param("storeId", String.valueOf(storeId)));

		// then
		resultActions.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.content()
						.json(StoreReservationEntity.createGsonInstance().toJson(response)));

		// verify
		verify(storePORService, times(1)).getReservation(anyShort(), anyString());
		verify(storePORService).getReservation(storeId, "0");
	}

	// 4. Delete Store Reservation
	@Test
	@DisplayName("가게 예약 삭제 성공")
	@WithMockUser(username = "0")
	void deleteReservationSuccess() throws Exception {
		// given
		short storeId = 0;
		String response = "success";
		doReturn(response).when(storePORService).deleteReservation(anyShort(), anyString());

		// when
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.delete("/por/reservation").with(csrf())
				.contentType(MediaType.APPLICATION_JSON).param("storeId", String.valueOf(storeId)));

		// then
		resultActions.andExpect(status().isOk()).andExpect(MockMvcResultMatchers.content().string(equalTo(response)));

		// verify
		verify(storePORService, times(1)).deleteReservation(anyShort(), anyString());
		verify(storePORService).deleteReservation(storeId, "0");
	}

	// 5. Register Store Order
	@Test
	@DisplayName("가게 주문 등록 성공")
	@WithMockUser(username = "0")
	void registerOrderSuccess() throws Exception {
		// given
		OrderDTO sample = OrderDTO.getSample();
		String response = "success";
		doReturn(response).when(storePORService).registerOrder(any(OrderDTO.class), anyString());

		// when
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/por/order").with(csrf())
				.contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(sample)));

		// then
		resultActions.andExpect(status().isOk()).andExpect(MockMvcResultMatchers.content().string(equalTo(response)));

		// verify
		verify(storePORService, times(1)).registerOrder(any(OrderDTO.class), anyString());
		verify(storePORService).registerOrder(sample, String.valueOf(0));
	}

	// 6. Modify Store Order
	@Test
	@DisplayName("가게 주문 수정 성공")
	@WithMockUser(username = "0")
	void updateOrderSuccess() throws Exception {
		// given
		OrderDTO sample = OrderDTO.getSample();
		String response = "success";
		doReturn(response).when(storePORService).updateOrder(any(OrderDTO.class), anyString());

		// when
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.put("/por/order").with(csrf())
				.contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(sample)));

		// then
		resultActions.andExpect(status().isOk()).andExpect(MockMvcResultMatchers.content().string(equalTo(response)));

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
		short storeId = 0;
		String foodName = "foodName1";
		String response = "success";
		doReturn(response).when(storePORService).deleteOrder(anyShort(), anyString(), anyString());

		// when
		ResultActions resultActions = mockMvc.perform(
				MockMvcRequestBuilders.delete("/por/order").with(csrf()).contentType(MediaType.APPLICATION_JSON)
						.param("storeId", String.valueOf(storeId)).param("foodName", foodName));

		// then
		resultActions.andExpect(status().isOk()).andExpect(MockMvcResultMatchers.content().string(equalTo(response)));

		// verify
		verify(storePORService, times(1)).deleteOrder(anyShort(), anyString(), anyString());
		verify(storePORService).deleteOrder(storeId, foodName, "0");
	}

	// 8. Register Store Pay
	@Test
	@DisplayName("가게 결제 등록 성공")
	@WithMockUser(username = "0")
	void registerPaySuccess() throws Exception {
		// given
		PayDTO sample = PayDTO.getSample();
		String response = "success";
		doReturn(response).when(storePORService).registerPay(any(PayDTO.class));

		// when
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/por/pay").with(csrf())
				.contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(sample)));

		// then
		resultActions.andExpect(status().isOk()).andExpect(MockMvcResultMatchers.content().string(equalTo(response)));

		// verify
		verify(storePORService, times(1)).registerPay(any(PayDTO.class));
		verify(storePORService).registerPay(sample);
	}

	@Test
	@DisplayName("가게 결제 등록 실패 : 권한 없음")
	@WithMockUser(username = "1")
	void registerPayFail() throws Exception {
		// given
		PayDTO sample = PayDTO.getSample();

		// when
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/por/pay").with(csrf())
				.contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(sample)));

		// then
		resultActions.andExpect(status().isBadRequest());

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
		int reservationId = 0;
		String response = "success";
		doReturn(response).when(storePORService).deletePay(anyInt());

		// when
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.delete("/por/pay").with(csrf())
				.contentType(MediaType.APPLICATION_JSON).param("storeId", String.valueOf(storeId))
				.param("reservationId", String.valueOf(reservationId)));

		// then
		resultActions.andExpect(status().isOk()).andExpect(MockMvcResultMatchers.content().string(equalTo(response)));

		// verify
		verify(storePORService, times(1)).deletePay(anyInt());
		verify(storePORService).deletePay(reservationId);
	}

	@Test
	@DisplayName("가게 결제 삭제 실패 : 권한 없음")
	@WithMockUser(username = "0")
	void deletePayFail() throws Exception {
		// given
		int storeId = 1;
		Long reservationId = 0L;

		// when
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.delete("/por/pay").with(csrf())
				.contentType(MediaType.APPLICATION_JSON).param("storeId", String.valueOf(storeId))
				.param("reservationId", String.valueOf(reservationId)));

		// then
		resultActions.andExpect(status().isBadRequest());

		// verify
		verify(storePORService, never()).deletePay(anyInt());

	}

	// 10. Register Store Pay Comment
	@Test
	@DisplayName("가게 결제 고객 댓글 등록 성공")
	@WithMockUser(username = "0")
	void registerPayCommentSuccess() throws Exception {
		// given
		int reservationId = 0;
		String comment = "{\"comment\": \"comment\"}";
		String response = "success";
		doReturn(response).when(storePORService).registerComment(anyInt(), anyString(), anyString());

		// when
		ResultActions resultActions = mockMvc.perform(
				MockMvcRequestBuilders.post("/por/pay/comment").with(csrf()).contentType(MediaType.APPLICATION_JSON)
						.content(comment).param("reservationId", String.valueOf(reservationId)));

		// then
		resultActions.andExpect(status().isOk()).andExpect(MockMvcResultMatchers.content().string(equalTo(response)));

		// verify
		verify(storePORService, times(1)).registerComment(anyInt(), anyString(), anyString());
		verify(storePORService).registerComment(reservationId, "comment", "0");
	}

	// 11. Delete Store Pay Comment
	@Test
	@DisplayName("가게 결제 고객 댓글 삭제 성공")
	@WithMockUser(username = "0")
	void deletePayCommentSuccess() throws Exception {
		// given
		int reservationId = 0;
		String response = "success";
		doReturn(response).when(storePORService).deleteComment(anyInt(), anyString());

		// when
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.delete("/por/pay/comment").with(csrf())
				.contentType(MediaType.APPLICATION_JSON).param("reservationId", String.valueOf(reservationId)));

		// then
		resultActions.andExpect(status().isOk()).andExpect(MockMvcResultMatchers.content().string(equalTo(response)));

		// verify
		verify(storePORService, times(1)).deleteComment(anyInt(), anyString());
		verify(storePORService).deleteComment(reservationId, "0");
	}

	// 12. Register Store Pay Big Comment
	@Test
	@DisplayName("가게 결제 사장 댓글 등록 성공")
	@WithMockUser(username = "0")
	void registerPayBigCommentSuccess() throws Exception {
		// given
		int reservationId = 0;
		String bigcomment = "{\"bigcomment\": \"bigcomment\"}";
		String response = "success";
		doReturn(response).when(storePORService).registerBigComment(anyInt(), anyString(), anyShort());

		// when
		ResultActions resultActions = mockMvc.perform(
				MockMvcRequestBuilders.post("/por/pay/bigcomment").with(csrf()).contentType(MediaType.APPLICATION_JSON)
						.content(bigcomment).param("reservationId", String.valueOf(reservationId)));

		// then
		resultActions.andExpect(status().isOk()).andExpect(MockMvcResultMatchers.content().string(equalTo(response)));

		// verify
		verify(storePORService, times(1)).registerBigComment(anyInt(), anyString(), anyShort());
		verify(storePORService).registerBigComment(reservationId, "bigcomment", (short) 0);
	}

	// 13. Delete Store Pay Big Comment
	@Test
	@DisplayName("가게 결제 사장 댓글 삭제 성공")
	@WithMockUser(username = "0")
	void deletePayBigCommentSuccess() throws Exception {
		// given
		int reservationId = 0;
		String response = "success";
		doReturn(response).when(storePORService).deleteBigComment(anyInt(), anyShort());

		// when
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.delete("/por/pay/bigcomment").with(csrf())
				.contentType(MediaType.APPLICATION_JSON).param("reservationId", String.valueOf(reservationId)));

		// then
		resultActions.andExpect(status().isOk()).andExpect(MockMvcResultMatchers.content().string(equalTo(response)));

		// verify
		verify(storePORService, times(1)).deleteBigComment(anyInt(), anyShort());
		verify(storePORService).deleteBigComment(reservationId, (short) 0);
	}

	// 14. Get Client Coupon
	@Test
	@DisplayName("가게 고객 쿠폰 수 출력 성공")
	@WithMockUser(username = "0")
	void getClientCouponSuccess() throws Exception {
		// given
		short storeId = 0;
		int response = 0;
		doReturn(response).when(storePORService).getCouponOfClient(anyShort(), anyString());

		// when
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/por/coupon/client").with(csrf())
				.contentType(MediaType.APPLICATION_JSON).param("storeId", String.valueOf(storeId)));

		// then
		resultActions.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().string(equalTo(String.valueOf(response))));

		// verify
		verify(storePORService, times(1)).getCouponOfClient(anyShort(), anyString());
		verify(storePORService).getCouponOfClient(storeId, "0");
	}

	// 14. Get Client Coupon
	@Test
	@DisplayName("가게 가게별 쿠폰 수 출력 성공")
	@WithMockUser(username = "0")
	void getOwenerCouponSuccess() throws Exception {
		// given
		HashMap<String, Integer> response = new HashMap<>();
		response.put("id", 0);
		doReturn(response).when(storePORService).getCouponOfStore(anyShort());

		// when
		ResultActions resultActions = mockMvc.perform(
				MockMvcRequestBuilders.get("/por/coupon/store").with(csrf()).contentType(MediaType.APPLICATION_JSON));

		// then
		resultActions.andExpect(status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.id").value(0));

		// verify
		verify(storePORService, times(1)).getCouponOfStore(anyShort());
		verify(storePORService).getCouponOfStore((short) 0);
	}

}
