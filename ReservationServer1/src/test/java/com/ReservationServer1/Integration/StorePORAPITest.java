package com.ReservationServer1.Integration;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.ReservationServer1.data.DTO.POR.OrderDTO;
import com.ReservationServer1.data.DTO.POR.PayDTO;
import com.ReservationServer1.data.DTO.POR.ReservationDTO;
import com.ReservationServer1.data.Entity.POR.StoreOrdersEntity;
import com.ReservationServer1.data.Entity.POR.StoreOrdersMapEntity;
import com.ReservationServer1.data.Entity.POR.StoreReservationEntity;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Transactional
public class StorePORAPITest {

	@Autowired
	protected MockMvc mockMvc;

	@Autowired
	protected EntityManager em;

	@Test
	@DisplayName("StorePORAPI : Register Reservation Success")
	@WithMockUser(username = "userId", roles = "USER")
	public void registerReservationSuccess() throws Exception {
		// given
		ReservationDTO sample = ReservationDTO.getSample();
		String response = "success";

		// when
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/por/reservation").with(csrf())
				.contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(sample)));

		// then
		resultActions.andExpect(status().isOk()).andExpect(MockMvcResultMatchers.content().string(equalTo(response)));
	}

	@Test
	@DisplayName("StorePORAPI : Update Reservation Success")
	@WithMockUser(username = "userId", roles = "USER")
	public void modReservationSuccess() throws Exception {
		// given
		makeReservation();
		ReservationDTO sample = ReservationDTO.getSample();
		String response = "success";

		// when
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.put("/por/reservation").with(csrf())
				.contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(sample)));

		// then
		resultActions.andExpect(status().isOk()).andExpect(MockMvcResultMatchers.content().string(equalTo(response)));
	}

	@Test
	@DisplayName("StorePORAPI : Get Reservation Success")
	@WithMockUser(username = "userId", roles = "USER")
	public void getReservationSuccess() throws Exception {
		// given
		StoreReservationEntity response = makeReservation();
		int storeId = response.getStoreId();

		// when
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/por/reservation").with(csrf())
				.contentType(MediaType.APPLICATION_JSON).param("storeId", String.valueOf(storeId)));

		// then
		resultActions.andExpect(status().isOk()).andExpect(
				MockMvcResultMatchers.content().json(StoreReservationEntity.createGsonInstance().toJson(response)));
	}

	@Test
	@DisplayName("StorePORAPI : Get Reservation Fail : 정보 없음")
	@WithMockUser(username = "userId", roles = "USER")
	public void getReservationFail() throws Exception {
		// given
		int storeId = 1;
		String response = "정보가 존재하지 않습니다.";

		// when
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/por/reservation").with(csrf())
				.contentType(MediaType.APPLICATION_JSON).param("storeId", String.valueOf(storeId)));

		// then
		resultActions.andExpect(status().isBadRequest()).andExpect(result -> {
			String responseBody = result.getResponse().getContentAsString();
			String utf8ResponseBody = new String(responseBody.getBytes(StandardCharsets.ISO_8859_1),
					StandardCharsets.UTF_8);
			JsonNode jsonNode = new ObjectMapper().readTree(utf8ResponseBody);
			String errorMessage = jsonNode.get("message").asText();
			assertEquals(errorMessage, response);
		});
	}

	@Test
	@DisplayName("StorePORAPI : Delete Reservation Success")
	@WithMockUser(username = "userId", roles = "USER")
	public void deleteReservationSuccess() throws Exception {
		// given
		// 저장
		StoreReservationEntity entity = makeReservation();
		int storeId = entity.getStoreId();
		String response = "success";

		// when
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.delete("/por/reservation").with(csrf())
				.contentType(MediaType.APPLICATION_JSON).param("storeId", String.valueOf(storeId)));

		// then
		resultActions.andExpect(status().isOk()).andExpect(MockMvcResultMatchers.content().string(equalTo(response)));
	}

	@Test
	@DisplayName("StorePORAPI : Register Order Success")
	@WithMockUser(username = "userId", roles = "USER")
	public void registerOrderSuccess() throws Exception {
		// given
		// 저장
		makeReservation();
		OrderDTO sample = OrderDTO.getSample();
		String response = "success";

		// when
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/por/order").with(csrf())
				.contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(sample)));

		// then
		resultActions.andExpect(status().isOk()).andExpect(MockMvcResultMatchers.content().string(equalTo(response)));
	}

	@Test
	@DisplayName("StorePORAPI : Register Order Fail : 에약 정보 없음")
	@WithMockUser(username = "userId", roles = "USER")
	public void registerOrderFail() throws Exception {
		// given
		// 저장
		OrderDTO sample = OrderDTO.getSample();
		String response = "정보가 존재하지 않습니다.";

		// when
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/por/order").with(csrf())
				.contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(sample)));

		// then
		resultActions.andExpect(status().isBadRequest()).andExpect(result -> {
			String responseBody = result.getResponse().getContentAsString();
			String utf8ResponseBody = new String(responseBody.getBytes(StandardCharsets.ISO_8859_1),
					StandardCharsets.UTF_8);
			JsonNode jsonNode = new ObjectMapper().readTree(utf8ResponseBody);
			String errorMessage = jsonNode.get("message").asText();
			assertEquals(errorMessage, response);
		});
	}

	@Test
	@DisplayName("StorePORAPI : Update Order Success")
	@WithMockUser(username = "userId", roles = "USER")
	public void modOrderSuccess() throws Exception {
		// given
		// 저장
		registerOrderSuccess();
		OrderDTO sample = OrderDTO.getSample();
		String response = "success";

		// when
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.put("/por/order").with(csrf())
				.contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(sample)));

		// then
		resultActions.andExpect(status().isOk()).andExpect(MockMvcResultMatchers.content().string(equalTo(response)));
	}

	@Test
	@DisplayName("StorePORAPI : Update Order Fail : 예약 내역 없음")
	@WithMockUser(username = "userId", roles = "USER")
	public void modOrderFail() throws Exception {
		// given
		OrderDTO sample = OrderDTO.getSample();
		String response = "정보가 존재하지 않습니다.";

		// when
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.put("/por/order").with(csrf())
				.contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(sample)));

		// then
		resultActions.andExpect(status().isBadRequest()).andExpect(result -> {
			String responseBody = result.getResponse().getContentAsString();
			String utf8ResponseBody = new String(responseBody.getBytes(StandardCharsets.ISO_8859_1),
					StandardCharsets.UTF_8);
			JsonNode jsonNode = new ObjectMapper().readTree(utf8ResponseBody);
			String errorMessage = jsonNode.get("message").asText();
			assertEquals(errorMessage, response);
		});
	}

	@Test
	@DisplayName("StorePORAPI : Delete Order Success")
	@WithMockUser(username = "userId", roles = "USER")
	public void deleteOrderSuccess() throws Exception {
		// given
		// 저장
		registerOrderSuccess();
		int storeId = 0;
		String foodName = "foodName1";
		String response = "success";

		// when
		ResultActions resultActions = mockMvc.perform(
				MockMvcRequestBuilders.delete("/por/order").with(csrf()).contentType(MediaType.APPLICATION_JSON)
						.param("storeId", String.valueOf(storeId)).param("foodName", foodName));

		// then
		resultActions.andExpect(status().isOk()).andExpect(MockMvcResultMatchers.content().string(equalTo(response)));
	}

	@Test
	@DisplayName("StorePORAPI : Register Pay Fail : 권한 없음")
	@WithMockUser(username = "1", roles = "OWNER")
	public void regitsterPayFail() throws Exception {
		// given
		// 저장
		StoreReservationEntity entity = makeOrder();
		PayDTO sample = PayDTO.getSample();
		sample.setReservationId(entity.getReservationId());
		String response = "권한이 없습니다.";

		// when
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/por/pay").with(csrf())
				.contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(sample)));

		// then
		resultActions.andExpect(status().isOk()).andExpect(MockMvcResultMatchers.content().string(equalTo(response)));
	}

	@Test
	@DisplayName("StorePORAPI : Delete Pay Success")
	@WithMockUser(username = "0", roles = "OWNER")
	public void deletePaySuccess() throws Exception {
		// given
		// 저장
		Long reservationId = 0L;
		int storeId = 0;
		String response = "success";

		// when
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.delete("/por/pay").with(csrf())
				.contentType(MediaType.APPLICATION_JSON).param("storeId", String.valueOf(storeId))
				.param("reservationId", String.valueOf(reservationId)));

		// then
		resultActions.andExpect(status().isOk()).andExpect(MockMvcResultMatchers.content().string(equalTo(response)));
	}

	@Test
	@DisplayName("StorePORAPI : Delete Pay Fail : 권한 없음")
	@WithMockUser(username = "0", roles = "OWNER")
	public void deletePayFail() throws Exception {
		// given
		// 저장
		Long reservationId = 0L;
		int storeId = 1;
		String response = "권한이 없습니다.";

		// when
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.delete("/por/pay").with(csrf())
				.contentType(MediaType.APPLICATION_JSON).param("storeId", String.valueOf(storeId))
				.param("reservationId", String.valueOf(reservationId)));

		// then
		resultActions.andExpect(status().isOk()).andExpect(MockMvcResultMatchers.content().string(equalTo(response)));
	}

	@Test
	@DisplayName("StorePORAPI : Register Comment Success")
	@WithMockUser(username = "userId", roles = "USER")
	public void registerCommentSuccess() throws Exception {
		// given
		// 저장
		int reservationId = 0;
		String comment = "{\"comment\": \"comment\"}";
		String response = "success";

		// when
		ResultActions resultActions = mockMvc.perform(
				MockMvcRequestBuilders.post("/por/pay/comment").with(csrf()).contentType(MediaType.APPLICATION_JSON)
						.content(comment).param("reservationId", String.valueOf(reservationId)));

		// then
		resultActions.andExpect(status().isOk()).andExpect(MockMvcResultMatchers.content().string(equalTo(response)));
	}

	@Test
	@DisplayName("StorePORAPI : Register Comment Fail : 권한 없음")
	@WithMockUser(username = "wrongId", roles = "USER")
	public void registerCommentFail() throws Exception {
		// given
		// 저장
		int reservationId = 0;
		String comment = "{\"comment\": \"comment\"}";
		String response = "권한이 없습니다.";

		// when
		ResultActions resultActions = mockMvc.perform(
				MockMvcRequestBuilders.post("/por/pay/comment").with(csrf()).contentType(MediaType.APPLICATION_JSON)
						.content(comment).param("reservationId", String.valueOf(reservationId)));

		// then
		resultActions.andExpect(status().isBadRequest()).andExpect(result -> {
			String responseBody = result.getResponse().getContentAsString();
			String utf8ResponseBody = new String(responseBody.getBytes(StandardCharsets.ISO_8859_1),
					StandardCharsets.UTF_8);
			JsonNode jsonNode = new ObjectMapper().readTree(utf8ResponseBody);
			String errorMessage = jsonNode.get("message").asText();
			assertEquals(errorMessage, response);
		});
	}

	@Test
	@DisplayName("StorePORAPI : Delete Comment Success")
	@WithMockUser(username = "userId", roles = "USER")
	public void deleteCommentSuccess() throws Exception {
		// given
		// 저장
		int reservationId = 0;
		String response = "success";

		// when
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.delete("/por/pay/comment").with(csrf())
				.contentType(MediaType.APPLICATION_JSON).param("reservationId", String.valueOf(reservationId)));

		// then
		resultActions.andExpect(status().isOk()).andExpect(MockMvcResultMatchers.content().string(equalTo(response)));
	}

	@Test
	@DisplayName("StorePORAPI : Delete Comment Fail : 권한 없음")
	@WithMockUser(username = "wrongId", roles = "USER")
	public void deleteCommentFail() throws Exception {
		// given
		// 저장
		int reservationId = 0;
		String response = "권한이 없습니다.";

		// when
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.delete("/por/pay/comment").with(csrf())
				.contentType(MediaType.APPLICATION_JSON).param("reservationId", String.valueOf(reservationId)));

		// then
		resultActions.andExpect(status().isBadRequest()).andExpect(result -> {
			String responseBody = result.getResponse().getContentAsString();
			String utf8ResponseBody = new String(responseBody.getBytes(StandardCharsets.ISO_8859_1),
					StandardCharsets.UTF_8);
			JsonNode jsonNode = new ObjectMapper().readTree(utf8ResponseBody);
			String errorMessage = jsonNode.get("message").asText();
			assertEquals(errorMessage, response);
		});
	}

	@Test
	@DisplayName("StorePORAPI : Register Big Comment Success")
	@WithMockUser(username = "0", roles = "OWNER")
	public void registerBigCommentSuccess() throws Exception {
		// given
		// 저장
		int reservationId = 0;
		String comment = "{\"bigcomment\": \"comment\"}";
		String response = "success";

		// when
		ResultActions resultActions = mockMvc.perform(
				MockMvcRequestBuilders.post("/por/pay/bigcomment").with(csrf()).contentType(MediaType.APPLICATION_JSON)
						.content(comment).param("reservationId", String.valueOf(reservationId)));

		// then
		resultActions.andExpect(status().isOk()).andExpect(MockMvcResultMatchers.content().string(equalTo(response)));
	}

	@Test
	@DisplayName("StorePORAPI : Register Big Comment Fail : 권한 없음")
	@WithMockUser(username = "100", roles = "OWNER")
	public void registerBigCommentFail() throws Exception {
		// given
		// 저장
		int reservationId = 0;
		String comment = "{\"bigcomment\": \"comment\"}";
		String response = "권한이 없습니다.";

		// when
		ResultActions resultActions = mockMvc.perform(
				MockMvcRequestBuilders.post("/por/pay/bigcomment").with(csrf()).contentType(MediaType.APPLICATION_JSON)
						.content(comment).param("reservationId", String.valueOf(reservationId)));

		// then
		resultActions.andExpect(status().isBadRequest()).andExpect(result -> {
			String responseBody = result.getResponse().getContentAsString();
			String utf8ResponseBody = new String(responseBody.getBytes(StandardCharsets.ISO_8859_1),
					StandardCharsets.UTF_8);
			JsonNode jsonNode = new ObjectMapper().readTree(utf8ResponseBody);
			String errorMessage = jsonNode.get("message").asText();
			assertEquals(errorMessage, response);
		});
	}

	@Test
	@DisplayName("StorePORAPI : Delete Big Comment Success")
	@WithMockUser(username = "0", roles = "OWNER")
	public void deleteBigCommentSuccess() throws Exception {
		// given
		// 저장
		int reservationId = 0;
		String response = "success";

		// when
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.delete("/por/pay/bigcomment").with(csrf())
				.contentType(MediaType.APPLICATION_JSON).param("reservationId", String.valueOf(reservationId)));

		// then
		resultActions.andExpect(status().isOk()).andExpect(MockMvcResultMatchers.content().string(equalTo(response)));
	}

	@Test
	@DisplayName("StorePORAPI : Delete Big Comment Fail : 권한 없음")
	@WithMockUser(username = "100", roles = "OWNER")
	public void deleteBigCommentFail() throws Exception {
		// given
		// 저장
		int reservationId = 0;
		String response = "권한이 없습니다.";

		// when
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.delete("/por/pay/bigcomment").with(csrf())
				.contentType(MediaType.APPLICATION_JSON).param("reservationId", String.valueOf(reservationId)));

		// then
		resultActions.andExpect(status().isBadRequest()).andExpect(result -> {
			String responseBody = result.getResponse().getContentAsString();
			String utf8ResponseBody = new String(responseBody.getBytes(StandardCharsets.ISO_8859_1),
					StandardCharsets.UTF_8);
			JsonNode jsonNode = new ObjectMapper().readTree(utf8ResponseBody);
			String errorMessage = jsonNode.get("message").asText();
			assertEquals(errorMessage, response);
		});
	}

	@Test
	@DisplayName("StorePORAPI : Get Coupon Client Success")
	@WithMockUser(username = "userId", roles = "USER")
	public void getCouponClientSuccess() throws Exception {
		// given
		// 저장
		int storeId = 0;
		String response = "1";

		// when
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/por/coupon/client").with(csrf())
				.contentType(MediaType.APPLICATION_JSON).param("storeId", String.valueOf(storeId)));

		// then
		resultActions.andExpect(status().isOk()).andExpect(MockMvcResultMatchers.content().string(equalTo(response)));
	}

	@Test
	@DisplayName("StorePORAPI : Get Coupon Owner Success")
	@WithMockUser(username = "0", roles = "OWNER")
	public void getCouponOwnerSuccess() throws Exception {
		// given
		// 저장
		String response = "{\"userId\":1}";

		// when
		ResultActions resultActions = mockMvc.perform(
				MockMvcRequestBuilders.get("/por/coupon/owner").with(csrf()).contentType(MediaType.APPLICATION_JSON));

		// then
		resultActions.andExpect(status().isOk()).andExpect(MockMvcResultMatchers.content().string(equalTo(response)));
	}

	@Test
	@DisplayName("StorePORAPI : Get Coupon Owner Fail : 정보 없음")
	@WithMockUser(username = "0", roles = "OWNER")
	public void getCouponOwnerFail() throws Exception {
		// given
		String response = "정보가 존재하지 않습니다.";

		// when
		ResultActions resultActions = mockMvc.perform(
				MockMvcRequestBuilders.get("/por/coupon/owner").with(csrf()).contentType(MediaType.APPLICATION_JSON));

		// then
		resultActions.andExpect(status().isBadRequest()).andExpect(result -> {
			String responseBody = result.getResponse().getContentAsString();
			String utf8ResponseBody = new String(responseBody.getBytes(StandardCharsets.ISO_8859_1),
					StandardCharsets.UTF_8);
			JsonNode jsonNode = new ObjectMapper().readTree(utf8ResponseBody);
			String errorMessage = jsonNode.get("message").asText();
			assertEquals(errorMessage, response);
		});
	}

	// Persist Reservation
	public StoreReservationEntity makeReservation() {
		StoreReservationEntity entity = StoreReservationEntity.builder().userId("userId").storeId((short) 0)
				.date("1월1일").time("1시").storeTable("1번").build();
		em.persist(entity);
		return entity;
	}

	// Persist Order
	public StoreReservationEntity makeOrder() {
		StoreReservationEntity entity = StoreReservationEntity.builder().userId("userId").storeId((short) 0)
				.date("1월1일").time("1시").storeTable("1번").build();
		em.persist(entity);

		OrderDTO orderDTO = OrderDTO.getSample();
		StoreOrdersEntity father = new StoreOrdersEntity(entity);
		em.persist(father);
		List<StoreOrdersMapEntity> childs = new ArrayList<>();
		for (String foodName : orderDTO.getOrderInfo().keySet()) {
			StoreOrdersMapEntity child = new StoreOrdersMapEntity(foodName, orderDTO.getOrderInfo().get(foodName),
					father);
			em.persist(child);
			childs.add(child);
		}
		father.setOrdersMap(childs);
		entity.setChild(father);

		return entity;
	}
}
