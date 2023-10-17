package com.ReservationServer1.DAO.Impl;


import static com.ReservationServer1.data.Entity.POR.QStoreReservationEntity.storeReservationEntity;
import static com.ReservationServer1.data.Entity.board.QStoreBoardEntity.storeBoardEntity;
import java.io.IOException;
import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.ReservationServer1.DAO.StoreBoardDAO;
import com.ReservationServer1.data.DTO.board.BoardDTO;
import com.ReservationServer1.data.Entity.board.StoreBoardEntity;
import com.ReservationServer1.exception.store.NoAuthorityException;
import com.ReservationServer1.exception.store.NoInformationException;
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
  public String registerBoard(StoreBoardEntity entity) {

    Integer reservationId =
        queryFactory.select(storeReservationEntity.reservationId).from(storeReservationEntity)
            .where(storeReservationEntity.userId.eq(entity.getUserId())).fetchFirst();

    // 예약 내역 없어 권한 없음
    if (reservationId == null) {
      throw new NoAuthorityException();
    }

    // 저장
    entityManager.persist(entity);
    return String.valueOf(entity.getBoardId());
  }

  @Override
  public String updateBoard(int boardId, BoardDTO boardDTO, String userId) {
    try {
      StoreBoardEntity entity = queryFactory.select(storeBoardEntity).from(storeBoardEntity)
          .where(storeBoardEntity.boardId.eq(boardId)).fetchFirst();

      if (entity == null) {
        throw new NoInformationException();
      }

      if (!userId.equals(entity.getUserId())) {
        throw new NoAuthorityException();
      }

      entity.setTitle(boardDTO.getTitle());
      entity.setContent(boardDTO.getContent());
      entity.setRating(boardDTO.getRating());
      entity.setBoardImage(boardDTO.getFoodImage().getBytes());
    } catch (IOException e) {
      return null;
    }
    return "success";
  }

  @Override
  public String deleteBoard(int boardId, String userId) {
    
    StoreBoardEntity entity = queryFactory.select(storeBoardEntity).from(storeBoardEntity)
        .where(storeBoardEntity.boardId.eq(boardId)).fetchFirst();
    
    if (!entity.getUserId().equals(userId)) {
      throw new NoAuthorityException();
    }
    
    queryFactory.delete(storeBoardEntity).where(storeBoardEntity.boardId.eq(boardId)).execute();
    return "success";
  }

  @Override
  public List<StoreBoardEntity> getBoard(short storeId) {
    List<StoreBoardEntity> result = queryFactory.select(storeBoardEntity).from(storeBoardEntity)
        .where(storeBoardEntity.storeId.eq(storeId)).fetch();
    return result;
  }

  @Override
  public List<StoreBoardEntity> getBoardById(String userId) {
    List<StoreBoardEntity> result = queryFactory.select(storeBoardEntity).from(storeBoardEntity)
        .where(storeBoardEntity.userId.eq(userId)).fetch();
    return result;
  }

  @Override
  public String registerBoardComment(int boardId, String comment, String storeId) {
    StoreBoardEntity entity = queryFactory.select(storeBoardEntity).from(storeBoardEntity)
        .where(storeBoardEntity.boardId.eq(boardId)).fetchFirst();
    if (!String.valueOf(entity.getStoreId()).equals(storeId)) {
      throw new NoAuthorityException();
    }
    entity.setComment(comment);
    return "success";
  }

  @Override
  public String updateBoardComment(int boardId, String comment, String storeId) {
    StoreBoardEntity entity = queryFactory.select(storeBoardEntity).from(storeBoardEntity)
        .where(storeBoardEntity.boardId.eq(boardId)).fetchFirst();
    if (!String.valueOf(entity.getStoreId()).equals(storeId)) {
      throw new NoAuthorityException();
    }
    entity.setComment(comment);
    return "success";
  }

  @Override
  public String deleteBoardComment(int boardId, String storeId) {
    StoreBoardEntity entity = queryFactory.select(storeBoardEntity).from(storeBoardEntity)
        .where(storeBoardEntity.boardId.eq(boardId)).fetchFirst();
    if (!String.valueOf(entity.getStoreId()).equals(storeId)) {
      throw new NoAuthorityException();
    }
    entity.setComment(null);
    return "success";
  }

}
