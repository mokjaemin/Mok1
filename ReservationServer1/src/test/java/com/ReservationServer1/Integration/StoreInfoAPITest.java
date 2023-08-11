package com.ReservationServer1.Integration;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.ReservationServer1.data.DTO.store.StoreFoodsInfoDTO;
import com.ReservationServer1.data.DTO.store.StoreFoodsInfoResultDTO;
import com.ReservationServer1.data.DTO.store.StoreRestDayDTO;
import com.ReservationServer1.data.DTO.store.StoreTableInfoDTO;
import com.ReservationServer1.data.DTO.store.StoreTimeInfoDTO;
import com.ReservationServer1.data.Entity.store.StoreFoodsInfoEntity;
import com.ReservationServer1.data.Entity.store.StoreRestDaysEntity;
import com.ReservationServer1.data.Entity.store.StoreRestDaysMapEntity;
import com.ReservationServer1.data.Entity.store.StoreTimeInfoEntity;
import com.ReservationServer1.data.Entity.store.StoreTimeInfoMapEntity;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Transactional
public class StoreInfoAPITest {


  @Autowired
  protected MockMvc mockMvc;

  @Autowired
  protected EntityManager em;

  public String DIR_TABLE = "/Users/mokjaemin/Desktop/Mok1/storeTable";

  public String DIR_FOODS = "/Users/mokjaemin/Desktop/Mok1/storeFoods";


  @Test
  @DisplayName("StoreInfoAPI : Register Day Off Success")
  @WithMockUser(username = "1", roles = "OWNER")
  public void registerDayOffSuccess() throws Exception {
    // given
    StoreRestDayDTO sample = StoreRestDayDTO.sample();
    String response = "success";

    // when
    ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/info/day")
        .with(csrf()).contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(sample)));

    // then
    resultActions.andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().string(equalTo(response)));
  }


  @Test
  @DisplayName("StoreInfoAPI : Register Day Off Fail : 권한 없음")
  @WithMockUser(username = "0", roles = "OWNER")
  public void registerDayOffFail() throws Exception {
    // given
    StoreRestDayDTO sample = StoreRestDayDTO.sample();
    String response = "권한이 없습니다.";

    // when
    ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/info/day")
        .with(csrf()).contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(sample)));

    // then
    resultActions.andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().string(equalTo(response)));
  }


  @Test
  @DisplayName("StoreInfoAPI : Get Day Off Success")
  @WithMockUser(username = "userId", roles = "USER")
  public void getDayOffSuccess() throws Exception {
    // given

    // 등록
    StoreRestDayDTO sample = StoreRestDayDTO.sample();
    persistDayOff(sample);
    String response = "[\"date1\"]";

    // when
    ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/info/day")
        .with(csrf()).contentType(MediaType.APPLICATION_JSON).param("storeId", String.valueOf(1)));

    // then
    resultActions.andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().string(equalTo(response)));
  }


  @Test
  @DisplayName("StoreInfoAPI : Get Day Off Fail : 정보 없음")
  @WithMockUser(username = "userId", roles = "USER")
  public void getDayOffFail() throws Exception {
    // given
    String response = "정보가 등록되지 않았습니다.";

    // when
    ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/info/day")
        .with(csrf()).contentType(MediaType.APPLICATION_JSON).param("storeId", String.valueOf(1)));

    // then
    resultActions.andExpect(status().isBadRequest()).andExpect(result -> {
      String responseBody = result.getResponse().getContentAsString();
      String utf8ResponseBody =
          new String(responseBody.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
      JsonNode jsonNode = new ObjectMapper().readTree(utf8ResponseBody);
      String errorMessage = jsonNode.get("message").asText();
      assertEquals(errorMessage, response);
    });

  }


  @Test
  @DisplayName("StoreInfoAPI : Delete Day Off Success")
  @WithMockUser(username = "1", roles = "OWNER")
  public void deleteDayOffSuccess() throws Exception {
    // given

    // 등록
    StoreRestDayDTO sample = StoreRestDayDTO.sample();
    persistDayOff(sample);
    String response = "success";

    // when
    ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.delete("/info/day")
        .with(csrf()).contentType(MediaType.APPLICATION_JSON).param("storeId", String.valueOf(1)));

    // then
    resultActions.andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().string(equalTo(response)));
  }


  @Test
  @DisplayName("StoreInfoAPI : Delete Day Off Fail : 권한 없음")
  @WithMockUser(username = "0", roles = "OWNER")
  public void deleteDayOffFail() throws Exception {
    // given
    String response = "권한이 없습니다.";

    // when
    ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.delete("/info/day")
        .with(csrf()).contentType(MediaType.APPLICATION_JSON).param("storeId", String.valueOf(1)));

    // then
    resultActions.andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().string(equalTo(response)));
  }

  @Test
  @DisplayName("StoreInfoAPI : Register Time Info Success")
  @WithMockUser(username = "1", roles = "OWNER")
  public void registerTimeInfoSuccess() throws Exception {
    // given
    StoreTimeInfoDTO sample = StoreTimeInfoDTO.sample();
    String response = "success";

    // when
    ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/info/time")
        .with(csrf()).contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(sample)));

    // then
    resultActions.andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().string(equalTo(response)));
  }

  @Test
  @DisplayName("StoreInfoAPI : Register Time Info Fail : 권한 없음")
  @WithMockUser(username = "0", roles = "OWNER")
  public void registerTimeInfoFail() throws Exception {
    // given
    StoreTimeInfoDTO sample = StoreTimeInfoDTO.sample();
    String response = "권한이 없습니다.";

    // when
    ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/info/time")
        .with(csrf()).contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(sample)));

    // then
    resultActions.andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().string(equalTo(response)));
  }


  @Test
  @DisplayName("StoreInfoAPI : Get Time Info Success")
  @WithMockUser(username = "userId", roles = "USER")
  public void getTimeInfoSuccess() throws Exception {
    // given
    StoreTimeInfoDTO sample = StoreTimeInfoDTO.sample();
    persistTimeInfo(sample);

    // when
    ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/info/time")
        .with(csrf()).contentType(MediaType.APPLICATION_JSON).param("storeId", "1"));

    // then
    resultActions.andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().json(new Gson().toJson(sample)));

  }

  @Test
  @DisplayName("StoreInfoAPI : Get Time Info Fail : 정보 없음")
  @WithMockUser(username = "userId", roles = "USER")
  public void getTimeInfoFail1() throws Exception {
    // given
    String response = "정보가 존재하지 않습니다.";

    // when
    ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/info/time")
        .with(csrf()).contentType(MediaType.APPLICATION_JSON).param("storeId", "1"));

    // then
    resultActions.andExpect(status().isBadRequest()).andExpect(result -> {
      String responseBody = result.getResponse().getContentAsString();
      String utf8ResponseBody =
          new String(responseBody.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
      JsonNode jsonNode = new ObjectMapper().readTree(utf8ResponseBody);
      String errorMessage = jsonNode.get("message").asText();
      assertEquals(errorMessage, response);
    });

  }

  @Test
  @DisplayName("StoreInfoAPI : Update Time Info Success")
  @WithMockUser(username = "1", roles = "OWNER")
  public void modTimeInfoSuccess() throws Exception {
    // given
    StoreTimeInfoDTO sample = StoreTimeInfoDTO.sample();
    String response = "success";

    persistTimeInfo(sample);

    // when
    ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.put("/info/time")
        .with(csrf()).contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(sample)));

    // then
    resultActions.andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().string(equalTo(response)));
  }

  @Test
  @DisplayName("StoreInfoAPI : Update Time Info Fail : 권한 없음")
  @WithMockUser(username = "0", roles = "OWNER")
  public void modTimeInfoFail1() throws Exception {
    // given
    StoreTimeInfoDTO sample = StoreTimeInfoDTO.sample();
    String response = "권한이 없습니다.";

    // when
    ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.put("/info/time")
        .with(csrf()).contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(sample)));

    // then
    resultActions.andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().string(equalTo(response)));
  }

  @Test
  @DisplayName("StoreInfoAPI : Update Time Info Fail : 정보 없음")
  @WithMockUser(username = "1", roles = "OWNER")
  public void modTimeInfoFail2() throws Exception {
    // given
    StoreTimeInfoDTO sample = StoreTimeInfoDTO.sample();
    String response = "정보가 존재하지 않습니다.";

    // when
    ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.put("/info/time")
        .with(csrf()).contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(sample)));

    // then
    resultActions.andExpect(status().isBadRequest()).andExpect(result -> {
      String responseBody = result.getResponse().getContentAsString();
      String utf8ResponseBody =
          new String(responseBody.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
      JsonNode jsonNode = new ObjectMapper().readTree(utf8ResponseBody);
      String errorMessage = jsonNode.get("message").asText();
      assertEquals(errorMessage, response);
    });
  }

  @Test
  @DisplayName("StoreInfoAPI : Delete Time Info Success")
  @WithMockUser(username = "1", roles = "OWNER")
  public void deleteTimeInfoSuccess() throws Exception {
    // given
    StoreTimeInfoDTO sample = StoreTimeInfoDTO.sample();
    String response = "success";

    persistTimeInfo(sample);

    // when
    ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.delete("/info/time")
        .with(csrf()).contentType(MediaType.APPLICATION_JSON)
        .param("storeId", String.valueOf(sample.getStoreId())));

    // then
    resultActions.andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().string(equalTo(response)));
  }

  @Test
  @DisplayName("StoreInfoAPI : Delete Time Info Fail : 권한 없음")
  @WithMockUser(username = "0", roles = "OWNER")
  public void deleteTimeInfoFail() throws Exception {
    // given
    StoreTimeInfoDTO sample = StoreTimeInfoDTO.sample();
    String response = "권한이 없습니다.";

    // when
    ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.delete("/info/time")
        .with(csrf()).contentType(MediaType.APPLICATION_JSON)
        .param("storeId", String.valueOf(sample.getStoreId())));

    // then
    resultActions.andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().string(equalTo(response)));
  }


  @Test
  @DisplayName("StoreInfoAPI : Register Table Info Success")
  @WithMockUser(username = "0", roles = "OWNER")
  public void registerTableInfoSuccess() throws Exception {
    // given
    StoreTableInfoDTO sample = StoreTableInfoDTO.sample();
    MockMultipartFile tableImageFile =
        new MockMultipartFile("tableImage", "table-image.png", "image/png", new byte[0]);
    String response = "success";

    // when
    ResultActions resultActions = mockMvc.perform(
        MockMvcRequestBuilders.multipart("/info/table").file(tableImageFile).with(request -> {
          request.setMethod("POST");
          return request;
        }).param("storeId", String.valueOf(sample.getStoreId()))
            .param("count", String.valueOf(sample.getCount())).with(csrf()));

    // then
    resultActions.andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().string(equalTo(response)));


    // 파일 삭제
    Path filePath = Path.of(DIR_TABLE, String.valueOf(sample.getStoreId()) + ".png");
    Files.delete(filePath);
  }

  @Test
  @DisplayName("StoreInfoAPI : Register Table Info Fail : 권한 없음")
  @WithMockUser(username = "1", roles = "OWNER")
  public void registerTableInfoFail1() throws Exception {
    // given
    StoreTableInfoDTO sample = StoreTableInfoDTO.sample();
    MockMultipartFile tableImageFile =
        new MockMultipartFile("tableImage", "table-image.png", "image/png", new byte[0]);
    String response = "권한이 없습니다.";

    // when
    ResultActions resultActions = mockMvc.perform(
        MockMvcRequestBuilders.multipart("/info/table").file(tableImageFile).with(request -> {
          request.setMethod("POST");
          return request;
        }).param("storeId", String.valueOf(sample.getStoreId()))
            .param("count", String.valueOf(sample.getCount())).with(csrf()));

    // then
    resultActions.andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().string(equalTo(response)));
  }

  @Test
  @DisplayName("StoreInfoAPI : Update Table Info Success")
  @WithMockUser(username = "0", roles = "OWNER")
  public void modTableInfoSuccess() throws Exception {
    // 저장
    registerTableInfoSuccess();

    // given
    StoreTableInfoDTO sample = StoreTableInfoDTO.sample();
    MockMultipartFile tableImageFile =
        new MockMultipartFile("tableImage", "table-image.png", "image/png", new byte[0]);
    String response = "success";

    // when
    ResultActions resultActions = mockMvc.perform(
        MockMvcRequestBuilders.multipart("/info/table").file(tableImageFile).with(request -> {
          request.setMethod("PUT");
          return request;
        }).param("storeId", String.valueOf(sample.getStoreId()))
            .param("count", String.valueOf(sample.getCount())).with(csrf()));

    // then
    resultActions.andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().string(equalTo(response)));


    // 파일 삭제
    Path filePath = Path.of(DIR_TABLE, String.valueOf(sample.getStoreId()) + ".png");
    Files.delete(filePath);
  }

  @Test
  @DisplayName("StoreInfoAPI : Update Table Info Fail : 권한 없음")
  @WithMockUser(username = "1", roles = "OWNER")
  public void modTableInfoFail1() throws Exception {
    // given
    StoreTableInfoDTO sample = StoreTableInfoDTO.sample();
    MockMultipartFile tableImageFile =
        new MockMultipartFile("tableImage", "table-image.png", "image/png", new byte[0]);
    String response = "권한이 없습니다.";

    // when
    ResultActions resultActions = mockMvc.perform(
        MockMvcRequestBuilders.multipart("/info/table").file(tableImageFile).with(request -> {
          request.setMethod("PUT");
          return request;
        }).param("storeId", String.valueOf(sample.getStoreId()))
            .param("count", String.valueOf(sample.getCount())).with(csrf()));

    // then
    resultActions.andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().string(equalTo(response)));
  }


  @Test
  @DisplayName("StoreInfoAPI : Delete Table Info Success")
  @WithMockUser(username = "0", roles = "OWNER")
  public void deleteTableInfoSuccess() throws Exception {
    // given

    // 저장
    registerTableInfoSuccess();
    StoreTableInfoDTO sample = StoreTableInfoDTO.sample();
    MockMultipartFile tableImageFile =
        new MockMultipartFile("tableImage", "table-image.png", "image/png", new byte[0]);
    sample.setTableImage(tableImageFile);
    File uploadDir = new File(DIR_TABLE);
    if (!uploadDir.exists()) {
      uploadDir.mkdirs();
    }
    int storeId = sample.getStoreId();
    Path filePath = Path.of(DIR_TABLE, String.valueOf(storeId) + ".png");
    Files.copy(sample.getTableImage().getInputStream(), filePath,
        StandardCopyOption.REPLACE_EXISTING);



    String response = "success";



    // when
    ResultActions resultActions =
        mockMvc.perform(MockMvcRequestBuilders.delete("/info/table").with(csrf())
            .contentType(MediaType.APPLICATION_JSON).param("storeId", String.valueOf(storeId)));


    // then
    resultActions.andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().string(equalTo(response)));

  }


  @Test
  @DisplayName("StoreInfoAPI : Delete Table Info Fail : 사진 없음")
  @WithMockUser(username = "0", roles = "OWNER")
  public void deleteTableInfoFail1() throws Exception {
    // 저장
    registerTableInfoSuccess();

    // given
    int storeId = 0;
    String response = "File Deleted failed";

    // when
    ResultActions resultActions =
        mockMvc.perform(MockMvcRequestBuilders.delete("/info/table").with(csrf())
            .contentType(MediaType.APPLICATION_JSON).param("storeId", String.valueOf(storeId)));

    // then
    resultActions.andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().string(equalTo(response)));

  }


  @Test
  @DisplayName("StoreInfoAPI : Delete Table Info Fail : 권한 없음")
  @WithMockUser(username = "1", roles = "OWNER")
  public void deleteTableInfoFail2() throws Exception {
    // given
    int storeId = 0;
    String response = "권한이 없습니다.";

    // when
    ResultActions resultActions =
        mockMvc.perform(MockMvcRequestBuilders.delete("/info/table").with(csrf())
            .contentType(MediaType.APPLICATION_JSON).param("storeId", String.valueOf(storeId)));

    // then
    resultActions.andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().string(equalTo(response)));

  }


  @Test
  @DisplayName("StoreInfoAPI : Register Foods Info Success")
  @WithMockUser(username = "0", roles = "OWNER")
  public void registerFoodsInfoSuccess() throws Exception {
    // given
    StoreFoodsInfoDTO sample = StoreFoodsInfoDTO.sample();
    MockMultipartFile foodsImageFile =
        new MockMultipartFile("foodImage", "food-image.png", "image/png", new byte[0]);
    String response = "success";

    // when
    ResultActions resultActions = mockMvc.perform(
        MockMvcRequestBuilders.multipart("/info/foods").file(foodsImageFile).with(request -> {
          request.setMethod("POST");
          return request;
        }).param("storeId", String.valueOf(sample.getStoreId()))
            .param("foodName", sample.getFoodName())
            .param("foodDescription", sample.getFoodDescription())
            .param("foodPrice", String.valueOf(sample.getFoodPrice())).with(csrf()));

    // then
    resultActions.andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().string(equalTo(response)));


    // 파일 삭제
    Path filePath = Path.of(DIR_FOODS,
        String.valueOf(sample.getStoreId()) + "_" + sample.getFoodName() + ".png");
    Files.delete(filePath);
  }

  @Test
  @DisplayName("StoreInfoAPI : Register Foods Info Fail : 권한 없음")
  @WithMockUser(username = "1", roles = "OWNER")
  public void registerFoodsInfoFail() throws Exception {
    // given
    StoreFoodsInfoDTO sample = StoreFoodsInfoDTO.sample();
    MockMultipartFile foodsImageFile =
        new MockMultipartFile("foodImage", "food-image.png", "image/png", new byte[0]);
    String response = "권한이 없습니다.";

    // when
    ResultActions resultActions = mockMvc.perform(
        MockMvcRequestBuilders.multipart("/info/foods").file(foodsImageFile).with(request -> {
          request.setMethod("POST");
          return request;
        }).param("storeId", String.valueOf(sample.getStoreId()))
            .param("foodName", sample.getFoodName())
            .param("foodDescription", sample.getFoodDescription())
            .param("foodPrice", String.valueOf(sample.getFoodPrice())).with(csrf()));

    // then
    resultActions.andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().string(equalTo(response)));

  }

  @Test
  @DisplayName("StoreInfoAPI : Get Foods Info Success")
  @WithMockUser(username = "userId", roles = "USER")
  public void getFoodsInfoSuccess() throws Exception {

    // 사진 저장
    StoreFoodsInfoDTO sample = StoreFoodsInfoDTO.sample();
    MockMultipartFile foodsImageFile =
        new MockMultipartFile("foodImage", "food-image.png", "image/png", new byte[0]);
    sample.setFoodImage(foodsImageFile);
    int storeId = sample.getStoreId();
    String foodName = sample.getFoodName();
    Path filePath = Path.of(DIR_FOODS, String.valueOf(storeId) + "_" + foodName + ".png");
    Files.copy(sample.getFoodImage().getInputStream(), filePath,
        StandardCopyOption.REPLACE_EXISTING);

    // 정보 저장
    StoreFoodsInfoEntity storeFoodsInfoEntity =
        StoreFoodsInfoEntity.builder().storeId(sample.getStoreId()).foodName(sample.getFoodName())
            .foodDescription(sample.getFoodDescription()).imageURL(filePath.toString())
            .foodPrice(sample.getFoodPrice()).build();
    em.persist(storeFoodsInfoEntity);


    // 결과 생성
    List<StoreFoodsInfoResultDTO> result = new ArrayList<>();
    Path filePath1 = Path.of(storeFoodsInfoEntity.getImageURL());
    byte[] imageBytes = Files.readAllBytes(filePath1);
    String encoded_image = Base64.getEncoder().encodeToString(imageBytes);
    StoreFoodsInfoResultDTO dto = storeFoodsInfoEntity.toStoreFoodsInfoResultDTO();
    dto.setEncoded_image(encoded_image);
    result.add(dto);


    // when
    ResultActions resultActions =
        mockMvc.perform(MockMvcRequestBuilders.get("/info/foods").with(csrf())
            .contentType(MediaType.APPLICATION_JSON).param("storeId", String.valueOf(storeId)));

    // then
    resultActions.andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().json(new Gson().toJson(result)));


    // 파일 삭제
    Files.delete(filePath);
  }


  @Test
  @DisplayName("StoreInfoAPI : Get Foods Info Fail : 사진 없음")
  @WithMockUser(username = "userId", roles = "USER")
  public void getFoodsInfoFail() throws Exception {

    // 사진 저장 안함
    StoreFoodsInfoDTO sample = StoreFoodsInfoDTO.sample();
    MockMultipartFile foodsImageFile =
        new MockMultipartFile("foodImage", "food-image.png", "image/png", new byte[0]);
    sample.setFoodImage(foodsImageFile);
    int storeId = sample.getStoreId();
    String foodName = sample.getFoodName();
    Path filePath = Path.of(DIR_FOODS, String.valueOf(storeId) + "_" + foodName + ".png");

    // 정보 저장
    StoreFoodsInfoEntity storeFoodsInfoEntity =
        StoreFoodsInfoEntity.builder().storeId(sample.getStoreId()).foodName(sample.getFoodName())
            .foodDescription(sample.getFoodDescription()).imageURL(filePath.toString())
            .foodPrice(sample.getFoodPrice()).build();
    em.persist(storeFoodsInfoEntity);

    // 결과
    String response = "해당 음식점 사진에 문제가 존재합니다.";



    // when
    ResultActions resultActions =
        mockMvc.perform(MockMvcRequestBuilders.get("/info/foods").with(csrf())
            .contentType(MediaType.APPLICATION_JSON).param("storeId", String.valueOf(storeId)));

    // then
    resultActions.andExpect(status().isBadRequest()).andExpect(result -> {
      String responseBody = result.getResponse().getContentAsString();
      String utf8ResponseBody =
          new String(responseBody.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
      JsonNode jsonNode = new ObjectMapper().readTree(utf8ResponseBody);
      String errorMessage = jsonNode.get("message").asText();
      assertEquals(errorMessage, response);
    });
  }


  @Test
  @DisplayName("StoreInfoAPI : Get Foods Info Fail : 정보 없음")
  @WithMockUser(username = "userId", roles = "USER")
  public void getFoodsInfoFail1() throws Exception {
    // 사진 저장
    StoreFoodsInfoDTO sample = StoreFoodsInfoDTO.sample();
    int storeId = sample.getStoreId();
    String response = "해당 음식점은 음식을 등록하지 않았습니다.";


    // when
    ResultActions resultActions =
        mockMvc.perform(MockMvcRequestBuilders.get("/info/foods").with(csrf())
            .contentType(MediaType.APPLICATION_JSON).param("storeId", String.valueOf(storeId)));

    // then
    resultActions.andExpect(status().isBadRequest()).andExpect(result -> {
      String responseBody = result.getResponse().getContentAsString();
      String utf8ResponseBody =
          new String(responseBody.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
      JsonNode jsonNode = new ObjectMapper().readTree(utf8ResponseBody);
      String errorMessage = jsonNode.get("message").asText();
      assertEquals(errorMessage, response);
    });
  }


  @Test
  @DisplayName("StoreInfoAPI : Update Foods Info Success")
  @WithMockUser(username = "0", roles = "OWNER")
  public void modFoodsInfoSuccess() throws Exception {

    // 사진 저장
    StoreFoodsInfoDTO sample = StoreFoodsInfoDTO.sample();
    MockMultipartFile foodsImageFile =
        new MockMultipartFile("foodImage", "food-image.png", "image/png", new byte[0]);
    sample.setFoodImage(foodsImageFile);
    int storeId = sample.getStoreId();
    String foodName = sample.getFoodName();
    Path filePath = Path.of(DIR_FOODS, String.valueOf(storeId) + "_" + foodName + ".png");
    Files.copy(sample.getFoodImage().getInputStream(), filePath,
        StandardCopyOption.REPLACE_EXISTING);

    // 정보 저장
    StoreFoodsInfoEntity storeFoodsInfoEntity =
        StoreFoodsInfoEntity.builder().storeId(sample.getStoreId()).foodName(sample.getFoodName())
            .foodDescription(sample.getFoodDescription()).imageURL(filePath.toString())
            .foodPrice(sample.getFoodPrice()).build();
    em.persist(storeFoodsInfoEntity);


    // 결과
    String response = "success";

    // when
    ResultActions resultActions = mockMvc.perform(
        MockMvcRequestBuilders.multipart("/info/foods").file(foodsImageFile).with(request -> {
          request.setMethod("PUT");
          return request;
        }).param("storeId", String.valueOf(sample.getStoreId()))
            .param("foodName", sample.getFoodName())
            .param("foodDescription", sample.getFoodDescription())
            .param("foodPrice", String.valueOf(sample.getFoodPrice())).with(csrf()));

    // then
    resultActions.andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().string(equalTo(response)));


    // 파일 삭제
    Files.delete(filePath);
  }

  @Test
  @DisplayName("StoreInfoAPI : Update Foods Info Fail : 권한 없음")
  @WithMockUser(username = "1", roles = "OWNER")
  public void modFoodsInfoFail() throws Exception {
    // given
    StoreFoodsInfoDTO sample = StoreFoodsInfoDTO.sample();
    MockMultipartFile foodsImageFile =
        new MockMultipartFile("foodImage", "food-image.png", "image/png", new byte[0]);
    String response = "권한이 없습니다.";

    // when
    ResultActions resultActions = mockMvc.perform(
        MockMvcRequestBuilders.multipart("/info/foods").file(foodsImageFile).with(request -> {
          request.setMethod("PUT");
          return request;
        }).param("storeId", String.valueOf(sample.getStoreId()))
            .param("foodName", sample.getFoodName())
            .param("foodDescription", sample.getFoodDescription())
            .param("foodPrice", String.valueOf(sample.getFoodPrice())).with(csrf()));

    // then
    resultActions.andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().string(equalTo(response)));

  }



  @Test
  @DisplayName("StoreInfoAPI : Delete Foods Info Success")
  @WithMockUser(username = "0", roles = "OWNER")
  public void deleteFoodsInfoSuccess() throws Exception {
    // given

    // 사진 저장
    StoreFoodsInfoDTO sample = StoreFoodsInfoDTO.sample();
    MockMultipartFile foodsImageFile =
        new MockMultipartFile("foodImage", "food-image.png", "image/png", new byte[0]);
    sample.setFoodImage(foodsImageFile);
    int storeId = sample.getStoreId();
    String foodName = sample.getFoodName();
    Path filePath = Path.of(DIR_FOODS, String.valueOf(storeId) + "_" + foodName + ".png");
    Files.copy(sample.getFoodImage().getInputStream(), filePath,
        StandardCopyOption.REPLACE_EXISTING);


    // 정보 저장
    StoreFoodsInfoEntity storeFoodsInfoEntity =
        StoreFoodsInfoEntity.builder().storeId(sample.getStoreId()).foodName(sample.getFoodName())
            .foodDescription(sample.getFoodDescription()).imageURL(filePath.toString())
            .foodPrice(sample.getFoodPrice()).build();
    em.persist(storeFoodsInfoEntity);



    String response = "success";



    // when
    ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.delete("/info/foods")
        .with(csrf()).contentType(MediaType.APPLICATION_JSON)
        .param("storeId", String.valueOf(storeId)).param("foodName", sample.getFoodName()));


    // then
    resultActions.andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().string(equalTo(response)));

  }


  @Test
  @DisplayName("StoreInfoAPI : Delete Foods Info Fail : 사진 없음")
  @WithMockUser(username = "0", roles = "OWNER")
  public void deleteFoodsInfoFail1() throws Exception {

    // 사진 저장 안함
    StoreFoodsInfoDTO sample = StoreFoodsInfoDTO.sample();
    MockMultipartFile foodsImageFile =
        new MockMultipartFile("foodImage", "food-image.png", "image/png", new byte[0]);
    sample.setFoodImage(foodsImageFile);
    int storeId = sample.getStoreId();
    String foodName = sample.getFoodName();
    Path filePath = Path.of(DIR_FOODS, String.valueOf(storeId) + "_" + foodName + ".png");

    // 정보 저장
    StoreFoodsInfoEntity storeFoodsInfoEntity =
        StoreFoodsInfoEntity.builder().storeId(sample.getStoreId()).foodName(sample.getFoodName())
            .foodDescription(sample.getFoodDescription()).imageURL(filePath.toString())
            .foodPrice(sample.getFoodPrice()).build();
    em.persist(storeFoodsInfoEntity);


    // given
    String response = "File Delete Failed";

    // when
    ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.delete("/info/foods")
        .with(csrf()).contentType(MediaType.APPLICATION_JSON)
        .param("storeId", String.valueOf(storeId)).param("foodName", sample.getFoodName()));

    // then
    resultActions.andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().string(equalTo(response)));

  }


  @Test
  @DisplayName("StoreInfoAPI : Delete Foods Info Fail : 권한 없음")
  @WithMockUser(username = "1", roles = "OWNER")
  public void deleteFoodsInfoFail2() throws Exception {
    // given
    int storeId = 0;
    String response = "권한이 없습니다.";

    // when
    ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.delete("/info/foods")
        .with(csrf()).contentType(MediaType.APPLICATION_JSON)
        .param("storeId", String.valueOf(storeId)).param("foodName", "foodName1"));

    // then
    resultActions.andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().string(equalTo(response)));

  }



  // Persist Time Info Method
  public void persistTimeInfo(StoreTimeInfoDTO sample) {
    StoreTimeInfoEntity parent =
        StoreTimeInfoEntity.builder().startTime(sample.getStartTime()).endTime(sample.getEndTime())
            .intervalTime(sample.getIntervalTime()).storeId(sample.getStoreId()).build();
    em.persist(parent);
    Set<StoreTimeInfoMapEntity> childs = new HashSet<>();

    for (String time : sample.getBreakTime()) {
      StoreTimeInfoMapEntity child =
          StoreTimeInfoMapEntity.builder().time(time).storeTimeInfoEntity(parent).build();
      em.persist(child);
      childs.add(child);
    }
    parent.setBreakTime(childs);
  }

  // Persist Day off Method
  public void persistDayOff(StoreRestDayDTO sample) {
    StoreRestDaysEntity parent = new StoreRestDaysEntity(sample.getStoreId());
    em.persist(parent);
    Set<StoreRestDaysMapEntity> childs = new HashSet<>();
    for (String date : sample.getDate()) {
      StoreRestDaysMapEntity child = new StoreRestDaysMapEntity(date, parent);
      em.persist(child);
      childs.add(child);
    }
    parent.setChildSet(childs);
  }
}
