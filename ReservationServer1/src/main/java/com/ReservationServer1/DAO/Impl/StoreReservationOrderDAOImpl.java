package com.ReservationServer1.DAO.Impl;


import static com.ReservationServer1.data.Entity.ReservationAndOrder.QStoreReservationEntity.storeReservationEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.ReservationServer1.DAO.StoreReservationOrderDAO;
import com.ReservationServer1.data.DTO.ReservationOrder.ReservationDTO;
import com.ReservationServer1.data.Entity.ReservationAndOrder.StoreReservationEntity;
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
    System.out.println(storeReservationEntity.toString());
    entityManager.persist(storeReservationEntity);
    return "success";
  }

}
