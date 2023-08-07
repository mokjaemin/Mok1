package com.ReservationServer1.DAO.Impl;

import static com.ReservationServer1.data.Entity.store.QStoreEntity.storeEntity;
import java.util.HashMap;
import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.ReservationServer1.DAO.StoreDAO;
import com.ReservationServer1.data.Entity.store.StoreEntity;
import com.ReservationServer1.exception.MessageException;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;


@Repository("StoreDAO")
@Transactional
public class StoreDAOImpl implements StoreDAO {

  private final JPAQueryFactory queryFactory;
  private final EntityManager entityManager;

  public StoreDAOImpl(JPAQueryFactory queryFactory, EntityManager entityManager) {
    this.entityManager = entityManager;
    this.queryFactory = queryFactory;
  }

  @Override
  public String registerStore(StoreEntity storeEntity) {
    entityManager.persist(storeEntity);
    return "success";
  }

  @Override
  public HashMap<String, Integer> getStoreList(String country, String city, String dong, String type, int page,
      int size) {
    List<StoreEntity> storeInfo = queryFactory.select(storeEntity).from(storeEntity)
        .where(storeEntity.country.eq(country).and(storeEntity.city.eq(city))
            .and(storeEntity.dong.eq(dong)).and(storeEntity.type.eq(type)))
        .limit(size).offset(page * size).fetch();
    HashMap<String, Integer> result = new HashMap<>();
    for(StoreEntity info : storeInfo) {
      result.put(info.getStoreName(), info.getStoreId());
    }
    return result;
  }

  @Override
  public String loginStore(int storeId) {
    String ownerId = queryFactory.select(storeEntity.ownerId).from(storeEntity)
        .where(storeEntity.storeId.eq(storeId)).fetchFirst();
    return ownerId;
  }

}
