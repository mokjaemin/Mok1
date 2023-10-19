package com.ReservationServer1.DAO.Impl;

import static com.ReservationServer1.data.Entity.POR.QStoreReservationEntity.storeReservationEntity;
import static com.ReservationServer1.data.Entity.board.QStoreBoardEntity.storeBoardEntity;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ReservationServer1.DAO.StoreBoardDAO;
import com.ReservationServer1.data.DTO.board.BoardDTO;
import com.ReservationServer1.data.Entity.board.StoreBoardEntity;
import com.ReservationServer1.exception.NoAuthorityException;
import com.ReservationServer1.exception.NoInformationException;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;

@Repository("StoreBoardDAO")
@Transactional
public class StoreBoardDAOImpl implements StoreBoardDAO {

	private final JPAQueryFactory queryFactory;
	private final EntityManager entityManager;

	public StoreBoardDAOImpl(JPAQueryFactory queryFactory, EntityManager entityManager) {
		this.queryFactory = queryFactory;
		this.entityManager = entityManager;
	}

	@Override
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
	public Map<String, Integer> getBoardListByStore(short storeId) {
		List<StoreBoardEntity> boardList = queryFactory.select(storeBoardEntity).from(storeBoardEntity)
				.where(storeBoardEntity.storeId.eq(storeId)).fetch();
		Map<String, Integer> result = boardList.stream()
				.collect(Collectors.toMap(StoreBoardEntity::getTitle, StoreBoardEntity::getBoardId));
		return result;
	}

	@Override
	public Map<String, Integer> getBoardListByUser(String userId) {
		List<StoreBoardEntity> boardList = queryFactory.select(storeBoardEntity).from(storeBoardEntity)
				.where(storeBoardEntity.userId.eq(userId)).fetch();
		Map<String, Integer> result = boardList.stream()
				.collect(Collectors.toMap(StoreBoardEntity::getTitle, StoreBoardEntity::getBoardId));
		return result;
	}

	@Override
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
