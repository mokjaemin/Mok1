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
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.ReservationServer1.data.DTO.board.BoardDTO;
import com.ReservationServer1.data.DTO.board.BoardResultDTO;
import com.ReservationServer1.service.StoreBoardService;
import com.google.gson.Gson;

@WebMvcTest(StoreBoardController.class)
public class StoreBoardControllerTest {

  @MockBean
  private StoreBoardService storeBoardService;

  @Autowired
  private MockMvc mockMvc;

  // 1. Register Store Board
  @Test
  @DisplayName("가게 게시판 글 등록 성공")
  @WithMockUser(username = "0")
  void registerReservationSuccess() throws Exception {
    // given
    BoardDTO sample = BoardDTO.sample();
    MockMultipartFile foodImageFile = new MockMultipartFile("foodImage", "food-image.jpg",
        MediaType.IMAGE_JPEG_VALUE, new byte[0]);
    sample.setFoodImage(foodImageFile);
    String userId = "0";
    String response = "success";
    doReturn(response).when(storeBoardService).registerBoard(any(BoardDTO.class), anyString());

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
    resultActions.andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().string(equalTo(response)));

    // verify
    verify(storeBoardService, times(1)).registerBoard(any(BoardDTO.class), anyString());
    verify(storeBoardService, times(1)).registerBoard(sample, userId);
  }

  @Test
  @DisplayName("가게 게시판 글 등록 실패 : 잘못된 형식U")
  @WithMockUser(username = "0")
  void registerReservationFail() throws Exception {

    // when
    ResultActions resultActions =
        mockMvc.perform(MockMvcRequestBuilders.multipart("/board").with(request -> {
          request.setMethod("POST");
          return request;
        }).with(csrf()));

    // then
    resultActions.andExpect(status().isBadRequest());

    // verify
    verify(storeBoardService, never()).registerBoard(any(BoardDTO.class), anyString());
  }


  // 2. Update Store Board
  @Test
  @DisplayName("가게 게시판 글 수정 성공")
  @WithMockUser(username = "0")
  void modReservationSuccess() throws Exception {
    // given
    BoardDTO sample = BoardDTO.sample();
    MockMultipartFile foodImageFile = new MockMultipartFile("foodImage", "food-image.jpg",
        MediaType.IMAGE_JPEG_VALUE, new byte[0]);
    sample.setFoodImage(foodImageFile);
    Long boardId = 0L;
    String userId = "0";
    String response = "success";
    doReturn(response).when(storeBoardService).updateBoard(anyLong(), any(BoardDTO.class),
        anyString());

    // when
    ResultActions resultActions = mockMvc
        .perform(MockMvcRequestBuilders.multipart("/board").file(foodImageFile).with(request -> {
          request.setMethod("PUT");
          return request;
        }).param("storeId", String.valueOf(sample.getStoreId()))
            .param("title", String.valueOf(sample.getTitle()))
            .param("content", String.valueOf(sample.getContent()))
            .param("rating", String.valueOf(sample.getRating()))
            .param("boardId", String.valueOf(boardId)).with(csrf()));

    // then
    resultActions.andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().string(equalTo(response)));

    // verify
    verify(storeBoardService, times(1)).updateBoard(anyLong(), any(BoardDTO.class), anyString());
    verify(storeBoardService, times(1)).updateBoard(boardId, sample, userId);
  }

  @Test
  @DisplayName("가게 게시판 글 수정 실패 : 잘못된 입력")
  @WithMockUser(username = "0")
  void modReservationFail() throws Exception {
    // when
    ResultActions resultActions =
        mockMvc.perform(MockMvcRequestBuilders.multipart("/board").with(request -> {
          request.setMethod("PUT");
          return request;
        }).with(csrf()));

    // then
    resultActions.andExpect(status().isBadRequest());

    // verify
    verify(storeBoardService, never()).updateBoard(anyLong(), any(BoardDTO.class), anyString());
  }

  // 3. Delete Store Board
  @Test
  @DisplayName("가게 게시판 글 삭제 성공")
  @WithMockUser(username = "0")
  void deleteReservationSuccess() throws Exception {
    // given
    Long boardId = 0L;
    String userId = "0";
    String response = "success";
    doReturn(response).when(storeBoardService).deleteBoard(anyLong(), anyString());

    // when
    ResultActions resultActions =
        mockMvc.perform(MockMvcRequestBuilders.delete("/board").with(csrf())
            .contentType(MediaType.APPLICATION_JSON).param("boardId", String.valueOf(boardId)));

    // then
    resultActions.andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().string(equalTo(response)));

    // verify
    verify(storeBoardService, times(1)).deleteBoard(anyLong(), anyString());
    verify(storeBoardService, times(1)).deleteBoard(boardId, userId);
  }

  @Test
  @DisplayName("가게 게시판 글 수정 실패 : 잘못된 입력")
  @WithMockUser(username = "0")
  void deleteReservationFail() throws Exception {
    // when
    ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.delete("/board")
        .with(csrf()).contentType(MediaType.APPLICATION_JSON));

    // then
    resultActions.andExpect(status().isBadRequest());

    // verify
    verify(storeBoardService, never()).deleteBoard(anyLong(), anyString());
  }


  // 4. Get Store Board By Store
  @Test
  @DisplayName("가게별 게시판 글 출력 성공")
  @WithMockUser(username = "0")
  void getReservationStoreSuccess() throws Exception {
    // given
    int storeId = 0;
    List<BoardResultDTO> response = new ArrayList<>();
    response.add(BoardResultDTO.sample());
    doReturn(response).when(storeBoardService).getBoard(anyInt());

    // when
    ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/board").with(csrf())
        .contentType(MediaType.APPLICATION_JSON).param("storeId", String.valueOf(storeId)));

    // then
    resultActions.andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().json(new Gson().toJson(response)));

    // verify
    verify(storeBoardService, times(1)).getBoard(anyInt());
    verify(storeBoardService, times(1)).getBoard(storeId);
  }

  @Test
  @DisplayName("가게별 게시판 글 출력 실패 : 잘못된 입력")
  @WithMockUser(username = "0")
  void getReservationStoreFail() throws Exception {
    // when
    ResultActions resultActions = mockMvc.perform(
        MockMvcRequestBuilders.get("/board").with(csrf()).contentType(MediaType.APPLICATION_JSON));

    // then
    resultActions.andExpect(status().isBadRequest());

    // verify
    verify(storeBoardService, never()).getBoard(anyInt());
  }


  // 5. Get Store Board By User
  @Test
  @DisplayName("가게별 게시판 글 출력 성공")
  @WithMockUser(username = "0")
  void getReservationUserSuccess() throws Exception {
    // given
    List<BoardResultDTO> response = new ArrayList<>();
    response.add(BoardResultDTO.sample());
    doReturn(response).when(storeBoardService).getBoardByUser(anyString());

    // when
    ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/board/user")
        .with(csrf()).contentType(MediaType.APPLICATION_JSON));

    // then
    resultActions.andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().json(new Gson().toJson(response)));

    // verify
    verify(storeBoardService, times(1)).getBoardByUser(anyString());
    verify(storeBoardService, times(1)).getBoardByUser("0");
  }


  // 6. Register Store Board Comment
  @Test
  @DisplayName("가게별 게시판 댓글 등록 성공")
  @WithMockUser(username = "0")
  void registerReservationCommentSuccess() throws Exception {
    // given
    int storeId = 0;
    Long boardId = 0L;
    String comment = "comment";
    String response = "success";
    doReturn(response).when(storeBoardService).registerBoardComment(anyLong(), anyString(),
        anyString());

    // when
    ResultActions resultActions =
        mockMvc.perform(MockMvcRequestBuilders.post("/board/comment").with(csrf())
            .contentType(MediaType.APPLICATION_JSON).param("boardId", String.valueOf(boardId))
            .contentType(MediaType.APPLICATION_JSON).content("{\"comment\": \"" + comment + "\"}"));


    // then
    resultActions.andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().string(equalTo(response)));

    // verify
    verify(storeBoardService, times(1)).registerBoardComment(anyLong(), anyString(), anyString());
    verify(storeBoardService, times(1)).registerBoardComment(boardId, comment,
        String.valueOf(storeId));
  }

  @Test
  @DisplayName("가게별 게시판 댓글 등록 실패 : 잘못된 형식")
  @WithMockUser(username = "0")
  void registerReservationCommentFail() throws Exception {

    // when
    ResultActions resultActions =
        mockMvc.perform(MockMvcRequestBuilders.post("/board/comment").with(csrf()));


    // then
    resultActions.andExpect(status().isBadRequest());

    // verify
    verify(storeBoardService, never()).registerBoardComment(anyLong(), anyString(), anyString());
  }


  // 7. Update Store Board Comment
  @Test
  @DisplayName("가게별 게시판 댓글 수정 성공")
  @WithMockUser(username = "0")
  void modReservationCommentSuccess() throws Exception {
    // given
    int storeId = 0;
    Long boardId = 0L;
    String comment = "comment";
    String response = "success";
    doReturn(response).when(storeBoardService).updateBoardComment(anyLong(), anyString(),
        anyString());

    // when
    ResultActions resultActions =
        mockMvc.perform(MockMvcRequestBuilders.put("/board/comment").with(csrf())
            .contentType(MediaType.APPLICATION_JSON).param("boardId", String.valueOf(boardId))
            .contentType(MediaType.APPLICATION_JSON).content("{\"comment\": \"" + comment + "\"}"));


    // then
    resultActions.andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().string(equalTo(response)));

    // verify
    verify(storeBoardService, times(1)).updateBoardComment(anyLong(), anyString(), anyString());
    verify(storeBoardService, times(1)).updateBoardComment(boardId, comment,
        String.valueOf(storeId));
  }

  @Test
  @DisplayName("가게별 게시판 댓글 수정 실패 : 잘못된 형식")
  @WithMockUser(username = "0")
  void modReservationCommentFail() throws Exception {

    // when
    ResultActions resultActions =
        mockMvc.perform(MockMvcRequestBuilders.put("/board/comment").with(csrf()));


    // then
    resultActions.andExpect(status().isBadRequest());

    // verify
    verify(storeBoardService, never()).updateBoardComment(anyLong(), anyString(), anyString());
  }


  // 8. Delete Store Board Comment
  @Test
  @DisplayName("가게별 게시판 댓글 삭제 성공")
  @WithMockUser(username = "0")
  void deleteReservationCommentSuccess() throws Exception {
    // given
    int storeId = 0;
    Long boardId = 0L;
    String response = "success";
    doReturn(response).when(storeBoardService).deleteBoardComment(anyLong(), anyString());

    // when
    ResultActions resultActions =
        mockMvc.perform(MockMvcRequestBuilders.delete("/board/comment").with(csrf())
            .contentType(MediaType.APPLICATION_JSON).param("boardId", String.valueOf(boardId)));


    // then
    resultActions.andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().string(equalTo(response)));

    // verify
    verify(storeBoardService, times(1)).deleteBoardComment(anyLong(), anyString());
    verify(storeBoardService, times(1)).deleteBoardComment(boardId, String.valueOf(storeId));
  }

  @Test
  @DisplayName("가게별 게시판 댓글 삭제 실패 : 잘못된 형식")
  @WithMockUser(username = "0")
  void deleteReservationCommentFail() throws Exception {
    
    // when
    ResultActions resultActions =
        mockMvc.perform(MockMvcRequestBuilders.delete("/board/comment").with(csrf()));


    // then
    resultActions.andExpect(status().isBadRequest());

    // verify
    verify(storeBoardService, never()).deleteBoardComment(anyLong(), anyString());
  }

}
