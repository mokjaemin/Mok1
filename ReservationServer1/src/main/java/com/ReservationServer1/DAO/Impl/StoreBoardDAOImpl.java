package com.ReservationServer1.DAO.Impl;

import static com.ReservationServer1.data.Entity.POR.QStoreReservationEntity.storeReservationEntity;
import static com.ReservationServer1.data.Entity.board.QStoreBoardEntity.storeBoardEntity;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.ReservationServer1.DAO.StoreBoardDAO;
import com.ReservationServer1.data.DTO.board.BoardCountResultDTO;
import com.ReservationServer1.data.DTO.board.BoardDTO;
import com.ReservationServer1.data.DTO.board.BoardListResultDTO;
import com.ReservationServer1.data.Entity.board.StoreBoardEntity;
import com.ReservationServer1.exception.NoAuthorityException;
import com.ReservationServer1.exception.NoInformationException;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;

@Repository("StoreBoardDAO")
public class StoreBoardDAOImpl implements StoreBoardDAO {

	private final JPAQueryFactory queryFactory;
	private final EntityManager entityManager;

	public StoreBoardDAOImpl(JPAQueryFactory queryFactory, EntityManager entityManager) {
		this.queryFactory = queryFactory;
		this.entityManager = entityManager;
	}

	@Override
	@Transactional(timeout = 10, rollbackFor = Exception.class)
	public String registerBoard(StoreBoardEntity board) {

		Integer reservationId = queryFactory.select(storeReservationEntity.reservationId).from(storeReservationEntity)
				.where(storeReservationEntity.userId.eq(board.getUserId())).fetchFirst();

		if (reservationId == null) {
			throw new NoAuthorityException();
		}

		entityManager.persist(board);

		return String.valueOf(board.getBoardId());
	}

	@Override
	@Transactional(isolation = Isolation.READ_UNCOMMITTED, timeout = 10, rollbackFor = Exception.class)
	public String updateBoard(int boardId, BoardDTO boardDTO, String userId) {
		try {
			StoreBoardEntity board = queryFactory.select(storeBoardEntity).from(storeBoardEntity)
					.where(storeBoardEntity.boardId.eq(boardId)).fetchFirst();

			if (board == null) {
				throw new NoInformationException();
			}

			if (!userId.equals(board.getUserId())) {
				throw new NoAuthorityException();
			}

			board.setTitle(boardDTO.getTitle());
			board.setContent(boardDTO.getContent());
			board.setRating(boardDTO.getRating());
			board.setBoardImage(boardDTO.getFoodImage().getBytes());

		} catch (IOException e) {
			return null;
		}
		return "success";
	}

	@Override
	@Transactional(isolation = Isolation.READ_UNCOMMITTED, timeout = 10, rollbackFor = Exception.class)
	public String deleteBoard(int boardId, String userId) {

		StoreBoardEntity board = queryFactory.select(storeBoardEntity).from(storeBoardEntity)
				.where(storeBoardEntity.boardId.eq(boardId)).fetchFirst();

		if (board.getUserId().equals(userId)) {
			throw new NoAuthorityException();
		}

		queryFactory.delete(storeBoardEntity).where(storeBoardEntity.boardId.eq(boardId)).execute();
		return "success";
	}

	@Override
	@Transactional(timeout = 10, readOnly = true, rollbackFor = Exception.class)
	public List<BoardListResultDTO> getBoardListByStore(short storeId) {
		List<BoardListResultDTO> boardListResult = queryFactory
				.select(Projections.fields(BoardListResultDTO.class, storeBoardEntity.title, storeBoardEntity.boardId))
				.from(storeBoardEntity).where(storeBoardEntity.storeId.eq(storeId)).fetch();
		return boardListResult;
	}

	
	@Override
	@Transactional(timeout = 10, readOnly = true, rollbackFor = Exception.class)
	public List<BoardListResultDTO> getBoardListByUser(String userId) {
		List<BoardListResultDTO> boardListResult = queryFactory
				.select(Projections.fields(BoardListResultDTO.class, storeBoardEntity.title, storeBoardEntity.boardId))
				.from(storeBoardEntity).where(storeBoardEntity.userId.eq(userId)).fetch();
		return boardListResult;
	}

	
	@Override
	@Transactional(isolation = Isolation.SERIALIZABLE, timeout = 10, rollbackFor = Exception.class)
	public StoreBoardEntity getFullBoard(int boardId) {
		StoreBoardEntity board = queryFactory.select(storeBoardEntity).from(storeBoardEntity)
				.where(storeBoardEntity.boardId.eq(boardId)).fetchFirst();
		if (board == null) {
			throw new NoInformationException();
		}
		board.setViews(board.getViews() + 1);
		return board;
	}

	@Override
	@Transactional(timeout = 10, readOnly = true, rollbackFor = Exception.class)
	public List<BoardCountResultDTO> getBoardCountByUserOfStore(short storeId) {
		List<BoardCountResultDTO> countResult = queryFactory
				.select(Projections.fields(BoardCountResultDTO.class, storeBoardEntity.userId,
						storeBoardEntity.count().as("count")))
				.from(storeBoardEntity).where(storeBoardEntity.storeId.eq(storeId))
				.groupBy(storeBoardEntity.userId)
				.fetch();
		return countResult;
	}

	@Override
	@Transactional(isolation = Isolation.READ_UNCOMMITTED, timeout = 10, rollbackFor = Exception.class)
	public String registerBoardComment(int boardId, String comment, String storeId) {
		StoreBoardEntity board = queryFactory.select(storeBoardEntity).from(storeBoardEntity)
				.where(storeBoardEntity.boardId.eq(boardId)).fetchFirst();
		if (!String.valueOf(board.getStoreId()).equals(storeId)) {
			throw new NoAuthorityException();
		}
		board.setComment(comment);
		return "success";
	}

	@Override
	@Transactional(isolation = Isolation.READ_UNCOMMITTED, timeout = 10, rollbackFor = Exception.class)
	public String updateBoardComment(int boardId, String comment, String storeId) {
		StoreBoardEntity board = queryFactory.select(storeBoardEntity).from(storeBoardEntity)
				.where(storeBoardEntity.boardId.eq(boardId)).fetchFirst();
		if (!String.valueOf(board.getStoreId()).equals(storeId)) {
			throw new NoAuthorityException();
		}
		board.setComment(comment);
		return "success";
	}

	@Override
	@Transactional(isolation = Isolation.READ_UNCOMMITTED, timeout = 10, rollbackFor = Exception.class)
	public String deleteBoardComment(int boardId, String storeId) {
		StoreBoardEntity board = queryFactory.select(storeBoardEntity).from(storeBoardEntity)
				.where(storeBoardEntity.boardId.eq(boardId)).fetchFirst();
		if (!String.valueOf(board.getStoreId()).equals(storeId)) {
			throw new NoAuthorityException();
		}
		board.setComment(null);
		return "success";
	}

}
