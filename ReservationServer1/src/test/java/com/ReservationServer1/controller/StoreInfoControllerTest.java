//package com.ReservationServer1.controller;
//
//import static org.hamcrest.CoreMatchers.equalTo;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyInt;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.doReturn;
//import static org.mockito.Mockito.never;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import java.util.ArrayList;
//import java.util.List;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.mock.web.MockMultipartFile;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.ResultActions;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//import com.ReservationServer1.data.DTO.store.StoreFoodsInfoDTO;
//import com.ReservationServer1.data.DTO.store.StoreFoodsInfoResultDTO;
//import com.ReservationServer1.data.DTO.store.StoreRestDayDTO;
//import com.ReservationServer1.data.DTO.store.StoreTableInfoDTO;
//import com.ReservationServer1.data.DTO.store.StoreTimeInfoDTO;
//import com.ReservationServer1.service.StoreInfoService;
//import com.google.gson.Gson;
//
//@WebMvcTest(StoreInfoController.class)
//public class StoreInfoControllerTest {
//
//  @MockBean
//  private StoreInfoService storeInfoService;
//
//  @Autowired
//  private MockMvc mockMvc;
//
//
//  // 1. Register Store Day Off
//  @Test
//  @DisplayName("가게 쉬는날 등록 성공")
//  @WithMockUser(username = "1")
//  void registerDayOffSuccess() throws Exception {
//    // given
//    StoreRestDayDTO sample = StoreRestDayDTO.sample();
//    String response = "success";
//    doReturn(response).when(storeInfoService).registerDayOff(any(StoreRestDayDTO.class));
//
//    // when
//    ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/info/day")
//        .with(csrf()).contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(sample)));
//
//    // then
//    resultActions.andExpect(status().isOk())
//        .andExpect(MockMvcResultMatchers.content().string(equalTo(response)));
//
//    // verify
//    verify(storeInfoService, times(1)).registerDayOff(any(StoreRestDayDTO.class));
//    verify(storeInfoService).registerDayOff(sample);
//  }
//
//  @Test
//  @DisplayName("가게 쉬는날 등록 실패 : 권한 없음")
//  @WithMockUser(username = "0")
//  void registerDayOffFail1() throws Exception {
//    // given
//    StoreRestDayDTO sample = StoreRestDayDTO.sample();
//    String response = "권한이 없습니다.";
//
//    // when
//    ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/info/day")
//        .with(csrf()).contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(sample)));
//
//    // then
//    resultActions.andExpect(status().isOk())
//        .andExpect(MockMvcResultMatchers.content().string(equalTo(response)));
//
//    // verify
//    verify(storeInfoService, never()).registerDayOff(sample);
//  }
//
//  @Test
//  @DisplayName("가게 쉬는날 등록 실패 : 잘못된 입력")
//  @WithMockUser
//  void registerDayOffFail2() throws Exception {
//    // given
//    StoreRestDayDTO sample = new StoreRestDayDTO();
//
//    // when
//    ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/info/day")
//        .with(csrf()).contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(sample)));
//
//    // then
//    resultActions.andExpect(status().isBadRequest());
//
//    // verify
//    verify(storeInfoService, never()).registerDayOff(sample);
//  }
//
//
//  // 2. Get Store Day Off
//  @Test
//  @DisplayName("가게 쉬는날 반환 성공")
//  @WithMockUser
//  void getDayOffSuccess() throws Exception {
//    // given
//    List<String> response = new ArrayList<>();
//    response.add("1월1일");
//    response.add("1월2일");
//    doReturn(response).when(storeInfoService).getDayOff(anyInt());
//
//    // when
//    ResultActions resultActions =
//        mockMvc.perform(MockMvcRequestBuilders.get("/info/day").with(csrf()).param("storeId", "1"));
//
//
//    // then
//    resultActions.andExpect(status().isOk())
//        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
//        .andExpect(MockMvcResultMatchers.content().json(new Gson().toJson(response)));
//
//    // verify
//    verify(storeInfoService, times(1)).getDayOff(anyInt());
//    verify(storeInfoService).getDayOff(1);
//  }
//
//
//
//  // 3. Delete Store Day Off
//  @Test
//  @DisplayName("가게 쉬는날 삭제 성공")
//  @WithMockUser(username = "1")
//  void deleteDayOffSuccess() throws Exception {
//    // given
//    int storeId = 1;
//    String response = "success";
//    doReturn(response).when(storeInfoService).deleteDayOff(anyInt());
//
//    // when
//    ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.delete("/info/day")
//        .with(csrf()).contentType(MediaType.APPLICATION_JSON).param("storeId", String.valueOf(storeId)));
//
//    // then
//    resultActions.andExpect(status().isOk())
//        .andExpect(MockMvcResultMatchers.content().string(equalTo(response)));
//
//    // verify
//    verify(storeInfoService, times(1)).deleteDayOff(anyInt());
//    verify(storeInfoService).deleteDayOff(storeId);
//  }
//
//  @Test
//  @DisplayName("가게 쉬는날 삭제 실패 : 권한 없음")
//  @WithMockUser(username = "0")
//  void deleteDayOffFail1() throws Exception {
//    // given
//    int storeId = 1;
//    String response = "권한이 없습니다.";
//
//    // when
//    ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.delete("/info/day")
//        .with(csrf()).contentType(MediaType.APPLICATION_JSON).param("storeId", String.valueOf(storeId)));
//
//    // then
//    resultActions.andExpect(status().isOk())
//        .andExpect(MockMvcResultMatchers.content().string(equalTo(response)));
//
//    // verify
//    verify(storeInfoService, never()).deleteDayOff(storeId);
//  }
//
//
//
//  // 4. Register Store Time Info
//  @Test
//  @DisplayName("가게 영업시간 등록 성공")
//  @WithMockUser(username = "1")
//  void registerTimeInfoSuccess() throws Exception {
//    // given
//    StoreTimeInfoDTO sample = StoreTimeInfoDTO.sample();
//    String response = "success";
//    doReturn(response).when(storeInfoService).registerTimeInfo(any(StoreTimeInfoDTO.class));
//
//    // when
//    ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/info/time")
//        .with(csrf()).contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(sample)));
//
//    // then
//    resultActions.andExpect(status().isOk())
//        .andExpect(MockMvcResultMatchers.content().string(equalTo(response)));
//
//    // verify
//    verify(storeInfoService, times(1)).registerTimeInfo(any(StoreTimeInfoDTO.class));
//    verify(storeInfoService).registerTimeInfo(sample);
//  }
//
//  @Test
//  @DisplayName("가게 영업시간 등록 실패 : 권한 없음")
//  @WithMockUser(username = "0")
//  void registerTimeInfoFail1() throws Exception {
//    // given
//    StoreTimeInfoDTO sample = StoreTimeInfoDTO.sample();
//    String response = "권한이 없습니다.";
//
//    // when
//    ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/info/time")
//        .with(csrf()).contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(sample)));
//
//    // then
//    resultActions.andExpect(status().isOk())
//        .andExpect(MockMvcResultMatchers.content().string(equalTo(response)));
//
//    // verify
//    verify(storeInfoService, never()).registerTimeInfo(sample);
//  }
//
//  @Test
//  @DisplayName("가게 영업시간 등록 실패 : 잘못된 입력")
//  @WithMockUser
//  void registerTimeInfofFail2() throws Exception {
//    // given
//    StoreTimeInfoDTO sample = new StoreTimeInfoDTO();
//
//    // when
//    ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/info/time")
//        .with(csrf()).contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(sample)));
//
//    // then
//    resultActions.andExpect(status().isBadRequest());
//
//    // verify
//    verify(storeInfoService, never()).registerTimeInfo(sample);
//  }
//
//
//  // 5. Get Store Time Info
//  @Test
//  @DisplayName("가게 영업시간 반환 성공")
//  @WithMockUser
//  void getTimeInfoSuccess() throws Exception {
//    // given
//    StoreTimeInfoDTO response = StoreTimeInfoDTO.sample();
//    int storeId = 1;
//    doReturn(response).when(storeInfoService).getTimeInfo(anyInt());
//
//    // when
//    ResultActions resultActions = mockMvc
//        .perform(MockMvcRequestBuilders.get("/info/time").with(csrf()).param("storeId", "1"));
//
//
//    // then
//    resultActions.andExpect(status().isOk())
//        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
//        .andExpect(MockMvcResultMatchers.content().json(new Gson().toJson(response)));
//
//    // verify
//    verify(storeInfoService, times(1)).getTimeInfo(anyInt());
//    verify(storeInfoService).getTimeInfo(storeId);
//  }
//
//
//  // 6. Modify Store Time Info
//  @Test
//  @DisplayName("가게 영업시간 수정 성공")
//  @WithMockUser(username = "1")
//  void modTimeInfoSuccess() throws Exception {
//    // given
//    StoreTimeInfoDTO sample = StoreTimeInfoDTO.sample();
//    String response = "success";
//    doReturn(response).when(storeInfoService).modTimeInfo(any(StoreTimeInfoDTO.class));
//
//    // when
//    ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.put("/info/time")
//        .with(csrf()).contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(sample)));
//
//    // then
//    resultActions.andExpect(status().isOk())
//        .andExpect(MockMvcResultMatchers.content().string(equalTo(response)));
//
//    // verify
//    verify(storeInfoService, times(1)).modTimeInfo(any(StoreTimeInfoDTO.class));
//    verify(storeInfoService).modTimeInfo(sample);
//  }
//
//
//  @Test
//  @DisplayName("가게 영업시간 수정 실패 : 권한 없음")
//  @WithMockUser(username = "0")
//  void modTimeInfoFail1() throws Exception {
//    // given
//    StoreTimeInfoDTO sample = StoreTimeInfoDTO.sample();
//    String response = "권한이 없습니다.";
//
//    // when
//    ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.put("/info/time")
//        .with(csrf()).contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(sample)));
//
//    // then
//    resultActions.andExpect(status().isOk())
//        .andExpect(MockMvcResultMatchers.content().string(equalTo(response)));
//
//    // verify
//    verify(storeInfoService, never()).modTimeInfo(sample);
//  }
//
//  @Test
//  @DisplayName("가게 영업시간 수정 실패 : 잘못된 입력")
//  @WithMockUser
//  void modTimeInfofFail2() throws Exception {
//    // given
//    StoreTimeInfoDTO sample = new StoreTimeInfoDTO();
//
//    // when
//    ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.put("/info/time")
//        .with(csrf()).contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(sample)));
//
//    // then
//    resultActions.andExpect(status().isBadRequest());
//
//    // verify
//    verify(storeInfoService, never()).modTimeInfo(sample);
//  }
//
//  // 7. Delete Time Info
//  @Test
//  @DisplayName("가게 영업시간 삭제 성공")
//  @WithMockUser(username = "1")
//  void deleteTimeInfoSuccess() throws Exception {
//    // given
//    int storeId = 1;
//    String response = "success";
//    doReturn(response).when(storeInfoService).deleteTimeInfo(anyInt());
//
//    // when
//    ResultActions resultActions =
//        mockMvc.perform(MockMvcRequestBuilders.delete("/info/time").with(csrf())
//            .contentType(MediaType.APPLICATION_JSON).param("storeId", String.valueOf(storeId)));
//
//    // then
//    resultActions.andExpect(status().isOk())
//        .andExpect(MockMvcResultMatchers.content().string(equalTo(response)));
//
//    // verify
//    verify(storeInfoService, times(1)).deleteTimeInfo(anyInt());
//    verify(storeInfoService).deleteTimeInfo(storeId);
//  }
//
//  @Test
//  @DisplayName("가게 영업시간 삭제 실패 : 권한 없음")
//  @WithMockUser(username = "0")
//  void deleteTimeInfoFail1() throws Exception {
//    // given
//    int storeId = 1;
//    String response = "권한이 없습니다.";
//    doReturn(response).when(storeInfoService).deleteTimeInfo(anyInt());
//
//    // when
//    ResultActions resultActions =
//        mockMvc.perform(MockMvcRequestBuilders.delete("/info/time").with(csrf())
//            .contentType(MediaType.APPLICATION_JSON).param("storeId", String.valueOf(storeId)));
//
//    // then
//    resultActions.andExpect(status().isOk())
//        .andExpect(MockMvcResultMatchers.content().string(equalTo(response)));
//
//
//    // verify
//    verify(storeInfoService, never()).deleteTimeInfo(storeId);
//  }
//
//
//
//  // 8. Register Table Info
//  @Test
//  @DisplayName("가게 테이블 정보 등록 성공")
//  @WithMockUser(username = "0")
//  void registerTableInfoSuccess() throws Exception {
//    // given
//    StoreTableInfoDTO sample = StoreTableInfoDTO.sample();
//    String response = "success";
//    MockMultipartFile tableImageFile = new MockMultipartFile("tableImage", "table-image.jpg",
//        MediaType.IMAGE_JPEG_VALUE, new byte[0]);
//    sample.setTableImage(tableImageFile);
//    when(storeInfoService.registerTableInfo(any(StoreTableInfoDTO.class))).thenReturn("success");
//
//    // when
//    ResultActions resultActions = mockMvc.perform(
//        MockMvcRequestBuilders.multipart("/info/table").file(tableImageFile).with(request -> {
//          request.setMethod("POST");
//          return request;
//        }).param("storeId", String.valueOf(sample.getStoreId()))
//            .param("count", String.valueOf(sample.getCount())).with(csrf()));
//
//
//    // then
//    resultActions.andExpect(status().isOk())
//        .andExpect(MockMvcResultMatchers.content().string(equalTo(response)));
//
//    // verify
//    verify(storeInfoService, times(1)).registerTableInfo(any(StoreTableInfoDTO.class));
//    verify(storeInfoService).registerTableInfo(sample);
//  }
//
//  @Test
//  @DisplayName("가게 테이블 정보 등록 실패 : 권한 없음")
//  @WithMockUser(username = "1")
//  void registerTableInfoFail1() throws Exception {
//    // given
//    StoreTableInfoDTO sample = StoreTableInfoDTO.sample();
//    String response = "권한이 없습니다.";
//    MockMultipartFile tableImageFile = new MockMultipartFile("tableImage", "table-image.jpg",
//        MediaType.IMAGE_JPEG_VALUE, new byte[0]);
//
//    // when
//    ResultActions resultActions = mockMvc.perform(
//        MockMvcRequestBuilders.multipart("/info/table").file(tableImageFile).with(request -> {
//          request.setMethod("POST");
//          return request;
//        }).param("storeId", String.valueOf(sample.getStoreId()))
//            .param("count", String.valueOf(sample.getCount())).with(csrf()));
//
//
//    // then
//    resultActions.andExpect(status().isOk())
//        .andExpect(MockMvcResultMatchers.content().string(equalTo(response)));
//
//    // verify
//    verify(storeInfoService, never()).registerTableInfo(any(StoreTableInfoDTO.class));
//  }
//
//
//  // 9. Modify Table Info
//  @Test
//  @DisplayName("가게 테이블 정보 수정 성공")
//  @WithMockUser(username = "0")
//  void modTableInfoSuccess() throws Exception {
//    // given
//    StoreTableInfoDTO sample = StoreTableInfoDTO.sample();
//    String response = "success";
//    MockMultipartFile tableImageFile = new MockMultipartFile("tableImage", "table-image.jpg",
//        MediaType.IMAGE_JPEG_VALUE, new byte[0]);
//    sample.setTableImage(tableImageFile);
//    when(storeInfoService.modTableInfo(any(StoreTableInfoDTO.class))).thenReturn("success");
//
//    // when
//    ResultActions resultActions = mockMvc.perform(
//        MockMvcRequestBuilders.multipart("/info/table").file(tableImageFile).with(request -> {
//          request.setMethod("PUT");
//          return request;
//        }).param("storeId", String.valueOf(sample.getStoreId()))
//            .param("count", String.valueOf(sample.getCount())).with(csrf()));
//
//
//    // then
//    resultActions.andExpect(status().isOk())
//        .andExpect(MockMvcResultMatchers.content().string(equalTo(response)));
//
//    // verify
//    verify(storeInfoService, times(1)).modTableInfo(any(StoreTableInfoDTO.class));
//    verify(storeInfoService).modTableInfo(sample);
//  }
//
//  @Test
//  @DisplayName("가게 테이블 정보 수정 실패 : 권한 없음")
//  @WithMockUser(username = "1")
//  void modTableInfoFail1() throws Exception {
//    // given
//    StoreTableInfoDTO sample = StoreTableInfoDTO.sample();
//    String response = "권한이 없습니다.";
//    MockMultipartFile tableImageFile = new MockMultipartFile("tableImage", "table-image.jpg",
//        MediaType.IMAGE_JPEG_VALUE, new byte[0]);
//
//    // when
//    ResultActions resultActions = mockMvc.perform(
//        MockMvcRequestBuilders.multipart("/info/table").file(tableImageFile).with(request -> {
//          request.setMethod("PUT");
//          return request;
//        }).param("storeId", String.valueOf(sample.getStoreId()))
//            .param("count", String.valueOf(sample.getCount())).with(csrf()));
//
//
//    // then
//    resultActions.andExpect(status().isOk())
//        .andExpect(MockMvcResultMatchers.content().string(equalTo(response)));
//
//    // verify
//    verify(storeInfoService, never()).modTableInfo(any(StoreTableInfoDTO.class));
//  }
//
//
//  // 10. Delete Table Info
//  @Test
//  @DisplayName("가게 테이블 정보 삭제 성공")
//  @WithMockUser(username = "1")
//  void deleteTableInfoSuccess() throws Exception {
//    // given
//    int storeId = 1;
//    String response = "success";
//    doReturn(response).when(storeInfoService).deleteTableInfo(anyInt());
//
//    // when
//    ResultActions resultActions =
//        mockMvc.perform(MockMvcRequestBuilders.delete("/info/table").with(csrf())
//            .contentType(MediaType.APPLICATION_JSON).param("storeId", String.valueOf(storeId)));
//
//    // then
//    resultActions.andExpect(status().isOk())
//        .andExpect(MockMvcResultMatchers.content().string(equalTo(response)));
//
//    // verify
//    verify(storeInfoService, times(1)).deleteTableInfo(anyInt());
//    verify(storeInfoService).deleteTableInfo(storeId);
//  }
//
//  @Test
//  @DisplayName("가게 테이블 정보 삭제 실패 : 권한 없음")
//  @WithMockUser(username = "0")
//  void deleteTableInfoFail1() throws Exception {
//    // given
//    int storeId = 1;
//    String response = "권한이 없습니다.";
//
//    // when
//    ResultActions resultActions =
//        mockMvc.perform(MockMvcRequestBuilders.delete("/info/table").with(csrf())
//            .contentType(MediaType.APPLICATION_JSON).param("storeId", String.valueOf(storeId)));
//
//    // then
//    resultActions.andExpect(status().isOk())
//        .andExpect(MockMvcResultMatchers.content().string(equalTo(response)));
//
//
//    // verify
//    verify(storeInfoService, never()).deleteTableInfo(storeId);
//  }
//
//
//  // 11. Register Food Info
//  @Test
//  @DisplayName("가게 음식 정보 등록 성공")
//  @WithMockUser(username = "0")
//  void registerFoodInfoSuccess() throws Exception {
//    // given
//    StoreFoodsInfoDTO sample = StoreFoodsInfoDTO.sample();
//    String response = "success";
//    MockMultipartFile foodImageFile = new MockMultipartFile("foodImage", "food-image.jpg",
//        MediaType.IMAGE_JPEG_VALUE, new byte[0]);
//    sample.setFoodImage(foodImageFile);
//    when(storeInfoService.registerFoodsInfo(any(StoreFoodsInfoDTO.class))).thenReturn("success");
//
//    // when
//    ResultActions resultActions = mockMvc.perform(
//        MockMvcRequestBuilders.multipart("/info/foods").file(foodImageFile).with(request -> {
//          request.setMethod("POST");
//          return request;
//        }).param("storeId", String.valueOf(sample.getStoreId()))
//            .param("foodName", sample.getFoodName())
//            .param("foodDescription", sample.getFoodDescription())
//            .param("foodPrice", String.valueOf(sample.getFoodPrice())).with(csrf()));
//
//
//    // then
//    resultActions.andExpect(status().isOk())
//        .andExpect(MockMvcResultMatchers.content().string(equalTo(response)));
//
//    // verify
//    verify(storeInfoService, times(1)).registerFoodsInfo(any(StoreFoodsInfoDTO.class));
//    verify(storeInfoService).registerFoodsInfo(sample);
//  }
//
//  @Test
//  @DisplayName("가게 음식 정보 등록 실패 : 권한 없음")
//  @WithMockUser(username = "1")
//  void registerFoodInfoFail() throws Exception {
//    // given
//    StoreFoodsInfoDTO sample = StoreFoodsInfoDTO.sample();
//    String response = "권한이 없습니다.";
//    MockMultipartFile foodImageFile = new MockMultipartFile("foodImage", "food-image.jpg",
//        MediaType.IMAGE_JPEG_VALUE, new byte[0]);
//    sample.setFoodImage(foodImageFile);
//
//    // when
//    ResultActions resultActions = mockMvc.perform(
//        MockMvcRequestBuilders.multipart("/info/foods").file(foodImageFile).with(request -> {
//          request.setMethod("POST");
//          return request;
//        }).param("storeId", String.valueOf(sample.getStoreId()))
//            .param("foodName", sample.getFoodName())
//            .param("foodDescription", sample.getFoodDescription())
//            .param("foodPrice", String.valueOf(sample.getFoodPrice())).with(csrf()));
//
//
//    // then
//    resultActions.andExpect(status().isOk())
//        .andExpect(MockMvcResultMatchers.content().string(equalTo(response)));
//
//    // verify
//    verify(storeInfoService, never()).registerFoodsInfo(any(StoreFoodsInfoDTO.class));
//  }
//
//  // 12. Get Store Foods Info
//  @Test
//  @DisplayName("가게 음식정보 반환 성공")
//  @WithMockUser
//  void getFoodsInfoSuccess() throws Exception {
//    // given
//    List<StoreFoodsInfoResultDTO> response = new ArrayList<>();
//    StoreFoodsInfoResultDTO sample = StoreFoodsInfoResultDTO.sample();
//    response.add(sample);
//    doReturn(response).when(storeInfoService).getFoodsInfo(anyInt());
//
//    // when
//    ResultActions resultActions = mockMvc
//        .perform(MockMvcRequestBuilders.get("/info/foods").with(csrf()).param("storeId", "1"));
//
//
//    // then
//    resultActions.andExpect(status().isOk())
//        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
//        .andExpect(MockMvcResultMatchers.content().json(new Gson().toJson(response)));
//
//    // verify
//    verify(storeInfoService, times(1)).getFoodsInfo(anyInt());
//    verify(storeInfoService).getFoodsInfo(1);
//  }
//
//
//  // 13. Modify Food Info
//  @Test
//  @DisplayName("가게 음식 정보 수정 성공")
//  @WithMockUser(username = "0")
//  void modFoodInfoSuccess() throws Exception {
//    // given
//    StoreFoodsInfoDTO sample = StoreFoodsInfoDTO.sample();
//    String response = "success";
//    MockMultipartFile foodImageFile = new MockMultipartFile("foodImage", "food-image.jpg",
//        MediaType.IMAGE_JPEG_VALUE, new byte[0]);
//    sample.setFoodImage(foodImageFile);
//    when(storeInfoService.modFoodsInfo(any(StoreFoodsInfoDTO.class))).thenReturn("success");
//
//    // when
//    ResultActions resultActions = mockMvc.perform(
//        MockMvcRequestBuilders.multipart("/info/foods").file(foodImageFile).with(request -> {
//          request.setMethod("PUT");
//          return request;
//        }).param("storeId", String.valueOf(sample.getStoreId()))
//            .param("foodName", sample.getFoodName())
//            .param("foodDescription", sample.getFoodDescription())
//            .param("foodPrice", String.valueOf(sample.getFoodPrice())).with(csrf()));
//
//
//    // then
//    resultActions.andExpect(status().isOk())
//        .andExpect(MockMvcResultMatchers.content().string(equalTo(response)));
//
//    // verify
//    verify(storeInfoService, times(1)).modFoodsInfo(any(StoreFoodsInfoDTO.class));
//    verify(storeInfoService).modFoodsInfo(sample);
//  }
//
//  @Test
//  @DisplayName("가게 음식 정보 수정 실패 : 권한 없음")
//  @WithMockUser(username = "1")
//  void modFoodInfoFail() throws Exception {
//    // given
//    StoreFoodsInfoDTO sample = StoreFoodsInfoDTO.sample();
//    String response = "권한이 없습니다.";
//    MockMultipartFile foodImageFile = new MockMultipartFile("foodImage", "food-image.jpg",
//        MediaType.IMAGE_JPEG_VALUE, new byte[0]);
//    sample.setFoodImage(foodImageFile);
//
//    // when
//    ResultActions resultActions = mockMvc.perform(
//        MockMvcRequestBuilders.multipart("/info/foods").file(foodImageFile).with(request -> {
//          request.setMethod("PUT");
//          return request;
//        }).param("storeId", String.valueOf(sample.getStoreId()))
//            .param("foodName", sample.getFoodName())
//            .param("foodDescription", sample.getFoodDescription())
//            .param("foodPrice", String.valueOf(sample.getFoodPrice())).with(csrf()));
//
//
//    // then
//    resultActions.andExpect(status().isOk())
//        .andExpect(MockMvcResultMatchers.content().string(equalTo(response)));
//
//    // verify
//    verify(storeInfoService, never()).modFoodsInfo(any(StoreFoodsInfoDTO.class));
//  }
//
//  // 14. Delete Foods Info
//  @Test
//  @DisplayName("가게 테이블 정보 삭제 성공")
//  @WithMockUser(username = "1")
//  void deleteFoodsInfoSuccess() throws Exception {
//    // given
//    int storeId = 1;
//    String foodName = "foodName";
//    String response = "success";
//    doReturn(response).when(storeInfoService).deleteFoodsInfo(anyInt(), anyString());
//
//    // when
//    ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.delete("/info/foods")
//        .with(csrf()).contentType(MediaType.APPLICATION_JSON)
//        .param("storeId", String.valueOf(storeId)).param("foodName", String.valueOf(foodName)));
//
//    // then
//    resultActions.andExpect(status().isOk())
//        .andExpect(MockMvcResultMatchers.content().string(equalTo(response)));
//
//    // verify
//    verify(storeInfoService, times(1)).deleteFoodsInfo(anyInt(), anyString());
//    verify(storeInfoService).deleteFoodsInfo(storeId, foodName);
//  }
//
//  @Test
//  @DisplayName("가게 테이블 정보 삭제 실패")
//  @WithMockUser(username = "0")
//  void deleteFoodsInfoFail() throws Exception {
//    // given
//    int storeId = 1;
//    String foodName = "foodName";
//    String response = "권한이 없습니다.";
//    doReturn(response).when(storeInfoService).deleteFoodsInfo(anyInt(), anyString());
//
//    // when
//    ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.delete("/info/foods")
//        .with(csrf()).contentType(MediaType.APPLICATION_JSON)
//        .param("storeId", String.valueOf(storeId)).param("foodName", String.valueOf(foodName)));
//
//    // then
//    resultActions.andExpect(status().isOk())
//        .andExpect(MockMvcResultMatchers.content().string(equalTo(response)));
//
//    // verify
//    verify(storeInfoService, never()).deleteFoodsInfo(anyInt(), anyString());
//  }
//
//}
