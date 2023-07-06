package com.ReservationServer1.DAO.Impl;


import static com.ReservationServer1.data.Entity.POR.QStoreCouponEntity.storeCouponEntity;
import static com.ReservationServer1.data.Entity.POR.QStoreOrdersEntity.storeOrdersEntity;
import static com.ReservationServer1.data.Entity.POR.QStoreOrdersMapEntity.storeOrdersMapEntity;
import static com.ReservationServer1.data.Entity.POR.QStorePayEntity.storePayEntity;
import static com.ReservationServer1.data.Entity.POR.QStoreReservationEntity.storeReservationEntity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.stereotype.Repository;
import com.ReservationServer1.DAO.StorePORDAO;
import com.ReservationServer1.data.DTO.POR.OrderDTO;
import com.ReservationServer1.data.DTO.POR.PayDTO;
import com.ReservationServer1.data.DTO.POR.ReservationDTO;
import com.ReservationServer1.data.Entity.POR.StoreCouponEntity;
import com.ReservationServer1.data.Entity.POR.StoreOrdersEntity;
import com.ReservationServer1.data.Entity.POR.StoreOrdersMapEntity;
import com.ReservationServer1.data.Entity.POR.StorePayEntity;
import com.ReservationServer1.data.Entity.POR.StoreReservationEntity;
import com.ReservationServer1.exception.MessageException;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@Repository("ReservationAndOrderDAO")
@Transactional
public class StorePORDAOImpl implements StorePORDAO {
  private final JPAQueryFactory queryFactory;
  private final EntityManager entityManager;

  public StorePORDAOImpl(JPAQueryFactory queryFactory, EntityManager entityManager) {
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
        .from(storeReservationEntity).where(storeReservationEntity.storeId
            .eq(reservationDTO.getStoreId()).and(storeReservationEntity.userId.eq(userId)))
        .fetchFirst();
    entity.setDate(reservationDTO.getDate());
    entity.setTime(reservationDTO.getTime());
    entity.setStoreTable(reservationDTO.getStoreTable());
    return "success";
  }


  // 수정!!
  @Override
  public StoreReservationEntity getReservation(int storeId, String userId) {
    StoreReservationEntity result = queryFactory.select(storeReservationEntity)
        .from(storeReservationEntity).leftJoin(storeReservationEntity.child, storeOrdersEntity)
        .fetchJoin().leftJoin(storeOrdersEntity.payment, storePayEntity).fetchJoin()
        .leftJoin(storeOrdersEntity.childSet, storeOrdersMapEntity).fetchJoin()
        .where(storeReservationEntity.storeId.eq(storeId)
            .and(storeReservationEntity.userId.eq(userId)))
        .fetchFirst();

    if (result == null) {
      throw new MessageException("정보가 존재하지 않습니다.");
    }
    return result;
  }

  @Override
  public String deleteReservation(int storeId, String userId) {
    queryFactory.delete(storeReservationEntity).where(
        storeReservationEntity.storeId.eq(storeId).and(storeReservationEntity.userId.eq(userId)))
        .execute();
    return "success";
  }

  @Override
  public String registerOrder(OrderDTO orderDTO, String userId) {
    StoreReservationEntity grandfa = queryFactory.select(storeReservationEntity)
        .from(storeReservationEntity).where(storeReservationEntity.userId.eq(userId)
            .and(storeReservationEntity.storeId.eq(orderDTO.getStoreId())))
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
        .where(storeReservationEntity.storeId.eq(orderDTO.getStoreId())
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
  public String deleteOrder(int storeId, String foodName, String userId) {
    StoreReservationEntity result = queryFactory.select(storeReservationEntity)
        .from(storeReservationEntity).leftJoin(storeReservationEntity.child, storeOrdersEntity)
        .fetchJoin().leftJoin(storeOrdersEntity.childSet, storeOrdersMapEntity).fetchJoin()
        .where(storeReservationEntity.storeId.eq(storeId)
            .and(storeReservationEntity.userId.eq(userId)))
        .fetchFirst();
    queryFactory
        .delete(storeOrdersMapEntity).where(storeOrdersMapEntity.storeOrdersEntity.ordersId
            .eq(result.getChild().getOrdersId()).and(storeOrdersMapEntity.foodName.eq(foodName)))
        .execute();
    return "success";
  }

  @Override
  public String registerPay(PayDTO payDTO) {
    int storeId = payDTO.getStoreId();
    StoreReservationEntity result = queryFactory.select(storeReservationEntity)
        .from(storeReservationEntity).leftJoin(storeReservationEntity.child, storeOrdersEntity)
        .where(storeReservationEntity.reservationId.eq(payDTO.getReservationId())).fetchFirst();
    StorePayEntity entity = StorePayEntity.builder().amount(payDTO.getAmount()).build();
    entity.setStoreOrdersEntity(result.getChild());
    entityManager.persist(entity);
    result.getChild().setPayment(entity);

    // 쿠폰 증가
    StoreCouponEntity coupon = queryFactory.select(storeCouponEntity).from(storeCouponEntity).where(
        storeCouponEntity.userId.eq(result.getUserId()).and(storeCouponEntity.storeId.eq(storeId)))
        .fetchFirst();
    if (coupon == null) {
      StoreCouponEntity newCoupon =
          StoreCouponEntity.builder().storeId(storeId).userId(result.getUserId()).amount(1).build();
      entityManager.persist(newCoupon);
    } else {
      coupon.setAmount(coupon.getAmount() + 1);
    }
    return "success";
  }

  @Override
  public String deletePay(Long reservationId) {
    StoreReservationEntity result = queryFactory.select(storeReservationEntity)
        .from(storeReservationEntity).leftJoin(storeReservationEntity.child, storeOrdersEntity)
        .fetchJoin().leftJoin(storeOrdersEntity.payment, storePayEntity).fetchJoin()
        .where(storeReservationEntity.reservationId.eq(reservationId)).fetchFirst();
    queryFactory.delete(storePayEntity)
        .where(storePayEntity.paymentId.eq(result.getChild().getPayment().getPaymentId()))
        .execute();

    // 쿠폰 감소
    StoreCouponEntity coupon = queryFactory
        .select(storeCouponEntity).from(storeCouponEntity).where(storeCouponEntity.userId
            .eq(result.getUserId()).and(storeCouponEntity.storeId.eq(result.getStoreId())))
        .fetchFirst();
    coupon.setAmount(coupon.getAmount() - 1);
    return "success";
  }

  @Override
  public String registerComment(long reservationId, String comment, String userId) {
    StoreReservationEntity entity = queryFactory.select(storeReservationEntity)
        .from(storeReservationEntity).leftJoin(storeReservationEntity.child, storeOrdersEntity)
        .fetchJoin().leftJoin(storeOrdersEntity.payment, storePayEntity).fetchJoin()
        .where(storeReservationEntity.reservationId.eq(reservationId)).fetchFirst();
    if (!userId.equals(entity.getUserId())) {
      throw new MessageException("권한이 없습니다.");
    }
    StorePayEntity child = entity.getChild().getPayment();
    child.setComment(comment);
    return "success";
  }

  @Override
  public String deleteComment(Long reservationId, String userId) {
    StoreReservationEntity entity = queryFactory.select(storeReservationEntity)
        .from(storeReservationEntity).leftJoin(storeReservationEntity.child, storeOrdersEntity)
        .fetchJoin().leftJoin(storeOrdersEntity.payment, storePayEntity).fetchJoin()
        .where(storeReservationEntity.reservationId.eq(reservationId)).fetchFirst();
    if (!userId.equals(entity.getUserId())) {
      throw new MessageException("권한이 없습니다.");
    }
    StorePayEntity child = entity.getChild().getPayment();
    child.setComment(null);
    return "success";
  }

  @Override
  public String registerBigComment(long reservationId, String bigcomment, int storeId) {
    StoreReservationEntity entity = queryFactory.select(storeReservationEntity)
        .from(storeReservationEntity).leftJoin(storeReservationEntity.child, storeOrdersEntity)
        .fetchJoin().leftJoin(storeOrdersEntity.payment, storePayEntity).fetchJoin()
        .where(storeReservationEntity.reservationId.eq(reservationId)).fetchFirst();
    if (storeId != entity.getStoreId()) {
      throw new MessageException("권한이 없습니다.");
    }
    StorePayEntity child = entity.getChild().getPayment();
    child.setBigComment(bigcomment);
    return "success";
  }

  @Override
  public String deleteBigComment(Long reservationId, int storeId) {
    StoreReservationEntity entity = queryFactory.select(storeReservationEntity)
        .from(storeReservationEntity).leftJoin(storeReservationEntity.child, storeOrdersEntity)
        .fetchJoin().leftJoin(storeOrdersEntity.payment, storePayEntity).fetchJoin()
        .where(storeReservationEntity.reservationId.eq(reservationId)).fetchFirst();
    if (storeId != entity.getStoreId()) {
      throw new MessageException("권한이 없습니다.");
    }
    StorePayEntity child = entity.getChild().getPayment();
    child.setBigComment(null);
    return "success";
  }

  @Override
  public int getCouponClient(int storeId, String userId) {
    StoreCouponEntity entity = queryFactory.select(storeCouponEntity).from(storeCouponEntity)
        .where(storeCouponEntity.storeId.eq(storeId).and(storeCouponEntity.userId.eq(userId))).fetchFirst();
    return entity.getAmount();
  }

  @Override
  public HashMap<String, Integer> getCouponOwner(int storeId) {
    List<StoreCouponEntity> entity = queryFactory.select(storeCouponEntity).from(storeCouponEntity)
        .where(storeCouponEntity.storeId.eq(storeId)).fetch();
    if(entity == null) {
      throw new MessageException("정보가 존재하지 않습니다.");
    }
    HashMap<String, Integer> result = new HashMap<>();
    for(StoreCouponEntity now : entity) {
      result.put(now.getUserId(), now.getAmount());
    }
    return result;
  }
}
