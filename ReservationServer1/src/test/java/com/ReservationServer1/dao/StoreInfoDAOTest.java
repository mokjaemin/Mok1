package com.ReservationServer1.dao;


import static com.ReservationServer1.data.Entity.member.QMemberEntity.memberEntity;
import static com.ReservationServer1.data.Entity.store.QStoreRestDaysEntity.storeRestDaysEntity;
import static com.ReservationServer1.data.Entity.store.QStoreRestDaysMapEntity.storeRestDaysMapEntity;
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
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.ReservationServer1.DAO.Impl.StoreInfoDAOImpl;
import com.ReservationServer1.data.DTO.store.StoreRestDayDTO;
import com.ReservationServer1.data.DTO.store.StoreTimeInfoDTO;
import com.ReservationServer1.data.Entity.store.StoreRestDaysEntity;
import com.ReservationServer1.data.Entity.store.StoreRestDaysMapEntity;
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
    doReturn(jpaQueryRestDaysMap).when(queryFactory).selectDistinct(storeRestDaysMapEntity.date);
    doReturn(jpaQueryRestDaysMap).when(jpaQueryRestDaysMap).from(storeRestDaysMapEntity);
    doReturn(jpaQueryRestDaysMap).when(jpaQueryRestDaysMap)
        .leftJoin(storeRestDaysMapEntity.storeRestDaysEntity, storeRestDaysEntity);
    doReturn(jpaQueryRestDaysMap).when(jpaQueryRestDaysMap)
        .where(storeRestDaysEntity.storeId.eq(storeId));
    doReturn(null).when(jpaQueryRestDaysMap).fetch();

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
//    // given
//    StoreRestDayDTO sample = StoreRestDayDTO.sample();
//    Set<String> date = sample.getDate();
//    for (String day : date) {
//      Long daysId = 1L;
//      doReturn(jpaQueryRestDays).when(queryFactory).select(storeRestDaysEntity.daysId);
//      doReturn(jpaQueryRestDays).when(jpaQueryRestDays).from(storeRestDaysEntity);
//      doReturn(jpaQueryRestDays).when(jpaQueryRestDays)
//          .where(storeRestDaysEntity.storeId.eq(sample.getStoreId()));
//      doReturn(jpaQueryRestDays).when(jpaQueryRestDays).limit(1);
//      doReturn(daysId).when(jpaQueryRestDays).fetchOne();
//
//      doReturn(jpaDeleteClause).when(queryFactory).delete(storeRestDaysMapEntity);
//      doReturn(jpaDeleteClause).when(jpaDeleteClause)
//          .where(storeRestDaysMapEntity.storeRestDaysEntity.daysId.eq(daysId)
//              .and(storeRestDaysMapEntity.date.eq(day)));
//    }
//    String response = "success";
//
//    // when
//    String result = storeInfoDAOImpl.deleteDayOff(sample);
//
//    // then
//    assertEquals(result, response);
//    verify(jpaDeleteClause).execute();
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
  public void modPwdMemberSuccess() {
    // given
//    StoreTimeInfoDTO sample = StoreTimeInfoDTO.sample();
//    StoreTimeInfoEntity p_entity = new StoreTimeInfoEntity(sample);
//
//    doReturn(jpaQueryTimeInfo).when(queryFactory).select(storeTimeInfoEntity);
//    doReturn(jpaQueryTimeInfo).when(jpaQueryTimeInfo).from(storeTimeInfoEntity);
//    doReturn(jpaQueryTimeInfo).when(jpaQueryTimeInfo)
//        .where(storeTimeInfoEntity.storeId.eq(sample.getStoreId()));
//    doReturn(jpaQueryTimeInfo).when(jpaQueryTimeInfo).limit(1);
//    doReturn(p_entity).when(jpaQueryTimeInfo).fetchOne();
//
//
//    List<StoreTimeInfoMapEntity> breakTimeList = new ArrayList<>();
//
//    doReturn(jpaQueryTimeInfoMap).when(queryFactory).selectDistinct(storeTimeInfoMapEntity);
//    doReturn(jpaQueryTimeInfoMap).when(jpaQueryTimeInfoMap).from(storeTimeInfoMapEntity);
//    doReturn(jpaQueryTimeInfoMap).when(jpaQueryTimeInfoMap)
//        .leftJoin(storeTimeInfoMapEntity.storeTimeInfoEntity, storeTimeInfoEntity);
//    doReturn(jpaQueryTimeInfoMap).when(jpaQueryTimeInfoMap)
//        .where(storeTimeInfoEntity.storeId.eq(p_entity.getStoreId()));
//    doReturn(breakTimeList).when(jpaQueryTimeInfo).fetch();
//
//   
//
//
//    // when
//    String result = storeInfoDAOImpl.modTimeInfo(sample);
//
//
//    // then
//    assertEquals(result, "success");
  }



}
