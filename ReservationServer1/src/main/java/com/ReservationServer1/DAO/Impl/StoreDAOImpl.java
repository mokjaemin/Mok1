package com.ReservationServer1.DAO.Impl;

import static com.ReservationServer1.data.Entity.store.QStoreEntity.storeEntity;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.ReservationServer1.DAO.StoreDAO;
import com.ReservationServer1.DAO.DB.DBMS.store.StoreDB;
import com.ReservationServer1.data.Entity.store.StoreEntity;
import com.ReservationServer1.exception.MessageException;
import com.querydsl.jpa.impl.JPAQueryFactory;


@Repository("StoreDAO")
@Transactional
public class StoreDAOImpl implements StoreDAO {

  private final StoreDB storeDB;
  private final JPAQueryFactory queryFactory;

  public StoreDAOImpl(StoreDB storeDB, JPAQueryFactory queryFactory) {
    this.storeDB = storeDB;
    this.queryFactory = queryFactory;
  }

  @Override
  public String registerStore(StoreEntity storeEntity) {
    storeDB.save(storeEntity);
    return "success";
  }

  @Override
  public List<String> getStoreList(String country, String city, String dong, String type, int page,
      int size) {
    List<String> store_name = queryFactory.select(storeEntity.storeName).from(storeEntity)
        .where(storeEntity.country.eq(country).and(storeEntity.city.eq(city))
            .and(storeEntity.dong.eq(dong)).and(storeEntity.type.eq(type)))
        .limit(size).offset(page * size).fetch();
    return store_name.stream().distinct().sorted().collect(Collectors.toList());
  }

  @Override
  public String loginStore(String storeName) {
    String ownerId = queryFactory.select(storeEntity.ownerId).from(storeEntity)
        .where(storeEntity.storeName.eq(storeName)).fetchFirst();
    if (ownerId == null) {
      throw new MessageException("존재하지 않는 가게입니다.");
    }
    return ownerId;
  }

}
