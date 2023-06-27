package com.ReservationServer1.DAO.Impl;


import static com.ReservationServer1.data.Entity.ReservationAndOrder.QStoreOrdersEntity.storeOrdersEntity;
import static com.ReservationServer1.data.Entity.ReservationAndOrder.QStoreReservationEntity.storeReservationEntity;
import static com.ReservationServer1.data.Entity.ReservationAndOrder.QStoreOrdersMapEntity.storeOrdersMapEntity;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.ReservationServer1.DAO.StoreReservationOrderDAO;
import com.ReservationServer1.data.DTO.ReservationOrder.OrderDTO;
import com.ReservationServer1.data.DTO.ReservationOrder.ReservationDTO;
import com.ReservationServer1.data.Entity.ReservationAndOrder.StoreOrdersEntity;
import com.ReservationServer1.data.Entity.ReservationAndOrder.StoreOrdersMapEntity;
import com.ReservationServer1.data.Entity.ReservationAndOrder.StoreReservationEntity;
import com.ReservationServer1.exception.MessageException;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

@Repository("ReservationAndOrderDAO")
@Transactional
public class StoreReservationOrderDAOImpl implements StoreReservationOrderDAO {


  private final JPAQueryFactory queryFactory;
  private final EntityManager entityManager;

  public StoreReservationOrderDAOImpl(JPAQueryFactory queryFactory, EntityManager entityManager) {
    this.entityManager = entityManager;
    this.queryFactory = queryFactory;
  }

  @Override
  public String registerReservation(ReservationDTO reservationDTO, String userId) {
    StoreReservationEntity storeReservationEntity = new StoreReservationEntity(reservationDTO);
    storeReservationEntity.setUserId(userId);
    entityManager.persist(storeReservationEntity);
    return "success";
  }

  @Override
  public String updateReservation(ReservationDTO reservationDTO, String userId) {
    StoreReservationEntity entity = queryFactory.select(storeReservationEntity)
        .from(storeReservationEntity).where(storeReservationEntity.storeName
            .eq(reservationDTO.getStoreName()).and(storeReservationEntity.userId.eq(userId)))
        .fetchFirst();
    entity.setDate(reservationDTO.getDate());
    entity.setTime(reservationDTO.getTime());
    entity.setStoreTable(reservationDTO.getStoreTable());
    return "success";
  }

  @Override
  public StoreReservationEntity getReservation(String storeName, String userId) {
    StoreReservationEntity result = queryFactory.select(storeReservationEntity)
        .from(storeReservationEntity).leftJoin(storeReservationEntity.child, storeOrdersEntity)
        .fetchJoin().leftJoin(storeOrdersEntity.childSet, storeOrdersMapEntity).fetchJoin()
        .where(storeReservationEntity.storeName.eq(storeName)
            .and(storeReservationEntity.userId.eq(userId)))
        .fetchFirst();

    if (result == null) {
      throw new MessageException("정보가 존재하지 않습니다.");
    }
    return result;
  }

  @Override
  public String deleteReservation(String storeName, String userId) {
    queryFactory.delete(storeReservationEntity).where(storeReservationEntity.storeName.eq(storeName)
        .and(storeReservationEntity.userId.eq(userId))).execute();
    return "success";
  }

  @Override
  public String registerOrder(OrderDTO orderDTO, String userId) {
    StoreReservationEntity grandfa = queryFactory.select(storeReservationEntity)
        .from(storeReservationEntity).where(storeReservationEntity.userId.eq(userId)
            .and(storeReservationEntity.storeName.eq(orderDTO.getStoreName())))
        .fetchFirst();
    if (grandfa == null) {
      throw new MessageException("정보가 존재하지 않습니다.");
    }
    StoreOrdersEntity father = new StoreOrdersEntity(grandfa);
    entityManager.persist(father);
    List<StoreOrdersMapEntity> childs = new ArrayList<>();
    for (String foodName : orderDTO.getOrderInfo().keySet()) {
      StoreOrdersMapEntity child =
          new StoreOrdersMapEntity(foodName, orderDTO.getOrderInfo().get(foodName), father);
      entityManager.persist(child);
      childs.add(child);
    }
    father.setChildSet(childs);
    grandfa.setChild(father);
    return "success";
  }

  @Override
  public String updateOrder(OrderDTO orderDTO, String userId) {
    StoreReservationEntity result = queryFactory.select(storeReservationEntity)
        .from(storeReservationEntity).leftJoin(storeReservationEntity.child, storeOrdersEntity)
        .fetchJoin().leftJoin(storeOrdersEntity.childSet, storeOrdersMapEntity).fetchJoin()
        .where(storeReservationEntity.storeName.eq(orderDTO.getStoreName())
            .and(storeReservationEntity.userId.eq(userId)))
        .fetchFirst();

    List<StoreOrdersMapEntity> childsSet = result.getChild().getChildSet();
    for (StoreOrdersMapEntity child : childsSet) {
      for (String foodName : orderDTO.getOrderInfo().keySet()) {
        if (child.getFoodName().equals(foodName)) {
          child.setFoodCount(orderDTO.getOrderInfo().get(foodName));
        }
      }
    }
    return "success";
  }

  @Override
  public String deleteOrder(String storeName, String foodName, String userId) {
    StoreReservationEntity result = queryFactory.select(storeReservationEntity)
        .from(storeReservationEntity).leftJoin(storeReservationEntity.child, storeOrdersEntity)
        .fetchJoin().leftJoin(storeOrdersEntity.childSet, storeOrdersMapEntity).fetchJoin()
        .where(storeReservationEntity.storeName.eq(storeName)
            .and(storeReservationEntity.userId.eq(userId)))
        .fetchFirst();
    queryFactory.delete(storeOrdersMapEntity)
    .where(storeOrdersMapEntity.storeOrdersEntity.ordersId.eq(result.getChild().getOrdersId())
        
        .and(storeOrdersMapEntity.foodName.eq(foodName)))
    .execute();
    return "success";
  }

}
