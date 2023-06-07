package com.ReservationServer1.DAO.Impl;


import static com.ReservationServer1.data.Entity.store.QStoreRestDaysEntity.storeRestDaysEntity;
import static com.ReservationServer1.data.Entity.store.QStoreRestDaysMapEntity.storeRestDaysMapEntity;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.ReservationServer1.DAO.StoreInfoDAO;
import com.ReservationServer1.DAO.DB.DBMS.storeInfo.StoreRestDayMapRepository;
import com.ReservationServer1.DAO.DB.DBMS.storeInfo.StoreRestDayRepository;
import com.ReservationServer1.DAO.DB.DBMS.storeInfo.StoreTimeInfoMapRepository;
import com.ReservationServer1.DAO.DB.DBMS.storeInfo.StoreTimeInfoRepository;
import com.ReservationServer1.data.DTO.store.StoreRestDayDTO;
import com.ReservationServer1.data.DTO.store.StoreTimeInfoDTO;
import com.ReservationServer1.data.Entity.store.StoreRestDaysEntity;
import com.ReservationServer1.data.Entity.store.StoreRestDaysMapEntity;
import com.ReservationServer1.data.Entity.store.StoreTimeInfoEntity;
import com.ReservationServer1.data.Entity.store.StoreTimeInfoMapEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;


@Repository
@Transactional
public class StoreInfoDAOImpl implements StoreInfoDAO {

  private final StoreRestDayRepository storeRestDayRepository;
  private final StoreRestDayMapRepository storeRestDayMapRepository;
  private final StoreTimeInfoRepository storeTimeInfoRepository;
  private final StoreTimeInfoMapRepository storeTimeInfoMapRepository;
  private final JPAQueryFactory queryFactory;

  public StoreInfoDAOImpl(StoreRestDayRepository storeRestDayRepository,
      StoreRestDayMapRepository storeRestDayMapRepository,
      StoreTimeInfoRepository storeTimeInfoRepository,
      StoreTimeInfoMapRepository storeTimeInfoMapRepository, JPAQueryFactory queryFactory) {
    this.storeRestDayRepository = storeRestDayRepository;
    this.storeRestDayMapRepository = storeRestDayMapRepository;
    this.storeTimeInfoRepository = storeTimeInfoRepository;
    this.storeTimeInfoMapRepository = storeTimeInfoMapRepository;
    this.queryFactory = queryFactory;
  }


  @Override
  public String registerDayOff(StoreRestDayDTO restDayDTO) {
    Map<String, String> dateInfo = restDayDTO.getDate();
    Set<String> keys = dateInfo.keySet();
    Set<StoreRestDaysMapEntity> childs = new HashSet<>();
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
  public String deleteDayOff(StoreRestDayDTO restDayDTO) {
    String storeName = restDayDTO.getStoreName();
    Map<String, String> date = restDayDTO.getDate();
    for (String key : date.keySet()) {
      String day = date.get(key);
      Long days_id = storeRestDayMapRepository.findDaysIdByDate(day);
      storeRestDayMapRepository.deleteByStoreNameAndDate(storeName, day);
      Integer exist_check = queryFactory.selectOne().from(storeRestDaysMapEntity)
          .where(storeRestDaysMapEntity.storeRestDaysEntity.daysId.eq(days_id)).fetchFirst();
      if (exist_check == null) {
        storeRestDayRepository.deleteByDaysId(days_id);
      }
    }
    return "success";
  }


  @Override
  public String registerTimeInfo(StoreTimeInfoDTO storeTimeInfoDTO) {
    System.out.println(storeTimeInfoDTO.toString());
    StoreTimeInfoEntity storeTimeInfoEntity =
        StoreTimeInfoEntity.builder().startTime(storeTimeInfoDTO.getStartTime())
            .endTime(storeTimeInfoDTO.getEndTime()).intervalTime(storeTimeInfoDTO.getIntervalTime())
            .storeName(storeTimeInfoDTO.getStoreName()).build();
    storeTimeInfoRepository.save(storeTimeInfoEntity);
    
    Set<String> keys = storeTimeInfoDTO.getBreakTime().keySet();
    Set<StoreTimeInfoMapEntity> breakTimes = new HashSet<>();
    for(String key : keys) {
      String time = storeTimeInfoDTO.getBreakTime().get(key);
      StoreTimeInfoMapEntity storeTimeInfoMapEntity = StoreTimeInfoMapEntity.builder().time(time).storeTimeInfoEntity(storeTimeInfoEntity).build();
      storeTimeInfoMapRepository.save(storeTimeInfoMapEntity);
      breakTimes.add(storeTimeInfoMapEntity);
    }
    storeTimeInfoEntity.setBreakTime(breakTimes);
    return "success";
  }

}
