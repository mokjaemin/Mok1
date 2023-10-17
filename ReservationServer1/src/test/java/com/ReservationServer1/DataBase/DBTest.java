//package com.ReservationServer1.DataBase;
//
//
//
//import static com.ReservationServer1.data.Entity.POR.QStoreCouponEntity.storeCouponEntity;
//import static com.ReservationServer1.data.Entity.POR.QStoreOrdersEntity.storeOrdersEntity;
//import static com.ReservationServer1.data.Entity.POR.QStoreOrdersMapEntity.storeOrdersMapEntity;
//import static com.ReservationServer1.data.Entity.POR.QStorePayEntity.storePayEntity;
//import static com.ReservationServer1.data.Entity.POR.QStoreReservationEntity.storeReservationEntity;
//import static com.ReservationServer1.data.Entity.board.QStoreBoardEntity.storeBoardEntity;
//import static com.ReservationServer1.data.Entity.member.QMemberEntity.memberEntity;
//import static com.ReservationServer1.data.Entity.store.QStoreEntity.storeEntity;
//import static com.ReservationServer1.data.Entity.store.QStoreFoodsInfoEntity.storeFoodsInfoEntity;
//import static com.ReservationServer1.data.Entity.store.QStoreRestDaysEntity.storeRestDaysEntity;
//import static com.ReservationServer1.data.Entity.store.QStoreRestDaysMapEntity.storeRestDaysMapEntity;
//import static com.ReservationServer1.data.Entity.store.QStoreTableInfoEntity.storeTableInfoEntity;
//import static com.ReservationServer1.data.Entity.store.QStoreTimeInfoEntity.storeTimeInfoEntity;
//import static com.ReservationServer1.data.Entity.store.QStoreTimeInfoMapEntity.storeTimeInfoMapEntity;
//import static org.hamcrest.CoreMatchers.equalTo;
//import static org.hamcrest.CoreMatchers.is;
//import static org.hamcrest.CoreMatchers.nullValue;
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.test.context.ActiveProfiles;
//import com.ReservationServer1.data.DTO.POR.OrderDTO;
//import com.ReservationServer1.data.DTO.POR.ReservationDTO;
//import com.ReservationServer1.data.DTO.board.BoardDTO;
//import com.ReservationServer1.data.DTO.store.StoreFoodsInfoDTO;
//import com.ReservationServer1.data.DTO.store.StoreRestDayDTO;
//import com.ReservationServer1.data.DTO.store.StoreTableInfoDTO;
//import com.ReservationServer1.data.DTO.store.StoreTimeInfoDTO;
//import com.ReservationServer1.data.Entity.POR.StoreCouponEntity;
//import com.ReservationServer1.data.Entity.POR.StoreOrdersEntity;
//import com.ReservationServer1.data.Entity.POR.StoreOrdersMapEntity;
//import com.ReservationServer1.data.Entity.POR.StorePayEntity;
//import com.ReservationServer1.data.Entity.POR.StoreReservationEntity;
//import com.ReservationServer1.data.Entity.board.StoreBoardEntity;
//import com.ReservationServer1.data.Entity.member.MemberEntity;
//import com.ReservationServer1.data.Entity.store.StoreEntity;
//import com.ReservationServer1.data.Entity.store.StoreFoodsInfoEntity;
//import com.ReservationServer1.data.Entity.store.StoreRestDaysEntity;
//import com.ReservationServer1.data.Entity.store.StoreRestDaysMapEntity;
//import com.ReservationServer1.data.Entity.store.StoreTableInfoEntity;
//import com.ReservationServer1.data.Entity.store.StoreTimeInfoEntity;
//import com.ReservationServer1.data.Entity.store.StoreTimeInfoMapEntity;
//import com.querydsl.core.types.Projections;
//import com.querydsl.core.types.dsl.Expressions;
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import jakarta.persistence.EntityExistsException;
//import jakarta.persistence.EntityManager;
//import jakarta.transaction.Transactional;
//
//@DataJpaTest
//@ActiveProfiles("test")
//@Transactional
//public class DBTest {
//
//
//  @Autowired
//  private TestEntityManager testEntityManager;
//
//  private JPAQueryFactory queryFactory;
//
//  private EntityManager em;
//
//  private BCryptPasswordEncoder passwordEncoder;
//
//
//
//  private String existIdError = "jakarta.persistence.EntityExistsException: "
//      + "A different object with the same identifier value was already associated with the session : ";
//  private String memberIdError = "[com.ReservationServer1.data.Entity.member.MemberEntity#testId]";
//
//  @BeforeEach
//  void init() {
//    em = testEntityManager.getEntityManager();
//    passwordEncoder = new BCryptPasswordEncoder();
//    queryFactory = new JPAQueryFactory(em);
//  }
//
//
//
//  // 1. Member Entity : MemberDAOImpl
//  @Test
//  @DisplayName("MemberEntity : PERSIST TEST : 회원가입 성공")
//  public void testPersistMemberSuccess() {
//    // given - 초기 데이터 생성
//    MemberEntity user =
//        MemberEntity.builder().userId("userId").userName("userName").userPwd("userPwd")
//            .userNumber("userNumber").userEmail("userEmail").userAddress("userEmail").build();
//    String encodedPassword = passwordEncoder.encode(user.getUserPwd());
//    user.setUserPwd(encodedPassword);
//
//    // when - 저장
//    em.persist(user);
//
//    // 동기화
//    em.flush();
//    em.clear();
//
//
//    // then - 조회 후 결과 체크
//    Long count = queryFactory.select(memberEntity.count()).from(memberEntity).fetch().get(0);
//    assertThat(count, equalTo(1L));
//  }
//
//  @Test
//  @DisplayName("MemberEntity : PERSIST TEST : 회원가입 실패 : EXIST ID")
//  public void testPersistMemberFail() {
//    // given - 초기 데이터 생성
//    MemberEntity memberTestEntity =
//        MemberEntity.builder().userId("testId").userName("testName").userPwd("testPwd")
//            .userNumber("testNumber").userEmail("testEmail").userAddress("testEmail").build();
//    String encodedPassword1 = passwordEncoder.encode(memberTestEntity.getUserPwd());
//    memberTestEntity.setUserPwd(encodedPassword1);
//    em.persist(memberTestEntity);
//
//    // 중복 아이디 가진 엔터티 생성
//    MemberEntity user =
//        MemberEntity.builder().userId("testId").userName("userName").userPwd("userPwd")
//            .userNumber("userNumber").userEmail("userEmail").userAddress("userEmail").build();
//    String encodedPassword2 = passwordEncoder.encode(user.getUserPwd());
//    user.setUserPwd(encodedPassword2);
//
//    // when - 영속화 시도
//    EntityExistsException exception = assertThrows(EntityExistsException.class, () -> {
//      em.persist(user);
//    });
//
//    // then - 영속화 실패 체크
//    assertEquals(exception.toString(), existIdError + memberIdError);
//    Long count = queryFactory.select(memberEntity.count()).from(memberEntity).fetch().get(0);
//    assertThat(count, equalTo(1L));
//  }
//
//
//  @Test
//  @DisplayName("MemberEntity : EXIST TEST : 아이디 존재 여부")
//  public void testExsistId() {
//    // when - 초기 데이터 저장
//    MemberEntity memberTestEntity =
//        MemberEntity.builder().userId("testId").userName("testName").userPwd("testPwd")
//            .userNumber("testNumber").userEmail("testEmail").userAddress("testEmail").build();
//    String encodedPassword1 = passwordEncoder.encode(memberTestEntity.getUserPwd());
//    memberTestEntity.setUserPwd(encodedPassword1);
//    em.persist(memberTestEntity);
//
//
//    // 동기화
//    em.flush();
//    em.clear();
//
//    // 조회 쿼리 시도
//    String userId = memberTestEntity.getUserId();
//    Integer exist = queryFactory.selectOne().from(memberEntity)
//        .where(memberEntity.userId.eq(userId)).fetchFirst();
//    Integer none = queryFactory.selectOne().from(memberEntity)
//        .where(memberEntity.userId.eq("NoInDB")).fetchFirst();
//
//    // then - 결과 체크
//    assertThat(exist, is(1));
//    assertThat(none, is(nullValue()));
//  }
//
//
//  @Test
//  @DisplayName("MemberEntity : SELECT PWD TEST : 비밀번호 GET")
//  public void testGetPwd() {
//    // when - 초기 데이터 생성
//    MemberEntity memberTestEntity =
//        MemberEntity.builder().userId("testId").userName("testName").userPwd("testPwd")
//            .userNumber("testNumber").userEmail("testEmail").userAddress("testEmail").build();
//    String encodedPassword1 = passwordEncoder.encode(memberTestEntity.getUserPwd());
//    memberTestEntity.setUserPwd(encodedPassword1);
//    em.persist(memberTestEntity);
//
//
//    // 동기화
//    em.flush();
//    em.clear();
//
//
//    // when - 존재하는 데이터와 존재하지 않는 데이터 조회
//    String userId = memberTestEntity.getUserId();
//    String pwd = queryFactory.select(memberEntity.userPwd).from(memberEntity)
//        .where(memberEntity.userId.eq(userId)).fetchFirst();
//    String none = queryFactory.select(memberEntity.userPwd).from(memberEntity)
//        .where(memberEntity.userId.eq("NoInDB")).fetchFirst();
//
//    // then
//    assertEquals(pwd, memberTestEntity.getUserPwd());
//    assertEquals(none, null);
//  }
//
//
//  @Test
//  @DisplayName("MemberEntity : SELECT EMAIL TEST : 비밀번호 GET")
//  public void testGetEmail() {
//    // when - 초기 데이터 생성
//    MemberEntity memberTestEntity =
//        MemberEntity.builder().userId("testId").userName("testName").userPwd("testPwd")
//            .userNumber("testNumber").userEmail("testEmail").userAddress("testEmail").build();
//    String encodedPassword1 = passwordEncoder.encode(memberTestEntity.getUserPwd());
//    memberTestEntity.setUserPwd(encodedPassword1);
//    em.persist(memberTestEntity);
//
//    // 동기화
//    em.flush();
//    em.clear();
//
//    // when - 이메일 조회 (존재하는 데이터, 존재하지 않는 데이터)
//    String userId = memberTestEntity.getUserId();
//    String email1 = queryFactory.select(memberEntity.userEmail).from(memberEntity)
//        .where(memberEntity.userId.eq(userId)).fetchFirst();
//    String none = queryFactory.select(memberEntity.userEmail).from(memberEntity)
//        .where(memberEntity.userId.eq("NoInDB")).fetchFirst();
//
//    // then
//    assertEquals(email1, memberTestEntity.getUserEmail());
//    assertEquals(none, null);
//  }
//
//
//  @Test
//  @DisplayName("MemberEntity : SET TEST : 회원정보 수정")
//  public void testSetInfo() {
//    // given - 초기 데이터 저장
//    MemberEntity user =
//        MemberEntity.builder().userId("userId").userName("userName").userPwd("userPwd")
//            .userNumber("userNumber").userEmail("userEmail").userAddress("userEmail").build();
//    String encodedPassword = passwordEncoder.encode(user.getUserPwd());
//    user.setUserPwd(encodedPassword);
//    em.persist(user);
//
//
//    String changedPwd = "changedPwd";
//    String changedName = "changedName";
//    String changedEmail = "changedEmail";
//    String changedAddress = "changedAddress";
//    String changedNumber = "changedNumber";
//
//    // 동기화
//    em.flush();
//    em.clear();
//
//
//    // when - 수정
//    queryFactory.update(memberEntity).set(memberEntity.userPwd, changedPwd)
//        .set(memberEntity.userName, changedName).set(memberEntity.userEmail, changedEmail)
//        .set(memberEntity.userAddress, changedAddress).set(memberEntity.userNumber, changedNumber)
//        .where(memberEntity.userId.eq(user.getUserId())).execute();
//
//    // 동기화
//    em.flush();
//    em.clear();
//
//
//
//    // then - 변경 여부 확인
//    MemberEntity updatedUser = em.find(MemberEntity.class, user.getUserId());
//    assertEquals(updatedUser.getUserPwd(), changedPwd);
//    assertEquals(updatedUser.getUserAddress(), changedAddress);
//    assertEquals(updatedUser.getUserName(), changedName);
//    assertEquals(updatedUser.getUserEmail(), changedEmail);
//    assertEquals(updatedUser.getUserNumber(), changedNumber);
//  }
//
//
//  @Test
//  @DisplayName("MemberEntity : DELETE TEST : 회원정보 삭제")
//  public void testDeleteMember() {
//
//    // when - 초기 데이터 저장
//    MemberEntity user =
//        MemberEntity.builder().userId("userId").userName("userName").userPwd("userPwd")
//            .userNumber("userNumber").userEmail("userEmail").userAddress("userEmail").build();
//    String encodedPassword = passwordEncoder.encode(user.getUserPwd());
//    user.setUserPwd(encodedPassword);
//    em.persist(user);
//
//    // 동기화
//    em.flush();
//    em.clear();
//
//
//    // when - 삭제 전
//    Long beforeDelete = queryFactory.select(memberEntity.count()).from(memberEntity).fetch().get(0);
//
//
//    // when - 삭제 후
//    queryFactory.delete(memberEntity).where(memberEntity.userId.eq(user.getUserId())).execute();
//    Long afterDelete = queryFactory.select(memberEntity.count()).from(memberEntity).fetch().get(0);
//
//
//    // then - 결과 체크
//    assertThat(beforeDelete, equalTo(1L));
//    assertThat(afterDelete, equalTo(0L));
//
//  }
//
//
//
//  // 2. Store Entity : StoreDAOImpl
//  @Test
//  @DisplayName("StoreEntity : PERSIST TEST : 가게등록 성공")
//  public void testPersistStoreSuccess() {
//    // given - 초기 데이터 저장
//    StoreEntity storeTestEntity =
//        StoreEntity.builder().storeName("storeName1").country("country1").city("city1")
//            .dong("dong1").type("type1").couponInfo("couponInfo1").ownerId("ownerId1").build();
//
//    // when
//    em.persist(storeTestEntity);
//
//
//    // 동기화
//    em.flush();
//    em.clear();
//
//    // then - 잘 저장 되었는지 확인
//    Long count = queryFactory.select(storeEntity.count()).from(storeEntity).fetch().get(0);
//    assertThat(count, equalTo(1L));
//  }
//
//
//  @Test
//  @DisplayName("StoreEntity : SELECT STORELIST TEST : 가게리스트 출력 성공")
//  public void testGetStoreListSuccess() {
//    // given - 초기 데이터 생성
//    StoreEntity storeTestEntity =
//        StoreEntity.builder().storeName("storeName1").country("country1").city("city1")
//            .dong("dong1").type("type1").couponInfo("couponInfo1").ownerId("ownerId1").build();
//    em.persist(storeTestEntity);
//
//
//    // 동기화
//    em.flush();
//    em.clear();
//
//
//    // when - 검색
//    List<StoreEntity> storeInfo = queryFactory.select(storeEntity).from(storeEntity)
//        .where(storeEntity.country.eq("country1").and(storeEntity.city.eq("city1"))
//            .and(storeEntity.dong.eq("dong1")).and(storeEntity.type.eq("type1")))
//        .limit(2).offset(0 * 2).fetch();
//
//
//    // then - 결과 확인
//    assertThat(storeInfo.size(), equalTo(1));
//  }
//
//
//
//  @Test
//  @DisplayName("StoreEntity : SELECT OWNER ID : 가게 사장 아이디 찾기 성공")
//  public void testGetStoreOwnerIdSuccess() {
//    // given - 초기 데이터 생성
//    StoreEntity storeTestEntity =
//        StoreEntity.builder().storeName("storeName1").country("country1").city("city1")
//            .dong("dong1").type("type1").couponInfo("couponInfo1").ownerId("ownerId1").build();
//    em.persist(storeTestEntity);
//
//
//    // 동기화
//    em.flush();
//    em.clear();
//
//    // when - 조회
//    String ownerId = queryFactory.select(storeEntity.ownerId).from(storeEntity)
//        .where(storeEntity.storeId.eq(3)).fetchFirst();
//
//    // then - 결과 확인
//    assertThat(ownerId, equalTo("ownerId1"));
//
//  }
//
//
//  // 3. StoreRestDaysEntity, StoreRestDaysMapEntity : StoreInfoDAOImpl
//  @Test
//  @DisplayName("StoreRestDaysEntity, StoreRestDaysMapEntity : PERSIST : 가게 쉬는날 등록 성공")
//  public void testPersistRestDaysSuccess() {
//    // given - 초기 데이터 생성
//    StoreRestDayDTO sample = StoreRestDayDTO.sample();
//    StoreRestDaysEntity parent = new StoreRestDaysEntity(sample.getStoreId());
//    em.persist(parent);
//    Set<StoreRestDaysMapEntity> childs = new HashSet<>();
//    for (String date : sample.getDate()) {
//      StoreRestDaysMapEntity child = new StoreRestDaysMapEntity(date, parent);
//      em.persist(child);
//      childs.add(child);
//    }
//    parent.setChildSet(childs);
//
//    // 동기화
//    em.flush();
//    em.clear();
//
//    // when - 결과 조회
//    List<StoreRestDaysEntity> p_entities =
//        queryFactory.select(storeRestDaysEntity).from(storeRestDaysEntity).fetch();
//    List<StoreRestDaysMapEntity> c_entities =
//        queryFactory.select(storeRestDaysMapEntity).from(storeRestDaysMapEntity).fetch();
//
//    // then - 결과 확인
//    assertThat(p_entities.size(), is(1));
//    assertThat(c_entities.size(), is(1));
//  }
//
//  @Test
//  @DisplayName("StoreRestDaysEntity, StoreRestDaysMapEntity : SELECT DATE BY STOREID : 가게 쉬는날 조회 성공")
//  public void testGetRestDaysByStoreIdSuccess() {
//    // given - 초기 데이터 생성
//    int storeId = 1;
//    StoreRestDayDTO sample = StoreRestDayDTO.sample();
//    StoreRestDaysEntity parent = new StoreRestDaysEntity(sample.getStoreId());
//    em.persist(parent);
//    Set<StoreRestDaysMapEntity> childs = new HashSet<>();
//    for (String date : sample.getDate()) {
//      StoreRestDaysMapEntity child = new StoreRestDaysMapEntity(date, parent);
//      em.persist(child);
//      childs.add(child);
//    }
//    parent.setChildSet(childs);
//
//
//    // 동기화
//    em.flush();
//    em.clear();
//
//    // when - 결과 조회
//    List<String> resultList =
//        queryFactory.selectDistinct(storeRestDaysMapEntity.date).from(storeRestDaysMapEntity)
//            .leftJoin(storeRestDaysMapEntity.storeRestDaysEntity, storeRestDaysEntity)
//            .where(storeRestDaysEntity.storeId.eq(storeId)).fetch();
//
//    // then - 결과 확인
//    assertThat(resultList.size(), is(1));
//  }
//
//  @Test
//  @DisplayName("StoreRestDaysEntity, StoreRestDaysMapEntity : DELETE BY STOREID : 가게 쉬는날 삭제 성공")
//  public void testDeleteRestDaysByStoreIdSuccess() {
//    // given - 초기 데이터 생성
//    int storeId = 1;
//    StoreRestDayDTO sample = StoreRestDayDTO.sample();
//    StoreRestDaysEntity parent = new StoreRestDaysEntity(sample.getStoreId());
//    em.persist(parent);
//    Set<StoreRestDaysMapEntity> childs = new HashSet<>();
//    for (String date : sample.getDate()) {
//      StoreRestDaysMapEntity child = new StoreRestDaysMapEntity(date, parent);
//      em.persist(child);
//      childs.add(child);
//    }
//    parent.setChildSet(childs);
//
//    // 동기화
//    em.flush();
//    em.clear();
//
//
//
//    // when - 데이터 삭제
//    Long days_id = queryFactory.select(storeRestDaysEntity.daysId).from(storeRestDaysEntity)
//        .where(storeRestDaysEntity.storeId.eq(storeId)).fetchFirst();
//    queryFactory.delete(storeRestDaysMapEntity)
//        .where(storeRestDaysMapEntity.storeRestDaysEntity.daysId.eq(days_id)).execute();
//    queryFactory.delete(storeRestDaysEntity).where(storeRestDaysEntity.storeId.eq(storeId))
//        .execute();
//    List<String> resultList =
//        queryFactory.selectDistinct(storeRestDaysMapEntity.date).from(storeRestDaysMapEntity)
//            .leftJoin(storeRestDaysMapEntity.storeRestDaysEntity, storeRestDaysEntity)
//            .where(storeRestDaysEntity.storeId.eq(storeId)).fetch();
//
//    // then - 결과 확인
//    assertThat(resultList.size(), is(0));
//  }
//
//
//  // 4. StoreTimeInfoEntity, StoreTimeInfoMapEntity : StoreInfoDAOImpl
//  @Test
//  @DisplayName("StoreTimeInfoEntity, StoreTimeInfoMapEntity : PERSIST : 가게 영업시간 등록 성공")
//  public void testPersistTimeInfoSuccess() {
//    // given - 초기 데이터 생성
//    StoreTimeInfoDTO storeTimeInfoDTO = StoreTimeInfoDTO.sample();
//    StoreTimeInfoEntity parent =
//        StoreTimeInfoEntity.builder().startTime(storeTimeInfoDTO.getStartTime())
//            .endTime(storeTimeInfoDTO.getEndTime()).intervalTime(storeTimeInfoDTO.getIntervalTime())
//            .storeId(storeTimeInfoDTO.getStoreId()).build();
//    em.persist(parent);
//    Set<StoreTimeInfoMapEntity> childs = new HashSet<>();
//    for (String time : storeTimeInfoDTO.getBreakTime()) {
//      StoreTimeInfoMapEntity child =
//          StoreTimeInfoMapEntity.builder().time(time).storeTimeInfoEntity(parent).build();
//      em.persist(child);
//      childs.add(child);
//    }
//    parent.setBreakTime(childs);
//
//
//    // 동기화
//    em.flush();
//    em.clear();
//
//
//    // when - 조회
//    List<StoreTimeInfoEntity> p_entities =
//        queryFactory.select(storeTimeInfoEntity).from(storeTimeInfoEntity).fetch();
//    List<StoreTimeInfoMapEntity> c_entities =
//        queryFactory.select(storeTimeInfoMapEntity).from(storeTimeInfoMapEntity).fetch();
//
//
//    // then - 잘 저장되었는지 확인
//    assertThat(p_entities.size(), is(1));
//    assertThat(c_entities.size(), is(1));
//  }
//
//  @Test
//  @DisplayName("StoreTimeInfoEntity, StoreTimeInfoMapEntity : SELECT DATE BY STOREID : 가게 영업시간 출력 성공")
//  public void testGetTimeInfoSuccess() {
//    // given - 초기 값 생성
//    StoreTimeInfoDTO storeTimeInfoDTO = StoreTimeInfoDTO.sample();
//    StoreTimeInfoEntity parent =
//        StoreTimeInfoEntity.builder().startTime(storeTimeInfoDTO.getStartTime())
//            .endTime(storeTimeInfoDTO.getEndTime()).intervalTime(storeTimeInfoDTO.getIntervalTime())
//            .storeId(storeTimeInfoDTO.getStoreId()).build();
//    em.persist(parent);
//    Set<StoreTimeInfoMapEntity> childs = new HashSet<>();
//    for (String time : storeTimeInfoDTO.getBreakTime()) {
//      StoreTimeInfoMapEntity child =
//          StoreTimeInfoMapEntity.builder().time(time).storeTimeInfoEntity(parent).build();
//      em.persist(child);
//      childs.add(child);
//    }
//    parent.setBreakTime(childs);
//
//
//    // 동기화
//    em.flush();
//    em.clear();
//
//
//    // when - 검색
//    int storeId = 1;
//    List<String> resultList =
//        queryFactory.selectDistinct(storeTimeInfoMapEntity.time).from(storeTimeInfoMapEntity)
//            .where(storeTimeInfoMapEntity.storeTimeInfoEntity.storeId.eq(storeId)).fetch();
//    StoreTimeInfoDTO result = queryFactory
//        .select(Projections.fields(StoreTimeInfoDTO.class, storeTimeInfoEntity.startTime,
//            storeTimeInfoEntity.endTime, storeTimeInfoEntity.intervalTime,
//            Expressions.asNumber(storeId).as("storeId")))
//        .from(storeTimeInfoEntity).where(storeTimeInfoEntity.storeId.eq(storeId)).fetchFirst();
//
//
//    // then - 초기값이 검색되는지 체크
//    assertThat(resultList.size(), is(1));
//    assertThat(storeTimeInfoDTO.getStartTime(), equalTo(result.getStartTime()));
//    assertThat(storeTimeInfoDTO.getEndTime(), equalTo(result.getEndTime()));
//    assertThat(storeTimeInfoDTO.getIntervalTime(), equalTo(result.getIntervalTime()));
//  }
//
//  @Test
//  @DisplayName("StoreTimeInfoEntity, StoreTimeInfoMapEntity : UPDATE : 가게 영업시간 수정 성공")
//  public void testModifyTimeInfoSuccess() {
//    // given - 초기 값 생성
//    StoreTimeInfoDTO storeTimeInfoDTO = StoreTimeInfoDTO.sample();
//    StoreTimeInfoEntity parent =
//        StoreTimeInfoEntity.builder().startTime(storeTimeInfoDTO.getStartTime())
//            .endTime(storeTimeInfoDTO.getEndTime()).intervalTime(storeTimeInfoDTO.getIntervalTime())
//            .storeId(storeTimeInfoDTO.getStoreId()).build();
//    em.persist(parent);
//    Set<StoreTimeInfoMapEntity> childs = new HashSet<>();
//    for (String time : storeTimeInfoDTO.getBreakTime()) {
//      // 자식 객체 생성
//      StoreTimeInfoMapEntity child =
//          StoreTimeInfoMapEntity.builder().time(time).storeTimeInfoEntity(parent).build();
//      em.persist(child);
//      childs.add(child);
//    }
//    parent.setBreakTime(childs);
//
//
//
//    // when - 수정
//    int storeId = 1;
//    StoreTimeInfoEntity p_entity = queryFactory.select(storeTimeInfoEntity)
//        .from(storeTimeInfoEntity).where(storeTimeInfoEntity.storeId.eq(storeId)).fetchFirst();
//    queryFactory.delete(storeTimeInfoMapEntity)
//        .where(storeTimeInfoMapEntity.storeTimeInfoEntity.eq(p_entity)).execute();
//    Set<StoreTimeInfoMapEntity> new_childs = new HashSet<>();
//    for (String breakTime : storeTimeInfoDTO.getBreakTime()) {
//      StoreTimeInfoMapEntity child =
//          StoreTimeInfoMapEntity.builder().time(breakTime).storeTimeInfoEntity(p_entity).build();
//      em.persist(child);
//      new_childs.add(child);
//    }
//    p_entity.setStartTime("new_startTime");
//    p_entity.setEndTime("new_endTime");
//    p_entity.setIntervalTime("new_intervalTime");
//    p_entity.setBreakTime(new_childs);
//
//
//    // 동기화
//    em.flush();
//    em.clear();
//
//
//    // then - 변경 여부 체크
//    StoreTimeInfoEntity entity = queryFactory.select(storeTimeInfoEntity).from(storeTimeInfoEntity)
//        .where(storeTimeInfoEntity.storeId.eq(storeId)).fetchFirst();
//    assertThat(entity.getStartTime(), equalTo("new_startTime"));
//    assertThat(entity.getEndTime(), equalTo("new_endTime"));
//    assertThat(entity.getIntervalTime(), equalTo("new_intervalTime"));
//
//  }
//
//
//  @Test
//  @DisplayName("StoreTimeInfoEntity, StoreTimeInfoMapEntity : DELETE : 가게 영업시간 삭제 성공")
//  public void testDeleteTimeInfoSuccess() {
//    // given - 초기 데이터 생성
//    StoreTimeInfoDTO storeTimeInfoDTO = StoreTimeInfoDTO.sample();
//    StoreTimeInfoEntity parent =
//        StoreTimeInfoEntity.builder().startTime(storeTimeInfoDTO.getStartTime())
//            .endTime(storeTimeInfoDTO.getEndTime()).intervalTime(storeTimeInfoDTO.getIntervalTime())
//            .storeId(storeTimeInfoDTO.getStoreId()).build();
//    em.persist(parent);
//    Set<StoreTimeInfoMapEntity> childs = new HashSet<>();
//    for (String time : storeTimeInfoDTO.getBreakTime()) {
//      StoreTimeInfoMapEntity child =
//          StoreTimeInfoMapEntity.builder().time(time).storeTimeInfoEntity(parent).build();
//      em.persist(child);
//      childs.add(child);
//    }
//    parent.setBreakTime(childs);
//
//    // 동기화
//    em.flush();
//    em.clear();
//
//
//    // when - 삭제
//    int storeId = 1;
//    Long times_id = queryFactory.select(storeTimeInfoEntity.timesId).from(storeTimeInfoEntity)
//        .where(storeTimeInfoEntity.storeId.eq(storeId)).fetchFirst();
//    queryFactory.delete(storeTimeInfoMapEntity)
//        .where(storeTimeInfoMapEntity.storeTimeInfoEntity.timesId.eq(times_id)).execute();
//    queryFactory.delete(storeTimeInfoEntity).where(storeTimeInfoEntity.timesId.eq(times_id))
//        .execute();
//
//
//    // 동기화
//    em.flush();
//    em.clear();
//
//
//    // when - 조회
//    List<StoreTimeInfoEntity> p_entities =
//        queryFactory.select(storeTimeInfoEntity).from(storeTimeInfoEntity).fetch();
//    List<StoreTimeInfoMapEntity> c_entities =
//        queryFactory.select(storeTimeInfoMapEntity).from(storeTimeInfoMapEntity).fetch();
//
//
//
//    // then - 잘 반영되었는지 확인
//    assertThat(p_entities.size(), is(0));
//    assertThat(c_entities.size(), is(0));
//  }
//
//
//  // 5. StoreTableInfoEntity : StoreInfoDAOImpl
//  @Test
//  @DisplayName("StoreTableInfoEntity : PERSIST : 가게 테이블 정보 등록 성공")
//  public void testPersistTableInfoSuccess() {
//    // given - 초기 데이터 생성
//    StoreTableInfoDTO storeTableInfoDTO = StoreTableInfoDTO.sample();
//    StoreTableInfoEntity parent = new StoreTableInfoEntity(storeTableInfoDTO);
//    em.persist(parent);
//
//
//    // 동기화
//    em.flush();
//    em.clear();
//
//
//    // when - 조회
//    List<StoreTableInfoEntity> entities =
//        queryFactory.select(storeTableInfoEntity).from(storeTableInfoEntity).fetch();
//
//
//    // then - 잘 저장되었는지 확인
//    assertThat(entities.size(), is(1));
//  }
//
//  @Test
//  @DisplayName("StoreTableInfoEntity : UPDATE : 가게 테이블 정보 수정 성공")
//  public void testModTableInfoSuccess() {
//    // given - 초기 데이터 생성
//    StoreTableInfoDTO storeTableInfoDTO = StoreTableInfoDTO.sample();
//    StoreTableInfoEntity parent = new StoreTableInfoEntity(storeTableInfoDTO);
//    em.persist(parent);
//
//
//    // 동기화
//    em.flush();
//    em.clear();
//
//
//    // when - 수정
//    StoreTableInfoEntity newData = StoreTableInfoEntity.builder().storeId(0).count(100).build();
//    StoreTableInfoEntity originalData =
//        queryFactory.select(storeTableInfoEntity).from(storeTableInfoEntity)
//            .where(storeTableInfoEntity.storeId.eq(newData.getStoreId())).fetchFirst();
//    originalData.setCount(newData.getCount());
//
//
//    // 동기화
//    em.flush();
//    em.clear();
//
//    // 재조회
//    StoreTableInfoEntity data = queryFactory.select(storeTableInfoEntity).from(storeTableInfoEntity)
//        .where(storeTableInfoEntity.storeId.eq(newData.getStoreId())).fetchFirst();
//
//    // then - 잘 변경되었는지 확인
//    assertThat(data.getCount(), is(100));
//  }
//
//
//
//  @Test
//  @DisplayName("StoreTableInfoEntity : DELETE : 가게 테이블 정보 삭제 성공")
//  public void testDeleteTableInfoSuccess() {
//    // given - 초기 데이터 생성
//    StoreTableInfoDTO storeTableInfoDTO = StoreTableInfoDTO.sample();
//    StoreTableInfoEntity parent = new StoreTableInfoEntity(storeTableInfoDTO);
//    em.persist(parent);
//
//
//    // 동기화
//    em.flush();
//    em.clear();
//
//
//    // when - 삭제
//    queryFactory.delete(storeTableInfoEntity).where(storeTableInfoEntity.storeId.eq(0)).execute();
//
//
//    // 동기화
//    em.flush();
//    em.clear();
//
//    StoreTableInfoEntity data = queryFactory.select(storeTableInfoEntity).from(storeTableInfoEntity)
//        .where(storeTableInfoEntity.storeId.eq(0)).fetchFirst();
//
//    // then - 잘 삭제되었는지 확인
//    assertThat(data, equalTo(null));
//  }
//
//
//  // 6. StoreFoodsInfoEntity : StoreInfoDAOImpl
//  @Test
//  @DisplayName("StoreFoodsInfoEntity : PERSIST : 가게 음식정보 등록 성공")
//  public void testPersistFoodsInfoSuccess() {
//    // given - 초기 데이터 생성
//    StoreFoodsInfoDTO storeTableInfoDTO = StoreFoodsInfoDTO.sample();
//    StoreFoodsInfoEntity parent = new StoreFoodsInfoEntity(storeTableInfoDTO);
//    em.persist(parent);
//
//
//    // 동기화
//    em.flush();
//    em.clear();
//
//
//    // when - 조회
//    List<StoreFoodsInfoEntity> entities =
//        queryFactory.select(storeFoodsInfoEntity).from(storeFoodsInfoEntity).fetch();
//
//
//    // then - 잘 저장되었는지 확인
//    assertThat(entities.size(), is(1));
//  }
//
//
//  @Test
//  @DisplayName("StoreFoodsInfoEntity : SELECT : 가게 음식정보 조회 성공")
//  public void testGetFoodsInfoSuccess() {
//    // given - 초기 데이터 생성
//    StoreFoodsInfoDTO storeTableInfoDTO = StoreFoodsInfoDTO.sample();
//    StoreFoodsInfoEntity parent = new StoreFoodsInfoEntity(storeTableInfoDTO);
//    em.persist(parent);
//
//
//    // 동기화
//    em.flush();
//    em.clear();
//
//
//    // when - 조회
//    List<StoreFoodsInfoEntity> result = queryFactory.select(storeFoodsInfoEntity)
//        .from(storeFoodsInfoEntity).where(storeFoodsInfoEntity.storeId.eq(0)).fetch();
//
//
//    // then - 잘 저장되었는지 확인
//    assertThat(result.size(), is(1));
//  }
//
//
//  @Test
//  @DisplayName("StoreFoodsInfoEntity : UPDATE : 가게 음식정보 수정 성공")
//  public void testUpdateFoodsInfoSuccess() {
//    // given - 초기 데이터 생성
//    StoreFoodsInfoDTO storeTableInfoDTO = StoreFoodsInfoDTO.sample();
//    StoreFoodsInfoEntity parent = new StoreFoodsInfoEntity(storeTableInfoDTO);
//    em.persist(parent);
//
//
//    // 동기화
//    em.flush();
//    em.clear();
//
//
//    // when - 조회
//    StoreFoodsInfoEntity result = queryFactory.select(storeFoodsInfoEntity)
//        .from(storeFoodsInfoEntity).where(storeFoodsInfoEntity.storeId.eq(0)).fetchFirst();
//
//
//    // 수정
//    result.setFoodName("newFOODNAME");
//    result.setFoodPrice(10000);
//    result.setFoodDescription("newFOODDESCRIPTION");
//    result.setImageURL("newURL");
//
//    // 동기화
//    em.flush();
//    em.clear();
//
//
//    // when - 재조회
//    StoreFoodsInfoEntity final_result = queryFactory.select(storeFoodsInfoEntity)
//        .from(storeFoodsInfoEntity).where(storeFoodsInfoEntity.storeId.eq(0)).fetchFirst();
//
//    // then - 잘 저장되었는지 확인
//    assertThat(final_result.getFoodName(), equalTo("newFOODNAME"));
//    assertThat(final_result.getFoodPrice(), is(10000));
//    assertThat(final_result.getFoodDescription(), equalTo("newFOODDESCRIPTION"));
//    assertThat(final_result.getImageURL(), equalTo("newURL"));
//  }
//
//
//
//  @Test
//  @DisplayName("StoreFoodsInfoEntity : DELETE : 가게 음식정보 삭제 성공")
//  public void testDeleteFoodsInfoSuccess() {
//    // given - 초기 데이터 생성
//    StoreFoodsInfoDTO storeTableInfoDTO = StoreFoodsInfoDTO.sample();
//    StoreFoodsInfoEntity parent = new StoreFoodsInfoEntity(storeTableInfoDTO);
//    em.persist(parent);
//
//
//    // 동기화
//    em.flush();
//    em.clear();
//
//
//    // when - 삭제
//    queryFactory.delete(storeFoodsInfoEntity).where(storeFoodsInfoEntity.storeId.eq(0)
//        .and(storeFoodsInfoEntity.foodName.eq(storeTableInfoDTO.getFoodName()))).execute();
//
//
//    // 동기화
//    em.flush();
//    em.clear();
//
//
//    // when - 재조회
//    StoreFoodsInfoEntity final_result = queryFactory.select(storeFoodsInfoEntity)
//        .from(storeFoodsInfoEntity).where(storeFoodsInfoEntity.storeId.eq(0)).fetchFirst();
//
//    // then - 잘 저장되었는지 확인
//    assertThat(final_result, equalTo(null));
//  }
//
//
//  // 7. Store Reservation Entity : StorePORDAOImpl
//  @Test
//  @DisplayName("StoreReservationEntity : PERSIST : 가게 예약정보 등록 성공")
//  public void testPersistReservationSuccess() {
//    // given
//    ReservationDTO reservationDTO = ReservationDTO.sample();
//    String userId = "userId";
//    StoreReservationEntity entity = new StoreReservationEntity(reservationDTO);
//    entity.setUserId(userId);
//
//    // when - 영속화
//    em.persist(entity);
//    em.flush();
//    em.clear();
//
//
//    // then - DB에 데이터는 1개
//    Long count = queryFactory.select(storeReservationEntity.count()).from(storeReservationEntity)
//        .fetch().get(0);
//    assertThat(count, equalTo(1L));
//  }
//
//  @Test
//  @DisplayName("StoreReservationEntity : SELECT, UPDATE : 가게 예약정보 조회 후 변경 성공")
//  public void testUpdateReservationSuccess() {
//    // given
//    ReservationDTO reservationDTO = ReservationDTO.sample();
//    String userId = "userId";
//    StoreReservationEntity entity = new StoreReservationEntity(reservationDTO);
//    entity.setUserId(userId);
//
//    // when - 영속화, 조회, 변경
//    em.persist(entity);
//    em.flush();
//    em.clear();
//
//    StoreReservationEntity new_entity = queryFactory.select(storeReservationEntity)
//        .from(storeReservationEntity).where(storeReservationEntity.storeId
//            .eq(reservationDTO.getStoreId()).and(storeReservationEntity.userId.eq(userId)))
//        .fetchFirst();
//    new_entity.setDate("날짜");
//    new_entity.setTime("시간");
//    new_entity.setStoreTable("테이블");
//
//    em.flush();
//    em.clear();
//
//
//    // then - 재 조회 후 검증
//    StoreReservationEntity final_entity = queryFactory.select(storeReservationEntity)
//        .from(storeReservationEntity).where(storeReservationEntity.storeId
//            .eq(reservationDTO.getStoreId()).and(storeReservationEntity.userId.eq(userId)))
//        .fetchFirst();
//    assertEquals(final_entity.getDate(), "날짜");
//    assertEquals(final_entity.getTime(), "시간");
//    assertEquals(final_entity.getStoreTable(), "테이블");
//
//  }
//
//
//  @Test
//  @DisplayName("StoreReservationEntity : SELECT BY STORE ID, USER ID : 가게 예약정보 가게아이디, 회원아이디로 조회")
//  public void testGetReservationByIDSuccess() {
//    // given
//    ReservationDTO reservationDTO = ReservationDTO.sample();
//    String userId = "userId";
//    int storeId = 0;
//    StoreReservationEntity entity = new StoreReservationEntity(reservationDTO);
//    entity.setUserId(userId);
//    entity.setStoreId(storeId);
//
//    // when - 영속화, 조회, 변경
//    em.persist(entity);
//    em.flush();
//    em.clear();
//
//
//    // when - 조회
//    StoreReservationEntity result = queryFactory.select(storeReservationEntity)
//        .from(storeReservationEntity).leftJoin(storeReservationEntity.child, storeOrdersEntity)
//        .fetchJoin().leftJoin(storeOrdersEntity.payment, storePayEntity).fetchJoin()
//        .leftJoin(storeOrdersEntity.childSet, storeOrdersMapEntity).fetchJoin()
//        .where(storeReservationEntity.storeId.eq(storeId)
//            .and(storeReservationEntity.userId.eq(userId)))
//        .fetchFirst();
//
//
//    // then - 검증
//    assertEquals(result.getUserId(), userId);
//    assertThat(result.getStoreId(), is(storeId));
//  }
//
//  @Test
//  @DisplayName("StoreReservationEntity : DELETE : 가게 예약정보 삭제")
//  public void testDeleteReservationByIDSuccess() {
//    // given
//    ReservationDTO reservationDTO = ReservationDTO.sample();
//    String userId = "userId";
//    int storeId = 0;
//    StoreReservationEntity entity = new StoreReservationEntity(reservationDTO);
//    entity.setUserId(userId);
//    entity.setStoreId(storeId);
//
//    // when - 영속화, 조회, 변경
//    em.persist(entity);
//    em.flush();
//    em.clear();
//
//
//    // when
//    queryFactory.delete(storeReservationEntity).where(
//        storeReservationEntity.storeId.eq(storeId).and(storeReservationEntity.userId.eq(userId)))
//        .execute();
//
//    // then - 데이터가 없어야 함.
//    Long count = queryFactory.select(storeReservationEntity.count()).from(storeReservationEntity)
//        .fetch().get(0);
//    assertThat(count, equalTo(0L));
//  }
//
//
//  // 8. StoreOrdersEntity, StoreOrdersMapEntity : StorePORDAOImpl
//  @Test
//  @DisplayName("StoreOrdersEntity, StoreOrdersMapEntity : PERSIST : 예약 정보 조회 후 주문 정보 삽입")
//  public void testRegisterOrderSuccess() {
//    // given
//
//    // 1. ReservationEntity 저장
//    ReservationDTO reservationDTO = ReservationDTO.sample();
//    String userId = "userId";
//    int storeId = 0;
//    StoreReservationEntity entity = new StoreReservationEntity(reservationDTO);
//    entity.setUserId(userId);
//    entity.setStoreId(storeId);
//
//    // when - 영속화, 조회, 변경
//    em.persist(entity);
//    em.flush();
//    em.clear();
//
//
//    // 2. 저장한 엔터티 불러와 OrdersEntity 연결후 저장
//    StoreReservationEntity grandfa = queryFactory.select(storeReservationEntity)
//        .from(storeReservationEntity).where(storeReservationEntity.storeId
//            .eq(reservationDTO.getStoreId()).and(storeReservationEntity.userId.eq(userId)))
//        .fetchFirst();
//
//    StoreOrdersEntity father = new StoreOrdersEntity(grandfa);
//    em.persist(father);
//
//
//    // 3. OrdersMap까지 저장 후 모두 연결
//    OrderDTO orderDTO = OrderDTO.sample();
//    List<StoreOrdersMapEntity> childs = new ArrayList<>();
//    for (String foodName : orderDTO.getOrderInfo().keySet()) {
//      StoreOrdersMapEntity child =
//          new StoreOrdersMapEntity(foodName, orderDTO.getOrderInfo().get(foodName), father);
//      em.persist(child);
//      childs.add(child);
//    }
//    father.setChildSet(childs);
//    grandfa.setChild(father);
//
//    em.flush();
//    em.clear();
//
//
//    // when - 모두 조회
//    StoreReservationEntity result = queryFactory.select(storeReservationEntity)
//        .from(storeReservationEntity).leftJoin(storeReservationEntity.child, storeOrdersEntity)
//        .fetchJoin().leftJoin(storeOrdersEntity.childSet, storeOrdersMapEntity).fetchJoin()
//        .where(storeReservationEntity.storeId.eq(storeId)
//            .and(storeReservationEntity.userId.eq(userId)))
//        .fetchFirst();
//
//
//    // then - 모두 연결 되어있음
//    assertEquals(result.getChild().getOrdersId(), father.getOrdersId());
//    assertEquals(result.getChild().getChildSet().get(0).getFoodName(), childs.get(0).getFoodName());
//
//  }
//
//  @Test
//  @DisplayName("StoreOrdersEntity, StoreOrdersMapEntity : UPDATE : 예약 정보 조회 후 주문 정보 수정")
//  public void testUpdateOrderSuccess() {
//    // given
//
//    // 1. ReservationEntity 저장
//    ReservationDTO reservationDTO = ReservationDTO.sample();
//    String userId = "userId";
//    int storeId = 0;
//    StoreReservationEntity entity = new StoreReservationEntity(reservationDTO);
//    entity.setUserId(userId);
//    entity.setStoreId(storeId);
//
//    // when - 영속화, 조회, 변경
//    em.persist(entity);
//    em.flush();
//    em.clear();
//
//
//    // 2. 저장한 엔터티 불러와 OrdersEntity 연결후 저장
//    StoreReservationEntity grandfa = queryFactory.select(storeReservationEntity)
//        .from(storeReservationEntity).where(storeReservationEntity.storeId
//            .eq(reservationDTO.getStoreId()).and(storeReservationEntity.userId.eq(userId)))
//        .fetchFirst();
//
//    StoreOrdersEntity father = new StoreOrdersEntity(grandfa);
//    em.persist(father);
//
//
//    // 3. OrdersMap까지 저장 후 모두 연결
//    OrderDTO orderDTO = OrderDTO.sample();
//    List<StoreOrdersMapEntity> childs = new ArrayList<>();
//    for (String foodName : orderDTO.getOrderInfo().keySet()) {
//      StoreOrdersMapEntity child =
//          new StoreOrdersMapEntity(foodName, orderDTO.getOrderInfo().get(foodName), father);
//      em.persist(child);
//      childs.add(child);
//    }
//    father.setChildSet(childs);
//    grandfa.setChild(father);
//
//    em.flush();
//    em.clear();
//
//
//    // when - 모두 조회 후 수정
//    StoreReservationEntity result = queryFactory.select(storeReservationEntity)
//        .from(storeReservationEntity).leftJoin(storeReservationEntity.child, storeOrdersEntity)
//        .fetchJoin().leftJoin(storeOrdersEntity.childSet, storeOrdersMapEntity).fetchJoin()
//        .where(storeReservationEntity.storeId.eq(orderDTO.getStoreId())
//            .and(storeReservationEntity.userId.eq(userId)))
//        .fetchFirst();
//
//    List<StoreOrdersMapEntity> childsSet = result.getChild().getChildSet();
//    for (StoreOrdersMapEntity child : childsSet) {
//      for (String foodName : orderDTO.getOrderInfo().keySet()) {
//        if (child.getFoodName().equals(foodName)) {
//          child.setFoodCount(0);
//        }
//      }
//    }
//
//
//    // then - 변경이 잘 되어있음, 변경내용 foodCount 체크
//    assertThat(result.getChild().getChildSet().get(0).getFoodCount(), is(0));
//    assertThat(result.getChild().getChildSet().get(1).getFoodCount(), is(0));
//
//  }
//
//
//  @Test
//  @DisplayName("StoreOrdersEntity, StoreOrdersMapEntity : DELETE : 예약 정보 조회 후 주문 정보 삭제")
//  public void testDeleteOrderSuccess() {
//    // given
//
//    // 1. ReservationEntity 저장
//    ReservationDTO reservationDTO = ReservationDTO.sample();
//    String userId = "userId";
//    int storeId = 0;
//    StoreReservationEntity entity = new StoreReservationEntity(reservationDTO);
//    entity.setUserId(userId);
//    entity.setStoreId(storeId);
//
//    // when - 영속화, 조회, 변경
//    em.persist(entity);
//    em.flush();
//    em.clear();
//
//
//    // 2. 저장한 엔터티 불러와 OrdersEntity 연결후 저장
//    StoreReservationEntity grandfa = queryFactory.select(storeReservationEntity)
//        .from(storeReservationEntity).where(storeReservationEntity.storeId
//            .eq(reservationDTO.getStoreId()).and(storeReservationEntity.userId.eq(userId)))
//        .fetchFirst();
//
//    StoreOrdersEntity father = new StoreOrdersEntity(grandfa);
//    em.persist(father);
//
//
//    // 3. OrdersMap까지 저장 후 모두 연결
//    OrderDTO orderDTO = OrderDTO.sample();
//    List<StoreOrdersMapEntity> childs = new ArrayList<>();
//    for (String foodName : orderDTO.getOrderInfo().keySet()) {
//      StoreOrdersMapEntity child =
//          new StoreOrdersMapEntity(foodName, orderDTO.getOrderInfo().get(foodName), father);
//      em.persist(child);
//      childs.add(child);
//    }
//    father.setChildSet(childs);
//    grandfa.setChild(father);
//
//    em.flush();
//    em.clear();
//
//
//    // when - 모두 조회 후 삭제 - 특정음식 주문 건 삭제
//    StoreReservationEntity result = queryFactory.select(storeReservationEntity)
//        .from(storeReservationEntity).leftJoin(storeReservationEntity.child, storeOrdersEntity)
//        .fetchJoin().leftJoin(storeOrdersEntity.childSet, storeOrdersMapEntity).fetchJoin()
//        .where(storeReservationEntity.storeId.eq(orderDTO.getStoreId())
//            .and(storeReservationEntity.userId.eq(userId)))
//        .fetchFirst();
//
//    queryFactory
//        .delete(storeOrdersMapEntity).where(storeOrdersMapEntity.storeOrdersEntity.ordersId
//            .eq(result.getChild().getOrdersId()).and(storeOrdersMapEntity.foodName.eq("foodName1")))
//        .execute();
//
//    em.flush();
//    em.clear();
//
//
//    // then - 삭제 적용 여부 확인 : 2개에서 1개로 삭제
//    StoreReservationEntity new_result = queryFactory.select(storeReservationEntity)
//        .from(storeReservationEntity).leftJoin(storeReservationEntity.child, storeOrdersEntity)
//        .fetchJoin().leftJoin(storeOrdersEntity.childSet, storeOrdersMapEntity).fetchJoin()
//        .where(storeReservationEntity.storeId.eq(orderDTO.getStoreId())
//            .and(storeReservationEntity.userId.eq(userId)))
//        .fetchFirst();
//    assertThat(new_result.getChild().getChildSet().size(), is(1));
//
//  }
//
//
//  // 9. StorePayEntity, StoreCouponEntity : StorePORDAOImpl
//  @Test
//  @DisplayName("StorePayEntity, StoreCouponEntity : PERSIST : 결제 등록 및 쿠폰 증가")
//  public void testRegisterPaySuccess() {
//    // given
//
//    // 1. ReservationEntity 저장
//    ReservationDTO reservationDTO = ReservationDTO.sample();
//    String userId = "userId";
//    int storeId = 0;
//    StoreReservationEntity entity = new StoreReservationEntity(reservationDTO);
//    entity.setUserId(userId);
//    entity.setStoreId(storeId);
//
//    // when - 영속화, 조회, 변경
//    em.persist(entity);
//    em.flush();
//    em.clear();
//
//
//    // 2. 저장한 엔터티 불러와 PayEntity 연결후 저장
//    StoreReservationEntity grandfa = queryFactory.select(storeReservationEntity)
//        .from(storeReservationEntity).where(storeReservationEntity.storeId
//            .eq(reservationDTO.getStoreId()).and(storeReservationEntity.userId.eq(userId)))
//        .fetchFirst();
//
//    StoreOrdersEntity father = new StoreOrdersEntity(grandfa);
//    em.persist(father);
//
//
//    // 3. Pay까지 저장 후 모두 연결
//    StorePayEntity child = new StorePayEntity();
//    child.setAmount(10000);
//    child.setStoreOrdersEntity(father);
//    em.persist(child);
//    father.setPayment(child);
//    grandfa.setChild(father);
//
//
//    // 4. 쿠폰 증가
//    StoreCouponEntity coupon = queryFactory.select(storeCouponEntity).from(storeCouponEntity)
//        .where(storeCouponEntity.userId.eq(userId).and(storeCouponEntity.storeId.eq(storeId)))
//        .fetchFirst();
//    if (coupon == null) {
//      StoreCouponEntity newCoupon =
//          StoreCouponEntity.builder().storeId(storeId).userId(userId).amount(1).build();
//      em.persist(newCoupon);
//    } else {
//      coupon.setAmount(coupon.getAmount() + 1);
//    }
//
//    em.flush();
//    em.clear();
//
//
//    // when - 모두 조회
//    StoreReservationEntity result = queryFactory.select(storeReservationEntity)
//        .from(storeReservationEntity).leftJoin(storeReservationEntity.child, storeOrdersEntity)
//        .fetchJoin().leftJoin(storeOrdersEntity.payment, storePayEntity).fetchJoin()
//        .where(storeReservationEntity.storeId.eq(grandfa.getStoreId())
//            .and(storeReservationEntity.userId.eq(userId)))
//        .fetchFirst();
//
//    StoreCouponEntity new_coupon = queryFactory.select(storeCouponEntity).from(storeCouponEntity)
//        .where(storeCouponEntity.userId.eq(userId).and(storeCouponEntity.storeId.eq(storeId)))
//        .fetchFirst();
//
//
//    // then - 모두 연결 되어있고 쿠폰도 잘 증가됨
//    assertEquals(result.getChild().getPayment().getAmount(), child.getAmount());
//    assertThat(new_coupon.getAmount(), is(1));
//
//
//  }
//
//  @Test
//  @DisplayName("StorePayEntity, StoreCouponEntity : Delete : 결제 취소 및 쿠폰 증가")
//  public void testDeletePaySuccess() {
//    // given
//
//    // 1. ReservationEntity 저장
//    ReservationDTO reservationDTO = ReservationDTO.sample();
//    String userId = "userId";
//    int storeId = 0;
//    StoreReservationEntity entity = new StoreReservationEntity(reservationDTO);
//    entity.setUserId(userId);
//    entity.setStoreId(storeId);
//
//    // when - 영속화, 조회, 변경
//    em.persist(entity);
//    em.flush();
//    em.clear();
//
//
//    // 2. 저장한 엔터티 불러와 PayEntity 연결후 저장
//    StoreReservationEntity grandfa = queryFactory.select(storeReservationEntity)
//        .from(storeReservationEntity).where(storeReservationEntity.storeId
//            .eq(reservationDTO.getStoreId()).and(storeReservationEntity.userId.eq(userId)))
//        .fetchFirst();
//
//    StoreOrdersEntity father = new StoreOrdersEntity(grandfa);
//    em.persist(father);
//
//
//    // 3. Pay까지 저장 후 모두 연결
//    StorePayEntity child = new StorePayEntity();
//    child.setAmount(10000);
//    child.setStoreOrdersEntity(father);
//    em.persist(child);
//    father.setPayment(child);
//    grandfa.setChild(father);
//
//
//    // 4. 쿠폰 증가
//    StoreCouponEntity coupon = queryFactory.select(storeCouponEntity).from(storeCouponEntity)
//        .where(storeCouponEntity.userId.eq(userId).and(storeCouponEntity.storeId.eq(storeId)))
//        .fetchFirst();
//    if (coupon == null) {
//      StoreCouponEntity newCoupon =
//          StoreCouponEntity.builder().storeId(storeId).userId(userId).amount(1).build();
//      em.persist(newCoupon);
//    } else {
//      coupon.setAmount(coupon.getAmount() + 1);
//    }
//
//    em.flush();
//    em.clear();
//
//
//    // 5. 주문 삭제 및 쿠폰 감소
//    StoreReservationEntity result1 = queryFactory.select(storeReservationEntity)
//        .from(storeReservationEntity).leftJoin(storeReservationEntity.child, storeOrdersEntity)
//        .fetchJoin().leftJoin(storeOrdersEntity.payment, storePayEntity).fetchJoin()
//        .where(storeReservationEntity.reservationId.eq(grandfa.getReservationId())).fetchFirst();
//
//
//    queryFactory.delete(storePayEntity)
//        .where(storePayEntity.paymentId.eq(grandfa.getChild().getPayment().getPaymentId()))
//        .execute();
//
//    // 쿠폰 감소
//    StoreCouponEntity coupon1 = queryFactory
//        .select(storeCouponEntity).from(storeCouponEntity).where(storeCouponEntity.userId
//            .eq(result1.getUserId()).and(storeCouponEntity.storeId.eq(result1.getStoreId())))
//        .fetchFirst();
//    coupon1.setAmount(coupon1.getAmount() - 1);
//
//    em.flush();
//    em.clear();
//
//    // when - 모두 조회
//    StoreReservationEntity result = queryFactory.select(storeReservationEntity)
//        .from(storeReservationEntity).leftJoin(storeReservationEntity.child, storeOrdersEntity)
//        .fetchJoin().leftJoin(storeOrdersEntity.payment, storePayEntity).fetchJoin()
//        .where(storeReservationEntity.storeId.eq(grandfa.getStoreId())
//            .and(storeReservationEntity.userId.eq(userId)))
//        .fetchFirst();
//
//    StoreCouponEntity new_coupon = queryFactory.select(storeCouponEntity).from(storeCouponEntity)
//        .where(storeCouponEntity.userId.eq(userId).and(storeCouponEntity.storeId.eq(storeId)))
//        .fetchFirst();
//
//
//    // then - 모두 삭제 되어있고 쿠폰도 잘 감소됨
//    assertEquals(result.getChild().getPayment(), null);
//    assertThat(new_coupon.getAmount(), is(0));
//
//
//  }
//
//  @Test
//  @DisplayName("StorePayEntity, StoreCouponEntity : Register Comment : 댓글 등록")
//  public void testRegisterPayCommentSuccess() {
//    // given
//
//    // 1. ReservationEntity 저장
//    ReservationDTO reservationDTO = ReservationDTO.sample();
//    String userId = "userId";
//    int storeId = 0;
//    StoreReservationEntity entity = new StoreReservationEntity(reservationDTO);
//    entity.setUserId(userId);
//    entity.setStoreId(storeId);
//
//    // when - 영속화, 조회, 변경
//    em.persist(entity);
//    em.flush();
//    em.clear();
//
//
//    // 2. 저장한 엔터티 불러와 PayEntity 연결후 저장
//    StoreReservationEntity grandfa = queryFactory.select(storeReservationEntity)
//        .from(storeReservationEntity).where(storeReservationEntity.storeId
//            .eq(reservationDTO.getStoreId()).and(storeReservationEntity.userId.eq(userId)))
//        .fetchFirst();
//
//    StoreOrdersEntity father = new StoreOrdersEntity(grandfa);
//    em.persist(father);
//
//
//    // 3. Pay까지 저장 후 모두 연결, 댓글도 작성
//    StorePayEntity child = new StorePayEntity();
//    child.setAmount(10000);
//    child.setComment("comment");
//    child.setStoreOrdersEntity(father);
//    em.persist(child);
//    father.setPayment(child);
//    grandfa.setChild(father);
//
//
//
//    em.flush();
//    em.clear();
//
//    // when - 모두 조회
//    StoreReservationEntity result = queryFactory.select(storeReservationEntity)
//        .from(storeReservationEntity).leftJoin(storeReservationEntity.child, storeOrdersEntity)
//        .fetchJoin().leftJoin(storeOrdersEntity.payment, storePayEntity).fetchJoin()
//        .where(storeReservationEntity.storeId.eq(grandfa.getStoreId())
//            .and(storeReservationEntity.userId.eq(userId)))
//        .fetchFirst();
//
//
//    // then - 모두 삭제 되어있고 쿠폰도 잘 감소됨
//    assertEquals(result.getChild().getPayment().getComment(), "comment");
//
//
//  }
//
//  @Test
//  @DisplayName("StorePayEntity, StoreCouponEntity : Delete Comment : 댓글 삭제")
//  public void testDeletePayCommentSuccess() {
//    // given
//
//    // 1. ReservationEntity 저장
//    ReservationDTO reservationDTO = ReservationDTO.sample();
//    String userId = "userId";
//    int storeId = 0;
//    StoreReservationEntity entity = new StoreReservationEntity(reservationDTO);
//    entity.setUserId(userId);
//    entity.setStoreId(storeId);
//
//    // when - 영속화, 조회, 변경
//    em.persist(entity);
//    em.flush();
//    em.clear();
//
//
//    // 2. 저장한 엔터티 불러와 PayEntity 연결후 저장
//    StoreReservationEntity grandfa = queryFactory.select(storeReservationEntity)
//        .from(storeReservationEntity).where(storeReservationEntity.storeId
//            .eq(reservationDTO.getStoreId()).and(storeReservationEntity.userId.eq(userId)))
//        .fetchFirst();
//
//    StoreOrdersEntity father = new StoreOrdersEntity(grandfa);
//    em.persist(father);
//
//
//    // 3. Pay까지 저장 후 모두 연결, 댓글도 작성
//    StorePayEntity child = new StorePayEntity();
//    child.setAmount(10000);
//    child.setComment("comment");
//    child.setStoreOrdersEntity(father);
//    em.persist(child);
//    father.setPayment(child);
//    grandfa.setChild(father);
//
//
//
//    em.flush();
//    em.clear();
//
//    // when - 모두 조회
//    StoreReservationEntity result = queryFactory.select(storeReservationEntity)
//        .from(storeReservationEntity).leftJoin(storeReservationEntity.child, storeOrdersEntity)
//        .fetchJoin().leftJoin(storeOrdersEntity.payment, storePayEntity).fetchJoin()
//        .where(storeReservationEntity.storeId.eq(grandfa.getStoreId())
//            .and(storeReservationEntity.userId.eq(userId)))
//        .fetchFirst();
//
//    // 댓글 삭제
//    result.getChild().getPayment().setComment(null);
//
//    em.flush();
//    em.clear();
//
//
//    // then - 모두 삭제 되어있고 쿠폰도 잘 감소됨
//    StoreReservationEntity final_result = queryFactory.select(storeReservationEntity)
//        .from(storeReservationEntity).leftJoin(storeReservationEntity.child, storeOrdersEntity)
//        .fetchJoin().leftJoin(storeOrdersEntity.payment, storePayEntity).fetchJoin()
//        .where(storeReservationEntity.storeId.eq(grandfa.getStoreId())
//            .and(storeReservationEntity.userId.eq(userId)))
//        .fetchFirst();
//    assertEquals(final_result.getChild().getPayment().getComment(), null);
//  }
//
//  @Test
//  @DisplayName("StorePayEntity, StoreCouponEntity : Register Big Comment : 대댓글 등록")
//  public void testRegisterPayBigCommentSuccess() {
//    // given
//
//    // 1. ReservationEntity 저장
//    ReservationDTO reservationDTO = ReservationDTO.sample();
//    String userId = "userId";
//    int storeId = 0;
//    StoreReservationEntity entity = new StoreReservationEntity(reservationDTO);
//    entity.setUserId(userId);
//    entity.setStoreId(storeId);
//
//    // when - 영속화, 조회, 변경
//    em.persist(entity);
//    em.flush();
//    em.clear();
//
//
//    // 2. 저장한 엔터티 불러와 PayEntity 연결후 저장
//    StoreReservationEntity grandfa = queryFactory.select(storeReservationEntity)
//        .from(storeReservationEntity).where(storeReservationEntity.storeId
//            .eq(reservationDTO.getStoreId()).and(storeReservationEntity.userId.eq(userId)))
//        .fetchFirst();
//
//    StoreOrdersEntity father = new StoreOrdersEntity(grandfa);
//    em.persist(father);
//
//
//    // 3. Pay까지 저장 후 모두 연결, 대댓글도 작성
//    StorePayEntity child = new StorePayEntity();
//    child.setAmount(10000);
//    child.setBigComment("bigcomment");
//    child.setStoreOrdersEntity(father);
//    em.persist(child);
//    father.setPayment(child);
//    grandfa.setChild(father);
//
//
//
//    em.flush();
//    em.clear();
//
//    // when - 모두 조회
//    StoreReservationEntity result = queryFactory.select(storeReservationEntity)
//        .from(storeReservationEntity).leftJoin(storeReservationEntity.child, storeOrdersEntity)
//        .fetchJoin().leftJoin(storeOrdersEntity.payment, storePayEntity).fetchJoin()
//        .where(storeReservationEntity.storeId.eq(grandfa.getStoreId())
//            .and(storeReservationEntity.userId.eq(userId)))
//        .fetchFirst();
//
//
//    // then - 대댓글이 잘 변경됨
//    assertEquals(result.getChild().getPayment().getBigComment(), "bigcomment");
//
//
//  }
//
//  @Test
//  @DisplayName("StorePayEntity, StoreCouponEntity : Delete Big Comment : 대댓글 삭제")
//  public void testDeletePayBigCommentSuccess() {
//    // given
//
//    // 1. ReservationEntity 저장
//    ReservationDTO reservationDTO = ReservationDTO.sample();
//    String userId = "userId";
//    int storeId = 0;
//    StoreReservationEntity entity = new StoreReservationEntity(reservationDTO);
//    entity.setUserId(userId);
//    entity.setStoreId(storeId);
//
//    // when - 영속화, 조회, 변경
//    em.persist(entity);
//    em.flush();
//    em.clear();
//
//
//    // 2. 저장한 엔터티 불러와 PayEntity 연결후 저장
//    StoreReservationEntity grandfa = queryFactory.select(storeReservationEntity)
//        .from(storeReservationEntity).where(storeReservationEntity.storeId
//            .eq(reservationDTO.getStoreId()).and(storeReservationEntity.userId.eq(userId)))
//        .fetchFirst();
//
//    StoreOrdersEntity father = new StoreOrdersEntity(grandfa);
//    em.persist(father);
//
//
//    // 3. Pay까지 저장 후 모두 연결, 대댓글도 작성
//    StorePayEntity child = new StorePayEntity();
//    child.setAmount(10000);
//    child.setBigComment("bigcomment");
//    child.setStoreOrdersEntity(father);
//    em.persist(child);
//    father.setPayment(child);
//    grandfa.setChild(father);
//
//
//
//    em.flush();
//    em.clear();
//
//    // when - 모두 조회
//    StoreReservationEntity result = queryFactory.select(storeReservationEntity)
//        .from(storeReservationEntity).leftJoin(storeReservationEntity.child, storeOrdersEntity)
//        .fetchJoin().leftJoin(storeOrdersEntity.payment, storePayEntity).fetchJoin()
//        .where(storeReservationEntity.storeId.eq(grandfa.getStoreId())
//            .and(storeReservationEntity.userId.eq(userId)))
//        .fetchFirst();
//
//    // 대댓글 삭제
//    result.getChild().getPayment().setBigComment(null);
//
//    em.flush();
//    em.clear();
//
//
//    // then - 대댓글이 잘 삭제됨
//    StoreReservationEntity final_result = queryFactory.select(storeReservationEntity)
//        .from(storeReservationEntity).leftJoin(storeReservationEntity.child, storeOrdersEntity)
//        .fetchJoin().leftJoin(storeOrdersEntity.payment, storePayEntity).fetchJoin()
//        .where(storeReservationEntity.storeId.eq(grandfa.getStoreId())
//            .and(storeReservationEntity.userId.eq(userId)))
//        .fetchFirst();
//    assertEquals(final_result.getChild().getPayment().getBigComment(), null);
//  }
//
//
//
//  @Test
//  @DisplayName("StorePayEntity, StoreCouponEntity : SELECT COUPON BY USER : 유저별 가게 쿠폰 수")
//  public void testGetCouponByClientSuccess() {
//    // given
//
//    // 1. ReservationEntity 저장
//    ReservationDTO reservationDTO = ReservationDTO.sample();
//    String userId = "userId";
//    int storeId = 0;
//    StoreReservationEntity entity = new StoreReservationEntity(reservationDTO);
//    entity.setUserId(userId);
//    entity.setStoreId(storeId);
//
//    // when - 영속화, 조회, 변경
//    em.persist(entity);
//    em.flush();
//    em.clear();
//
//
//    // 2. 저장한 엔터티 불러와 PayEntity 연결후 저장
//    StoreReservationEntity grandfa = queryFactory.select(storeReservationEntity)
//        .from(storeReservationEntity).where(storeReservationEntity.storeId
//            .eq(reservationDTO.getStoreId()).and(storeReservationEntity.userId.eq(userId)))
//        .fetchFirst();
//
//    StoreOrdersEntity father = new StoreOrdersEntity(grandfa);
//    em.persist(father);
//
//
//    // 3. Pay까지 저장 후 모두 연결
//    StorePayEntity child = new StorePayEntity();
//    child.setAmount(10000);
//    child.setStoreOrdersEntity(father);
//    em.persist(child);
//    father.setPayment(child);
//    grandfa.setChild(father);
//
//
//    // 4. 쿠폰 증가
//    StoreCouponEntity coupon = queryFactory.select(storeCouponEntity).from(storeCouponEntity)
//        .where(storeCouponEntity.userId.eq(userId).and(storeCouponEntity.storeId.eq(storeId)))
//        .fetchFirst();
//    if (coupon == null) {
//      StoreCouponEntity newCoupon =
//          StoreCouponEntity.builder().storeId(storeId).userId(userId).amount(1).build();
//      em.persist(newCoupon);
//    } else {
//      coupon.setAmount(coupon.getAmount() + 1);
//    }
//
//    em.flush();
//    em.clear();
//
//
//    // when - 개인별 쿠폰 조회
//    StoreCouponEntity new_coupon = queryFactory.select(storeCouponEntity).from(storeCouponEntity)
//        .where(storeCouponEntity.storeId.eq(storeId).and(storeCouponEntity.userId.eq(userId)))
//        .fetchFirst();
//
//
//    // then - 모두 연결 되어있고 쿠폰도 잘 증가됨
//    assertThat(new_coupon.getAmount(), is(1));
//
//  }
//
//
//  @Test
//  @DisplayName("StorePayEntity, StoreCouponEntity : SELECT COUPON BY OWNER : 가게별 쿠폰 수")
//  public void testGetCouponByOwnerSuccess() {
//    // given
//
//    // 1. ReservationEntity 저장
//    ReservationDTO reservationDTO = ReservationDTO.sample();
//    String userId = "userId";
//    int storeId = 0;
//    StoreReservationEntity entity = new StoreReservationEntity(reservationDTO);
//    entity.setUserId(userId);
//    entity.setStoreId(storeId);
//
//    // when - 영속화, 조회, 변경
//    em.persist(entity);
//    em.flush();
//    em.clear();
//
//
//    // 2. 저장한 엔터티 불러와 PayEntity 연결후 저장
//    StoreReservationEntity grandfa = queryFactory.select(storeReservationEntity)
//        .from(storeReservationEntity).where(storeReservationEntity.storeId
//            .eq(reservationDTO.getStoreId()).and(storeReservationEntity.userId.eq(userId)))
//        .fetchFirst();
//
//    StoreOrdersEntity father = new StoreOrdersEntity(grandfa);
//    em.persist(father);
//
//
//    // 3. Pay까지 저장 후 모두 연결
//    StorePayEntity child = new StorePayEntity();
//    child.setAmount(10000);
//    child.setStoreOrdersEntity(father);
//    em.persist(child);
//    father.setPayment(child);
//    grandfa.setChild(father);
//
//
//    // 4. 쿠폰 증가
//    StoreCouponEntity coupon = queryFactory.select(storeCouponEntity).from(storeCouponEntity)
//        .where(storeCouponEntity.userId.eq(userId).and(storeCouponEntity.storeId.eq(storeId)))
//        .fetchFirst();
//    if (coupon == null) {
//      StoreCouponEntity newCoupon =
//          StoreCouponEntity.builder().storeId(storeId).userId(userId).amount(1).build();
//      em.persist(newCoupon);
//    } else {
//      coupon.setAmount(coupon.getAmount() + 1);
//    }
//
//    em.flush();
//    em.clear();
//
//
//    // when - 가게별 쿠폰 조회
//    List<StoreCouponEntity> result = queryFactory.select(storeCouponEntity).from(storeCouponEntity)
//        .where(storeCouponEntity.storeId.eq(storeId)).fetch();
//    HashMap<String, Integer> map = new HashMap<>();
//    for (StoreCouponEntity now : result) {
//      map.put(now.getUserId(), now.getAmount());
//    }
//
//
//    // then - 모두 잘 조회됨
//    assertThat(result.size(), is(1));
//    assertThat(map.size(), is(1));
//
//  }
//
//
//  // 10. StoreBoardEntity : StoreBoardDAOImpl
//  @Test
//  @DisplayName("StoreBoardEntity : PERSIST, UPDATE : 게시판 글 등록 및 수정")
//  public void testRegisterBoardSuccess() {
//    // given
//
//    // 1. 게시판 등록 및 저장
//    String userId = "userId";
//    String new_imageURL = "imageURL";
//    String new_title = "title";
//    String new_content = "content";
//    Double new_rating = 5.0;
//    BoardDTO boardDTO = BoardDTO.sample();
//    StoreBoardEntity entity =
//        StoreBoardEntity.builder().storeId(boardDTO.getStoreId()).title(boardDTO.getTitle())
//            .content(boardDTO.getContent()).userId(userId).rating(boardDTO.getRating()).build();
//    em.persist(entity);
//    entity.setImageURL(new_imageURL);
//    entity.setTitle(new_title);
//    entity.setContent(new_content);
//    entity.setRating(new_rating);
//
//
//    em.flush();
//    em.clear();
//
//
//    // when - 조회
//    StoreBoardEntity new_entity = queryFactory.select(storeBoardEntity).from(storeBoardEntity)
//        .where(storeBoardEntity.boardId.eq(entity.getBoardId())).fetchFirst();
//
//
//    // then
//    assertEquals(new_entity.getTitle(), new_title);
//    assertEquals(new_entity.getContent(), new_content);
//    assertEquals(new_entity.getImageURL(), new_imageURL);
//    assertThat(new_entity.getRating(), is(new_rating));
//
//  }
//
//
//  @Test
//  @DisplayName("StoreBoardEntity : DELETE : 게시판 글 삭제")
//  public void testDeleteBoardSuccess() {
//    // given
//
//    // 1. 게시판 등록 및 저장
//    String userId = "userId";
//    BoardDTO boardDTO = BoardDTO.sample();
//    StoreBoardEntity entity =
//        StoreBoardEntity.builder().storeId(boardDTO.getStoreId()).title(boardDTO.getTitle())
//            .content(boardDTO.getContent()).userId(userId).rating(boardDTO.getRating()).build();
//    em.persist(entity);
//    em.flush();
//    em.clear();
//
//
//    // 2. 삭제
//    queryFactory.delete(storeBoardEntity).where(storeBoardEntity.boardId.eq(entity.getBoardId()))
//        .execute();
//
//    // when - 조회
//    StoreBoardEntity new_entity = queryFactory.select(storeBoardEntity).from(storeBoardEntity)
//        .where(storeBoardEntity.boardId.eq(entity.getBoardId())).fetchFirst();
//
//
//    // then
//    assertEquals(new_entity, null);
//
//  }
//
//
//  @Test
//  @DisplayName("StoreBoardEntity : SELECT : 게시판 글 조회(가게 아이디, 유저 아이디로 조회)")
//  public void testGetBoardSuccess() {
//    // given
//
//    // 1. 게시판 등록 및 저장
//    String userId = "userId";
//    BoardDTO boardDTO = BoardDTO.sample();
//    StoreBoardEntity entity =
//        StoreBoardEntity.builder().storeId(boardDTO.getStoreId()).title(boardDTO.getTitle())
//            .content(boardDTO.getContent()).userId(userId).rating(boardDTO.getRating()).build();
//    em.persist(entity);
//    em.flush();
//    em.clear();
//
//
//    // when - 조회
//    List<StoreBoardEntity> resultStore = queryFactory.select(storeBoardEntity)
//        .from(storeBoardEntity).where(storeBoardEntity.storeId.eq(boardDTO.getStoreId())).fetch();
//
//    List<StoreBoardEntity> resultUser = queryFactory.select(storeBoardEntity).from(storeBoardEntity)
//        .where(storeBoardEntity.userId.eq(userId)).fetch();
//
//
//    // then
//    assertThat(resultStore.size(), is(1));
//    assertThat(resultUser.size(), is(1));
//    assertEquals(resultStore, resultUser);
//
//  }
//
//
//  @Test
//  @DisplayName("StoreBoardEntity Comment : PERSIST, UPDATE, DELETE : 게시판 글 댓글 등록, 수정, 삭제")
//  public void testRegisterBoardCommentSuccess() {
//    // given
//
//    // 1. 게시판 등록 및 저장
//    String userId = "userId";
//    String new_imageURL = "imageURL";
//    String new_title = "title";
//    String new_content = "content";
//    Double new_rating = 5.0;
//    BoardDTO boardDTO = BoardDTO.sample();
//    StoreBoardEntity entity =
//        StoreBoardEntity.builder().storeId(boardDTO.getStoreId()).title(boardDTO.getTitle())
//            .content(boardDTO.getContent()).userId(userId).rating(boardDTO.getRating()).build();
//    em.persist(entity);
//    entity.setImageURL(new_imageURL);
//    entity.setTitle(new_title);
//    entity.setContent(new_content);
//    entity.setRating(new_rating);
//
//
//    em.flush();
//    em.clear();
//
//
//    // 2. 조회
//    StoreBoardEntity entity1 = queryFactory.select(storeBoardEntity).from(storeBoardEntity)
//        .where(storeBoardEntity.boardId.eq(entity.getBoardId())).fetchFirst();
//
//
//    // 3. 댓글 등록 및 수정
//    String comment = "comment";
//    entity1.setComment(comment);
//    em.flush();
//    em.clear();
//
//
//    // 4. 재조회
//    StoreBoardEntity entity2 = queryFactory.select(storeBoardEntity).from(storeBoardEntity)
//        .where(storeBoardEntity.boardId.eq(entity.getBoardId())).fetchFirst();
//
//    assertEquals(entity2.getComment(), comment);
//
//
//    // 5. 수정
//    String new_comment = "new_comment";
//    entity2.setComment(new_comment);
//    em.flush();
//    em.clear();
//
//
//    // 6. 재조회
//    StoreBoardEntity entity3 = queryFactory.select(storeBoardEntity).from(storeBoardEntity)
//        .where(storeBoardEntity.boardId.eq(entity.getBoardId())).fetchFirst();
//
//    assertEquals(entity3.getComment(), new_comment);
//
//
//    // 7. 삭제
//    entity3.setComment(null);
//    em.flush();
//    em.clear();
//
//    // 8. 재조회
//    StoreBoardEntity entity4 = queryFactory.select(storeBoardEntity).from(storeBoardEntity)
//        .where(storeBoardEntity.boardId.eq(entity.getBoardId())).fetchFirst();
//
//    assertEquals(entity4.getComment(), null);
//
//  }
//
//
//}
