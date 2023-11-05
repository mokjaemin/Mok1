//package com.ReservationServer1.Integration;
//
//import static org.hamcrest.CoreMatchers.equalTo;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.TestMethodOrder;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.ResultActions;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//import com.ReservationServer1.data.DTO.store.StoreDTO;
//import com.ReservationServer1.data.Entity.store.StoreEntity;
//import com.ReservationServer1.utils.JWTutil;
//import com.google.gson.Gson;
//import io.jsonwebtoken.ExpiredJwtException;
//import jakarta.persistence.EntityManager;
//import jakarta.transaction.Transactional;
//
//@SpringBootTest
//@ActiveProfiles("test")
//@AutoConfigureMockMvc
//@Transactional
//@TestMethodOrder(OrderAnnotation.class)
//public class StoreAPITest {
//
//	@Autowired
//	protected MockMvc mockMvc;
//
//	@Value("${jwt.secret}")
//	private String secretKey;
//
//	@Autowired
//	protected EntityManager em;
//
//	@Test
//	@DisplayName("StoreAPI : Register Store Success")
//	@WithMockUser(username = "userId", roles = "USER")
//
//	public void registerStoreSuccess() throws Exception {
//		// given
//		StoreDTO sample = StoreDTO.sample();
//		String response = "success";
//
//		// when
//		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/store").with(csrf())
//				.contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(sample)));
//
//		// then
//		resultActions.andExpect(status().isOk()).andExpect(MockMvcResultMatchers.content().string(equalTo(response)));
//	}
//
////  @Test
////  @DisplayName("StoreAPI : Get Store List Success")
////  @WithMockUser(username = "userId", roles = "USER")
////  public void getStoreSuccess() throws Exception {
////    // given
////    int page = 0;
////    int size = 1;
////
////    // 가게 등록
////    StoreEntity entity = persistStore();
////
////
////    // when
////    ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/store/list")
////        .with(csrf()).contentType(MediaType.APPLICATION_JSON).param("country", entity.getCountry())
////        .param("city", entity.getCity()).param("dong", entity.getDong())
////        .param("type", entity.getType()).param("page", String.valueOf(page))
////        .param("size", String.valueOf(size)));
////
////    // then
////    resultActions.andExpect(status().isOk());
////  }
//
//	@Test
//	@DisplayName("StoreAPI : Login Store Success")
//	@WithMockUser(username = "userId", roles = "USER")
//	public void loginStoreSuccess() throws Exception {
//		// given
//		// 가게 등록
//		StoreEntity entity = persistStore();
//
//		// when & then
//		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/store").with(csrf())
//				.contentType(MediaType.APPLICATION_JSON).param("storeId", String.valueOf(entity.getStoreId())))
//				.andExpect(status().isOk()).andReturn();
//
//		// 토큰 확인
//		String response = mvcResult.getResponse().getContentAsString();
//		assertTrue(JWTutil.isExpired(response, secretKey) == false);
//		assertTrue(JWTutil.getUserId(response, secretKey).equals(String.valueOf(entity.getStoreId())));
//		assertTrue(JWTutil.getUserRole(response, secretKey).equals("OWNER"));
//	}
//
//	@Test
//	@DisplayName("StoreAPI : Login Store Fail : 잘못된 정보")
//	@WithMockUser(username = "userId", roles = "USER")
//	public void loginStoreFail() throws Exception {
//		// given
//		int storeId = -1;
//		String result = "user";
//
//		// when & then
//		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/store").with(csrf())
//				.contentType(MediaType.APPLICATION_JSON).param("storeId", String.valueOf(storeId)))
//				.andExpect(status().isOk()).andReturn();
//
//		String response = mvcResult.getResponse().getContentAsString();
//		assertTrue(response.equals(result));
//
//	}
//
//	@Test
//	@DisplayName("JWTTest")
//	public void JWTTest() throws Exception {
//		// given
//		// 가게 등록
//		StoreEntity entity = persistStore();
//		String token = JWTutil.createJWT(entity.getOwnerId(), "USER", secretKey, 60 * 1000);
//
//		// when
//		MvcResult mvcResult = mockMvc.perform(
//				MockMvcRequestBuilders.get("/store").with(csrf()).header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
//						.contentType(MediaType.APPLICATION_JSON).param("storeId", String.valueOf(1)))
//				.andExpect(status().isOk()).andReturn();
//	}
//
//	@Test
//	@DisplayName("JWTTest : Expired Token")
//	public void JWTTest2() throws Exception {
//		// given
//		// 가게 등록
//		StoreEntity entity = persistStore();
//		String token = JWTutil.createJWT(entity.getOwnerId(), "USER", secretKey, 0);
//
//		// then && when
//		ExpiredJwtException message = assertThrows(ExpiredJwtException.class, () -> {
//			mockMvc.perform(MockMvcRequestBuilders.get("/store").with(csrf())
//					.header(HttpHeaders.AUTHORIZATION, "Bearer " + token).contentType(MediaType.APPLICATION_JSON)
//					.param("storeId", String.valueOf(1)));
//		});
//		String start = "JWT expired at";
//		assertTrue(message.getMessage().startsWith(start));
//	}
//
//	public StoreEntity persistStore() {
//		StoreEntity entity = new StoreEntity(StoreDTO.sample());
//		em.persist(entity);
//		return entity;
//	}
//
//}
