package com.ReservationServer1.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
import com.ReservationServer1.data.DTO.store.StoreDTO;
import com.ReservationServer1.service.StoreService;
import com.google.gson.Gson;

@WebMvcTest(StoreController.class)
public class StoreControllerTest {


  @MockBean
  private StoreService storeService;

  @Autowired
  private MockMvc mockMvc;

  // 1. Register Store
  @Test
  @DisplayName("가게 등록 성공")
  @WithMockUser
  void registerStoreSuccess() throws Exception {
    // given
    StoreDTO sample = StoreDTO.sample();
    String response = "success";
    doReturn(response).when(storeService).registerStore(any(StoreDTO.class));

    // when
    ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/store").with(csrf())
        .contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(sample)));

    // then
    resultActions.andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().string(equalTo(response)));
  }

  @Test
  @DisplayName("가게 등록 실패 : 잘못된 StoreDTO 입력")
  @WithMockUser
  void registerStoreFail() throws Exception {
    // given
    StoreDTO sample = new StoreDTO();

    // when
    ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/store").with(csrf())
        .contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(sample)));

    // then
    resultActions.andExpect(status().isBadRequest());
  }


  // 2. Get Store List
  @Test
  @DisplayName("가게 리스트 출력 성공")
  @WithMockUser
  void getStoreListSuccess() throws Exception {
    // given
    HashMap<String, Integer> testResult = new HashMap<>();
    testResult.put("store1", 1);
    testResult.put("store2", 2);
    testResult.put("store3", 3);
    testResult.put("store4", 4);
    testResult.put("store5", 5);
    doReturn(testResult).when(storeService).getStoreList(anyString(), anyString(), anyString(),
        anyString(), anyInt(), anyInt());

    // when
    ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/store/list")
        .with(csrf()).contentType(MediaType.APPLICATION_JSON).param("country", "Country")
        .param("city", "City").param("dong", "Dong").param("type", "Type").param("page", "1")
        .param("size", "10"));

    // then
    resultActions.andExpect(status().isOk()).andExpect(jsonPath("$.store1").value(1))
        .andExpect(jsonPath("$.store2").value(2)).andExpect(jsonPath("$.store3").value(3))
        .andExpect(jsonPath("$.store4").value(4)).andExpect(jsonPath("$.store5").value(5));
  }


  @Test
  @DisplayName("가게 리스트 출력 실패 : 잘못된 입력 : Country 파라미터 생략")
  @WithMockUser
  void getStoreListFail() throws Exception {

    // when
    ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/store/list")
        .with(csrf()).contentType(MediaType.APPLICATION_JSON).param("city", "City")
        .param("dong", "Dong").param("type", "Type").param("page", "1").param("size", "10"));

    // then
    resultActions.andExpect(status().isBadRequest());
  }


  // Login Store
  @Test
  @DisplayName("가게 로그인 성공")
  @WithMockUser
  void loginStoreSuccess() throws Exception {
    // given
    String storeId = "1";
    String response = "OwnerToken";
    doReturn(response).when(storeService).loginStore(anyInt(), anyString());

    // when
    ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/store").with(csrf())
        .contentType(MediaType.APPLICATION_JSON).param("storeId", storeId));

    // then
    resultActions.andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().string(equalTo(response)));
  }
  
  
  @Test
  @DisplayName("가게 로그인 실패 : 잘못된 입력")
  @WithMockUser
  void loginStoreFailByInput() throws Exception {
    
    // when
    ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/store").with(csrf())
        .contentType(MediaType.APPLICATION_JSON));

    // then
    resultActions.andExpect(status().isBadRequest());
  }

  
  @Test
  @DisplayName("가게 로그인 실패 : 가게 권한 없음")
  @WithMockUser
  void loginStoreFailByAuthority() throws Exception {
    // given
    String storeId = "1";
    String response = "user";
    doReturn(response).when(storeService).loginStore(anyInt(), anyString());

    // when
    ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/store").with(csrf())
        .contentType(MediaType.APPLICATION_JSON).param("storeId", storeId));

    // then
    resultActions.andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().string(equalTo(response)));
  }
}
