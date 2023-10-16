package com.ReservationServer1.DataBase;

import static com.ReservationServer1.data.Entity.POR.QStoreOrdersEntity.storeOrdersEntity;
import static com.ReservationServer1.data.Entity.POR.QStoreReservationEntity.storeReservationEntity;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import com.ReservationServer1.data.DTO.POR.ReservationDTO;
import com.ReservationServer1.data.Entity.POR.StoreOrdersEntity;
import com.ReservationServer1.data.Entity.POR.StoreReservationEntity;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;


@DataJpaTest
@ActiveProfiles("test")
@Transactional
public class DBPractice {


  @Autowired
  private TestEntityManager testEntityManager;

  private JPAQueryFactory queryFactory;

  private EntityManager em;



  @BeforeEach
  void init() {
    em = testEntityManager.getEntityManager();
    queryFactory = new JPAQueryFactory(em);
  }


  @Test
  @DisplayName("Entity 자제 Test")
  public void Test1() {

    // 예약 저장
    StoreReservationEntity reservation = StoreReservationEntity.builder().userId("userId").build();
    em.persist(reservation);

    // 동기화
    em.flush();
    em.clear();

    // 예약 조회
    StoreReservationEntity reservation1 =
        queryFactory.select(storeReservationEntity).from(storeReservationEntity)
            .where(storeReservationEntity.userId.eq("userId")).fetchFirst();


    // 주문 저장 - 예약의 Id만 활용
    StoreOrdersEntity orders = StoreOrdersEntity.builder().build();
    orders.setStoreReservationEntity(
        StoreReservationEntity.builder().reservationId(reservation1.getReservationId()).build());
    em.persist(orders);

    // 동기화
    em.flush();
    em.clear();


    // 예약 조회
    StoreReservationEntity reservation2 =
        queryFactory.select(storeReservationEntity).from(storeReservationEntity)
            .where(storeReservationEntity.userId.eq("userId")).fetchFirst();

    // 동기화
    em.flush();
    em.clear();

    // 주문 조회
    StoreOrdersEntity orders1 =
        queryFactory.select(storeOrdersEntity).from(storeOrdersEntity).fetchFirst();

    // 검증
    assertTrue(reservation2.getChild().getOrdersId() == orders1.getOrdersId());


    em.flush();
    em.clear();

    ReservationDTO dto =
        queryFactory.select(Projections.fields(ReservationDTO.class, storeReservationEntity.storeId,
            Expressions.asString("date").as("date"), storeReservationEntity.time,
            storeReservationEntity.storeTable)).from(storeReservationEntity).fetchFirst();
    System.out.println(dto);

  }

}
