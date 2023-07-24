package com.ReservationServer1.DAO.Impl;



import static com.ReservationServer1.data.Entity.store.QStoreRestDaysEntity.storeRestDaysEntity;
import static com.ReservationServer1.data.Entity.store.QStoreRestDaysMapEntity.storeRestDaysMapEntity;
import static com.ReservationServer1.data.Entity.store.QStoreTableInfoEntity.storeTableInfoEntity;
import static com.ReservationServer1.data.Entity.store.QStoreTimeInfoEntity.storeTimeInfoEntity;
import static com.ReservationServer1.data.Entity.store.QStoreTimeInfoMapEntity.storeTimeInfoMapEntity;
import static com.ReservationServer1.data.Entity.store.QStoreFoodsInfoEntity.storeFoodsInfoEntity;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.ReservationServer1.DAO.StoreInfoDAO;
import com.ReservationServer1.data.DTO.store.StoreRestDayDTO;
import com.ReservationServer1.data.DTO.store.StoreTimeInfoDTO;
import com.ReservationServer1.data.Entity.store.StoreFoodsInfoEntity;
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

  private final JPAQueryFactory queryFactory;
  private final EntityManager entityManager;

  public StoreInfoDAOImpl(JPAQueryFactory queryFactory, EntityManager entityManager) {
    this.queryFactory = queryFactory;
    this.entityManager = entityManager;
  }


  @Override
  public String registerDayOff(StoreRestDayDTO restDayDTO) {
    StoreRestDaysEntity parent = new StoreRestDaysEntity(restDayDTO.getStoreId());
    entityManager.persist(parent);
    Set<StoreRestDaysMapEntity> childs = new HashSet<>();
    for (String date : restDayDTO.getDate()) {
      StoreRestDaysMapEntity child = new StoreRestDaysMapEntity(date, parent);
      entityManager.persist(child);
      childs.add(child);
    }
    parent.setChildSet(childs);
    return "success";
  }


  @Override
  public List<String> getDayOff(int storeId) {
    List<String> resultList =
        queryFactory.selectDistinct(storeRestDaysMapEntity.date).from(storeRestDaysMapEntity)
            .leftJoin(storeRestDaysMapEntity.storeRestDaysEntity, storeRestDaysEntity)
            .where(storeRestDaysEntity.storeId.eq(storeId)).fetch();
    if (resultList == null) {
      throw new MessageException("정보가 등록되지 않았습니다.");
    }
    return resultList.stream().sorted().collect(Collectors.toList());
  }


  @Override
  public String deleteDayOff(int storeId) {
    Long days_id = queryFactory.select(storeRestDaysEntity.daysId).from(storeRestDaysEntity)
        .where(storeRestDaysEntity.storeId.eq(storeId)).fetchFirst();
    queryFactory.delete(storeRestDaysMapEntity)
        .where(storeRestDaysMapEntity.storeRestDaysEntity.daysId.eq(days_id)).execute();
    queryFactory.delete(storeRestDaysEntity).where(storeRestDaysEntity.storeId.eq(storeId))
        .execute();
    return "success";
  }



  @Override
  public String registerTimeInfo(StoreTimeInfoDTO storeTimeInfoDTO) {

    // 부모 객체 생성
    StoreTimeInfoEntity parent =
        StoreTimeInfoEntity.builder().startTime(storeTimeInfoDTO.getStartTime())
            .endTime(storeTimeInfoDTO.getEndTime()).intervalTime(storeTimeInfoDTO.getIntervalTime())
            .storeId(storeTimeInfoDTO.getStoreId()).build();

    // 부모 테이블 저장
    entityManager.persist(parent);

    // 자식들 컬렉션 생성
    Set<StoreTimeInfoMapEntity> childs = new HashSet<>();

    for (String time : storeTimeInfoDTO.getBreakTime()) {
      // 자식 객체 생성
      StoreTimeInfoMapEntity child =
          StoreTimeInfoMapEntity.builder().time(time).storeTimeInfoEntity(parent).build();

      // 자식 테이블 저장
      entityManager.persist(child);
      childs.add(child);
    }
    // 부모테이블에 자식테이블 저장
    parent.setBreakTime(childs);
    return "success";
  }


  @Override
  public StoreTimeInfoDTO getTimeInfo(int storeId) {
    // 자식 테이블에서 쉬는 시간 불러옴
    List<String> resultList =
        queryFactory.selectDistinct(storeTimeInfoMapEntity.time).from(storeTimeInfoMapEntity)
            .where(storeTimeInfoMapEntity.storeTimeInfoEntity.storeId.eq(storeId)).fetch();
    if (resultList == null) {
      throw new MessageException("정보가 존재하지 않습니다.");
    }
    // 부모테이블의 데이터 불러옴
    StoreTimeInfoDTO result = queryFactory
        .select(Projections.fields(StoreTimeInfoDTO.class, storeTimeInfoEntity.startTime,
            storeTimeInfoEntity.endTime, storeTimeInfoEntity.intervalTime,
            Expressions.asNumber(storeId).as("storeId")))
        .from(storeTimeInfoEntity).where(storeTimeInfoEntity.storeId.eq(storeId)).fetchFirst();
    if (result == null) {
      throw new MessageException("정보가 존재하지 않습니다.");
    }
    // 부모테이블에 자식테이블 삽입
    result.setBreakTime(resultList.stream().sorted().collect(Collectors.toList()));
    return result;
  }



  @Override
  public String modTimeInfo(StoreTimeInfoDTO storeTimeInfoDTO) {

    // 부모 엔터티 수정
    int storeId = storeTimeInfoDTO.getStoreId();
    StoreTimeInfoEntity p_entity = queryFactory.select(storeTimeInfoEntity)
        .from(storeTimeInfoEntity).where(storeTimeInfoEntity.storeId.eq(storeId)).fetchFirst();
    if (p_entity == null) {
      throw new MessageException("정보가 존재하지 않습니다.");
    }

    // 자식 엔터티 삭제 및 추가
    queryFactory.delete(storeTimeInfoMapEntity)
        .where(storeTimeInfoMapEntity.storeTimeInfoEntity.eq(p_entity)).execute();

    Set<StoreTimeInfoMapEntity> childs = new HashSet<>();
    for (String breakTime : storeTimeInfoDTO.getBreakTime()) {
      StoreTimeInfoMapEntity child =
          StoreTimeInfoMapEntity.builder().time(breakTime).storeTimeInfoEntity(p_entity).build();
      entityManager.persist(child);
      childs.add(child);
    }

    p_entity.setStartTime(storeTimeInfoDTO.getStartTime());
    p_entity.setEndTime(storeTimeInfoDTO.getEndTime());
    p_entity.setIntervalTime(storeTimeInfoDTO.getIntervalTime());
    p_entity.setBreakTime(childs);

    return "success";
  }


  @Override
  public String deleteTimeInfo(int storeId) {
    Long times_id = queryFactory.select(storeTimeInfoEntity.timesId).from(storeTimeInfoEntity)
        .where(storeTimeInfoEntity.storeId.eq(storeId)).fetchFirst();
    queryFactory.delete(storeTimeInfoMapEntity)
        .where(storeTimeInfoMapEntity.storeTimeInfoEntity.timesId.eq(times_id)).execute();
    queryFactory.delete(storeTimeInfoEntity).where(storeTimeInfoEntity.timesId.eq(times_id))
        .execute();
    return "success";
  }



  @Override
  public String registerTableInfo(StoreTableInfoEntity storeTableInfoEntity) {
    entityManager.persist(storeTableInfoEntity);
    return "success";
  }


  @Override
  public String modTableInfo(StoreTableInfoEntity newData) {
    StoreTableInfoEntity originalData =
        queryFactory.select(storeTableInfoEntity).from(storeTableInfoEntity)
            .where(storeTableInfoEntity.storeId.eq(newData.getStoreId())).fetchFirst();
    originalData.setCount(newData.getCount());
    return "success";
  }


  @Override
  public String deleteTableInfo(int storeId) {
    queryFactory.delete(storeTableInfoEntity).where(storeTableInfoEntity.storeId.eq(storeId))
        .execute();
    return "success";
  }


  @Override
  public String registerFoodsInfo(StoreFoodsInfoEntity storeFoodsInfoEntity) {
    entityManager.persist(storeFoodsInfoEntity);
    return "success";
  }


  @Override
  public List<StoreFoodsInfoEntity> getFoodsInfo(int storeId) {
    List<StoreFoodsInfoEntity> result = queryFactory.select(storeFoodsInfoEntity)
        .from(storeFoodsInfoEntity).where(storeFoodsInfoEntity.storeId.eq(storeId)).fetch();
    if (result == null) {
      throw new MessageException("해당 음식점은 음식을 등록하지 않았습니다.");
    }
    return result;
  }


  @Override
  public String modFoodsInfo(StoreFoodsInfoEntity new_entity) {
    StoreFoodsInfoEntity origin_entity = queryFactory.select(storeFoodsInfoEntity)
        .from(storeFoodsInfoEntity).where(storeFoodsInfoEntity.storeId.eq(new_entity.getStoreId())
            .and(storeFoodsInfoEntity.foodName.eq(new_entity.getFoodName())))
        .fetchFirst();
    origin_entity.setFoodName(new_entity.getFoodName());
    origin_entity.setFoodDescription(new_entity.getFoodDescription());
    origin_entity.setFoodPrice(new_entity.getFoodPrice());
    origin_entity.setImageURL(new_entity.getImageURL());
    return "success";
  }


  @Override
  public String deleteFoodsInfo(int storeId, String foodName) {
    queryFactory.delete(storeFoodsInfoEntity).where(
        storeFoodsInfoEntity.storeId.eq(storeId).and(storeFoodsInfoEntity.foodName.eq(foodName)))
        .execute();
    return "success";
  }
}
