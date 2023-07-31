package com.ReservationServer1.DAO.Impl;


import static com.ReservationServer1.data.Entity.POR.QStoreReservationEntity.storeReservationEntity;
import static com.ReservationServer1.data.Entity.board.QStoreBoardEntity.storeBoardEntity;
import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.ReservationServer1.DAO.StoreBoardDAO;
import com.ReservationServer1.data.DTO.board.BoardDTO;
import com.ReservationServer1.data.Entity.board.StoreBoardEntity;
import com.ReservationServer1.exception.MessageException;
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
  public String registerBoard(BoardDTO boardDTO, String userId, String imageURL) {
    Long reservationId = queryFactory.select(storeReservationEntity.reservationId)
        .from(storeReservationEntity).where(storeReservationEntity.userId.eq(userId)).fetchFirst();
    if (reservationId == null) {
      throw new MessageException("방문 기록이 존재하지 않습니다.: " + userId);
    }
    StoreBoardEntity entity =
        StoreBoardEntity.builder().storeId(boardDTO.getStoreId()).title(boardDTO.getTitle())
            .content(boardDTO.getContent()).userId(userId).rating(boardDTO.getRating()).build();
    entityManager.persist(entity);
    String string_boardId = String.valueOf(entity.getBoardId());
    entity.setImageURL(imageURL + string_boardId + ".png");
    return string_boardId;
  }

  @Override
  public String updateBoard(Long boardId, BoardDTO boardDTO, String userId) {
    StoreBoardEntity entity = queryFactory.select(storeBoardEntity).from(storeBoardEntity)
        .where(storeBoardEntity.boardId.eq(boardId)).fetchFirst();
    if (!userId.equals(entity.getUserId())) {
      throw new MessageException("권한이 없습니다.: " + userId);
    }
    entity.setTitle(boardDTO.getTitle());
    entity.setContent(boardDTO.getContent());
    entity.setRating(boardDTO.getRating());
    return "success";
  }

  @Override
  public Long deleteBoard(Long boardId, String userId) {
    StoreBoardEntity entity = queryFactory.select(storeBoardEntity).from(storeBoardEntity)
        .where(storeBoardEntity.boardId.eq(boardId)).fetchFirst();
    if (!entity.getUserId().equals(userId)) {
      throw new MessageException("권한이 없습니다.: " + userId);
    }
    queryFactory.delete(storeBoardEntity).where(storeBoardEntity.boardId.eq(boardId)).execute();
    return boardId;
  }

  @Override
  public List<StoreBoardEntity> getBoard(int storeId) {
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
  public String registerBoardComment(Long boardId, String comment, String storeId) {
    StoreBoardEntity entity = queryFactory.select(storeBoardEntity).from(storeBoardEntity)
        .where(storeBoardEntity.boardId.eq(boardId)).fetchFirst();
    if(!String.valueOf(entity.getStoreId()).equals(storeId)) {
      throw new MessageException("권한이 없습니다.: " + storeId);
    }
    entity.setComment(comment);
    return "success";
  }

  @Override
  public String updateBoardComment(Long boardId, String comment, String storeId) {
    StoreBoardEntity entity = queryFactory.select(storeBoardEntity).from(storeBoardEntity)
        .where(storeBoardEntity.boardId.eq(boardId)).fetchFirst();
    if(!String.valueOf(entity.getStoreId()).equals(storeId)) {
      throw new MessageException("권한이 없습니다.: " + storeId);
    }
    entity.setComment(comment);
    return "success";
  }

  @Override
  public String deleteBoardComment(Long boardId, String storeId) {
    StoreBoardEntity entity = queryFactory.select(storeBoardEntity).from(storeBoardEntity)
        .where(storeBoardEntity.boardId.eq(boardId)).fetchFirst();
    if(!String.valueOf(entity.getStoreId()).equals(storeId)) {
      throw new MessageException("권한이 없습니다.: " + storeId);
    }
    entity.setComment(null);
    return "success";
  }

}
