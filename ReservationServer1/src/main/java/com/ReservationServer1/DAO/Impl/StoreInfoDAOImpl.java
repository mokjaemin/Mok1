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


  // Exist로 변경, inner join, boolean Expression(for 대신 쿼리문 만들어줌) 사용
  // TROUBLE SHOOTING에 작성
  // 1. 조회해서 map의 아이디 반환 (쿼리 작성시 boolean Expression으로 하나의 쿼리로 작성)
  // 2. 해당 아이디 삭제 boolean expression으로 하나의 쿼리 사용
  // 3. exist로 삭제한 자식 테이블 존재 여 확인 후 부모 테이블 삭제
  @Override
  public String deleteDayOff(RestDayDTO restDayDTO) {
    String storeName = restDayDTO.getStoreName();
    Map<String, String> date = restDayDTO.getDate();
    for (String key : date.keySet()) {
      String day = date.get(key);
      String id = storeRestDayMapRepository.findDaysIdByDate(day);
      storeRestDayMapRepository.deleteByStoreNameAndDate(storeName, day);
      if (storeRestDayMapRepository.findCountByDaysId(id) == 0) {
        storeRestDayRepository.deleteByDaysId(id);
      }
    }
    return "success";
  }

}
