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
import com.ReservationServer1.data.Entity.store.StoreTableInfoEntity;
import com.ReservationServer1.data.Entity.store.StoreTimeInfoEntity;
import com.ReservationServer1.data.Entity.store.StoreTimeInfoMapEntity;
import com.ReservationServer1.exception.MessageException;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;


@Repository("StoreInfoDAO")
@Transactional
public class StoreInfoDAOImpl implements StoreInfoDAO {

  private final StoreRestDayDB storeRestDayDB;
  private final StoreRestDayMapDB storeRestDayMapDB;
  private final StoreTimeInfoDB storeTimeInfoDB;
  private final StoreTimeInfoMapDB storeTimeInfoMapDB;
  private final JPAQueryFactory queryFactory;
  private final EntityManager entityManager;

  public StoreInfoDAOImpl(StoreRestDayDB storeRestDayDB, StoreRestDayMapDB storeRestDayMapDB,
      StoreTimeInfoDB storeTimeInfoDB, StoreTimeInfoMapDB storeTimeInfoMapDB,
      JPAQueryFactory queryFactory, EntityManager entityManager) {
    this.storeRestDayDB = storeRestDayDB;
    this.storeRestDayMapDB = storeRestDayMapDB;
    this.storeTimeInfoDB = storeTimeInfoDB;
    this.storeTimeInfoMapDB = storeTimeInfoMapDB;
    this.queryFactory = queryFactory;
    this.entityManager = entityManager;
  }


  @Override
  public String registerDayOff(StoreRestDayDTO restDayDTO) {
    StoreRestDaysEntity parent = new StoreRestDaysEntity(restDayDTO.getStoreName());
    Set<StoreRestDaysMapEntity> childs = new HashSet<>();
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
      // 만약 자식 테이블 비었다면 해당 부모테이블도 삭제
      if (exist_check == null) {
        storeRestDayDB.deleteByDaysId(days_id);
      }
    }
    return "success";
  }


  @Override
  public String registerTimeInfo(StoreTimeInfoDTO storeTimeInfoDTO) {

    // 부모 객체 생성
    StoreTimeInfoEntity parent =
        StoreTimeInfoEntity.builder().startTime(storeTimeInfoDTO.getStartTime())
            .endTime(storeTimeInfoDTO.getEndTime()).intervalTime(storeTimeInfoDTO.getIntervalTime())
            .storeName(storeTimeInfoDTO.getStoreName()).build();

    // 부모 테이블 저장
    storeTimeInfoDB.save(parent);

    // 자식들 컬렉션 생성
    Set<StoreTimeInfoMapEntity> childs = new HashSet<>();

    for (String time : storeTimeInfoDTO.getBreakTime()) {
      // 자식 객체 생성
      StoreTimeInfoMapEntity child =
          StoreTimeInfoMapEntity.builder().time(time).storeTimeInfoEntity(parent).build();

      // 자식 테이블 저장
      storeTimeInfoMapDB.save(child);
      childs.add(child);
    }
    // 부모테이블에 자식테이블 저장
    parent.setBreakTime(childs);
    return "success";
  }


  @Override
  public StoreTimeInfoDTO getTimeInfo(String storeName) {
    // 자식 테이블에서 쉬는 시간 불러옴
    List<String> resultList =
        queryFactory.selectDistinct(storeTimeInfoMapEntity.time).from(storeTimeInfoMapEntity)
            .where(storeTimeInfoMapEntity.storeTimeInfoEntity.storeName.eq(storeName)).fetch();
    if(resultList == null) {
      throw new MessageException("정보가 존재하지 않습니다.");
    }
    // 부모테이블의 데이터 불러옴
    StoreTimeInfoDTO result = queryFactory
        .select(Projections.fields(StoreTimeInfoDTO.class, storeTimeInfoEntity.startTime,
            storeTimeInfoEntity.endTime, storeTimeInfoEntity.intervalTime,
            Expressions.asString(storeName).as("storeName")))
        .from(storeTimeInfoEntity).where(storeTimeInfoEntity.storeName.eq(storeName)).fetchFirst();
    if(result == null) {
      throw new MessageException("정보가 존재하지 않습니다.");
    }
    // 부모테이블에 자식테이블 삽입
    result.setBreakTime(resultList.stream().sorted().toList());
    return result;
  }


  @Override
  public String modTimeInfo(StoreTimeInfoDTO storeTimeInfoDTO) {
    
    // 부모 엔터티 수정
    String storeName = storeTimeInfoDTO.getStoreName();
    StoreTimeInfoEntity p_entity = queryFactory.select(storeTimeInfoEntity)
        .from(storeTimeInfoEntity).where(storeTimeInfoEntity.storeName.eq(storeName)).fetchFirst();
    if(p_entity == null) {
      throw new MessageException("정보가 존재하지 않습니다.");
    }
    p_entity.setStartTime(storeTimeInfoDTO.getStartTime());
    p_entity.setEndTime(storeTimeInfoDTO.getEndTime());
    p_entity.setIntervalTime(storeTimeInfoDTO.getIntervalTime());

    // 자식 엔터티 수정
    List<StoreTimeInfoMapEntity> breakTimeList =
        queryFactory.selectDistinct(storeTimeInfoMapEntity).from(storeTimeInfoMapEntity)
            .leftJoin(storeTimeInfoMapEntity.storeTimeInfoEntity, storeTimeInfoEntity)
            .where(storeTimeInfoEntity.storeName.eq(storeName)).fetch();
    List<String> new_times = storeTimeInfoDTO.getBreakTime();
    int count = 0;
    for (StoreTimeInfoMapEntity c_entity : breakTimeList) {
      c_entity.setTime(new_times.get(count));
      count += 1;
    }
    
    // 추가적인 데이터에 대해서는 새로 생성해서 추가
    if (count < new_times.size()) {
      while (count < new_times.size()) {
        StoreTimeInfoMapEntity storeTimeInfoMapEntity = StoreTimeInfoMapEntity.builder()
            .time(new_times.get(count)).storeTimeInfoEntity(p_entity).build();
        storeTimeInfoMapDB.save(storeTimeInfoMapEntity);
        count += 1;
      }
    }
    return "success";
  }

  @Override
  public String deleteTimeInfo(String storeName) {
    queryFactory.delete(storeTimeInfoMapEntity).where(storeTimeInfoMapEntity.storeTimeInfoEntity.storeName.eq(storeName))
        .execute();
    queryFactory.delete(storeTimeInfoEntity).where(storeTimeInfoEntity.storeName.eq(storeName))
        .execute();
    return "success";
  }


  @Override
  public String registerTableInfo(StoreTableInfoEntity storeTableInfoEntity) {
    entityManager.persist(storeTableInfoEntity);
    return "success";
  }

}
