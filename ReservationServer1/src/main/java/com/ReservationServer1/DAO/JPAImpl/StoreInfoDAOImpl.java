package com.ReservationServer1.DAO.JPAImpl;


import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.ReservationServer1.DAO.StoreInfoDAO;
import com.ReservationServer1.DAO.JPAImpl.Repository.StoreRestDayMapRepository;
import com.ReservationServer1.DAO.JPAImpl.Repository.StoreRestDayRepository;
import com.ReservationServer1.data.DTO.store.RestDayDTO;
import com.ReservationServer1.data.Entity.store.QStoreEntity;
import com.ReservationServer1.data.Entity.store.QStoreRestDaysEntity;
import com.ReservationServer1.data.Entity.store.QStoreRestDaysMapEntity;
import com.ReservationServer1.data.Entity.store.StoreEntity;
import com.ReservationServer1.data.Entity.store.StoreRestDaysEntity;
import com.ReservationServer1.data.Entity.store.StoreRestDaysMapEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;


@Repository
@Transactional
public class StoreInfoDAOImpl implements StoreInfoDAO {

  private final Logger logger = LoggerFactory.getLogger(StoreInfoDAO.class);
  private StoreRestDayRepository storeRestDayRepositoty;
  private StoreRestDayMapRepository storeRestDayMapRepositoty;
  private final JPAQueryFactory queryFactory;

  public StoreInfoDAOImpl(StoreRestDayRepository storeRestDayRepositoty,
      StoreRestDayMapRepository storeRestDayMapRepositot, JPAQueryFactory queryFactory) {
    this.storeRestDayRepositoty = storeRestDayRepositoty;
    this.storeRestDayMapRepositoty = storeRestDayMapRepositot;
    this.queryFactory = queryFactory;
  }


  @Override
  public void postDayOff(RestDayDTO restDayDTO) {
    logger.info("[StoreRestDayDAOImpl] day register(쉬는날 등록) 호출");
    StoreRestDaysEntity parent = new StoreRestDaysEntity(restDayDTO.getStoreName());
    storeRestDayRepositoty.save(parent);
    Set<StoreRestDaysMapEntity> childs = new LinkedHashSet<>();
    Set<String> keys = restDayDTO.getDate().keySet();
    for (String key : keys) {
      StoreRestDaysMapEntity child =
          new StoreRestDaysMapEntity(restDayDTO.getDate().get(key), parent);
      storeRestDayMapRepositoty.save(child);
      childs.add(child);
    }
    parent.setChildSet(childs);
  }


  @Override
  public List<String> getDayOff(String storeName) {
    logger.info("[StoreRestDayDAOImpl] get rest days(쉬는날 반환) 호출");
    QStoreRestDaysEntity parent = QStoreRestDaysEntity.storeRestDaysEntity;
    QStoreRestDaysMapEntity child = QStoreRestDaysMapEntity.storeRestDaysMapEntity;
    List<StoreRestDaysEntity> check = queryFactory
        .select(parent)
        .distinct()
        .from(parent)
        .leftJoin(parent.childSet, child).fetchJoin()
        .where(parent.storeName.eq(storeName))
        .fetch();

    
    List<String> result = new ArrayList<>();
//    List<StoreRestDaysEntity> check = storeRestDayRepositoty.findByStoreName(storeName);
    if (check.isEmpty() == false) {
      for (StoreRestDaysEntity srde : check) {
        for (StoreRestDaysMapEntity srdme : srde.getChildSet()) {
          result.add(srdme.getDate());
        }
      }
    }
    return result;
  }

  @Override
  public void deleteDayOff(RestDayDTO restDayDTO) {
    QStoreEntity now = QStoreEntity.storeEntity;
    StoreEntity result =
        queryFactory.select(now).from(now).where(now.storeName.eq("steakHouse8")).fetchOne();
    System.out.println(result);
  }

}
