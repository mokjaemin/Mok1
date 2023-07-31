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
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import com.ReservationServer1.DAO.StoreBoardDAO;
import com.ReservationServer1.data.DTO.board.BoardDTO;
import com.ReservationServer1.data.DTO.board.BoardResultDTO;
import com.ReservationServer1.data.Entity.board.StoreBoardEntity;
import com.ReservationServer1.service.Impl.StoreBoardServiceImpl;

@ExtendWith(MockitoExtension.class)
public class StoreBoardServiceTest {


  @InjectMocks
  private StoreBoardServiceImpl storeBoardServiceImpl;

  @Mock
  private StoreBoardDAO storeBoardDAO;

  private static final String DIR_BOARD = "/Users/mokjaemin/Desktop/Mok1/storeBoard/";


  // 1. Register Board
  @Test
  @DisplayName("가게 게시판 등록 성공")
  void registerBoardSuccess() throws Exception {

    // given
    BoardDTO sample = BoardDTO.sample();
    MockMultipartFile foodImageFile =
        new MockMultipartFile("foodImage", "food-image.png", "image/png", new byte[0]);
    sample.setFoodImage(foodImageFile);
    String userId = "userId";

    String boardId = "-1";
    doReturn(boardId).when(storeBoardDAO).registerBoard(any(BoardDTO.class), anyString(),
        anyString());

    String response = "success";

    // when
    String result = storeBoardServiceImpl.registerBoard(sample, userId);

    // then
    assertEquals(result, response);

    // verify
    verify(storeBoardDAO, times(1)).registerBoard(any(BoardDTO.class), anyString(), anyString());
    verify(storeBoardDAO, times(1)).registerBoard(sample, userId, DIR_BOARD);
  }



  // 2. Update Board
  @Test
  @DisplayName("가게 게시판 수정 성공")
  void modBoardSuccess() throws Exception {

    // given
    Long boardId = -1L;
    BoardDTO sample = BoardDTO.sample();
    MockMultipartFile foodImageFile =
        new MockMultipartFile("foodImage", "food-image.png", "image/png", new byte[0]);
    sample.setFoodImage(foodImageFile);
    String userId = "userId";
    String response = "success";

    // when
    String result = storeBoardServiceImpl.updateBoard(boardId, sample, userId);

    // then
    assertEquals(result, response);

    // verify
    verify(storeBoardDAO, times(1)).updateBoard(anyLong(), any(BoardDTO.class), anyString());
    verify(storeBoardDAO, times(1)).updateBoard(boardId, sample, userId);
  }

  // 3. Delete Board
  @Test
  @DisplayName("가게 게시판 삭제 성공")
  void deleteBoardSuccess() throws Exception {

    // given
    Long boardId = -1L;
    String userId = "userId";
    String response = "success";
    doReturn(boardId).when(storeBoardDAO).deleteBoard(anyLong(), anyString());

    // when
    String result = storeBoardServiceImpl.deleteBoard(boardId, userId);

    // then
    assertEquals(result, response);

    // verify
    verify(storeBoardDAO, times(1)).deleteBoard(anyLong(), anyString());
    verify(storeBoardDAO, times(1)).deleteBoard(boardId, userId);
  }

  // 4. Get Board By Store
  @Test
  @DisplayName("가게 게시판 가게별 출력 성공")
  void getBoardStoreSuccess() throws Exception {

    // given
    int storeId = 0;
    List<StoreBoardEntity> result = new ArrayList<>();
    StoreBoardEntity sample = StoreBoardEntity.sample();
    sample.setImageURL(DIR_BOARD + "-1" + ".png");
    result.add(sample);

    doReturn(result).when(storeBoardDAO).getBoard(anyInt());

    // when
    List<BoardResultDTO> response = storeBoardServiceImpl.getBoard(storeId);

    // then
    assertThat(sample.getBoardId(), is(response.get(0).getBoardId()));
    assertThat(sample.getStoreId(), is(response.get(0).getStoreId()));
    assertEquals(sample.getUserId(), response.get(0).getUserId());
    assertEquals(sample.getTitle(), response.get(0).getTitle());
    assertEquals(sample.getContent(), response.get(0).getContent());
    assertEquals(sample.getComment(), response.get(0).getComment());
    assertThat(sample.getRating(), is(response.get(0).getRating()));

    // verify
    verify(storeBoardDAO, times(1)).getBoard(anyInt());
    verify(storeBoardDAO, times(1)).getBoard(storeId);
  }

  // 5. Get Board By User
  @Test
  @DisplayName("가게 게시판 개인별 출력 성공")
  void getBoardUserSuccess() throws Exception {

    // given
    String userId = "userId";
    List<StoreBoardEntity> result = new ArrayList<>();
    StoreBoardEntity sample = StoreBoardEntity.sample();
    sample.setImageURL(DIR_BOARD + "-1" + ".png");
    result.add(sample);

    doReturn(result).when(storeBoardDAO).getBoardById(anyString());

    // when
    List<BoardResultDTO> response = storeBoardServiceImpl.getBoardByUser(userId);

    // then
    assertThat(sample.getBoardId(), is(response.get(0).getBoardId()));
    assertThat(sample.getStoreId(), is(response.get(0).getStoreId()));
    assertEquals(sample.getUserId(), response.get(0).getUserId());
    assertEquals(sample.getTitle(), response.get(0).getTitle());
    assertEquals(sample.getContent(), response.get(0).getContent());
    assertEquals(sample.getComment(), response.get(0).getComment());
    assertThat(sample.getRating(), is(response.get(0).getRating()));

    // verify
    verify(storeBoardDAO, times(1)).getBoardById(anyString());
    verify(storeBoardDAO, times(1)).getBoardById(userId);
  }

  // 6. Register Board Comment
  @Test
  @DisplayName("가게 게시판 댓글 등록 성공")
  void registeBoardCommentSuccess() throws Exception {

    // given
    Long boardId = -1L;
    String comment = "comment";
    String storeId = "storeId";
    String response = "success";
    doReturn(response).when(storeBoardDAO).registerBoardComment(anyLong(), anyString(),
        anyString());

    // when
    String result = storeBoardServiceImpl.registerBoardComment(boardId, comment, storeId);

    // then
    assertEquals(result, response);

    // verify
    verify(storeBoardDAO, times(1)).registerBoardComment(anyLong(), anyString(), anyString());
    verify(storeBoardDAO, times(1)).registerBoardComment(boardId, comment, storeId);
  }


  // 7. Update Board Comment
  @Test
  @DisplayName("가게 게시판 댓글 수정 성공")
  void updateBoardCommentSuccess() throws Exception {

    // given
    Long boardId = -1L;
    String comment = "comment";
    String storeId = "storeId";
    String response = "success";
    doReturn(response).when(storeBoardDAO).updateBoardComment(anyLong(), anyString(),
        anyString());

    // when
    String result = storeBoardServiceImpl.updateBoardComment(boardId, comment, storeId);

    // then
    assertEquals(result, response);

    // verify
    verify(storeBoardDAO, times(1)).updateBoardComment(anyLong(), anyString(), anyString());
    verify(storeBoardDAO, times(1)).updateBoardComment(boardId, comment, storeId);
  }

  // 8. Delete Board Comment
  @Test
  @DisplayName("가게 게시판 댓글 삭제 성공")
  void deleteBoardCommentSuccess() throws Exception {

    // given
    Long boardId = -1L;
    String storeId = "storeId";
    String response = "success";
    doReturn(response).when(storeBoardDAO).deleteBoardComment(anyLong(), anyString());

    // when
    String result = storeBoardServiceImpl.deleteBoardComment(boardId, storeId);

    // then
    assertEquals(result, response);

    // verify
    verify(storeBoardDAO, times(1)).deleteBoardComment(anyLong(), anyString());
    verify(storeBoardDAO, times(1)).deleteBoardComment(boardId, storeId);
  }


}
