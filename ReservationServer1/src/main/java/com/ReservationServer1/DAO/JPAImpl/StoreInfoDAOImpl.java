package com.ReservationServer1.DAO.JPAImpl;


import static com.ReservationServer1.data.Entity.store.QStoreRestDaysEntity.storeRestDaysEntity;
import static com.ReservationServer1.data.Entity.store.QStoreRestDaysMapEntity.storeRestDaysMapEntity;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.ReservationServer1.DAO.StoreInfoDAO;
import com.ReservationServer1.DAO.JPAImpl.Repository.StoreRestDayMapRepository;
import com.ReservationServer1.DAO.JPAImpl.Repository.StoreRestDayRepository;
import com.ReservationServer1.data.DTO.store.RestDayDTO;
import com.ReservationServer1.data.Entity.store.StoreRestDaysEntity;
import com.ReservationServer1.data.Entity.store.StoreRestDaysMapEntity;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;


@Repository
@Transactional
public class StoreInfoDAOImpl implements StoreInfoDAO {

  private final Logger logger = LoggerFactory.getLogger(StoreInfoDAO.class);
  private StoreRestDayRepository storeRestDayRepository;
  private StoreRestDayMapRepository storeRestDayMapRepository;
  private final JPAQueryFactory queryFactory;

  public StoreInfoDAOImpl(StoreRestDayRepository storeRestDayRepository,
      StoreRestDayMapRepository storeRestDayMapRepository, JPAQueryFactory queryFactory) {
    this.storeRestDayRepository = storeRestDayRepository;
    this.storeRestDayMapRepository = storeRestDayMapRepository;
    this.queryFactory = queryFactory;
  }


  @Override
  public void postDayOff(RestDayDTO restDayDTO) {
    logger.info("[StoreRestDayDAOImpl] day register(쉬는날 등록) 호출");
    StoreRestDaysEntity parent = new StoreRestDaysEntity(restDayDTO.getStoreName());
    storeRestDayRepository.save(parent);
    Set<StoreRestDaysMapEntity> childs = new LinkedHashSet<>();
    Set<String> keys = restDayDTO.getDate().keySet();
    for (String key : keys) {
      StoreRestDaysMapEntity child =
          new StoreRestDaysMapEntity(restDayDTO.getDate().get(key), parent);
      storeRestDayMapRepository.save(child);
      childs.add(child);
    }
    parent.setChildSet(childs);
  }


  @Override
  public List<String> getDayOff(String storeName) {
    logger.info("[StoreRestDayDAOImpl] get rest days(쉬는날 반환) 호출");
    List<StoreRestDaysEntity> check = queryFactory.select(storeRestDaysEntity).distinct()
        .from(storeRestDaysEntity).leftJoin(storeRestDaysEntity.childSet, storeRestDaysMapEntity)
        .fetchJoin().where(storeRestDaysEntity.storeName.eq(storeName)).fetch();
    List<String> result = new ArrayList<>();
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
    String storeName = restDayDTO.getStoreName();
    Map<String, String> date = restDayDTO.getDate();
    for (String key : date.keySet()) {
      String day = date.get(key);
      String id = storeRestDayMapRepository.findDaysIdByDate(day);
      storeRestDayMapRepository.deleteByStoreNameAndDate(storeName, day);
      if(storeRestDayMapRepository.findCountByDaysId(id) == 0) {
        storeRestDayRepository.deleteByDaysId(id);
      }
    }
  }

}
