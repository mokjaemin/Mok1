package com.ReservationServer1.dao;


import static com.ReservationServer1.data.Entity.store.QStoreFoodsInfoEntity.storeFoodsInfoEntity;
import static com.ReservationServer1.data.Entity.store.QStoreRestDaysEntity.storeRestDaysEntity;
import static com.ReservationServer1.data.Entity.store.QStoreRestDaysMapEntity.storeRestDaysMapEntity;
import static com.ReservationServer1.data.Entity.store.QStoreTableInfoEntity.storeTableInfoEntity;
import static com.ReservationServer1.data.Entity.store.QStoreTimeInfoEntity.storeTimeInfoEntity;
import static com.ReservationServer1.data.Entity.store.QStoreTimeInfoMapEntity.storeTimeInfoMapEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.ReservationServer1.DAO.Impl.StoreInfoDAOImpl;
import com.ReservationServer1.data.DTO.store.StoreFoodsInfoDTO;
import com.ReservationServer1.data.DTO.store.StoreRestDayDTO;
import com.ReservationServer1.data.DTO.store.StoreTableInfoDTO;
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
import com.querydsl.jpa.impl.JPADeleteClause;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import jakarta.persistence.EntityManager;


public class StoreInfoDAOTest {


  @Mock
  private JPAQueryFactory queryFactory;

  @InjectMocks
  private StoreInfoDAOImpl storeInfoDAOImpl;

  @Mock
  private EntityManager entityManager;

  @Mock
  private JPAQuery<StoreRestDaysEntity> jpaQueryRestDays;

  @Mock
  private JPAQuery<StoreRestDaysMapEntity> jpaQueryRestDaysMap;

  @Mock
  private JPAQuery<StoreTimeInfoEntity> jpaQueryTimeInfo;

  @Mock
  private JPAQuery<StoreTimeInfoMapEntity> jpaQueryTimeInfoMap;

  @Mock
  private JPAQuery<StoreTableInfoEntity> jpaQueryTableInfo;

  @Mock
  private JPAQuery<StoreFoodsInfoEntity> jpaQueryFoodsInfo;

  @Mock
  private JPADeleteClause jpaDeleteClause;

  @Mock
  private JPAUpdateClause jpaUpdateClause;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }


  // 1. Register Day Off
  @Test
  @DisplayName("가게 쉬는날 등록 성공")
  public void registerDayOffSuccess() {
    // given
    String response = "success";
    StoreRestDayDTO dto = StoreRestDayDTO.sample();
    doNothing().when(entityManager).persist(any(StoreRestDaysEntity.class));
    doNothing().when(entityManager).persist(any(StoreRestDaysMapEntity.class));

    // when
    String result = storeInfoDAOImpl.registerDayOff(dto);

    // then
    assertEquals(response, result);
    verify(entityManager, times(1)).persist(any(StoreRestDaysEntity.class));
    verify(entityManager, times(1)).persist(any(StoreRestDaysMapEntity.class));
  }


  // 2. Get Day Off
  @Test
  @DisplayName("가게 쉬는날 출력 성공")
  public void getDayOffSuccess() {
    // given
    List<String> response = new ArrayList<>();
    response.add("1월2일");
    response.add("1월1일");
    int storeId = 0;
    doReturn(jpaQueryRestDaysMap).when(queryFactory).selectDistinct(storeRestDaysMapEntity.date);
    doReturn(jpaQueryRestDaysMap).when(jpaQueryRestDaysMap).from(storeRestDaysMapEntity);
    doReturn(jpaQueryRestDaysMap).when(jpaQueryRestDaysMap)
        .leftJoin(storeRestDaysMapEntity.storeRestDaysEntity, storeRestDaysEntity);
    doReturn(jpaQueryRestDaysMap).when(jpaQueryRestDaysMap)
        .where(storeRestDaysEntity.storeId.eq(storeId));
    doReturn(response).when(jpaQueryRestDaysMap).fetch();

    // when
    List<String> result = storeInfoDAOImpl.getDayOff(storeId);

    // then
    Collections.sort(response);
    assertEquals(response, result);
    for (int i = 0; i < result.size(); i++) {
      assertEquals(response.get(i), result.get(i));
    }
  }


  @Test
  @DisplayName("가게 쉬는날 출력 실패 : 결과 없음")
  public void getDayOffFail() {
    // given
    int storeId = 0;
    List<String> result = new ArrayList<>();
    doReturn(jpaQueryRestDaysMap).when(queryFactory).selectDistinct(storeRestDaysMapEntity.date);
    doReturn(jpaQueryRestDaysMap).when(jpaQueryRestDaysMap).from(storeRestDaysMapEntity);
    doReturn(jpaQueryRestDaysMap).when(jpaQueryRestDaysMap)
        .leftJoin(storeRestDaysMapEntity.storeRestDaysEntity, storeRestDaysEntity);
    doReturn(jpaQueryRestDaysMap).when(jpaQueryRestDaysMap)
        .where(storeRestDaysEntity.storeId.eq(storeId));
    doReturn(result).when(jpaQueryRestDaysMap).fetch();

    // then && when
    MessageException message = assertThrows(MessageException.class, () -> {
      storeInfoDAOImpl.getDayOff(storeId);
    });
    String expected = "정보가 등록되지 않았습니다.";
    assertEquals(expected, message.getMessage());

  }


  // 3. Delete Day Off
  @Test
  @DisplayName("가게 쉬는날 삭제 성공")
  public void deleteDayOffSuccess() {
    // given
    int storeId = 1;
    Long daysId = 1L;
    doReturn(jpaQueryRestDays).when(queryFactory).select(storeRestDaysEntity.daysId);
    doReturn(jpaQueryRestDays).when(jpaQueryRestDays).from(storeRestDaysEntity);
    doReturn(jpaQueryRestDays).when(jpaQueryRestDays)
        .where(storeRestDaysEntity.storeId.eq(storeId));
    doReturn(jpaQueryRestDays).when(jpaQueryRestDays).limit(1);
    doReturn(daysId).when(jpaQueryRestDays).fetchOne();

    doReturn(jpaDeleteClause).when(queryFactory).delete(storeRestDaysMapEntity);
    doReturn(jpaDeleteClause).when(jpaDeleteClause)
        .where(storeRestDaysMapEntity.storeRestDaysEntity.daysId.eq(daysId));

    doReturn(jpaDeleteClause).when(queryFactory).delete(storeRestDaysEntity);
    doReturn(jpaDeleteClause).when(jpaDeleteClause).where(storeRestDaysEntity.storeId.eq(storeId));
    String response = "success";

    // when
    String result = storeInfoDAOImpl.deleteDayOff(storeId);

    // then
    assertEquals(result, response);
    verify(jpaDeleteClause, times(2)).execute();
  }


  // 4. Register Time Info
  @Test
  @DisplayName("가게 시간정보 등록 성공")
  public void registerTimeInfoSuccess() {
    // given
    String response = "success";
    doNothing().when(entityManager).persist(any(StoreTimeInfoEntity.class));
    doNothing().when(entityManager).persist(any(StoreTimeInfoMapEntity.class));

    // when
    String result = storeInfoDAOImpl.registerTimeInfo(StoreTimeInfoDTO.sample());

    // then
    assertEquals(response, result);
    verify(entityManager, times(1)).persist(any(StoreTimeInfoEntity.class));
    verify(entityManager, times(1)).persist(any(StoreTimeInfoMapEntity.class));
  }


  // 5. Get Time Info
  @Test
  @DisplayName("가게 시간정보 출력 성공")
  public void getTimeInfoSuccess() {
    // given
    StoreTimeInfoDTO sample = StoreTimeInfoDTO.sample();
    List<String> response = new ArrayList<>();
    response.add("12시");
    response.add("11시");
    doReturn(jpaQueryTimeInfoMap).when(queryFactory).selectDistinct(storeTimeInfoMapEntity.time);
    doReturn(jpaQueryTimeInfoMap).when(jpaQueryTimeInfoMap).from(storeTimeInfoMapEntity);
    doReturn(jpaQueryTimeInfoMap).when(jpaQueryTimeInfoMap)
        .where(storeTimeInfoMapEntity.storeTimeInfoEntity.storeId.eq(sample.getStoreId()));
    doReturn(response).when(jpaQueryTimeInfoMap).fetch();

    doReturn(jpaQueryTimeInfo).when(queryFactory)
        .select(Projections.fields(StoreTimeInfoDTO.class, storeTimeInfoEntity.startTime,
            storeTimeInfoEntity.endTime, storeTimeInfoEntity.intervalTime,
            Expressions.asNumber(sample.getStoreId()).as("storeId")));
    doReturn(jpaQueryTimeInfo).when(jpaQueryTimeInfo).from(storeTimeInfoEntity);
    doReturn(jpaQueryTimeInfo).when(jpaQueryTimeInfo)
        .where(storeTimeInfoEntity.storeId.eq(sample.getStoreId()));
    doReturn(jpaQueryTimeInfo).when(jpaQueryTimeInfo).limit(1);
    doReturn(sample).when(jpaQueryTimeInfo).fetchOne();



    // when
    StoreTimeInfoDTO result = storeInfoDAOImpl.getTimeInfo(sample.getStoreId());

    // then
    Collections.sort(response);
    sample.setBreakTime(response);
    assertEquals(sample, result);
  }

  @Test
  @DisplayName("가게 시간정보 출력 실패 : 쉬는 시간 정보 없음")
  public void getTimeInfoFail1() {
    // given
    StoreTimeInfoDTO sample = StoreTimeInfoDTO.sample();
    doReturn(jpaQueryTimeInfoMap).when(queryFactory).selectDistinct(storeTimeInfoMapEntity.time);
    doReturn(jpaQueryTimeInfoMap).when(jpaQueryTimeInfoMap).from(storeTimeInfoMapEntity);
    doReturn(jpaQueryTimeInfoMap).when(jpaQueryTimeInfoMap)
        .where(storeTimeInfoMapEntity.storeTimeInfoEntity.storeId.eq(sample.getStoreId()));
    doReturn(null).when(jpaQueryTimeInfoMap).fetch();


    // then && when
    MessageException message = assertThrows(MessageException.class, () -> {
      storeInfoDAOImpl.getTimeInfo(sample.getStoreId());
    });
    String expected = "정보가 존재하지 않습니다.";
    assertEquals(expected, message.getMessage());
  }

  @Test
  @DisplayName("가게 시간정보 출력 실패 : 전체 정보 없음")
  public void getTimeInfoFail2() {
    // given
    StoreTimeInfoDTO sample = StoreTimeInfoDTO.sample();
    List<String> response = new ArrayList<>();
    response.add("12시");
    response.add("11시");
    doReturn(jpaQueryTimeInfoMap).when(queryFactory).selectDistinct(storeTimeInfoMapEntity.time);
    doReturn(jpaQueryTimeInfoMap).when(jpaQueryTimeInfoMap).from(storeTimeInfoMapEntity);
    doReturn(jpaQueryTimeInfoMap).when(jpaQueryTimeInfoMap)
        .where(storeTimeInfoMapEntity.storeTimeInfoEntity.storeId.eq(sample.getStoreId()));
    doReturn(response).when(jpaQueryTimeInfoMap).fetch();

    doReturn(jpaQueryTimeInfo).when(queryFactory)
        .select(Projections.fields(StoreTimeInfoDTO.class, storeTimeInfoEntity.startTime,
            storeTimeInfoEntity.endTime, storeTimeInfoEntity.intervalTime,
            Expressions.asNumber(sample.getStoreId()).as("storeId")));
    doReturn(jpaQueryTimeInfo).when(jpaQueryTimeInfo).from(storeTimeInfoEntity);
    doReturn(jpaQueryTimeInfo).when(jpaQueryTimeInfo)
        .where(storeTimeInfoEntity.storeId.eq(sample.getStoreId()));
    doReturn(jpaQueryTimeInfo).when(jpaQueryTimeInfo).limit(1);
    doReturn(null).when(jpaQueryTimeInfo).fetchOne();



    // then && when
    MessageException message = assertThrows(MessageException.class, () -> {
      storeInfoDAOImpl.getTimeInfo(sample.getStoreId());
    });
    String expected = "정보가 존재하지 않습니다.";
    assertEquals(expected, message.getMessage());
  }


  // 4. Modify Time Info
  @Test
  @DisplayName("가게 시간정보 수정 성공")
  public void modTimeInfoSuccess() {
    // given
    StoreTimeInfoDTO sample = StoreTimeInfoDTO.sample();
    StoreTimeInfoEntity p_entity = new StoreTimeInfoEntity(sample);

    doReturn(jpaQueryTimeInfo).when(queryFactory).select(storeTimeInfoEntity);
    doReturn(jpaQueryTimeInfo).when(jpaQueryTimeInfo).from(storeTimeInfoEntity);
    doReturn(jpaQueryTimeInfo).when(jpaQueryTimeInfo)
        .where(storeTimeInfoEntity.storeId.eq(sample.getStoreId()));
    doReturn(jpaQueryTimeInfo).when(jpaQueryTimeInfo).limit(1);
    doReturn(p_entity).when(jpaQueryTimeInfo).fetchOne();

    doReturn(jpaDeleteClause).when(queryFactory).delete(storeTimeInfoMapEntity);
    doReturn(jpaDeleteClause).when(jpaDeleteClause)
        .where(storeTimeInfoMapEntity.storeTimeInfoEntity.eq(p_entity));


    Set<StoreTimeInfoMapEntity> childs = new HashSet<>();
    doReturn(jpaUpdateClause).when(queryFactory).update(storeTimeInfoEntity);
    doReturn(jpaUpdateClause).when(jpaUpdateClause).set(storeTimeInfoEntity.startTime,
        sample.getStartTime());
    doReturn(jpaUpdateClause).when(jpaUpdateClause).set(storeTimeInfoEntity.endTime,
        sample.getEndTime());
    doReturn(jpaUpdateClause).when(jpaUpdateClause).set(storeTimeInfoEntity.intervalTime,
        sample.getIntervalTime());
    doReturn(jpaUpdateClause).when(jpaUpdateClause).set(storeTimeInfoEntity.breakTime, childs);

    // when
    String result = storeInfoDAOImpl.modTimeInfo(sample);


    // then
    assertEquals(result, "success");
    verify(jpaDeleteClause, times(1)).execute();
  }


  @Test
  @DisplayName("가게 시간정보 수정 실패 : 정보 없음")
  public void modTimeInfoFail() {
    // given
    StoreTimeInfoDTO sample = StoreTimeInfoDTO.sample();

    doReturn(jpaQueryTimeInfo).when(queryFactory).select(storeTimeInfoEntity);
    doReturn(jpaQueryTimeInfo).when(jpaQueryTimeInfo).from(storeTimeInfoEntity);
    doReturn(jpaQueryTimeInfo).when(jpaQueryTimeInfo)
        .where(storeTimeInfoEntity.storeId.eq(sample.getStoreId()));
    doReturn(jpaQueryTimeInfo).when(jpaQueryTimeInfo).limit(1);
    doReturn(null).when(jpaQueryTimeInfo).fetchOne();



    // then && when
    MessageException message = assertThrows(MessageException.class, () -> {
      storeInfoDAOImpl.modTimeInfo(sample);
    });
    String expected = "정보가 존재하지 않습니다.";
    assertEquals(expected, message.getMessage());
  }


  // 5. Delete Time Info
  @Test
  @DisplayName("가게 쉬는날 삭제 성공")
  public void deleteTimeInfoSuccess() {
    // given
    int storeId = 1;
    Long timesId = 1L;
    doReturn(jpaQueryTimeInfo).when(queryFactory).select(storeTimeInfoEntity.timesId);
    doReturn(jpaQueryTimeInfo).when(jpaQueryTimeInfo).from(storeTimeInfoEntity);
    doReturn(jpaQueryTimeInfo).when(jpaQueryTimeInfo)
        .where(storeTimeInfoEntity.storeId.eq(storeId));
    doReturn(jpaQueryTimeInfo).when(jpaQueryTimeInfo).limit(1);
    doReturn(timesId).when(jpaQueryTimeInfo).fetchOne();

    doReturn(jpaDeleteClause).when(queryFactory).delete(storeTimeInfoMapEntity);
    doReturn(jpaDeleteClause).when(jpaDeleteClause)
        .where(storeTimeInfoMapEntity.storeTimeInfoEntity.timesId.eq(timesId));

    doReturn(jpaDeleteClause).when(queryFactory).delete(storeTimeInfoEntity);
    doReturn(jpaDeleteClause).when(jpaDeleteClause).where(storeTimeInfoEntity.timesId.eq(timesId));
    String response = "success";

    // when
    String result = storeInfoDAOImpl.deleteTimeInfo(storeId);

    // then
    assertEquals(result, response);
    verify(jpaDeleteClause, times(2)).execute();
  }


  // 6. Register Table Info
  @Test
  @DisplayName("가게 테이블 정보 등록 성공")
  public void registerTableInfoSuccess() {
    // given
    String response = "success";
    StoreTableInfoEntity entity = new StoreTableInfoEntity(StoreTableInfoDTO.sample());
    doNothing().when(entityManager).persist(any(StoreTableInfoEntity.class));

    // when
    String result = storeInfoDAOImpl.registerTableInfo(entity);

    // then
    assertEquals(response, result);
    verify(entityManager, times(1)).persist(any(StoreTableInfoEntity.class));
  }


  // 7. Modify Table Info
  @Test
  @DisplayName("가게 테이블 정보 수정 성공")
  public void modTableInfoSuccess() {
    // given
    String response = "success";
    StoreTableInfoEntity newData = new StoreTableInfoEntity(StoreTableInfoDTO.sample());
    StoreTableInfoEntity originalData = new StoreTableInfoEntity(StoreTableInfoDTO.sample());
    originalData.setCount(100);

    doReturn(jpaQueryTableInfo).when(queryFactory).select(storeTableInfoEntity);
    doReturn(jpaQueryTableInfo).when(jpaQueryTableInfo).from(storeTableInfoEntity);
    doReturn(jpaQueryTableInfo).when(jpaQueryTableInfo)
        .where(storeTableInfoEntity.storeId.eq(newData.getStoreId()));
    doReturn(jpaQueryTableInfo).when(jpaQueryTableInfo).limit(1);
    doReturn(originalData).when(jpaQueryTableInfo).fetchOne();


    doReturn(jpaUpdateClause).when(queryFactory).update(storeTableInfoEntity);
    doReturn(jpaUpdateClause).when(jpaUpdateClause).set(storeTableInfoEntity.count,
        newData.getCount());



    // when
    String result = storeInfoDAOImpl.modTableInfo(newData);

    // then
    assertEquals(response, result);
  }


  // 8. Delete Table Info
  @Test
  @DisplayName("가게 쉬는날 삭제 성공")
  public void deleteTableInfoSuccess() {
    // given
    int storeId = 1;
    doReturn(jpaDeleteClause).when(queryFactory).delete(storeTableInfoEntity);
    doReturn(jpaDeleteClause).when(jpaDeleteClause).where(storeTableInfoEntity.storeId.eq(storeId));

    String response = "success";

    // when
    String result = storeInfoDAOImpl.deleteTableInfo(storeId);

    // then
    assertEquals(result, response);
    verify(jpaDeleteClause, times(1)).execute();
  }

  // 9. Register Foods Info
  @Test
  @DisplayName("가게 테이블 정보 등록 성공")
  public void registerFoodsInfoSuccess() {
    // given
    String response = "success";
    StoreFoodsInfoEntity entity = new StoreFoodsInfoEntity(StoreFoodsInfoDTO.sample());
    doNothing().when(entityManager).persist(any(StoreFoodsInfoEntity.class));

    // when
    String result = storeInfoDAOImpl.registerFoodsInfo(entity);

    // then
    assertEquals(response, result);
    verify(entityManager, times(1)).persist(any(StoreFoodsInfoEntity.class));
  }


  // 11. Get Foods Info
  @Test
  @DisplayName("가게 음식 정보 출력 성공")
  public void getFoodsInfoSuccess() {
    // given
    int storeId = 0;
    List<StoreFoodsInfoEntity> response = new ArrayList<>();
    response.add(new StoreFoodsInfoEntity(StoreFoodsInfoDTO.sample()));
    doReturn(jpaQueryFoodsInfo).when(queryFactory).select(storeFoodsInfoEntity);
    doReturn(jpaQueryFoodsInfo).when(jpaQueryFoodsInfo).from(storeFoodsInfoEntity);
    doReturn(jpaQueryFoodsInfo).when(jpaQueryFoodsInfo)
        .where(storeFoodsInfoEntity.storeId.eq(storeId));
    doReturn(response).when(jpaQueryFoodsInfo).fetch();

    // when
    List<StoreFoodsInfoEntity> result = storeInfoDAOImpl.getFoodsInfo(storeId);

    // then
    assertEquals(response, result);
  }


  @Test
  @DisplayName("가게 음식 정보 출력 실패 : 정보 없음")
  public void getFoodsInfoFail() {
    // given
    int storeId = 0;
    List<StoreFoodsInfoEntity> response = new ArrayList<>();
    doReturn(jpaQueryFoodsInfo).when(queryFactory).select(storeFoodsInfoEntity);
    doReturn(jpaQueryFoodsInfo).when(jpaQueryFoodsInfo).from(storeFoodsInfoEntity);
    doReturn(jpaQueryFoodsInfo).when(jpaQueryFoodsInfo)
        .where(storeFoodsInfoEntity.storeId.eq(storeId));
    doReturn(response).when(jpaQueryFoodsInfo).fetch();

    // then && when
    MessageException message = assertThrows(MessageException.class, () -> {
      storeInfoDAOImpl.getFoodsInfo(storeId);
    });
    String expected = "해당 음식점은 음식을 등록하지 않았습니다.";
    assertEquals(expected, message.getMessage());
  }

  // 10. Modify Foods Info
  @Test
  @DisplayName("가게 음식정보 정보 수정 성공")
  public void modFoodsInfoSuccess() {
    // given
    String response = "success";
    StoreFoodsInfoEntity newData = new StoreFoodsInfoEntity(StoreFoodsInfoDTO.sample());
    StoreFoodsInfoEntity originalData = new StoreFoodsInfoEntity(StoreFoodsInfoDTO.sample());

    doReturn(jpaQueryFoodsInfo).when(queryFactory).select(storeFoodsInfoEntity);
    doReturn(jpaQueryFoodsInfo).when(jpaQueryFoodsInfo).from(storeFoodsInfoEntity);
    doReturn(jpaQueryFoodsInfo).when(jpaQueryFoodsInfo).where(storeFoodsInfoEntity.storeId
        .eq(newData.getStoreId()).and(storeFoodsInfoEntity.foodName.eq(newData.getFoodName())));
    doReturn(jpaQueryFoodsInfo).when(jpaQueryFoodsInfo).limit(1);
    doReturn(originalData).when(jpaQueryFoodsInfo).fetchOne();


    doReturn(jpaUpdateClause).when(queryFactory).update(storeFoodsInfoEntity);
    doReturn(jpaUpdateClause).when(jpaUpdateClause).set(storeFoodsInfoEntity.foodName,
        newData.getFoodName());
    doReturn(jpaUpdateClause).when(jpaUpdateClause).set(storeFoodsInfoEntity.foodDescription,
        newData.getFoodDescription());
    doReturn(jpaUpdateClause).when(jpaUpdateClause).set(storeFoodsInfoEntity.foodPrice,
        newData.getFoodPrice());
    doReturn(jpaUpdateClause).when(jpaUpdateClause).set(storeFoodsInfoEntity.imageURL,
        newData.getImageURL());



    // when
    String result = storeInfoDAOImpl.modFoodsInfo(newData);

    // then
    assertEquals(response, result);
  }


  // 11. Delete Foods Info
  @Test
  @DisplayName("가게 음식정보 삭제 성공")
  public void deleteFoodsInfoSuccess() {
    // given
    int storeId = 1;
    String foodName = "foodName";
    doReturn(jpaDeleteClause).when(queryFactory).delete(storeFoodsInfoEntity);
    doReturn(jpaDeleteClause).when(jpaDeleteClause).where(
        storeFoodsInfoEntity.storeId.eq(storeId).and(storeFoodsInfoEntity.foodName.eq(foodName)));

    String response = "success";

    // when
    String result = storeInfoDAOImpl.deleteFoodsInfo(storeId, foodName);

    // then
    assertEquals(result, response);
    verify(jpaDeleteClause, times(1)).execute();
  }

}
