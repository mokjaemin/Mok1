package com.ReservationServer1.DAO.Impl;


import static com.ReservationServer1.data.Entity.store.QStoreRestDaysEntity.storeRestDaysEntity;
import static com.ReservationServer1.data.Entity.store.QStoreRestDaysMapEntity.storeRestDaysMapEntity;
import static com.ReservationServer1.data.Entity.store.QStoreTimeInfoEntity.storeTimeInfoEntity;
import static com.ReservationServer1.data.Entity.store.QStoreTimeInfoMapEntity.storeTimeInfoMapEntity;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.ReservationServer1.DAO.StoreInfoDAO;
import com.ReservationServer1.DAO.DB.DBMS.storeInfo.StoreRestDayDB;
import com.ReservationServer1.DAO.DB.DBMS.storeInfo.StoreRestDayMapDB;
import com.ReservationServer1.DAO.DB.DBMS.storeInfo.StoreTimeInfoDB;
import com.ReservationServer1.DAO.DB.DBMS.storeInfo.StoreTimeInfoMapDB;
import com.ReservationServer1.data.DTO.store.StoreRestDayDTO;
import com.ReservationServer1.data.DTO.store.StoreTimeInfoDTO;
import com.ReservationServer1.data.Entity.store.StoreRestDaysEntity;
import com.ReservationServer1.data.Entity.store.StoreRestDaysMapEntity;
import com.ReservationServer1.data.Entity.store.StoreTimeInfoEntity;
import com.ReservationServer1.data.Entity.store.StoreTimeInfoMapEntity;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;


@Repository("StoreInfoDAO")
@Transactional
public class StoreInfoDAOImpl implements StoreInfoDAO {

  private final StoreRestDayDB storeRestDayDB;
  private final StoreRestDayMapDB storeRestDayMapDB;
  private final StoreTimeInfoDB storeTimeInfoDB;
  private final StoreTimeInfoMapDB storeTimeInfoMapDB;
  private final JPAQueryFactory queryFactory;

  public StoreInfoDAOImpl(StoreRestDayDB storeRestDayDB, StoreRestDayMapDB storeRestDayMapDB,
      StoreTimeInfoDB storeTimeInfoDB, StoreTimeInfoMapDB storeTimeInfoMapDB,
      JPAQueryFactory queryFactory) {
    this.storeRestDayDB = storeRestDayDB;
    this.storeRestDayMapDB = storeRestDayMapDB;
    this.storeTimeInfoDB = storeTimeInfoDB;
    this.storeTimeInfoMapDB = storeTimeInfoMapDB;
    this.queryFactory = queryFactory;
  }


  @Override
  public String registerDayOff(StoreRestDayDTO restDayDTO) {
    Set<StoreRestDaysMapEntity> childs = new HashSet<>();
    StoreRestDaysEntity parent = new StoreRestDaysEntity(restDayDTO.getStoreName());
    storeRestDayDB.save(parent);
    for (String date : restDayDTO.getDate()) {
      StoreRestDaysMapEntity child = new StoreRestDaysMapEntity(date, parent);
      storeRestDayMapDB.save(child);
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
  public String deleteDayOff(StoreRestDayDTO restDayDTO) {
    String storeName = restDayDTO.getStoreName();
    Set<String> date = restDayDTO.getDate();
    for (String day : date) {
      Long days_id = storeRestDayMapDB.findDaysIdByDate(day);
      storeRestDayMapDB.deleteByStoreNameAndDate(storeName, day);
      Integer exist_check = queryFactory.selectOne().from(storeRestDaysMapEntity)
          .where(storeRestDaysMapEntity.storeRestDaysEntity.daysId.eq(days_id)).fetchFirst();
      if (exist_check == null) {
        storeRestDayDB.deleteByDaysId(days_id);
      }
    }
    return "success";
  }


  @Override
  public String registerTimeInfo(StoreTimeInfoDTO storeTimeInfoDTO) {
    StoreTimeInfoEntity storeTimeInfoEntity =
        StoreTimeInfoEntity.builder().startTime(storeTimeInfoDTO.getStartTime())
            .endTime(storeTimeInfoDTO.getEndTime()).intervalTime(storeTimeInfoDTO.getIntervalTime())
            .storeName(storeTimeInfoDTO.getStoreName()).build();
    storeTimeInfoDB.save(storeTimeInfoEntity);
    Set<StoreTimeInfoMapEntity> childs = new HashSet<>();
    for (String time : storeTimeInfoDTO.getBreakTime()) {
      StoreTimeInfoMapEntity storeTimeInfoMapEntity = StoreTimeInfoMapEntity.builder().time(time)
          .storeTimeInfoEntity(storeTimeInfoEntity).build();
      storeTimeInfoMapDB.save(storeTimeInfoMapEntity);
      childs.add(storeTimeInfoMapEntity);
    }
    storeTimeInfoEntity.setBreakTime(childs);
    return "success";
  }


  @Override
  public StoreTimeInfoDTO getTimeInfo(String storeName) {
    List<String> resultList =
        queryFactory.selectDistinct(storeTimeInfoMapEntity.time).from(storeTimeInfoMapEntity)
            .leftJoin(storeTimeInfoMapEntity.storeTimeInfoEntity, storeTimeInfoEntity)
            .where(storeTimeInfoEntity.storeName.eq(storeName)).fetch();
    StoreTimeInfoDTO result = queryFactory
        .select(Projections.fields(StoreTimeInfoDTO.class,
            storeTimeInfoEntity.startTime,
            storeTimeInfoEntity.endTime,
            storeTimeInfoEntity.intervalTime,
            Expressions.asString(storeName).as("storeName")
        ))
        .from(storeTimeInfoEntity)
        .where(storeTimeInfoEntity.storeName.eq(storeName))
        .fetchOne();
    result.setBreakTime(resultList.stream().sorted().toList());
    return result;
  }

}
