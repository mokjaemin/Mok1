package com.ReservationServer1.dao;


import static com.ReservationServer1.data.Entity.POR.QStoreOrdersEntity.storeOrdersEntity;
import static com.ReservationServer1.data.Entity.POR.QStoreOrdersMapEntity.storeOrdersMapEntity;
import static com.ReservationServer1.data.Entity.POR.QStorePayEntity.storePayEntity;
import static com.ReservationServer1.data.Entity.POR.QStoreReservationEntity.storeReservationEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
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
import com.ReservationServer1.DAO.Impl.StorePORDAOImpl;
import com.ReservationServer1.data.DTO.POR.OrderDTO;
import com.ReservationServer1.data.DTO.POR.ReservationDTO;
import com.ReservationServer1.data.Entity.POR.StoreOrdersEntity;
import com.ReservationServer1.data.Entity.POR.StoreOrdersMapEntity;
import com.ReservationServer1.data.Entity.POR.StorePayEntity;
import com.ReservationServer1.data.Entity.POR.StoreReservationEntity;
import com.ReservationServer1.exception.MessageException;
import com.querydsl.jpa.impl.JPADeleteClause;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

public class StorePORDAOTest {


  @Mock
  private JPAQueryFactory queryFactory;

  @InjectMocks
  private StorePORDAOImpl storePORDAOImpl;

  @Mock
  private EntityManager entityManager;

  @Mock
  private JPAQuery<StoreReservationEntity> jpaQueryReservation;

  @Mock
  private JPAQuery<StoreOrdersEntity> jpaQueryOrders;

  @Mock
  private JPAQuery<StoreOrdersMapEntity> jpaQueryOrdersMap;

  @Mock
  private JPAQuery<StorePayEntity> jpaQueryPay;

  @Mock
  private JPADeleteClause jpaDeleteClause;


  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  // 1. Register Reservation
  @Test
  @DisplayName("가게 예약 등록 성공")
  public void registerReservationSuccess() {
    // given
    ReservationDTO sample = ReservationDTO.sample();
    String userId = "userId";
    String response = "success";
    doNothing().when(entityManager).persist(any(StoreReservationEntity.class));

    // when
    String result = storePORDAOImpl.registerReservation(sample, userId);

    // then
    assertEquals(response, result);
    verify(entityManager, times(1)).persist(any(StoreReservationEntity.class));
  }


  // 2. Update Reservation
  @Test
  @DisplayName("가게 예약 수정 성공")
  public void updateReservationSuccess() {
    // given
    ReservationDTO sample = ReservationDTO.sample();
    StoreReservationEntity entity = mock(StoreReservationEntity.class);
    String userId = "userId";
    String response = "success";


    doReturn(jpaQueryReservation).when(queryFactory).select(storeReservationEntity);
    doReturn(jpaQueryReservation).when(jpaQueryReservation).from(storeReservationEntity);
    doReturn(jpaQueryReservation).when(jpaQueryReservation).where(storeReservationEntity.storeId
        .eq(sample.getStoreId()).and(storeReservationEntity.userId.eq(userId)));
    doReturn(jpaQueryReservation).when(jpaQueryReservation).limit(1);
    doReturn(entity).when(jpaQueryReservation).fetchOne();



    // when
    String result = storePORDAOImpl.updateReservation(sample, userId);

    // then
    assertEquals(response, result);

    // verify
    verify(queryFactory, times(1)).select(storeReservationEntity);
    verify(jpaQueryReservation, times(1)).from(storeReservationEntity);
    verify(jpaQueryReservation, times(1)).where(storeReservationEntity.storeId
        .eq(sample.getStoreId()).and(storeReservationEntity.userId.eq(userId)));
    verify(jpaQueryReservation, times(1)).limit(1);
    verify(jpaQueryReservation, times(1)).fetchOne();


    verify(entity, times(1)).setDate(sample.getDate());
    verify(entity, times(1)).setTime(sample.getTime());
    verify(entity, times(1)).setStoreTable(sample.getStoreTable());

  }


  // 3. Get Reservation
  @Test
  @DisplayName("가게 예약 출력 성공")
  public void getReservationSuccess() {
    // given
    int storeId = 0;
    String userId = "userId";
    StoreReservationEntity response = mock(StoreReservationEntity.class);


    doReturn(jpaQueryReservation).when(queryFactory).select(storeReservationEntity);
    doReturn(jpaQueryReservation).when(jpaQueryReservation).from(storeReservationEntity);
    doReturn(jpaQueryOrders).when(jpaQueryReservation).leftJoin(storeReservationEntity.child,
        storeOrdersEntity);
    doReturn(jpaQueryOrders).when(jpaQueryOrders).fetchJoin();
    doReturn(jpaQueryPay).when(jpaQueryOrders).leftJoin(storeOrdersEntity.payment, storePayEntity);
    doReturn(jpaQueryPay).when(jpaQueryPay).fetchJoin();
    doReturn(jpaQueryOrdersMap).when(jpaQueryPay).leftJoin(storeOrdersEntity.childSet,
        storeOrdersMapEntity);
    doReturn(jpaQueryOrdersMap).when(jpaQueryOrdersMap).fetchJoin();
    doReturn(jpaQueryReservation).when(jpaQueryOrdersMap).where(
        storeReservationEntity.storeId.eq(storeId).and(storeReservationEntity.userId.eq(userId)));
    doReturn(jpaQueryReservation).when(jpaQueryReservation).limit(1);
    doReturn(response).when(jpaQueryReservation).fetchOne();



    // when
    StoreReservationEntity result = storePORDAOImpl.getReservation(storeId, userId);

    // then
    assertEquals(response, result);

    // verify
    verify(queryFactory, times(1)).select(storeReservationEntity);
    verify(jpaQueryReservation, times(1)).from(storeReservationEntity);
    verify(jpaQueryReservation, times(1)).leftJoin(storeReservationEntity.child, storeOrdersEntity);
    verify(jpaQueryOrders, times(1)).fetchJoin();
    verify(jpaQueryOrders, times(1)).leftJoin(storeOrdersEntity.payment, storePayEntity);
    verify(jpaQueryPay, times(1)).fetchJoin();
    verify(jpaQueryPay, times(1)).leftJoin(storeOrdersEntity.childSet, storeOrdersMapEntity);
    verify(jpaQueryOrdersMap, times(1)).fetchJoin();
    verify(jpaQueryOrdersMap, times(1)).where(
        storeReservationEntity.storeId.eq(storeId).and(storeReservationEntity.userId.eq(userId)));
    verify(jpaQueryReservation, times(1)).limit(1);
    verify(jpaQueryReservation, times(1)).fetchOne();
  }


  @Test
  @DisplayName("가게 예약 출력 실패 : 정보 없음")
  public void getReservationFail() {
    // given
    int storeId = 0;
    String userId = "userId";


    doReturn(jpaQueryReservation).when(queryFactory).select(storeReservationEntity);
    doReturn(jpaQueryReservation).when(jpaQueryReservation).from(storeReservationEntity);
    doReturn(jpaQueryOrders).when(jpaQueryReservation).leftJoin(storeReservationEntity.child,
        storeOrdersEntity);
    doReturn(jpaQueryOrders).when(jpaQueryOrders).fetchJoin();
    doReturn(jpaQueryPay).when(jpaQueryOrders).leftJoin(storeOrdersEntity.payment, storePayEntity);
    doReturn(jpaQueryPay).when(jpaQueryPay).fetchJoin();
    doReturn(jpaQueryOrdersMap).when(jpaQueryPay).leftJoin(storeOrdersEntity.childSet,
        storeOrdersMapEntity);
    doReturn(jpaQueryOrdersMap).when(jpaQueryOrdersMap).fetchJoin();
    doReturn(jpaQueryReservation).when(jpaQueryOrdersMap).where(
        storeReservationEntity.storeId.eq(storeId).and(storeReservationEntity.userId.eq(userId)));
    doReturn(jpaQueryReservation).when(jpaQueryReservation).limit(1);
    doReturn(null).when(jpaQueryReservation).fetchOne();



    // then && when
    MessageException message = assertThrows(MessageException.class, () -> {
      storePORDAOImpl.getReservation(storeId, userId);
    });
    String expected = "정보가 존재하지 않습니다.";
    assertEquals(expected, message.getMessage());

    verify(queryFactory, times(1)).select(storeReservationEntity);
    verify(jpaQueryReservation, times(1)).from(storeReservationEntity);
    verify(jpaQueryReservation, times(1)).leftJoin(storeReservationEntity.child, storeOrdersEntity);
    verify(jpaQueryOrders, times(1)).fetchJoin();
    verify(jpaQueryOrders, times(1)).leftJoin(storeOrdersEntity.payment, storePayEntity);
    verify(jpaQueryPay, times(1)).fetchJoin();
    verify(jpaQueryPay, times(1)).leftJoin(storeOrdersEntity.childSet, storeOrdersMapEntity);
    verify(jpaQueryOrdersMap, times(1)).fetchJoin();
    verify(jpaQueryOrdersMap, times(1)).where(
        storeReservationEntity.storeId.eq(storeId).and(storeReservationEntity.userId.eq(userId)));
    verify(jpaQueryReservation, times(1)).limit(1);
    verify(jpaQueryReservation, times(1)).fetchOne();
  }


  // 4. Delete Reservation
  @Test
  @DisplayName("가게 예약 삭제 성공")
  public void deleteReservationSuccess() {
    // given
    int storeId = 0;
    String userId = "userId";
    String response = "success";


    doReturn(jpaDeleteClause).when(queryFactory).delete(storeReservationEntity);
    doReturn(jpaDeleteClause).when(jpaDeleteClause).where(
        storeReservationEntity.storeId.eq(storeId).and(storeReservationEntity.userId.eq(userId)));



    // when
    String result = storePORDAOImpl.deleteReservation(storeId, userId);

    // then
    assertEquals(response, result);

    // verify
    verify(queryFactory, times(1)).delete(storeReservationEntity);
    verify(jpaDeleteClause, times(1)).where(
        storeReservationEntity.storeId.eq(storeId).and(storeReservationEntity.userId.eq(userId)));
    verify(jpaDeleteClause, times(1)).execute();
  }


  // 5. Register Order
  @Test
  @DisplayName("가게 주문 등록 성공")
  public void registerOrderSuccess() {
    // given
    OrderDTO sample = OrderDTO.sample();
    String userId = "userId";
    StoreReservationEntity entity = mock(StoreReservationEntity.class);
    String response = "success";

    doReturn(jpaQueryReservation).when(queryFactory).select(storeReservationEntity);
    doReturn(jpaQueryReservation).when(jpaQueryReservation).from(storeReservationEntity);
    doReturn(jpaQueryReservation).when(jpaQueryReservation).where(storeReservationEntity.userId
        .eq(userId).and(storeReservationEntity.storeId.eq(sample.getStoreId())));
    doReturn(jpaQueryReservation).when(jpaQueryReservation).limit(1);
    doReturn(entity).when(jpaQueryReservation).fetchOne();

    doNothing().when(entityManager).persist(any(StoreOrdersEntity.class));
    doNothing().when(entityManager).persist(any(StoreOrdersMapEntity.class));


    // when
    String result = storePORDAOImpl.registerOrder(sample, userId);

    // then
    assertEquals(result, response);


    // verify
    verify(queryFactory, times(1)).select(storeReservationEntity);
    verify(jpaQueryReservation, times(1)).from(storeReservationEntity);
    verify(jpaQueryReservation, times(1)).where(storeReservationEntity.userId.eq(userId)
        .and(storeReservationEntity.storeId.eq(sample.getStoreId())));
    verify(jpaQueryReservation, times(1)).limit(1);
    verify(jpaQueryReservation, times(1)).fetchOne();

    verify(entityManager, times(1)).persist(any(StoreOrdersEntity.class));
    verify(entityManager, times(2)).persist(any(StoreOrdersMapEntity.class));

  }

  @Test
  @DisplayName("가게 주문 등록 실패 : 정보 없음")
  public void registerOrderFail() {
    // given
    OrderDTO sample = OrderDTO.sample();
    String userId = "userId";

    doReturn(jpaQueryReservation).when(queryFactory).select(storeReservationEntity);
    doReturn(jpaQueryReservation).when(jpaQueryReservation).from(storeReservationEntity);
    doReturn(jpaQueryReservation).when(jpaQueryReservation).where(storeReservationEntity.userId
        .eq(userId).and(storeReservationEntity.storeId.eq(sample.getStoreId())));
    doReturn(jpaQueryReservation).when(jpaQueryReservation).limit(1);
    doReturn(null).when(jpaQueryReservation).fetchOne();



    // then && when
    MessageException message = assertThrows(MessageException.class, () -> {
      storePORDAOImpl.registerOrder(sample, userId);
    });
    String expected = "정보가 존재하지 않습니다.";
    assertEquals(expected, message.getMessage());


    // verify
    verify(queryFactory, times(1)).select(storeReservationEntity);
    verify(jpaQueryReservation, times(1)).from(storeReservationEntity);
    verify(jpaQueryReservation, times(1)).where(storeReservationEntity.userId.eq(userId)
        .and(storeReservationEntity.storeId.eq(sample.getStoreId())));
    verify(jpaQueryReservation, times(1)).limit(1);
    verify(jpaQueryReservation, times(1)).fetchOne();

    verify(entityManager, never()).persist(any(StoreOrdersEntity.class));
    verify(entityManager, never()).persist(any(StoreOrdersMapEntity.class));

  }


  // 6. Modify Order
  @Test
  @DisplayName("가게 주문 수정 성공")
  public void modOrderSuccess() {
    // given
    OrderDTO sample = OrderDTO.sample();
    String userId = "userId";
    StoreReservationEntity entity = new StoreReservationEntity();
    StoreOrdersEntity orders = new StoreOrdersEntity();
    List<StoreOrdersMapEntity> ordersMap = new ArrayList<>();
    StoreOrdersMapEntity map = new StoreOrdersMapEntity();
    map.setFoodName("foodName");
    map.setFoodCount(0);
    ordersMap.add(map);
    orders.setChildSet(ordersMap);
    entity.setChild(orders);
    String response = "success";

    doReturn(jpaQueryReservation).when(queryFactory).select(storeReservationEntity);
    doReturn(jpaQueryReservation).when(jpaQueryReservation).from(storeReservationEntity);
    doReturn(jpaQueryOrders).when(jpaQueryReservation).leftJoin(storeReservationEntity.child,
        storeOrdersEntity);
    doReturn(jpaQueryOrders).when(jpaQueryOrders).fetchJoin();
    doReturn(jpaQueryOrdersMap).when(jpaQueryOrders).leftJoin(storeOrdersEntity.childSet,
        storeOrdersMapEntity);
    doReturn(jpaQueryOrdersMap).when(jpaQueryOrdersMap).fetchJoin();
    doReturn(jpaQueryReservation).when(jpaQueryOrdersMap).where(storeReservationEntity.storeId
        .eq(sample.getStoreId()).and(storeReservationEntity.userId.eq(userId)));
    doReturn(jpaQueryReservation).when(jpaQueryReservation).limit(1);
    doReturn(entity).when(jpaQueryReservation).fetchOne();



    // when
    String result = storePORDAOImpl.updateOrder(sample, userId);

    // then
    assertEquals(result, response);


    // verify
    verify(queryFactory, times(1)).select(storeReservationEntity);
    verify(jpaQueryReservation, times(1)).from(storeReservationEntity);
    verify(jpaQueryReservation, times(1)).leftJoin(storeReservationEntity.child, storeOrdersEntity);
    verify(jpaQueryOrders, times(1)).fetchJoin();
    verify(jpaQueryOrders, times(1)).leftJoin(storeOrdersEntity.childSet, storeOrdersMapEntity);
    verify(jpaQueryOrdersMap, times(1)).fetchJoin();
    verify(jpaQueryOrdersMap, times(1)).where(storeReservationEntity.storeId.eq(sample.getStoreId())
        .and(storeReservationEntity.userId.eq(userId)));
    verify(jpaQueryReservation, times(1)).limit(1);
    verify(jpaQueryReservation, times(1)).fetchOne();
  }

  // 7. Delete Order
  @Test
  @DisplayName("가게 주문 삭제 성공")
  public void deleteOrderSuccess() {
    // given
    int storeId = 0;
    String foodName = "foodName";
    String userId = "userId";
    String response = "success";
    StoreReservationEntity entity = mock(StoreReservationEntity.class);

    doReturn(jpaQueryReservation).when(queryFactory).select(storeReservationEntity);
    doReturn(jpaQueryReservation).when(jpaQueryReservation).from(storeReservationEntity);
    doReturn(jpaQueryOrders).when(jpaQueryReservation).leftJoin(storeReservationEntity.child,
        storeOrdersEntity);
    doReturn(jpaQueryOrders).when(jpaQueryOrders).fetchJoin();
    doReturn(jpaQueryOrdersMap).when(jpaQueryOrders).leftJoin(storeOrdersEntity.childSet,
        storeOrdersMapEntity);
    doReturn(jpaQueryOrdersMap).when(jpaQueryOrdersMap).fetchJoin();
    doReturn(jpaQueryReservation).when(jpaQueryOrdersMap).where(
        storeReservationEntity.storeId.eq(storeId).and(storeReservationEntity.userId.eq(userId)));
    doReturn(jpaQueryReservation).when(jpaQueryReservation).limit(1);
    doReturn(entity).when(jpaQueryReservation).fetchOne();

    StoreOrdersEntity child = new StoreOrdersEntity();
    child.setOrdersId(0L);
    doReturn(child).when(entity).getChild();

    doReturn(jpaDeleteClause).when(queryFactory).delete(storeOrdersMapEntity);
    doReturn(jpaDeleteClause).when(jpaDeleteClause)
        .where(storeOrdersMapEntity.storeOrdersEntity.ordersId.eq(0L)
            .and(storeOrdersMapEntity.foodName.eq(foodName)));



    // when
    String result = storePORDAOImpl.deleteOrder(storeId, foodName, userId);

    // then
    assertEquals(response, result);



    // verify
    verify(queryFactory, times(1)).select(storeReservationEntity);
    verify(jpaQueryReservation, times(1)).from(storeReservationEntity);
    verify(jpaQueryReservation, times(1)).leftJoin(storeReservationEntity.child, storeOrdersEntity);
    verify(jpaQueryOrders, times(1)).fetchJoin();
    verify(jpaQueryOrders, times(1)).leftJoin(storeOrdersEntity.childSet, storeOrdersMapEntity);
    verify(jpaQueryOrdersMap, times(1)).fetchJoin();
    verify(jpaQueryOrdersMap, times(1)).where(
        storeReservationEntity.storeId.eq(storeId).and(storeReservationEntity.userId.eq(userId)));
    verify(jpaQueryReservation, times(1)).limit(1);
    verify(jpaQueryReservation, times(1)).fetchOne();
    verify(queryFactory, times(1)).delete(storeOrdersMapEntity);
    verify(jpaDeleteClause, times(1)).where(storeOrdersMapEntity.storeOrdersEntity.ordersId.eq(0L)
        .and(storeOrdersMapEntity.foodName.eq(foodName)));
    verify(jpaDeleteClause, times(1)).execute();
  }

}
