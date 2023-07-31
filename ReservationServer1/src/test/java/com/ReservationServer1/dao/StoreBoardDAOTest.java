package com.ReservationServer1.dao;


import static com.ReservationServer1.data.Entity.POR.QStoreReservationEntity.storeReservationEntity;
import static com.ReservationServer1.data.Entity.board.QStoreBoardEntity.storeBoardEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.ReservationServer1.DAO.Impl.StoreBoardDAOImpl;
import com.ReservationServer1.data.DTO.board.BoardDTO;
import com.ReservationServer1.data.Entity.POR.StoreReservationEntity;
import com.ReservationServer1.data.Entity.board.StoreBoardEntity;
import com.ReservationServer1.exception.MessageException;
import com.querydsl.jpa.impl.JPADeleteClause;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

public class StoreBoardDAOTest {

  @Mock
  private JPAQueryFactory queryFactory;

  @InjectMocks
  private StoreBoardDAOImpl storeBoardDAOImpl;

  @Mock
  private EntityManager entityManager;

  @Mock
  private JPAQuery<StoreReservationEntity> jpaQueryReservation;

  @Mock
  private JPAQuery<StoreBoardEntity> jpaQueryBoard;

  @Mock
  private JPADeleteClause jpaDeleteClause;



  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  // 1. Register Board
  @Test
  @DisplayName("가게 게시판 등록 성공")
  public void registerBoardSuccess() {
    // given
    BoardDTO sample = BoardDTO.sample();
    String userId = "userId";
    String imageURL = "imageURL";
    Long reservationId = 0L;

    doReturn(jpaQueryReservation).when(queryFactory).select(storeReservationEntity.reservationId);
    doReturn(jpaQueryReservation).when(jpaQueryReservation).from(storeReservationEntity);
    doReturn(jpaQueryReservation).when(jpaQueryReservation)
        .where(storeReservationEntity.userId.eq(userId));
    doReturn(jpaQueryReservation).when(jpaQueryReservation).limit(1);
    doReturn(reservationId).when(jpaQueryReservation).fetchOne();

    doNothing().when(entityManager).persist(any(StoreBoardEntity.class));


    // when
    String result = storeBoardDAOImpl.registerBoard(sample, userId, imageURL);

    // then
    assertNotNull(result);
    verify(entityManager, times(1)).persist(any(StoreBoardEntity.class));
  }

  @Test
  @DisplayName("가게 게시판 등록 실패 : 정보 없음")
  public void registerBoardFail() {
    // given
    BoardDTO sample = BoardDTO.sample();
    String userId = "userId";
    String imageURL = "imageURL";

    doReturn(jpaQueryReservation).when(queryFactory).select(storeReservationEntity.reservationId);
    doReturn(jpaQueryReservation).when(jpaQueryReservation).from(storeReservationEntity);
    doReturn(jpaQueryReservation).when(jpaQueryReservation)
        .where(storeReservationEntity.userId.eq(userId));
    doReturn(jpaQueryReservation).when(jpaQueryReservation).limit(1);
    doReturn(null).when(jpaQueryReservation).fetchOne();


    // then && when
    MessageException message = assertThrows(MessageException.class, () -> {
      storeBoardDAOImpl.registerBoard(sample, userId, imageURL);
    });
    String expected = "방문 기록이 존재하지 않습니다.: " + userId;
    assertEquals(expected, message.getMessage());
    verify(entityManager, never()).persist(any(StoreBoardEntity.class));
  }


  // 2. Update Board
  @Test
  @DisplayName("가게 게시판 수정 성공")
  public void updateBoardSuccess() {
    // given
    Long boardId = 0L;
    String userId = "userId";
    BoardDTO boardDTO = new BoardDTO();
    boardDTO.setTitle("Updated Title");
    boardDTO.setContent("Updated Content");
    boardDTO.setRating(4);

    StoreBoardEntity entity = new StoreBoardEntity();
    entity.setBoardId(boardId);
    entity.setUserId(userId);
    entity.setTitle("Original Title");
    entity.setContent("Original Content");
    entity.setRating(5);


    doReturn(jpaQueryBoard).when(queryFactory).select(storeBoardEntity);
    doReturn(jpaQueryBoard).when(jpaQueryBoard).from(storeBoardEntity);
    doReturn(jpaQueryBoard).when(jpaQueryBoard).where(storeBoardEntity.boardId.eq(boardId));
    doReturn(jpaQueryBoard).when(jpaQueryBoard).limit(1);
    doReturn(entity).when(jpaQueryBoard).fetchOne();

    // when
    String result = storeBoardDAOImpl.updateBoard(boardId, boardDTO, userId);

    // Then
    assertEquals("success", result);
    assertEquals(boardDTO.getTitle(), entity.getTitle());
    assertEquals(boardDTO.getContent(), entity.getContent());
    assertEquals(boardDTO.getRating(), entity.getRating());



  }

  @Test
  @DisplayName("가게 게시판 수정 실패 : 권한 없음")
  public void updateBoardFail() {
    // given
    Long boardId = 0L;
    String userId = "userId";
    BoardDTO boardDTO = new BoardDTO();
    boardDTO.setTitle("Updated Title");
    boardDTO.setContent("Updated Content");
    boardDTO.setRating(4);

    StoreBoardEntity entity = new StoreBoardEntity();
    entity.setUserId("wrongId");


    doReturn(jpaQueryBoard).when(queryFactory).select(storeBoardEntity);
    doReturn(jpaQueryBoard).when(jpaQueryBoard).from(storeBoardEntity);
    doReturn(jpaQueryBoard).when(jpaQueryBoard).where(storeBoardEntity.boardId.eq(boardId));
    doReturn(jpaQueryBoard).when(jpaQueryBoard).limit(1);
    doReturn(entity).when(jpaQueryBoard).fetchOne();

    // then && when
    MessageException message = assertThrows(MessageException.class, () -> {
      storeBoardDAOImpl.updateBoard(boardId, boardDTO, userId);
    });
    String expected = "권한이 없습니다.: " + userId;
    assertEquals(expected, message.getMessage());
  }


  // 3. Delete Board
  @Test
  @DisplayName("가게 게시판 삭제 성공")
  public void deleteBoardSuccess() {
    // given
    Long boardId = 0L;
    String userId = "userId";
    StoreBoardEntity entity = new StoreBoardEntity();
    entity.setUserId(userId);


    doReturn(jpaQueryBoard).when(queryFactory).select(storeBoardEntity);
    doReturn(jpaQueryBoard).when(jpaQueryBoard).from(storeBoardEntity);
    doReturn(jpaQueryBoard).when(jpaQueryBoard).where(storeBoardEntity.boardId.eq(boardId));
    doReturn(jpaQueryBoard).when(jpaQueryBoard).limit(1);
    doReturn(entity).when(jpaQueryBoard).fetchOne();


    doReturn(jpaDeleteClause).when(queryFactory).delete(storeBoardEntity);
    doReturn(jpaDeleteClause).when(jpaDeleteClause).where(storeBoardEntity.boardId.eq(boardId));



    // when
    Long result = storeBoardDAOImpl.deleteBoard(boardId, userId);

    // Then
    assertEquals(boardId, result);

  }

  @Test
  @DisplayName("가게 게시판 삭제 실패 : 권한 없음")
  public void deleteBoardFail() {
    // given
    Long boardId = 0L;
    String userId = "userId";
    StoreBoardEntity entity = new StoreBoardEntity();
    entity.setUserId("wrongId");


    doReturn(jpaQueryBoard).when(queryFactory).select(storeBoardEntity);
    doReturn(jpaQueryBoard).when(jpaQueryBoard).from(storeBoardEntity);
    doReturn(jpaQueryBoard).when(jpaQueryBoard).where(storeBoardEntity.boardId.eq(boardId));
    doReturn(jpaQueryBoard).when(jpaQueryBoard).limit(1);
    doReturn(entity).when(jpaQueryBoard).fetchOne();

    // then && when
    MessageException message = assertThrows(MessageException.class, () -> {
      storeBoardDAOImpl.deleteBoard(boardId, userId);
    });
    String expected = "권한이 없습니다.: " + userId;
    assertEquals(expected, message.getMessage());
  }



  // 4. Get Board By Store
  @Test
  @DisplayName("가게별 게시판 출력 성공")
  public void getBoardStoreSuccess() {
    // given
    int storeId = 0;
    List<StoreBoardEntity> response = new ArrayList<>();
    StoreBoardEntity sample = StoreBoardEntity.sample();
    response.add(sample);


    doReturn(jpaQueryBoard).when(queryFactory).select(storeBoardEntity);
    doReturn(jpaQueryBoard).when(jpaQueryBoard).from(storeBoardEntity);
    doReturn(jpaQueryBoard).when(jpaQueryBoard).where(storeBoardEntity.storeId.eq(storeId));
    doReturn(response).when(jpaQueryBoard).fetch();

    // when
    List<StoreBoardEntity> result = storeBoardDAOImpl.getBoard(storeId);

    // Then
    assertEquals(response, result);

  }

  // 5. Get Board By User
  @Test
  @DisplayName("개인별 게시판 출력 성공")
  public void getBoardUserSuccess() {
    // given
    String userId = "userId";
    List<StoreBoardEntity> response = new ArrayList<>();
    StoreBoardEntity sample = StoreBoardEntity.sample();
    response.add(sample);


    doReturn(jpaQueryBoard).when(queryFactory).select(storeBoardEntity);
    doReturn(jpaQueryBoard).when(jpaQueryBoard).from(storeBoardEntity);
    doReturn(jpaQueryBoard).when(jpaQueryBoard).where(storeBoardEntity.userId.eq(userId));
    doReturn(response).when(jpaQueryBoard).fetch();

    // when
    List<StoreBoardEntity> result = storeBoardDAOImpl.getBoardById(userId);

    // Then
    assertEquals(response, result);

  }

  // 6. Register Board Comment
  @Test
  @DisplayName("게시판 댓글 등록 성공")
  public void registerBoardCommentSuccess() {
    // given
    Long boardId = 0L;
    String comment = "new_comment";
    String storeId = "0";
    StoreBoardEntity sample = StoreBoardEntity.sample();
    String response = "success";


    doReturn(jpaQueryBoard).when(queryFactory).select(storeBoardEntity);
    doReturn(jpaQueryBoard).when(jpaQueryBoard).from(storeBoardEntity);
    doReturn(jpaQueryBoard).when(jpaQueryBoard).where(storeBoardEntity.boardId.eq(boardId));
    doReturn(jpaQueryBoard).when(jpaQueryBoard).limit(1);
    doReturn(sample).when(jpaQueryBoard).fetchOne();

    // when
    String result = storeBoardDAOImpl.registerBoardComment(boardId, comment, storeId);

    // Then
    assertEquals(response, result);
    assertEquals(sample.getComment(), comment);

  }

  @Test
  @DisplayName("게시판 댓글 등록 실패 : 권한 없음")
  public void registerBoardCommentFail() {
    // given
    Long boardId = 0L;
    String comment = "new_comment";
    String storeId = "1";
    StoreBoardEntity sample = StoreBoardEntity.sample();


    doReturn(jpaQueryBoard).when(queryFactory).select(storeBoardEntity);
    doReturn(jpaQueryBoard).when(jpaQueryBoard).from(storeBoardEntity);
    doReturn(jpaQueryBoard).when(jpaQueryBoard).where(storeBoardEntity.boardId.eq(boardId));
    doReturn(jpaQueryBoard).when(jpaQueryBoard).limit(1);
    doReturn(sample).when(jpaQueryBoard).fetchOne();

    // then && when
    MessageException message = assertThrows(MessageException.class, () -> {
      storeBoardDAOImpl.registerBoardComment(boardId, comment, storeId);
    });
    String expected = "권한이 없습니다.: " + storeId;
    assertEquals(expected, message.getMessage());

  }

  // 7. Update Board Comment
  @Test
  @DisplayName("게시판 댓글 수정 성공")
  public void updateBoardCommentSuccess() {
    // given
    Long boardId = 0L;
    String comment = "new_comment";
    String storeId = "0";
    StoreBoardEntity sample = StoreBoardEntity.sample();
    String response = "success";


    doReturn(jpaQueryBoard).when(queryFactory).select(storeBoardEntity);
    doReturn(jpaQueryBoard).when(jpaQueryBoard).from(storeBoardEntity);
    doReturn(jpaQueryBoard).when(jpaQueryBoard).where(storeBoardEntity.boardId.eq(boardId));
    doReturn(jpaQueryBoard).when(jpaQueryBoard).limit(1);
    doReturn(sample).when(jpaQueryBoard).fetchOne();

    // when
    String result = storeBoardDAOImpl.updateBoardComment(boardId, comment, storeId);

    // Then
    assertEquals(response, result);
    assertEquals(sample.getComment(), comment);

  }

  @Test
  @DisplayName("게시판 댓글 수정 실패 : 권한 없음")
  public void updateBoardCommentFail() {
    // given
    Long boardId = 0L;
    String comment = "new_comment";
    String storeId = "1";
    StoreBoardEntity sample = StoreBoardEntity.sample();


    doReturn(jpaQueryBoard).when(queryFactory).select(storeBoardEntity);
    doReturn(jpaQueryBoard).when(jpaQueryBoard).from(storeBoardEntity);
    doReturn(jpaQueryBoard).when(jpaQueryBoard).where(storeBoardEntity.boardId.eq(boardId));
    doReturn(jpaQueryBoard).when(jpaQueryBoard).limit(1);
    doReturn(sample).when(jpaQueryBoard).fetchOne();

    // then && when
    MessageException message = assertThrows(MessageException.class, () -> {
      storeBoardDAOImpl.updateBoardComment(boardId, comment, storeId);
    });
    String expected = "권한이 없습니다.: " + storeId;
    assertEquals(expected, message.getMessage());

  }


  // 8. Delete Board Comment
  @Test
  @DisplayName("게시판 댓글 삭제 성공")
  public void deleteBoardCommentSuccess() {
    // given
    Long boardId = 0L;
    String storeId = "0";
    StoreBoardEntity sample = StoreBoardEntity.sample();
    String response = "success";


    doReturn(jpaQueryBoard).when(queryFactory).select(storeBoardEntity);
    doReturn(jpaQueryBoard).when(jpaQueryBoard).from(storeBoardEntity);
    doReturn(jpaQueryBoard).when(jpaQueryBoard).where(storeBoardEntity.boardId.eq(boardId));
    doReturn(jpaQueryBoard).when(jpaQueryBoard).limit(1);
    doReturn(sample).when(jpaQueryBoard).fetchOne();

    // when
    String result = storeBoardDAOImpl.deleteBoardComment(boardId, storeId);

    // Then
    assertEquals(response, result);
    assertEquals(sample.getComment(), null);

  }

  @Test
  @DisplayName("게시판 댓글 삭제 실패 : 권한 없음")
  public void deleteBoardCommentFail() {
    // given
    Long boardId = 0L;
    String storeId = "1";
    StoreBoardEntity sample = StoreBoardEntity.sample();

    doReturn(jpaQueryBoard).when(queryFactory).select(storeBoardEntity);
    doReturn(jpaQueryBoard).when(jpaQueryBoard).from(storeBoardEntity);
    doReturn(jpaQueryBoard).when(jpaQueryBoard).where(storeBoardEntity.boardId.eq(boardId));
    doReturn(jpaQueryBoard).when(jpaQueryBoard).limit(1);
    doReturn(sample).when(jpaQueryBoard).fetchOne();

    // then && when
    MessageException message = assertThrows(MessageException.class, () -> {
      storeBoardDAOImpl.deleteBoardComment(boardId, storeId);
    });
    String expected = "권한이 없습니다.: " + storeId;
    assertEquals(expected, message.getMessage());

  }

}
