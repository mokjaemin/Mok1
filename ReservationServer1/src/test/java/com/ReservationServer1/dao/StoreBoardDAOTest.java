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
import com.ReservationServer1.exception.NoAuthorityException;
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
		StoreBoardEntity boardEntity = StoreBoardEntity.getSample();
		int reservationId = 1;

		doReturn(jpaQueryReservation).when(queryFactory).select(storeReservationEntity.reservationId);
		doReturn(jpaQueryReservation).when(jpaQueryReservation).from(storeReservationEntity);
		doReturn(jpaQueryReservation).when(jpaQueryReservation)
				.where(storeReservationEntity.userId.eq(boardEntity.getUserId()));
		doReturn(jpaQueryReservation).when(jpaQueryReservation).limit(1);
		doReturn(reservationId).when(jpaQueryReservation).fetchOne();

		doNothing().when(entityManager).persist(any(StoreBoardEntity.class));

		// when
		String result = storeBoardDAOImpl.registerBoard(boardEntity);

		// then
		assertNotNull(result);
		verify(entityManager, times(1)).persist(any(StoreBoardEntity.class));
	}

	@Test
	@DisplayName("가게 게시판 등록 실패 : 정보 없음")
	public void registerBoardFail() {
		// given
		StoreBoardEntity boardEntity = StoreBoardEntity.getSample();

		doReturn(jpaQueryReservation).when(queryFactory).select(storeReservationEntity.reservationId);
		doReturn(jpaQueryReservation).when(jpaQueryReservation).from(storeReservationEntity);
		doReturn(jpaQueryReservation).when(jpaQueryReservation)
				.where(storeReservationEntity.userId.eq(boardEntity.getUserId()));
		doReturn(jpaQueryReservation).when(jpaQueryReservation).limit(1);
		doReturn(null).when(jpaQueryReservation).fetchOne();

		// then && when
		NoAuthorityException message = assertThrows(NoAuthorityException.class, () -> {
			storeBoardDAOImpl.registerBoard(boardEntity);
		});
		String expected = "해당 정보에 접근할 권한이 없습니다.";
		assertEquals(expected, message.getMessage());
		verify(entityManager, never()).persist(any(StoreBoardEntity.class));
	}

	@Test
	@DisplayName("가게 게시판 수정 실패 : 권한 없음")
	public void updateBoardFail() {
		// given
		int boardId = 0;
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
		NoAuthorityException message = assertThrows(NoAuthorityException.class, () -> {
			storeBoardDAOImpl.updateBoard(boardId, boardDTO, userId);
		});
		String expected = "해당 정보에 접근할 권한이 없습니다.";
		assertEquals(expected, message.getMessage());
	}

	// 3. Delete Board
	@Test
	@DisplayName("가게 게시판 삭제 성공")
	public void deleteBoardSuccess() {
		// given
		int boardId = 0;
		String userId = "userId";
		StoreBoardEntity entity = StoreBoardEntity.getSample();

		doReturn(jpaQueryBoard).when(queryFactory).select(storeBoardEntity);
		doReturn(jpaQueryBoard).when(jpaQueryBoard).from(storeBoardEntity);
		doReturn(jpaQueryBoard).when(jpaQueryBoard).where(storeBoardEntity.boardId.eq(boardId));
		doReturn(jpaQueryBoard).when(jpaQueryBoard).limit(1);
		doReturn(entity).when(jpaQueryBoard).fetchOne();

		doReturn(jpaDeleteClause).when(queryFactory).delete(storeBoardEntity);
		doReturn(jpaDeleteClause).when(jpaDeleteClause).where(storeBoardEntity.boardId.eq(boardId));

		// when
		String result = storeBoardDAOImpl.deleteBoard(boardId, userId);

		// Then
		assertEquals("success", result);

	}

	// 6. Register Board Comment
	@Test
	@DisplayName("게시판 댓글 등록 성공")
	public void registerBoardCommentSuccess() {
		// given
		int boardId = 0;
		String comment = "new_comment";
		String storeId = "0";
		StoreBoardEntity sample = StoreBoardEntity.getSample();
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
		int boardId = 0;
		String comment = "new_comment";
		String storeId = "1";
		StoreBoardEntity sample = StoreBoardEntity.getSample();

		doReturn(jpaQueryBoard).when(queryFactory).select(storeBoardEntity);
		doReturn(jpaQueryBoard).when(jpaQueryBoard).from(storeBoardEntity);
		doReturn(jpaQueryBoard).when(jpaQueryBoard).where(storeBoardEntity.boardId.eq(boardId));
		doReturn(jpaQueryBoard).when(jpaQueryBoard).limit(1);
		doReturn(sample).when(jpaQueryBoard).fetchOne();

		// then && when
		NoAuthorityException message = assertThrows(NoAuthorityException.class, () -> {
			storeBoardDAOImpl.registerBoardComment(boardId, comment, storeId);
		});
		String expected = "해당 정보에 접근할 권한이 없습니다.";
		assertEquals(expected, message.getMessage());

	}

	// 7. Update Board Comment
	@Test
	@DisplayName("게시판 댓글 수정 성공")
	public void updateBoardCommentSuccess() {
		// given
		int boardId = 0;
		String comment = "new_comment";
		String storeId = "0";
		StoreBoardEntity sample = StoreBoardEntity.getSample();
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
		int boardId = 0;
		String comment = "new_comment";
		String storeId = "1";
		StoreBoardEntity sample = StoreBoardEntity.getSample();

		doReturn(jpaQueryBoard).when(queryFactory).select(storeBoardEntity);
		doReturn(jpaQueryBoard).when(jpaQueryBoard).from(storeBoardEntity);
		doReturn(jpaQueryBoard).when(jpaQueryBoard).where(storeBoardEntity.boardId.eq(boardId));
		doReturn(jpaQueryBoard).when(jpaQueryBoard).limit(1);
		doReturn(sample).when(jpaQueryBoard).fetchOne();

		// then && when
		NoAuthorityException message = assertThrows(NoAuthorityException.class, () -> {
			storeBoardDAOImpl.updateBoardComment(boardId, comment, storeId);
		});
		String expected = "해당 정보에 접근할 권한이 없습니다.";
		assertEquals(expected, message.getMessage());

	}

	// 8. Delete Board Comment
	@Test
	@DisplayName("게시판 댓글 삭제 성공")
	public void deleteBoardCommentSuccess() {
		// given
		int boardId = 0;
		String storeId = "0";
		StoreBoardEntity sample = StoreBoardEntity.getSample();
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
		int boardId = 0;
		String storeId = "1";
		StoreBoardEntity sample = StoreBoardEntity.getSample();

		doReturn(jpaQueryBoard).when(queryFactory).select(storeBoardEntity);
		doReturn(jpaQueryBoard).when(jpaQueryBoard).from(storeBoardEntity);
		doReturn(jpaQueryBoard).when(jpaQueryBoard).where(storeBoardEntity.boardId.eq(boardId));
		doReturn(jpaQueryBoard).when(jpaQueryBoard).limit(1);
		doReturn(sample).when(jpaQueryBoard).fetchOne();

		// then && when
		NoAuthorityException message = assertThrows(NoAuthorityException.class, () -> {
			storeBoardDAOImpl.deleteBoardComment(boardId, storeId);
		});
		String expected = "해당 정보에 접근할 권한이 없습니다.";
		assertEquals(expected, message.getMessage());

	}

}
