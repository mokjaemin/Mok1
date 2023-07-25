package com.ReservationServer1.DataBase;



import static com.ReservationServer1.data.Entity.member.QMemberEntity.memberEntity;
import static com.ReservationServer1.data.Entity.store.QStoreEntity.storeEntity;
import static com.ReservationServer1.data.Entity.store.QStoreFoodsInfoEntity.storeFoodsInfoEntity;
import static com.ReservationServer1.data.Entity.store.QStoreRestDaysEntity.storeRestDaysEntity;
import static com.ReservationServer1.data.Entity.store.QStoreRestDaysMapEntity.storeRestDaysMapEntity;
import static com.ReservationServer1.data.Entity.store.QStoreTableInfoEntity.storeTableInfoEntity;
import static com.ReservationServer1.data.Entity.store.QStoreTimeInfoEntity.storeTimeInfoEntity;
import static com.ReservationServer1.data.Entity.store.QStoreTimeInfoMapEntity.storeTimeInfoMapEntity;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import com.ReservationServer1.data.DTO.store.StoreFoodsInfoDTO;
import com.ReservationServer1.data.DTO.store.StoreRestDayDTO;
import com.ReservationServer1.data.DTO.store.StoreTableInfoDTO;
import com.ReservationServer1.data.DTO.store.StoreTimeInfoDTO;
import com.ReservationServer1.data.Entity.member.MemberEntity;
import com.ReservationServer1.data.Entity.store.StoreEntity;
import com.ReservationServer1.data.Entity.store.StoreFoodsInfoEntity;
import com.ReservationServer1.data.Entity.store.StoreRestDaysEntity;
import com.ReservationServer1.data.Entity.store.StoreRestDaysMapEntity;
import com.ReservationServer1.data.Entity.store.StoreTableInfoEntity;
import com.ReservationServer1.data.Entity.store.StoreTimeInfoEntity;
import com.ReservationServer1.data.Entity.store.StoreTimeInfoMapEntity;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@DataJpaTest
@ActiveProfiles("test")
@Transactional
public class DBTest {


  @Autowired
  private TestEntityManager testEntityManager;

  private JPAQueryFactory queryFactory;

  private EntityManager em;

  private BCryptPasswordEncoder passwordEncoder;



  private String existIdError = "jakarta.persistence.EntityExistsException: "
      + "A different object with the same identifier value was already associated with the session : ";
  private String memberIdError = "[com.ReservationServer1.data.Entity.member.MemberEntity#testId]";

  @BeforeEach
  void init() {
    em = testEntityManager.getEntityManager();
    passwordEncoder = new BCryptPasswordEncoder();
    queryFactory = new JPAQueryFactory(em);
  }



  // 1. Member Entity : MemberDAOImpl
  @Test
  @DisplayName("MemberEntity : PERSIST TEST : 회원가입 성공")
  public void testPersistMemberSuccess() {
    // given - 초기 데이터 생성
    MemberEntity user =
        MemberEntity.builder().userId("userId").userName("userName").userPwd("userPwd")
            .userNumber("userNumber").userEmail("userEmail").userAddress("userEmail").build();
    String encodedPassword = passwordEncoder.encode(user.getUserPwd());
    user.setUserPwd(encodedPassword);

    // when - 저장
    em.persist(user);

    // 동기화
    em.flush();
    em.clear();


    // then - 조회 후 결과 체크
    Long count = queryFactory.select(memberEntity.count()).from(memberEntity).fetch().get(0);
    assertThat(count, equalTo(1L));
  }

  @Test
  @DisplayName("MemberEntity : PERSIST TEST : 회원가입 실패 : EXIST ID")
  public void testPersistMemberFail() {
    // given - 초기 데이터 생성
    MemberEntity memberTestEntity =
        MemberEntity.builder().userId("testId").userName("testName").userPwd("testPwd")
            .userNumber("testNumber").userEmail("testEmail").userAddress("testEmail").build();
    String encodedPassword1 = passwordEncoder.encode(memberTestEntity.getUserPwd());
    memberTestEntity.setUserPwd(encodedPassword1);
    em.persist(memberTestEntity);

    // 중복 아이디 가진 엔터티 생성
    MemberEntity user =
        MemberEntity.builder().userId("testId").userName("userName").userPwd("userPwd")
            .userNumber("userNumber").userEmail("userEmail").userAddress("userEmail").build();
    String encodedPassword2 = passwordEncoder.encode(user.getUserPwd());
    user.setUserPwd(encodedPassword2);

    // when - 영속화 시도
    EntityExistsException exception = assertThrows(EntityExistsException.class, () -> {
      em.persist(user);
    });

    // then - 영속화 실패 체크
    assertEquals(exception.toString(), existIdError + memberIdError);
    Long count = queryFactory.select(memberEntity.count()).from(memberEntity).fetch().get(0);
    assertThat(count, equalTo(1L));
  }


  @Test
  @DisplayName("MemberEntity : EXIST TEST : 아이디 존재 여부")
  public void testExsistId() {
    // when - 초기 데이터 저장
    MemberEntity memberTestEntity =
        MemberEntity.builder().userId("testId").userName("testName").userPwd("testPwd")
            .userNumber("testNumber").userEmail("testEmail").userAddress("testEmail").build();
    String encodedPassword1 = passwordEncoder.encode(memberTestEntity.getUserPwd());
    memberTestEntity.setUserPwd(encodedPassword1);
    em.persist(memberTestEntity);


    // 동기화
    em.flush();
    em.clear();

    // 조회 쿼리 시도
    String userId = memberTestEntity.getUserId();
    Integer exist = queryFactory.selectOne().from(memberEntity)
        .where(memberEntity.userId.eq(userId)).fetchFirst();
    Integer none = queryFactory.selectOne().from(memberEntity)
        .where(memberEntity.userId.eq("NoInDB")).fetchFirst();

    // then - 결과 체크
    assertThat(exist, is(1));
    assertThat(none, is(nullValue()));
  }


  @Test
  @DisplayName("MemberEntity : SELECT PWD TEST : 비밀번호 GET")
  public void testGetPwd() {
    // when - 초기 데이터 생성
    MemberEntity memberTestEntity =
        MemberEntity.builder().userId("testId").userName("testName").userPwd("testPwd")
            .userNumber("testNumber").userEmail("testEmail").userAddress("testEmail").build();
    String encodedPassword1 = passwordEncoder.encode(memberTestEntity.getUserPwd());
    memberTestEntity.setUserPwd(encodedPassword1);
    em.persist(memberTestEntity);


    // 동기화
    em.flush();
    em.clear();


    // when - 존재하는 데이터와 존재하지 않는 데이터 조회
    String userId = memberTestEntity.getUserId();
    String pwd = queryFactory.select(memberEntity.userPwd).from(memberEntity)
        .where(memberEntity.userId.eq(userId)).fetchFirst();
    String none = queryFactory.select(memberEntity.userPwd).from(memberEntity)
        .where(memberEntity.userId.eq("NoInDB")).fetchFirst();

    // then
    assertEquals(pwd, memberTestEntity.getUserPwd());
    assertEquals(none, null);
  }


  @Test
  @DisplayName("MemberEntity : SELECT EMAIL TEST : 비밀번호 GET")
  public void testGetEmail() {
    // when - 초기 데이터 생성
    MemberEntity memberTestEntity =
        MemberEntity.builder().userId("testId").userName("testName").userPwd("testPwd")
            .userNumber("testNumber").userEmail("testEmail").userAddress("testEmail").build();
    String encodedPassword1 = passwordEncoder.encode(memberTestEntity.getUserPwd());
    memberTestEntity.setUserPwd(encodedPassword1);
    em.persist(memberTestEntity);

    // 동기화
    em.flush();
    em.clear();

    // when - 이메일 조회 (존재하는 데이터, 존재하지 않는 데이터)
    String userId = memberTestEntity.getUserId();
    String email1 = queryFactory.select(memberEntity.userEmail).from(memberEntity)
        .where(memberEntity.userId.eq(userId)).fetchFirst();
    String none = queryFactory.select(memberEntity.userEmail).from(memberEntity)
        .where(memberEntity.userId.eq("NoInDB")).fetchFirst();

    // then
    assertEquals(email1, memberTestEntity.getUserEmail());
    assertEquals(none, null);
  }


  @Test
  @DisplayName("MemberEntity : SET TEST : 회원정보 수정")
  public void testSetInfo() {
    // given - 초기 데이터 저장
    MemberEntity user =
        MemberEntity.builder().userId("userId").userName("userName").userPwd("userPwd")
            .userNumber("userNumber").userEmail("userEmail").userAddress("userEmail").build();
    String encodedPassword = passwordEncoder.encode(user.getUserPwd());
    user.setUserPwd(encodedPassword);
    em.persist(user);


    String changedPwd = "changedPwd";
    String changedName = "changedName";
    String changedEmail = "changedEmail";
    String changedAddress = "changedAddress";
    String changedNumber = "changedNumber";

    // 동기화
    em.flush();
    em.clear();


    // when - 수정
    queryFactory.update(memberEntity).set(memberEntity.userPwd, changedPwd)
        .set(memberEntity.userName, changedName).set(memberEntity.userEmail, changedEmail)
        .set(memberEntity.userAddress, changedAddress).set(memberEntity.userNumber, changedNumber)
        .where(memberEntity.userId.eq(user.getUserId())).execute();

    // 동기화
    em.flush();
    em.clear();



    // then - 변경 여부 확인
    MemberEntity updatedUser = em.find(MemberEntity.class, user.getUserId());
    assertEquals(updatedUser.getUserPwd(), changedPwd);
    assertEquals(updatedUser.getUserAddress(), changedAddress);
    assertEquals(updatedUser.getUserName(), changedName);
    assertEquals(updatedUser.getUserEmail(), changedEmail);
    assertEquals(updatedUser.getUserNumber(), changedNumber);
  }


  @Test
  @DisplayName("MemberEntity : DELETE TEST : 회원정보 삭제")
  public void testDeleteMember() {

    // when - 초기 데이터 저장
    MemberEntity user =
        MemberEntity.builder().userId("userId").userName("userName").userPwd("userPwd")
            .userNumber("userNumber").userEmail("userEmail").userAddress("userEmail").build();
    String encodedPassword = passwordEncoder.encode(user.getUserPwd());
    user.setUserPwd(encodedPassword);
    em.persist(user);

    // 동기화
    em.flush();
    em.clear();


    // when - 삭제 전
    Long beforeDelete = queryFactory.select(memberEntity.count()).from(memberEntity).fetch().get(0);


    // when - 삭제 후
    queryFactory.delete(memberEntity).where(memberEntity.userId.eq(user.getUserId())).execute();
    Long afterDelete = queryFactory.select(memberEntity.count()).from(memberEntity).fetch().get(0);


    // then - 결과 체크
    assertThat(beforeDelete, equalTo(1L));
    assertThat(afterDelete, equalTo(0L));

  }



  // 2. Store Entity : StoreDAOImpl
  @Test
  @DisplayName("StoreEntity : PERSIST TEST : 가게등록 성공")
  public void testPersistStoreSuccess() {
    // given - 초기 데이터 저장
    StoreEntity storeTestEntity =
        StoreEntity.builder().storeName("storeName1").country("country1").city("city1")
            .dong("dong1").type("type1").couponInfo("couponInfo1").ownerId("ownerId1").build();

    // when
    em.persist(storeTestEntity);


    // 동기화
    em.flush();
    em.clear();

    // then - 잘 저장 되었는지 확인
    Long count = queryFactory.select(storeEntity.count()).from(storeEntity).fetch().get(0);
    assertThat(count, equalTo(1L));
  }


  @Test
  @DisplayName("StoreEntity : SELECT STORELIST TEST : 가게리스트 출력 성공")
  public void testGetStoreListSuccess() {
    // given - 초기 데이터 생성
    StoreEntity storeTestEntity =
        StoreEntity.builder().storeName("storeName1").country("country1").city("city1")
            .dong("dong1").type("type1").couponInfo("couponInfo1").ownerId("ownerId1").build();
    em.persist(storeTestEntity);


    // 동기화
    em.flush();
    em.clear();


    // when - 검색
    List<StoreEntity> storeInfo = queryFactory.select(storeEntity).from(storeEntity)
        .where(storeEntity.country.eq("country1").and(storeEntity.city.eq("city1"))
            .and(storeEntity.dong.eq("dong1")).and(storeEntity.type.eq("type1")))
        .limit(2).offset(0 * 2).fetch();


    // then - 결과 확인
    assertThat(storeInfo.size(), equalTo(1));
  }



  @Test
  @DisplayName("StoreEntity : SELECT OWNER ID : 가게 사장 아이디 찾기 성공")
  public void testGetStoreOwnerIdSuccess() {
    // given - 초기 데이터 생성
    StoreEntity storeTestEntity =
        StoreEntity.builder().storeName("storeName1").country("country1").city("city1")
            .dong("dong1").type("type1").couponInfo("couponInfo1").ownerId("ownerId1").build();
    em.persist(storeTestEntity);


    // 동기화
    em.flush();
    em.clear();

    // when - 조회
    String ownerId = queryFactory.select(storeEntity.ownerId).from(storeEntity)
        .where(storeEntity.storeId.eq(3)).fetchFirst();

    // then - 결과 확인
    assertThat(ownerId, equalTo("ownerId1"));

  }


  // 3. StoreRestDaysEntity, StoreRestDaysMapEntity : StoreInfoDAOImpl
  @Test
  @DisplayName("StoreRestDaysEntity, StoreRestDaysMapEntity : PERSIST : 가게 쉬는날 등록 성공")
  public void testPersistRestDaysSuccess() {
    // given - 초기 데이터 생성
    StoreRestDayDTO sample = StoreRestDayDTO.sample();
    StoreRestDaysEntity parent = new StoreRestDaysEntity(sample.getStoreId());
    em.persist(parent);
    Set<StoreRestDaysMapEntity> childs = new HashSet<>();
    for (String date : sample.getDate()) {
      StoreRestDaysMapEntity child = new StoreRestDaysMapEntity(date, parent);
      em.persist(child);
      childs.add(child);
    }
    parent.setChildSet(childs);

    // 동기화
    em.flush();
    em.clear();

    // when - 결과 조회
    List<StoreRestDaysEntity> p_entities =
        queryFactory.select(storeRestDaysEntity).from(storeRestDaysEntity).fetch();
    List<StoreRestDaysMapEntity> c_entities =
        queryFactory.select(storeRestDaysMapEntity).from(storeRestDaysMapEntity).fetch();

    // then - 결과 확인
    assertThat(p_entities.size(), is(1));
    assertThat(c_entities.size(), is(1));
  }

  @Test
  @DisplayName("StoreRestDaysEntity, StoreRestDaysMapEntity : SELECT DATE BY STOREID : 가게 쉬는날 조회 성공")
  public void testGetRestDaysByStoreIdSuccess() {
    // given - 초기 데이터 생성
    int storeId = 1;
    StoreRestDayDTO sample = StoreRestDayDTO.sample();
    StoreRestDaysEntity parent = new StoreRestDaysEntity(sample.getStoreId());
    em.persist(parent);
    Set<StoreRestDaysMapEntity> childs = new HashSet<>();
    for (String date : sample.getDate()) {
      StoreRestDaysMapEntity child = new StoreRestDaysMapEntity(date, parent);
      em.persist(child);
      childs.add(child);
    }
    parent.setChildSet(childs);


    // 동기화
    em.flush();
    em.clear();

    // when - 결과 조회
    List<String> resultList =
        queryFactory.selectDistinct(storeRestDaysMapEntity.date).from(storeRestDaysMapEntity)
            .leftJoin(storeRestDaysMapEntity.storeRestDaysEntity, storeRestDaysEntity)
            .where(storeRestDaysEntity.storeId.eq(storeId)).fetch();

    // then - 결과 확인
    assertThat(resultList.size(), is(1));
  }

  @Test
  @DisplayName("StoreRestDaysEntity, StoreRestDaysMapEntity : DELETE BY STOREID : 가게 쉬는날 삭제 성공")
  public void testDeleteRestDaysByStoreIdSuccess() {
    // given - 초기 데이터 생성
    int storeId = 1;
    StoreRestDayDTO sample = StoreRestDayDTO.sample();
    StoreRestDaysEntity parent = new StoreRestDaysEntity(sample.getStoreId());
    em.persist(parent);
    Set<StoreRestDaysMapEntity> childs = new HashSet<>();
    for (String date : sample.getDate()) {
      StoreRestDaysMapEntity child = new StoreRestDaysMapEntity(date, parent);
      em.persist(child);
      childs.add(child);
    }
    parent.setChildSet(childs);

    // 동기화
    em.flush();
    em.clear();



    // when - 데이터 삭제
    Long days_id = queryFactory.select(storeRestDaysEntity.daysId).from(storeRestDaysEntity)
        .where(storeRestDaysEntity.storeId.eq(storeId)).fetchFirst();
    queryFactory.delete(storeRestDaysMapEntity)
        .where(storeRestDaysMapEntity.storeRestDaysEntity.daysId.eq(days_id)).execute();
    queryFactory.delete(storeRestDaysEntity).where(storeRestDaysEntity.storeId.eq(storeId))
        .execute();
    List<String> resultList =
        queryFactory.selectDistinct(storeRestDaysMapEntity.date).from(storeRestDaysMapEntity)
            .leftJoin(storeRestDaysMapEntity.storeRestDaysEntity, storeRestDaysEntity)
            .where(storeRestDaysEntity.storeId.eq(storeId)).fetch();

    // then - 결과 확인
    assertThat(resultList.size(), is(0));
  }


  // 4. StoreTimeInfoEntity, StoreTimeInfoMapEntity : StoreInfoDAOImpl
  @Test
  @DisplayName("StoreTimeInfoEntity, StoreTimeInfoMapEntity : PERSIST : 가게 영업시간 등록 성공")
  public void testPersistTimeInfoSuccess() {
    // given - 초기 데이터 생성
    StoreTimeInfoDTO storeTimeInfoDTO = StoreTimeInfoDTO.sample();
    StoreTimeInfoEntity parent =
        StoreTimeInfoEntity.builder().startTime(storeTimeInfoDTO.getStartTime())
            .endTime(storeTimeInfoDTO.getEndTime()).intervalTime(storeTimeInfoDTO.getIntervalTime())
            .storeId(storeTimeInfoDTO.getStoreId()).build();
    em.persist(parent);
    Set<StoreTimeInfoMapEntity> childs = new HashSet<>();
    for (String time : storeTimeInfoDTO.getBreakTime()) {
      StoreTimeInfoMapEntity child =
          StoreTimeInfoMapEntity.builder().time(time).storeTimeInfoEntity(parent).build();
      em.persist(child);
      childs.add(child);
    }
    parent.setBreakTime(childs);


    // 동기화
    em.flush();
    em.clear();


    // when - 조회
    List<StoreTimeInfoEntity> p_entities =
        queryFactory.select(storeTimeInfoEntity).from(storeTimeInfoEntity).fetch();
    List<StoreTimeInfoMapEntity> c_entities =
        queryFactory.select(storeTimeInfoMapEntity).from(storeTimeInfoMapEntity).fetch();


    // then - 잘 저장되었는지 확인
    assertThat(p_entities.size(), is(1));
    assertThat(c_entities.size(), is(1));
  }

  @Test
  @DisplayName("StoreTimeInfoEntity, StoreTimeInfoMapEntity : SELECT DATE BY STOREID : 가게 영업시간 출력 성공")
  public void testGetTimeInfoSuccess() {
    // given - 초기 값 생성
    StoreTimeInfoDTO storeTimeInfoDTO = StoreTimeInfoDTO.sample();
    StoreTimeInfoEntity parent =
        StoreTimeInfoEntity.builder().startTime(storeTimeInfoDTO.getStartTime())
            .endTime(storeTimeInfoDTO.getEndTime()).intervalTime(storeTimeInfoDTO.getIntervalTime())
            .storeId(storeTimeInfoDTO.getStoreId()).build();
    em.persist(parent);
    Set<StoreTimeInfoMapEntity> childs = new HashSet<>();
    for (String time : storeTimeInfoDTO.getBreakTime()) {
      StoreTimeInfoMapEntity child =
          StoreTimeInfoMapEntity.builder().time(time).storeTimeInfoEntity(parent).build();
      em.persist(child);
      childs.add(child);
    }
    parent.setBreakTime(childs);


    // 동기화
    em.flush();
    em.clear();


    // when - 검색
    int storeId = 1;
    List<String> resultList =
        queryFactory.selectDistinct(storeTimeInfoMapEntity.time).from(storeTimeInfoMapEntity)
            .where(storeTimeInfoMapEntity.storeTimeInfoEntity.storeId.eq(storeId)).fetch();
    StoreTimeInfoDTO result = queryFactory
        .select(Projections.fields(StoreTimeInfoDTO.class, storeTimeInfoEntity.startTime,
            storeTimeInfoEntity.endTime, storeTimeInfoEntity.intervalTime,
            Expressions.asNumber(storeId).as("storeId")))
        .from(storeTimeInfoEntity).where(storeTimeInfoEntity.storeId.eq(storeId)).fetchFirst();


    // then - 초기값이 검색되는지 체크
    assertThat(resultList.size(), is(1));
    assertThat(storeTimeInfoDTO.getStartTime(), equalTo(result.getStartTime()));
    assertThat(storeTimeInfoDTO.getEndTime(), equalTo(result.getEndTime()));
    assertThat(storeTimeInfoDTO.getIntervalTime(), equalTo(result.getIntervalTime()));
  }

  @Test
  @DisplayName("StoreTimeInfoEntity, StoreTimeInfoMapEntity : UPDATE : 가게 영업시간 수정 성공")
  public void testModifyTimeInfoSuccess() {
    // given - 초기 값 생성
    StoreTimeInfoDTO storeTimeInfoDTO = StoreTimeInfoDTO.sample();
    StoreTimeInfoEntity parent =
        StoreTimeInfoEntity.builder().startTime(storeTimeInfoDTO.getStartTime())
            .endTime(storeTimeInfoDTO.getEndTime()).intervalTime(storeTimeInfoDTO.getIntervalTime())
            .storeId(storeTimeInfoDTO.getStoreId()).build();
    em.persist(parent);
    Set<StoreTimeInfoMapEntity> childs = new HashSet<>();
    for (String time : storeTimeInfoDTO.getBreakTime()) {
      // 자식 객체 생성
      StoreTimeInfoMapEntity child =
          StoreTimeInfoMapEntity.builder().time(time).storeTimeInfoEntity(parent).build();
      em.persist(child);
      childs.add(child);
    }
    parent.setBreakTime(childs);



    // when - 수정
    int storeId = 1;
    StoreTimeInfoEntity p_entity = queryFactory.select(storeTimeInfoEntity)
        .from(storeTimeInfoEntity).where(storeTimeInfoEntity.storeId.eq(storeId)).fetchFirst();
    queryFactory.delete(storeTimeInfoMapEntity)
        .where(storeTimeInfoMapEntity.storeTimeInfoEntity.eq(p_entity)).execute();
    Set<StoreTimeInfoMapEntity> new_childs = new HashSet<>();
    for (String breakTime : storeTimeInfoDTO.getBreakTime()) {
      StoreTimeInfoMapEntity child =
          StoreTimeInfoMapEntity.builder().time(breakTime).storeTimeInfoEntity(p_entity).build();
      em.persist(child);
      new_childs.add(child);
    }
    p_entity.setStartTime("new_startTime");
    p_entity.setEndTime("new_endTime");
    p_entity.setIntervalTime("new_intervalTime");
    p_entity.setBreakTime(new_childs);


    // 동기화
    em.flush();
    em.clear();


    // then - 변경 여부 체크
    StoreTimeInfoEntity entity = queryFactory.select(storeTimeInfoEntity).from(storeTimeInfoEntity)
        .where(storeTimeInfoEntity.storeId.eq(storeId)).fetchFirst();
    assertThat(entity.getStartTime(), equalTo("new_startTime"));
    assertThat(entity.getEndTime(), equalTo("new_endTime"));
    assertThat(entity.getIntervalTime(), equalTo("new_intervalTime"));

  }


  @Test
  @DisplayName("StoreTimeInfoEntity, StoreTimeInfoMapEntity : DELETE : 가게 영업시간 삭제 성공")
  public void testDeleteTimeInfoSuccess() {
    // given - 초기 데이터 생성
    StoreTimeInfoDTO storeTimeInfoDTO = StoreTimeInfoDTO.sample();
    StoreTimeInfoEntity parent =
        StoreTimeInfoEntity.builder().startTime(storeTimeInfoDTO.getStartTime())
            .endTime(storeTimeInfoDTO.getEndTime()).intervalTime(storeTimeInfoDTO.getIntervalTime())
            .storeId(storeTimeInfoDTO.getStoreId()).build();
    em.persist(parent);
    Set<StoreTimeInfoMapEntity> childs = new HashSet<>();
    for (String time : storeTimeInfoDTO.getBreakTime()) {
      StoreTimeInfoMapEntity child =
          StoreTimeInfoMapEntity.builder().time(time).storeTimeInfoEntity(parent).build();
      em.persist(child);
      childs.add(child);
    }
    parent.setBreakTime(childs);

    // 동기화
    em.flush();
    em.clear();


    // when - 삭제
    int storeId = 1;
    Long times_id = queryFactory.select(storeTimeInfoEntity.timesId).from(storeTimeInfoEntity)
        .where(storeTimeInfoEntity.storeId.eq(storeId)).fetchFirst();
    queryFactory.delete(storeTimeInfoMapEntity)
        .where(storeTimeInfoMapEntity.storeTimeInfoEntity.timesId.eq(times_id)).execute();
    queryFactory.delete(storeTimeInfoEntity).where(storeTimeInfoEntity.timesId.eq(times_id))
        .execute();


    // 동기화
    em.flush();
    em.clear();


    // when - 조회
    List<StoreTimeInfoEntity> p_entities =
        queryFactory.select(storeTimeInfoEntity).from(storeTimeInfoEntity).fetch();
    List<StoreTimeInfoMapEntity> c_entities =
        queryFactory.select(storeTimeInfoMapEntity).from(storeTimeInfoMapEntity).fetch();



    // then - 잘 반영되었는지 확인
    assertThat(p_entities.size(), is(0));
    assertThat(c_entities.size(), is(0));
  }


  // 5. StoreTableInfoEntity : StoreInfoDAOImpl
  @Test
  @DisplayName("StoreTableInfoEntity : PERSIST : 가게 테이블 정보 등록 성공")
  public void testPersistTableInfoSuccess() {
    // given - 초기 데이터 생성
    StoreTableInfoDTO storeTableInfoDTO = StoreTableInfoDTO.sample();
    StoreTableInfoEntity parent = new StoreTableInfoEntity(storeTableInfoDTO);
    em.persist(parent);


    // 동기화
    em.flush();
    em.clear();


    // when - 조회
    List<StoreTableInfoEntity> entities =
        queryFactory.select(storeTableInfoEntity).from(storeTableInfoEntity).fetch();


    // then - 잘 저장되었는지 확인
    assertThat(entities.size(), is(1));
  }

  @Test
  @DisplayName("StoreTableInfoEntity : UPDATE : 가게 테이블 정보 수정 성공")
  public void testModTableInfoSuccess() {
    // given - 초기 데이터 생성
    StoreTableInfoDTO storeTableInfoDTO = StoreTableInfoDTO.sample();
    StoreTableInfoEntity parent = new StoreTableInfoEntity(storeTableInfoDTO);
    em.persist(parent);


    // 동기화
    em.flush();
    em.clear();


    // when - 수정
    StoreTableInfoEntity newData = StoreTableInfoEntity.builder().storeId(0).count(100).build();
    StoreTableInfoEntity originalData =
        queryFactory.select(storeTableInfoEntity).from(storeTableInfoEntity)
            .where(storeTableInfoEntity.storeId.eq(newData.getStoreId())).fetchFirst();
    originalData.setCount(newData.getCount());


    // 동기화
    em.flush();
    em.clear();

    // 재조회
    StoreTableInfoEntity data = queryFactory.select(storeTableInfoEntity).from(storeTableInfoEntity)
        .where(storeTableInfoEntity.storeId.eq(newData.getStoreId())).fetchFirst();

    // then - 잘 변경되었는지 확인
    assertThat(data.getCount(), is(100));
  }



  @Test
  @DisplayName("StoreTableInfoEntity : DELETE : 가게 테이블 정보 삭제 성공")
  public void testDeleteTableInfoSuccess() {
    // given - 초기 데이터 생성
    StoreTableInfoDTO storeTableInfoDTO = StoreTableInfoDTO.sample();
    StoreTableInfoEntity parent = new StoreTableInfoEntity(storeTableInfoDTO);
    em.persist(parent);


    // 동기화
    em.flush();
    em.clear();


    // when - 삭제
    queryFactory.delete(storeTableInfoEntity).where(storeTableInfoEntity.storeId.eq(0)).execute();


    // 동기화
    em.flush();
    em.clear();

    StoreTableInfoEntity data = queryFactory.select(storeTableInfoEntity).from(storeTableInfoEntity)
        .where(storeTableInfoEntity.storeId.eq(0)).fetchFirst();

    // then - 잘 삭제되었는지 확인
    assertThat(data, equalTo(null));
  }


  // 6. StoreFoodsInfoEntity : StoreInfoDAOImpl
  @Test
  @DisplayName("StoreFoodsInfoEntity : PERSIST : 가게 음식정보 등록 성공")
  public void testPersistFoodsInfoSuccess() {
    // given - 초기 데이터 생성
    StoreFoodsInfoDTO storeTableInfoDTO = StoreFoodsInfoDTO.sample();
    StoreFoodsInfoEntity parent = new StoreFoodsInfoEntity(storeTableInfoDTO);
    em.persist(parent);


    // 동기화
    em.flush();
    em.clear();


    // when - 조회
    List<StoreFoodsInfoEntity> entities =
        queryFactory.select(storeFoodsInfoEntity).from(storeFoodsInfoEntity).fetch();


    // then - 잘 저장되었는지 확인
    assertThat(entities.size(), is(1));
  }


  @Test
  @DisplayName("StoreFoodsInfoEntity : SELECT : 가게 음식정보 조회 성공")
  public void testGetFoodsInfoSuccess() {
    // given - 초기 데이터 생성
    StoreFoodsInfoDTO storeTableInfoDTO = StoreFoodsInfoDTO.sample();
    StoreFoodsInfoEntity parent = new StoreFoodsInfoEntity(storeTableInfoDTO);
    em.persist(parent);


    // 동기화
    em.flush();
    em.clear();


    // when - 조회
    List<StoreFoodsInfoEntity> result = queryFactory.select(storeFoodsInfoEntity)
        .from(storeFoodsInfoEntity).where(storeFoodsInfoEntity.storeId.eq(0)).fetch();


    // then - 잘 저장되었는지 확인
    assertThat(result.size(), is(1));
  }


  @Test
  @DisplayName("StoreFoodsInfoEntity : UPDATE : 가게 음식정보 수정 성공")
  public void testUpdateFoodsInfoSuccess() {
    // given - 초기 데이터 생성
    StoreFoodsInfoDTO storeTableInfoDTO = StoreFoodsInfoDTO.sample();
    StoreFoodsInfoEntity parent = new StoreFoodsInfoEntity(storeTableInfoDTO);
    em.persist(parent);


    // 동기화
    em.flush();
    em.clear();


    // when - 조회
    StoreFoodsInfoEntity result = queryFactory.select(storeFoodsInfoEntity)
        .from(storeFoodsInfoEntity).where(storeFoodsInfoEntity.storeId.eq(0)).fetchFirst();


    // 수정
    result.setFoodName("newFOODNAME");
    result.setFoodPrice(10000);
    result.setFoodDescription("newFOODDESCRIPTION");
    result.setImageURL("newURL");

    // 동기화
    em.flush();
    em.clear();


    // when - 재조회
    StoreFoodsInfoEntity final_result = queryFactory.select(storeFoodsInfoEntity)
        .from(storeFoodsInfoEntity).where(storeFoodsInfoEntity.storeId.eq(0)).fetchFirst();

    // then - 잘 저장되었는지 확인
    assertThat(final_result.getFoodName(), equalTo("newFOODNAME"));
    assertThat(final_result.getFoodPrice(), is(10000));
    assertThat(final_result.getFoodDescription(), equalTo("newFOODDESCRIPTION"));
    assertThat(final_result.getImageURL(), equalTo("newURL"));
  }



  @Test
  @DisplayName("StoreFoodsInfoEntity : DELETE : 가게 음식정보 삭제 성공")
  public void testDeleteFoodsInfoSuccess() {
    // given - 초기 데이터 생성
    StoreFoodsInfoDTO storeTableInfoDTO = StoreFoodsInfoDTO.sample();
    StoreFoodsInfoEntity parent = new StoreFoodsInfoEntity(storeTableInfoDTO);
    em.persist(parent);


    // 동기화
    em.flush();
    em.clear();


    // when - 삭제
    queryFactory.delete(storeFoodsInfoEntity).where(storeFoodsInfoEntity.storeId.eq(0)
        .and(storeFoodsInfoEntity.foodName.eq(storeTableInfoDTO.getFoodName()))).execute();


    // 동기화
    em.flush();
    em.clear();


    // when - 재조회
    StoreFoodsInfoEntity final_result = queryFactory.select(storeFoodsInfoEntity)
        .from(storeFoodsInfoEntity).where(storeFoodsInfoEntity.storeId.eq(0)).fetchFirst();

    // then - 잘 저장되었는지 확인
    assertThat(final_result, equalTo(null));
  }



}
