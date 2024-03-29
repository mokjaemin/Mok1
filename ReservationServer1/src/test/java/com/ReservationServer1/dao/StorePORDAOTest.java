package com.ReservationServer1.dao;


import static com.ReservationServer1.data.Entity.POR.QStoreCouponEntity.storeCouponEntity;
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
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ReservationServer1.DAO.Impl.StorePORDAOImpl;
import com.ReservationServer1.data.DTO.POR.OrderDTO;
import com.ReservationServer1.data.DTO.POR.PayDTO;
import com.ReservationServer1.data.DTO.POR.ReservationDTO;
import com.ReservationServer1.data.Entity.POR.StoreCouponEntity;
import com.ReservationServer1.data.Entity.POR.StoreOrdersEntity;
import com.ReservationServer1.data.Entity.POR.StoreOrdersMapEntity;
import com.ReservationServer1.data.Entity.POR.StorePayEntity;
import com.ReservationServer1.data.Entity.POR.StoreReservationEntity;
import com.ReservationServer1.exception.NoAuthorityException;
import com.ReservationServer1.exception.NoInformationException;
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
  private JPAQuery<StoreCouponEntity> jpaQueryCoupon;

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
    ReservationDTO sample = ReservationDTO.getSample();
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
    ReservationDTO sample = ReservationDTO.getSample();
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
    short storeId = 0;
    String userId = "userId";
    StoreReservationEntity response = mock(StoreReservationEntity.class);


    doReturn(jpaQueryReservation).when(queryFactory).select(storeReservationEntity);
    doReturn(jpaQueryReservation).when(jpaQueryReservation).from(storeReservationEntity);
    doReturn(jpaQueryOrders).when(jpaQueryReservation).leftJoin(storeReservationEntity.child,
        storeOrdersEntity);
    doReturn(jpaQueryOrders).when(jpaQueryOrders).fetchJoin();
    doReturn(jpaQueryPay).when(jpaQueryOrders).leftJoin(storeOrdersEntity.payment, storePayEntity);
    doReturn(jpaQueryPay).when(jpaQueryPay).fetchJoin();
    doReturn(jpaQueryOrdersMap).when(jpaQueryPay).leftJoin(storeOrdersEntity.ordersMap,
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
    verify(jpaQueryPay, times(1)).leftJoin(storeOrdersEntity.ordersMap, storeOrdersMapEntity);
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
    short storeId = 0;
    String userId = "userId";


    doReturn(jpaQueryReservation).when(queryFactory).select(storeReservationEntity);
    doReturn(jpaQueryReservation).when(jpaQueryReservation).from(storeReservationEntity);
    doReturn(jpaQueryOrders).when(jpaQueryReservation).leftJoin(storeReservationEntity.child,
        storeOrdersEntity);
    doReturn(jpaQueryOrders).when(jpaQueryOrders).fetchJoin();
    doReturn(jpaQueryPay).when(jpaQueryOrders).leftJoin(storeOrdersEntity.payment, storePayEntity);
    doReturn(jpaQueryPay).when(jpaQueryPay).fetchJoin();
    doReturn(jpaQueryOrdersMap).when(jpaQueryPay).leftJoin(storeOrdersEntity.ordersMap,
        storeOrdersMapEntity);
    doReturn(jpaQueryOrdersMap).when(jpaQueryOrdersMap).fetchJoin();
    doReturn(jpaQueryReservation).when(jpaQueryOrdersMap).where(
        storeReservationEntity.storeId.eq(storeId).and(storeReservationEntity.userId.eq(userId)));
    doReturn(jpaQueryReservation).when(jpaQueryReservation).limit(1);
    doReturn(null).when(jpaQueryReservation).fetchOne();



    // then && when
    NoInformationException message = assertThrows(NoInformationException.class, () -> {
      storePORDAOImpl.getReservation(storeId, userId);
    });
    String expected = "해당 정보를 찾을 수 없습니다.";
    assertEquals(expected, message.getMessage());

    verify(queryFactory, times(1)).select(storeReservationEntity);
    verify(jpaQueryReservation, times(1)).from(storeReservationEntity);
    verify(jpaQueryReservation, times(1)).leftJoin(storeReservationEntity.child, storeOrdersEntity);
    verify(jpaQueryOrders, times(1)).fetchJoin();
    verify(jpaQueryOrders, times(1)).leftJoin(storeOrdersEntity.payment, storePayEntity);
    verify(jpaQueryPay, times(1)).fetchJoin();
    verify(jpaQueryPay, times(1)).leftJoin(storeOrdersEntity.ordersMap, storeOrdersMapEntity);
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
    short storeId = 0;
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




  // 6. Modify Order
  @Test
  @DisplayName("가게 주문 수정 성공")
  public void modOrderSuccess() {
    // given
    OrderDTO sample = OrderDTO.getSample();
    String userId = "userId";
    StoreReservationEntity entity = new StoreReservationEntity();
    StoreOrdersEntity orders = new StoreOrdersEntity();
    List<StoreOrdersMapEntity> ordersMap = new ArrayList<>();
    StoreOrdersMapEntity map = new StoreOrdersMapEntity();
    map.setFoodName("foodName1");
    map.setFoodCount((short) 0);
    ordersMap.add(map);
    orders.setOrdersMap(ordersMap);
    entity.setChild(orders);
    String response = "success";

    doReturn(jpaQueryReservation).when(queryFactory).select(storeReservationEntity);
    doReturn(jpaQueryReservation).when(jpaQueryReservation).from(storeReservationEntity);
    doReturn(jpaQueryOrders).when(jpaQueryReservation).leftJoin(storeReservationEntity.child,
        storeOrdersEntity);
    doReturn(jpaQueryOrders).when(jpaQueryOrders).fetchJoin();
    doReturn(jpaQueryOrdersMap).when(jpaQueryOrders).leftJoin(storeOrdersEntity.ordersMap,
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
    verify(jpaQueryOrders, times(1)).leftJoin(storeOrdersEntity.ordersMap, storeOrdersMapEntity);
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
    short storeId = 0;
    String foodName = "foodName";
    String userId = "userId";
    String response = "success";
    StoreReservationEntity entity = mock(StoreReservationEntity.class);

    doReturn(jpaQueryReservation).when(queryFactory).select(storeReservationEntity);
    doReturn(jpaQueryReservation).when(jpaQueryReservation).from(storeReservationEntity);
    doReturn(jpaQueryOrders).when(jpaQueryReservation).leftJoin(storeReservationEntity.child,
        storeOrdersEntity);
    doReturn(jpaQueryOrders).when(jpaQueryOrders).fetchJoin();
    doReturn(jpaQueryOrdersMap).when(jpaQueryOrders).leftJoin(storeOrdersEntity.ordersMap,
        storeOrdersMapEntity);
    doReturn(jpaQueryOrdersMap).when(jpaQueryOrdersMap).fetchJoin();
    doReturn(jpaQueryReservation).when(jpaQueryOrdersMap).where(
        storeReservationEntity.storeId.eq(storeId).and(storeReservationEntity.userId.eq(userId)));
    doReturn(jpaQueryReservation).when(jpaQueryReservation).limit(1);
    doReturn(entity).when(jpaQueryReservation).fetchOne();

    StoreOrdersEntity child = new StoreOrdersEntity();
    child.setOrdersId(0);
    doReturn(child).when(entity).getChild();

    doReturn(jpaDeleteClause).when(queryFactory).delete(storeOrdersMapEntity);
    doReturn(jpaDeleteClause).when(jpaDeleteClause)
        .where(storeOrdersMapEntity.storeOrdersEntity.ordersId.eq(0)
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
    verify(jpaQueryOrders, times(1)).leftJoin(storeOrdersEntity.ordersMap, storeOrdersMapEntity);
    verify(jpaQueryOrdersMap, times(1)).fetchJoin();
    verify(jpaQueryOrdersMap, times(1)).where(
        storeReservationEntity.storeId.eq(storeId).and(storeReservationEntity.userId.eq(userId)));
    verify(jpaQueryReservation, times(1)).limit(1);
    verify(jpaQueryReservation, times(1)).fetchOne();
    verify(queryFactory, times(1)).delete(storeOrdersMapEntity);
    verify(jpaDeleteClause, times(1)).where(storeOrdersMapEntity.storeOrdersEntity.ordersId.eq(0)
        .and(storeOrdersMapEntity.foodName.eq(foodName)));
    verify(jpaDeleteClause, times(1)).execute();
  }



  // 8. Register Pay
  @Test
  @DisplayName("가게 결제 등록 성공 : 기존 쿠폰 증가")
  public void registerPaySuccess() {
    // given
    String response = "success";
    PayDTO sample = PayDTO.getSample();
    StoreReservationEntity result = new StoreReservationEntity();
    result.setChild(new StoreOrdersEntity());
    result.setUserId("userId");
    StorePayEntity entity = StorePayEntity.builder().amount(sample.getAmount()).build();
    entity.setStoreOrdersEntity(result.getChild());
    result.getChild().setPayment(entity);


    doReturn(jpaQueryReservation).when(queryFactory).select(storeReservationEntity);
    doReturn(jpaQueryReservation).when(jpaQueryReservation).from(storeReservationEntity);
    doReturn(jpaQueryOrders).when(jpaQueryReservation).leftJoin(storeReservationEntity.child,
        storeOrdersEntity);
    doReturn(jpaQueryReservation).when(jpaQueryOrders)
        .where(storeReservationEntity.reservationId.eq(sample.getReservationId()));
    doReturn(jpaQueryReservation).when(jpaQueryReservation).limit(1);
    doReturn(result).when(jpaQueryReservation).fetchOne();

    doNothing().when(entityManager).persist(any(StorePayEntity.class));

    StoreCouponEntity coupon = new StoreCouponEntity();
    coupon.setAmount(0);

    doReturn(jpaQueryCoupon).when(queryFactory).select(storeCouponEntity);
    doReturn(jpaQueryCoupon).when(jpaQueryCoupon).from(storeCouponEntity);
    doReturn(jpaQueryCoupon).when(jpaQueryCoupon).where(storeCouponEntity.userId
        .eq(result.getUserId()).and(storeCouponEntity.storeId.eq(sample.getStoreId())));
    doReturn(jpaQueryCoupon).when(jpaQueryCoupon).limit(1);
    doReturn(coupon).when(jpaQueryCoupon).fetchOne();

    doNothing().when(entityManager).persist(any(StoreCouponEntity.class));


    // when
    String final_result = storePORDAOImpl.registerPay(sample);

    // then
    assertEquals(response, final_result);


    // verify
    verify(entityManager, times(1)).persist(any(StorePayEntity.class));
    verify(entityManager, never()).persist(any(StoreCouponEntity.class));

  }

  @Test
  @DisplayName("가게 결제 등록 성공 : 새로운 쿠폰 생성")
  public void registerPaySuccess1() {
    // given
    String response = "success";
    PayDTO sample = PayDTO.getSample();
    StoreReservationEntity result = new StoreReservationEntity();
    result.setChild(new StoreOrdersEntity());
    result.setUserId("userId");
    StorePayEntity entity = StorePayEntity.builder().amount(sample.getAmount()).build();
    entity.setStoreOrdersEntity(result.getChild());
    result.getChild().setPayment(entity);


    doReturn(jpaQueryReservation).when(queryFactory).select(storeReservationEntity);
    doReturn(jpaQueryReservation).when(jpaQueryReservation).from(storeReservationEntity);
    doReturn(jpaQueryOrders).when(jpaQueryReservation).leftJoin(storeReservationEntity.child,
        storeOrdersEntity);
    doReturn(jpaQueryReservation).when(jpaQueryOrders)
        .where(storeReservationEntity.reservationId.eq(sample.getReservationId()));
    doReturn(jpaQueryReservation).when(jpaQueryReservation).limit(1);
    doReturn(result).when(jpaQueryReservation).fetchOne();

    doNothing().when(entityManager).persist(any(StorePayEntity.class));


    doReturn(jpaQueryCoupon).when(queryFactory).select(storeCouponEntity);
    doReturn(jpaQueryCoupon).when(jpaQueryCoupon).from(storeCouponEntity);
    doReturn(jpaQueryCoupon).when(jpaQueryCoupon).where(storeCouponEntity.userId
        .eq(result.getUserId()).and(storeCouponEntity.storeId.eq(sample.getStoreId())));
    doReturn(jpaQueryCoupon).when(jpaQueryCoupon).limit(1);
    doReturn(null).when(jpaQueryCoupon).fetchOne();

    doNothing().when(entityManager).persist(any(StoreCouponEntity.class));


    // when
    String final_result = storePORDAOImpl.registerPay(sample);

    // then
    assertEquals(response, final_result);


    // verify
    verify(entityManager, times(1)).persist(any(StorePayEntity.class));
    verify(entityManager, times(1)).persist(any(StoreCouponEntity.class));

  }

  // 9. Delete Pay
  @Test
  @DisplayName("가게 결제 삭제 성공")
  public void deletePaySuccess() {
    // given
    String response = "success";
    int reservationId = 0;
    StoreReservationEntity result1 = new StoreReservationEntity();
    StoreOrdersEntity result2 = new StoreOrdersEntity();
    StorePayEntity result3 = new StorePayEntity();
    result3.setPaymentId(0);
    result2.setPayment(result3);
    result1.setChild(result2);
    result1.setUserId("userId");
    result1.setStoreId((short) 0);


    doReturn(jpaQueryReservation).when(queryFactory).select(storeReservationEntity);
    doReturn(jpaQueryReservation).when(jpaQueryReservation).from(storeReservationEntity);
    doReturn(jpaQueryOrders).when(jpaQueryReservation).leftJoin(storeReservationEntity.child,
        storeOrdersEntity);
    doReturn(jpaQueryOrders).when(jpaQueryOrders).fetchJoin();
    doReturn(jpaQueryPay).when(jpaQueryOrders).leftJoin(storeOrdersEntity.payment, storePayEntity);
    doReturn(jpaQueryPay).when(jpaQueryPay).fetchJoin();
    doReturn(jpaQueryReservation).when(jpaQueryPay)
        .where(storeReservationEntity.reservationId.eq(reservationId));
    doReturn(jpaQueryReservation).when(jpaQueryReservation).limit(1);
    doReturn(result1).when(jpaQueryReservation).fetchOne();


    doReturn(jpaDeleteClause).when(queryFactory).delete(storePayEntity);
    doReturn(jpaDeleteClause).when(jpaDeleteClause)
        .where(storePayEntity.paymentId.eq(result1.getChild().getPayment().getPaymentId()));



    StoreCouponEntity coupon = new StoreCouponEntity();
    coupon.setAmount(1);

    doReturn(jpaQueryCoupon).when(queryFactory).select(storeCouponEntity);
    doReturn(jpaQueryCoupon).when(jpaQueryCoupon).from(storeCouponEntity);
    doReturn(jpaQueryCoupon).when(jpaQueryCoupon).where(storeCouponEntity.userId
        .eq(result1.getUserId()).and(storeCouponEntity.storeId.eq(result1.getStoreId())));
    doReturn(jpaQueryCoupon).when(jpaQueryCoupon).limit(1);
    doReturn(coupon).when(jpaQueryCoupon).fetchOne();



    // when
    String final_result = storePORDAOImpl.deletePay(reservationId);


    // then
    assertEquals(response, final_result);
  }


  // 10. Register Comment
  @Test
  @DisplayName("가게 댓글 등록 성공")
  public void registerCommentSuccess() {
    // given
    int reservationId = 0;
    String comment = "comment";
    String userId = "userId";
    String response = "success";

    StoreReservationEntity result = new StoreReservationEntity();
    StoreOrdersEntity result2 = new StoreOrdersEntity();
    StorePayEntity result3 = new StorePayEntity();
    result3.setPaymentId(0);
    result2.setPayment(result3);
    result.setChild(result2);
    result.setUserId(userId);


    doReturn(jpaQueryReservation).when(queryFactory).select(storeReservationEntity);
    doReturn(jpaQueryReservation).when(jpaQueryReservation).from(storeReservationEntity);
    doReturn(jpaQueryOrders).when(jpaQueryReservation).leftJoin(storeReservationEntity.child,
        storeOrdersEntity);
    doReturn(jpaQueryOrders).when(jpaQueryOrders).fetchJoin();
    doReturn(jpaQueryPay).when(jpaQueryOrders).leftJoin(storeOrdersEntity.payment, storePayEntity);
    doReturn(jpaQueryPay).when(jpaQueryPay).fetchJoin();
    doReturn(jpaQueryReservation).when(jpaQueryPay)
        .where(storeReservationEntity.reservationId.eq(reservationId));
    doReturn(jpaQueryReservation).when(jpaQueryReservation).limit(1);
    doReturn(result).when(jpaQueryReservation).fetchOne();


    // when
    String final_result = storePORDAOImpl.registerComment(reservationId, comment, userId);


    // then
    assertEquals(response, final_result);
  }

  @Test
  @DisplayName("가게 댓글 등록 실패 : 권한 없음")
  public void registerCommentFail() {
    // given
    int reservationId = 0;
    String comment = "comment";
    String userId = "userId";

    StoreReservationEntity result = new StoreReservationEntity();
    StoreOrdersEntity result2 = new StoreOrdersEntity();
    StorePayEntity result3 = new StorePayEntity();
    result3.setPaymentId(0);
    result2.setPayment(result3);
    result.setChild(result2);
    result.setUserId("diffUserId");


    doReturn(jpaQueryReservation).when(queryFactory).select(storeReservationEntity);
    doReturn(jpaQueryReservation).when(jpaQueryReservation).from(storeReservationEntity);
    doReturn(jpaQueryOrders).when(jpaQueryReservation).leftJoin(storeReservationEntity.child,
        storeOrdersEntity);
    doReturn(jpaQueryOrders).when(jpaQueryOrders).fetchJoin();
    doReturn(jpaQueryPay).when(jpaQueryOrders).leftJoin(storeOrdersEntity.payment, storePayEntity);
    doReturn(jpaQueryPay).when(jpaQueryPay).fetchJoin();
    doReturn(jpaQueryReservation).when(jpaQueryPay)
        .where(storeReservationEntity.reservationId.eq(reservationId));
    doReturn(jpaQueryReservation).when(jpaQueryReservation).limit(1);
    doReturn(result).when(jpaQueryReservation).fetchOne();


    // then && when
    NoAuthorityException message = assertThrows(NoAuthorityException.class, () -> {
      storePORDAOImpl.registerComment(reservationId, comment, userId);
    });
    String expected = "해당 정보에 접근할 권한이 없습니다.";
    assertEquals(expected, message.getMessage());
  }


  // 11. Delete Comment
  @Test
  @DisplayName("가게 댓글 삭제 성공")
  public void deleteCommentSuccess() {
    // given
    int reservationId = 0;
    String userId = "userId";
    String response = "success";

    StoreReservationEntity result = new StoreReservationEntity();
    StoreOrdersEntity result2 = new StoreOrdersEntity();
    StorePayEntity result3 = new StorePayEntity();
    result3.setPaymentId(0);
    result2.setPayment(result3);
    result.setChild(result2);
    result.setUserId(userId);


    doReturn(jpaQueryReservation).when(queryFactory).select(storeReservationEntity);
    doReturn(jpaQueryReservation).when(jpaQueryReservation).from(storeReservationEntity);
    doReturn(jpaQueryOrders).when(jpaQueryReservation).leftJoin(storeReservationEntity.child,
        storeOrdersEntity);
    doReturn(jpaQueryOrders).when(jpaQueryOrders).fetchJoin();
    doReturn(jpaQueryPay).when(jpaQueryOrders).leftJoin(storeOrdersEntity.payment, storePayEntity);
    doReturn(jpaQueryPay).when(jpaQueryPay).fetchJoin();
    doReturn(jpaQueryReservation).when(jpaQueryPay)
        .where(storeReservationEntity.reservationId.eq(reservationId));
    doReturn(jpaQueryReservation).when(jpaQueryReservation).limit(1);
    doReturn(result).when(jpaQueryReservation).fetchOne();


    // when
    String final_result = storePORDAOImpl.deleteComment(reservationId, userId);


    // then
    assertEquals(response, final_result);
  }


  @Test
  @DisplayName("가게 댓글 삭제 실패 : 권한 없음")
  public void deleteCommentFail() {
    // given
    int reservationId = 0;
    String userId = "userId";

    StoreReservationEntity result = new StoreReservationEntity();
    StoreOrdersEntity result2 = new StoreOrdersEntity();
    StorePayEntity result3 = new StorePayEntity();
    result3.setPaymentId(0);
    result2.setPayment(result3);
    result.setChild(result2);
    result.setUserId("diffUserId");


    doReturn(jpaQueryReservation).when(queryFactory).select(storeReservationEntity);
    doReturn(jpaQueryReservation).when(jpaQueryReservation).from(storeReservationEntity);
    doReturn(jpaQueryOrders).when(jpaQueryReservation).leftJoin(storeReservationEntity.child,
        storeOrdersEntity);
    doReturn(jpaQueryOrders).when(jpaQueryOrders).fetchJoin();
    doReturn(jpaQueryPay).when(jpaQueryOrders).leftJoin(storeOrdersEntity.payment, storePayEntity);
    doReturn(jpaQueryPay).when(jpaQueryPay).fetchJoin();
    doReturn(jpaQueryReservation).when(jpaQueryPay)
        .where(storeReservationEntity.reservationId.eq(reservationId));
    doReturn(jpaQueryReservation).when(jpaQueryReservation).limit(1);
    doReturn(result).when(jpaQueryReservation).fetchOne();


    // then && when
    NoAuthorityException message = assertThrows(NoAuthorityException.class, () -> {
      storePORDAOImpl.deleteComment(reservationId, userId);
    });
    String expected = "해당 정보에 접근할 권한이 없습니다.";
    assertEquals(expected, message.getMessage());
  }

  // 12. Register Big Comment
  @Test
  @DisplayName("가게 대댓글 등록 성공")
  public void registerBigCommentSuccess() {
    // given
    int reservationId = 0;
    String bigcomment = "comment";
    short storeId = 0;
    String response = "success";

    StoreReservationEntity result = new StoreReservationEntity();
    StoreOrdersEntity result2 = new StoreOrdersEntity();
    StorePayEntity result3 = new StorePayEntity();
    result3.setPaymentId(0);
    result2.setPayment(result3);
    result.setChild(result2);
    result.setStoreId(storeId);


    doReturn(jpaQueryReservation).when(queryFactory).select(storeReservationEntity);
    doReturn(jpaQueryReservation).when(jpaQueryReservation).from(storeReservationEntity);
    doReturn(jpaQueryOrders).when(jpaQueryReservation).leftJoin(storeReservationEntity.child,
        storeOrdersEntity);
    doReturn(jpaQueryOrders).when(jpaQueryOrders).fetchJoin();
    doReturn(jpaQueryPay).when(jpaQueryOrders).leftJoin(storeOrdersEntity.payment, storePayEntity);
    doReturn(jpaQueryPay).when(jpaQueryPay).fetchJoin();
    doReturn(jpaQueryReservation).when(jpaQueryPay)
        .where(storeReservationEntity.reservationId.eq(reservationId));
    doReturn(jpaQueryReservation).when(jpaQueryReservation).limit(1);
    doReturn(result).when(jpaQueryReservation).fetchOne();


    // when
    String final_result = storePORDAOImpl.registerBigComment(reservationId, bigcomment, storeId);


    // then
    assertEquals(response, final_result);
  }

  @Test
  @DisplayName("가게 대댓글 등록 실패 : 권한 없음")
  public void registerBigCommentFail() {
    // given
    int reservationId = 0;
    String comment = "comment";
    short storeId = 0;

    StoreReservationEntity result = new StoreReservationEntity();
    StoreOrdersEntity result2 = new StoreOrdersEntity();
    StorePayEntity result3 = new StorePayEntity();
    result3.setPaymentId(0);
    result2.setPayment(result3);
    result.setChild(result2);
    result.setStoreId((short) 1);


    doReturn(jpaQueryReservation).when(queryFactory).select(storeReservationEntity);
    doReturn(jpaQueryReservation).when(jpaQueryReservation).from(storeReservationEntity);
    doReturn(jpaQueryOrders).when(jpaQueryReservation).leftJoin(storeReservationEntity.child,
        storeOrdersEntity);
    doReturn(jpaQueryOrders).when(jpaQueryOrders).fetchJoin();
    doReturn(jpaQueryPay).when(jpaQueryOrders).leftJoin(storeOrdersEntity.payment, storePayEntity);
    doReturn(jpaQueryPay).when(jpaQueryPay).fetchJoin();
    doReturn(jpaQueryReservation).when(jpaQueryPay)
        .where(storeReservationEntity.reservationId.eq(reservationId));
    doReturn(jpaQueryReservation).when(jpaQueryReservation).limit(1);
    doReturn(result).when(jpaQueryReservation).fetchOne();


    // then && when
    NoAuthorityException message = assertThrows(NoAuthorityException.class, () -> {
      storePORDAOImpl.registerBigComment(reservationId, comment, storeId);
    });
    String expected = "해당 정보에 접근할 권한이 없습니다.";
    assertEquals(expected, message.getMessage());
  }


  // 13. Delete Big Comment
  @Test
  @DisplayName("가게 대댓글 삭제 성공")
  public void deleteBigCommentSuccess() {
    // given
    int reservationId = 0;
    short storeId = 0;
    String response = "success";

    StoreReservationEntity result = new StoreReservationEntity();
    StoreOrdersEntity result2 = new StoreOrdersEntity();
    StorePayEntity result3 = new StorePayEntity();
    result3.setPaymentId(0);
    result2.setPayment(result3);
    result.setChild(result2);
    result.setStoreId(storeId);


    doReturn(jpaQueryReservation).when(queryFactory).select(storeReservationEntity);
    doReturn(jpaQueryReservation).when(jpaQueryReservation).from(storeReservationEntity);
    doReturn(jpaQueryOrders).when(jpaQueryReservation).leftJoin(storeReservationEntity.child,
        storeOrdersEntity);
    doReturn(jpaQueryOrders).when(jpaQueryOrders).fetchJoin();
    doReturn(jpaQueryPay).when(jpaQueryOrders).leftJoin(storeOrdersEntity.payment, storePayEntity);
    doReturn(jpaQueryPay).when(jpaQueryPay).fetchJoin();
    doReturn(jpaQueryReservation).when(jpaQueryPay)
        .where(storeReservationEntity.reservationId.eq(reservationId));
    doReturn(jpaQueryReservation).when(jpaQueryReservation).limit(1);
    doReturn(result).when(jpaQueryReservation).fetchOne();


    // when
    String final_result = storePORDAOImpl.deleteBigComment(reservationId, storeId);


    // then
    assertEquals(response, final_result);
  }


  @Test
  @DisplayName("가게 대댓글 삭제 실패 : 권한 없음")
  public void deleteBigCommentFail() {
    // given
    int reservationId = 0;
    short storeId = 0;

    StoreReservationEntity result = new StoreReservationEntity();
    StoreOrdersEntity result2 = new StoreOrdersEntity();
    StorePayEntity result3 = new StorePayEntity();
    result3.setPaymentId(0);
    result2.setPayment(result3);
    result.setChild(result2);
    result.setStoreId((short) 1);


    doReturn(jpaQueryReservation).when(queryFactory).select(storeReservationEntity);
    doReturn(jpaQueryReservation).when(jpaQueryReservation).from(storeReservationEntity);
    doReturn(jpaQueryOrders).when(jpaQueryReservation).leftJoin(storeReservationEntity.child,
        storeOrdersEntity);
    doReturn(jpaQueryOrders).when(jpaQueryOrders).fetchJoin();
    doReturn(jpaQueryPay).when(jpaQueryOrders).leftJoin(storeOrdersEntity.payment, storePayEntity);
    doReturn(jpaQueryPay).when(jpaQueryPay).fetchJoin();
    doReturn(jpaQueryReservation).when(jpaQueryPay)
        .where(storeReservationEntity.reservationId.eq(reservationId));
    doReturn(jpaQueryReservation).when(jpaQueryReservation).limit(1);
    doReturn(result).when(jpaQueryReservation).fetchOne();


    // then && when
    NoAuthorityException message = assertThrows(NoAuthorityException.class, () -> {
      storePORDAOImpl.deleteBigComment(reservationId, storeId);
    });
    String expected = "해당 정보에 접근할 권한이 없습니다.";
    assertEquals(expected, message.getMessage());
  }


  // 15. Get Coupon For Store
  @Test
  @DisplayName("가게별 쿠폰 출력 성공")
  public void getCouponStoreSuccess() {
    // given
    short storeId = 0;
    List<StoreCouponEntity> entity = new ArrayList<>();
    StoreCouponEntity coupon = StoreCouponEntity.getSample();
    entity.add(coupon);
    HashMap<String, Integer> result = new HashMap<>();
    for (StoreCouponEntity now : entity) {
      result.put(now.getUserId(), now.getAmount());
    }

    doReturn(jpaQueryCoupon).when(queryFactory).select(storeCouponEntity);
    doReturn(jpaQueryCoupon).when(jpaQueryCoupon).from(storeCouponEntity);
    doReturn(jpaQueryCoupon).when(jpaQueryCoupon).where(storeCouponEntity.storeId.eq(storeId));
    doReturn(entity).when(jpaQueryCoupon).fetch();


    // when
    HashMap<String, Integer> response = storePORDAOImpl.getCouponOfStore(storeId);


    // then
    assertEquals(response, result);

  }


  @Test
  @DisplayName("가게별 쿠폰 출력 실패 : 정보 없음")
  public void getCouponStoreFail() {
    // given
    short storeId = 0;
    List<StoreCouponEntity> result = new ArrayList<>();
    doReturn(jpaQueryCoupon).when(queryFactory).select(storeCouponEntity);
    doReturn(jpaQueryCoupon).when(jpaQueryCoupon).from(storeCouponEntity);
    doReturn(jpaQueryCoupon).when(jpaQueryCoupon).where(storeCouponEntity.storeId.eq(storeId));
    doReturn(result).when(jpaQueryCoupon).fetch();


    // then && when
    NoInformationException message = assertThrows(NoInformationException.class, () -> {
      storePORDAOImpl.getCouponOfStore(storeId);
    });
    String expected = "해당 정보를 찾을 수 없습니다.";
    assertEquals(expected, message.getMessage());

  }


}
