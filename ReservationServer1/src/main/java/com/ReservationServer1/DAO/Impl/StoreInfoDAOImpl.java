package com.ReservationServer1.DAO.Impl;


import static com.ReservationServer1.data.Entity.store.QStoreRestDaysEntity.storeRestDaysEntity;
import static com.ReservationServer1.data.Entity.store.QStoreRestDaysMapEntity.storeRestDaysMapEntity;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.ReservationServer1.DAO.StoreInfoDAO;
import com.ReservationServer1.DAO.DB.DBMS.StoreRestDayMapRepository;
import com.ReservationServer1.DAO.DB.DBMS.StoreRestDayRepository;
import com.ReservationServer1.data.DTO.store.RestDayDTO;
import com.ReservationServer1.data.Entity.store.StoreRestDaysEntity;
import com.ReservationServer1.data.Entity.store.StoreRestDaysMapEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;


@Repository
@Transactional
public class StoreInfoDAOImpl implements StoreInfoDAO {

  private final StoreRestDayRepository storeRestDayRepository;
  private final StoreRestDayMapRepository storeRestDayMapRepository;
  private final JPAQueryFactory queryFactory;

  public StoreInfoDAOImpl(StoreRestDayRepository storeRestDayRepository,
      StoreRestDayMapRepository storeRestDayMapRepository, JPAQueryFactory queryFactory) {
    this.storeRestDayRepository = storeRestDayRepository;
    this.storeRestDayMapRepository = storeRestDayMapRepository;
    this.queryFactory = queryFactory;
  }


  @Override
  public String registerDayOff(RestDayDTO restDayDTO) {
    Map<String, String> dateInfo = restDayDTO.getDate();
    Set<String> keys = dateInfo.keySet();
    Set<StoreRestDaysMapEntity> childs = new LinkedHashSet<>();
    StoreRestDaysEntity parent = new StoreRestDaysEntity(restDayDTO.getStoreName());
    storeRestDayRepository.save(parent);
    for (String key : keys) {
      StoreRestDaysMapEntity child = new StoreRestDaysMapEntity(dateInfo.get(key), parent);
      storeRestDayMapRepository.save(child);
      childs.add(child);
    }
    parent.setChildSet(childs);
    return "success";
  }


  @Override
  public List<String> getDayOff(String storeName) {
    List<String> resultList =
        queryFactory.selectDistinct(storeRestDaysMapEntity.date).from(storeRestDaysMapEntity)
            .leftJoin(storeRestDaysMapEntity.storeRestDaysEntity, storeRestDaysEntity)
            .where(storeRestDaysEntity.storeName.eq(storeName)).fetch();
    return resultList.stream().sorted().collect(Collectors.toList());
  }


  @Override
  public String deleteDayOff(RestDayDTO restDayDTO) {
    String storeName = restDayDTO.getStoreName();
    Map<String, String> date = restDayDTO.getDate();
    for (String key : date.keySet()) {
      String day = date.get(key);
      Long id = storeRestDayMapRepository.findDaysIdByDate(day);
      storeRestDayMapRepository.deleteByStoreNameAndDate(storeName, day);
      Integer exist_check = queryFactory
          .selectOne()
          .from(storeRestDaysMapEntity)
          .where(storeRestDaysMapEntity.storeRestDaysEntity.daysId.eq(id))
          .fetchFirst();
      if (exist_check == null) {
        storeRestDayRepository.deleteByDaysId(id);
      }
    }
    return "success";
  }

}
