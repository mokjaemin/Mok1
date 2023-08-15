package com.ReservationServer1.Integration;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
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
import com.ReservationServer1.data.DTO.board.BoardDTO;
import com.ReservationServer1.data.Entity.POR.StoreReservationEntity;
import com.ReservationServer1.data.Entity.board.StoreBoardEntity;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Transactional
public class StoreBoardAPITest {

  @Autowired
  protected MockMvc mockMvc;

  @Autowired
  protected EntityManager em;

  private String DIR_BOARD = "/Users/mokjaemin/Desktop/Mok1/storeBoard/";

  @Test
  @DisplayName("StoreInfoAPI : Register Board Success")
  @WithMockUser(username = "userId", roles = "USER")
  public void registerBoardSuccess() throws Exception {
    // given
    makeReservation();
    BoardDTO sample = BoardDTO.sample();
    MockMultipartFile foodImageFile =
        new MockMultipartFile("foodImage", "food-image.png", "image/png", new byte[0]);
    sample.setFoodImage(foodImageFile);

    // when
    ResultActions resultActions = mockMvc
        .perform(MockMvcRequestBuilders.multipart("/board").file(foodImageFile).with(request -> {
          request.setMethod("POST");
          return request;
        }).param("storeId", String.valueOf(sample.getStoreId()))
            .param("title", String.valueOf(sample.getTitle()))
            .param("content", String.valueOf(sample.getContent()))
            .param("rating", String.valueOf(sample.getRating())).with(csrf()));


    // then
    resultActions.andExpect(status().isOk()).andExpect(result -> {
      String responseBody = result.getResponse().getContentAsString();
      // 사진 삭제
      Path filePath = Path.of(DIR_BOARD, String.valueOf(responseBody) + ".png");
      Files.delete(filePath);
    });

  }


  @Test
  @DisplayName("StoreInfoAPI : Register Board Fail : 방문 기록 없음")
  @WithMockUser(username = "userId", roles = "USER")
  public void registerBoardFail() throws Exception {
    // given
    BoardDTO sample = BoardDTO.sample();
    MockMultipartFile foodImageFile =
        new MockMultipartFile("foodImage", "food-image.png", "image/png", new byte[0]);
    sample.setFoodImage(foodImageFile);
    String response = "방문 기록이 존재하지 않습니다.: " + "userId";

    // when
    ResultActions resultActions = mockMvc
        .perform(MockMvcRequestBuilders.multipart("/board").file(foodImageFile).with(request -> {
          request.setMethod("POST");
          return request;
        }).param("storeId", String.valueOf(sample.getStoreId()))
            .param("title", String.valueOf(sample.getTitle()))
            .param("content", String.valueOf(sample.getContent()))
            .param("rating", String.valueOf(sample.getRating())).with(csrf()));


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
  @DisplayName("StoreInfoAPI : Update Board Success")
  @WithMockUser(username = "userId", roles = "USER")
  public void modBoardSuccess() throws Exception {
    // given
    BoardDTO sample = BoardDTO.sample();
    StoreBoardEntity entity = makeBoard(sample);
    MockMultipartFile foodImageFile =
        new MockMultipartFile("foodImage", "food-image.png", "image/png", new byte[0]);
    sample.setFoodImage(foodImageFile);
    String response = "success";

    // when
    ResultActions resultActions = mockMvc
        .perform(MockMvcRequestBuilders.multipart("/board").file(foodImageFile).with(request -> {
          request.setMethod("PUT");
          return request;
        }).param("storeId", String.valueOf(sample.getStoreId()))
            .param("title", String.valueOf(sample.getTitle()))
            .param("content", String.valueOf(sample.getContent()))
            .param("rating", String.valueOf(sample.getRating()))
            .param("boardId", String.valueOf(entity.getBoardId())).with(csrf()));


    // then
    resultActions.andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().string(equalTo(response)));

    // 사진 삭제
    Path filePath = Path.of(DIR_BOARD, String.valueOf(entity.getBoardId()) + ".png");
    Files.delete(filePath);

  }


  @Test
  @DisplayName("StoreInfoAPI : Update Board Fail : 권한 없음")
  @WithMockUser(username = "wrongId", roles = "USER")
  public void modBoardFail() throws Exception {
    // given
    BoardDTO sample = BoardDTO.sample();
    StoreBoardEntity entity = makeBoard(sample);
    MockMultipartFile foodImageFile =
        new MockMultipartFile("foodImage", "food-image.png", "image/png", new byte[0]);
    sample.setFoodImage(foodImageFile);
    String response = "권한이 없습니다.: " + "wrongId";

    // when
    ResultActions resultActions = mockMvc
        .perform(MockMvcRequestBuilders.multipart("/board").file(foodImageFile).with(request -> {
          request.setMethod("PUT");
          return request;
        }).param("storeId", String.valueOf(sample.getStoreId()))
            .param("title", String.valueOf(sample.getTitle()))
            .param("content", String.valueOf(sample.getContent()))
            .param("rating", String.valueOf(sample.getRating()))
            .param("boardId", String.valueOf(entity.getBoardId())).with(csrf()));


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
  @DisplayName("StoreInfoAPI : Delete Board Success")
  @WithMockUser(username = "userId", roles = "USER")
  public void deleteBoardSuccess() throws Exception {
    // given
    BoardDTO sample = BoardDTO.sample();
    StoreBoardEntity entity = makeBoard(sample);
    MockMultipartFile foodImageFile =
        new MockMultipartFile("foodImage", "food-image.png", "image/png", new byte[0]);
    sample.setFoodImage(foodImageFile);
    String response = "success";

    // 사진 저장
    Path filePath = Path.of(DIR_BOARD, String.valueOf(entity.getBoardId()) + ".png");
    Files.copy(foodImageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

    // when
    ResultActions resultActions = mockMvc.perform(
        MockMvcRequestBuilders.delete("/board").with(csrf()).contentType(MediaType.APPLICATION_JSON)
            .param("boardId", String.valueOf(entity.getBoardId())));


    // then
    resultActions.andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().string(equalTo(response)));
  }

  @Test
  @DisplayName("StoreInfoAPI : Delete Board Fail : 권한 없음")
  @WithMockUser(username = "wrongId", roles = "USER")
  public void deleteBoardFail() throws Exception {
    // given
    BoardDTO sample = BoardDTO.sample();
    StoreBoardEntity entity = makeBoard(sample);
    String response = "권한이 없습니다.: " + "wrongId";


    // when
    ResultActions resultActions = mockMvc.perform(
        MockMvcRequestBuilders.delete("/board").with(csrf()).contentType(MediaType.APPLICATION_JSON)
            .param("boardId", String.valueOf(entity.getBoardId())));


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


//  @Test
//  @DisplayName("StoreInfoAPI : Get Board By Store Success")
//  @WithMockUser(username = "userId", roles = "USER")
//  public void getBoardStoreSuccess() throws Exception {
//    // given
//    BoardDTO sample = BoardDTO.sample();
//    StoreBoardEntity entity = makeBoard(sample);
//    MockMultipartFile foodImageFile =
//        new MockMultipartFile("foodImage", "food-image.png", "image/png", new byte[0]);
//    sample.setFoodImage(foodImageFile);
//
//    // 사진 저장
//    Path filePath = Path.of(DIR_BOARD, String.valueOf(entity.getBoardId()) + ".png");
//    Files.copy(foodImageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
//
//    // when
//    ResultActions resultActions = mockMvc.perform(
//        MockMvcRequestBuilders.get("/board").with(csrf()).contentType(MediaType.APPLICATION_JSON)
//            .param("storeId", String.valueOf(entity.getStoreId())));
//
//
//    // then
//    resultActions.andExpect(status().isOk());
//    Files.delete(filePath);
//  }

//  @Test
//  @DisplayName("StoreInfoAPI : Get Board By User Success")
//  @WithMockUser(username = "userId", roles = "USER")
//  public void getBoardUserSuccess() throws Exception {
//    // given
//    BoardDTO sample = BoardDTO.sample();
//    StoreBoardEntity entity = makeBoard(sample);
//    MockMultipartFile foodImageFile =
//        new MockMultipartFile("foodImage", "food-image.png", "image/png", new byte[0]);
//    sample.setFoodImage(foodImageFile);
//
//    // 사진 저장
//    Path filePath = Path.of(DIR_BOARD, String.valueOf(entity.getBoardId()) + ".png");
//    Files.copy(foodImageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
//
//    // when
//    ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/board/user")
//        .with(csrf()).contentType(MediaType.APPLICATION_JSON));
//
//    // then
//    resultActions.andExpect(status().isOk());
//    Files.delete(filePath);
//  }


  @Test
  @DisplayName("StoreInfoAPI : Register Board Comment Success")
  @WithMockUser(username = "0", roles = "OWNER")
  public void registerBoardCommentSuccess() throws Exception {
    // given
    BoardDTO sample = BoardDTO.sample();
    StoreBoardEntity entity = makeBoard(sample);
    String comment = "comment";
    String response = "success";

    // when
    ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/board/comment")
        .param("boardId", String.valueOf(entity.getBoardId()))
        .content("{\"comment\": \"" + comment + "\"}").with(csrf())
        .contentType(MediaType.APPLICATION_JSON));

    // then
    resultActions.andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().string(equalTo(response)));
  }

  @Test
  @DisplayName("StoreInfoAPI : Register Board Comment Fail : 권한 없음")
  @WithMockUser(username = "1", roles = "OWNER")
  public void registerBoardCommentFail() throws Exception {
    // given
    BoardDTO sample = BoardDTO.sample();
    StoreBoardEntity entity = makeBoard(sample);
    String comment = "comment";
    String response = "권한이 없습니다.: " + "1";

    // when
    ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/board/comment")
        .param("boardId", String.valueOf(entity.getBoardId()))
        .content("{\"comment\": \"" + comment + "\"}").with(csrf())
        .contentType(MediaType.APPLICATION_JSON));

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
  @DisplayName("StoreInfoAPI : Update Board Comment Success")
  @WithMockUser(username = "0", roles = "OWNER")
  public void modBoardCommentSuccess() throws Exception {
    // given
    BoardDTO sample = BoardDTO.sample();
    StoreBoardEntity entity = makeBoard(sample);
    String comment = "comment";
    String response = "success";

    // when
    ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.put("/board/comment")
        .param("boardId", String.valueOf(entity.getBoardId()))
        .content("{\"comment\": \"" + comment + "\"}").with(csrf())
        .contentType(MediaType.APPLICATION_JSON));

    // then
    resultActions.andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().string(equalTo(response)));
  }

  @Test
  @DisplayName("StoreInfoAPI : Update Board Comment Fail : 권한 없음")
  @WithMockUser(username = "1", roles = "OWNER")
  public void modBoardCommentFail() throws Exception {
    // given
    BoardDTO sample = BoardDTO.sample();
    StoreBoardEntity entity = makeBoard(sample);
    String comment = "comment";
    String response = "권한이 없습니다.: " + "1";

    // when
    ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.put("/board/comment")
        .param("boardId", String.valueOf(entity.getBoardId()))
        .content("{\"comment\": \"" + comment + "\"}").with(csrf())
        .contentType(MediaType.APPLICATION_JSON));

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
  @DisplayName("StoreInfoAPI : Delete Board Comment Success")
  @WithMockUser(username = "0", roles = "OWNER")
  public void deleteBoardCommentSuccess() throws Exception {
    // given
    BoardDTO sample = BoardDTO.sample();
    StoreBoardEntity entity = makeBoard(sample);
    String comment = "comment";
    String response = "success";

    // when
    ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.delete("/board/comment")
        .param("boardId", String.valueOf(entity.getBoardId())).with(csrf())
        .contentType(MediaType.APPLICATION_JSON));

    // then
    resultActions.andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().string(equalTo(response)));
  }

  @Test
  @DisplayName("StoreInfoAPI : Delete Board Comment Fail : 권한 없음")
  @WithMockUser(username = "1", roles = "OWNER")
  public void deleteBoardCommentFail() throws Exception {
    // given
    BoardDTO sample = BoardDTO.sample();
    StoreBoardEntity entity = makeBoard(sample);
    String response = "권한이 없습니다.: " + "1";

    // when
    ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.delete("/board/comment")
        .param("boardId", String.valueOf(entity.getBoardId())).with(csrf())
        .contentType(MediaType.APPLICATION_JSON));

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



  // Persist Board
  public StoreBoardEntity makeBoard(BoardDTO boardDTO) {
    StoreBoardEntity entity =
        StoreBoardEntity.builder().storeId(boardDTO.getStoreId()).title(boardDTO.getTitle())
            .content(boardDTO.getContent()).userId("userId").rating(boardDTO.getRating()).build();
    em.persist(entity);
    entity.setImageURL(DIR_BOARD + String.valueOf(entity.getBoardId()) + ".png");
    return entity;
  }

  // Persist Reservation
  public StoreReservationEntity makeReservation() {
    StoreReservationEntity entity = StoreReservationEntity.builder().userId("userId").storeId(0)
        .date("1월1일").time("1시").storeTable("1번").build();
    em.persist(entity);
    return entity;
  }

}
