package com.ReservationServer1.DataBase;


import static com.ReservationServer1.data.Entity.member.QMemberEntity.memberEntity;
import static com.ReservationServer1.data.Entity.store.QStoreEntity.storeEntity;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import com.ReservationServer1.DAO.Cache.StoreListCache;
import com.ReservationServer1.data.Entity.member.MemberEntity;
import com.ReservationServer1.data.Entity.store.StoreEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@DataJpaTest
@ActiveProfiles("test")
@Transactional
public class DBTest {


  @Autowired
  TestEntityManager testEntityManager;

  JPAQueryFactory queryFactory;

  EntityManager em;

  BCryptPasswordEncoder passwordEncoder;
  


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
    // given
    MemberEntity user =
        MemberEntity.builder().userId("userId").userName("userName").userPwd("userPwd")
            .userNumber("userNumber").userEmail("userEmail").userAddress("userEmail").build();
    String encodedPassword = passwordEncoder.encode(user.getUserPwd());
    user.setUserPwd(encodedPassword);

    // when
    em.persist(user);


    // then
    Long count = queryFactory.select(memberEntity.count()).from(memberEntity).fetch().get(0);
    assertThat(count, equalTo(1L));
  }

  @Test
  @DisplayName("MemberEntity : PERSIST TEST : 회원가입 실패 : EXIST ID")
  public void testPersistMemberFail() {
    // given
    MemberEntity memberTestEntity =
        MemberEntity.builder().userId("testId").userName("testName").userPwd("testPwd")
            .userNumber("testNumber").userEmail("testEmail").userAddress("testEmail").build();
    String encodedPassword1 = passwordEncoder.encode(memberTestEntity.getUserPwd());
    memberTestEntity.setUserPwd(encodedPassword1);
    em.persist(memberTestEntity);

    MemberEntity user =
        MemberEntity.builder().userId("testId").userName("userName").userPwd("userPwd")
            .userNumber("userNumber").userEmail("userEmail").userAddress("userEmail").build();
    String encodedPassword2 = passwordEncoder.encode(user.getUserPwd());
    user.setUserPwd(encodedPassword2);

    // when
    EntityExistsException exception = assertThrows(EntityExistsException.class, () -> {
      em.persist(user);
    });

    // then
    assertEquals(exception.toString(), existIdError + memberIdError);
    Long count = queryFactory.select(memberEntity.count()).from(memberEntity).fetch().get(0);
    assertThat(count, equalTo(1L));
  }


  @Test
  @DisplayName("MemberEntity : EXIST TEST : 아이디 존재 여부")
  public void testExsistId() {
    // when
    MemberEntity memberTestEntity =
        MemberEntity.builder().userId("testId").userName("testName").userPwd("testPwd")
            .userNumber("testNumber").userEmail("testEmail").userAddress("testEmail").build();
    String encodedPassword1 = passwordEncoder.encode(memberTestEntity.getUserPwd());
    memberTestEntity.setUserPwd(encodedPassword1);
    em.persist(memberTestEntity);

    String userId = memberTestEntity.getUserId();
    Integer exist = queryFactory.selectOne().from(memberEntity)
        .where(memberEntity.userId.eq(userId)).fetchFirst();
    Integer none = queryFactory.selectOne().from(memberEntity)
        .where(memberEntity.userId.eq("NoInDB")).fetchFirst();

    // then
    assertThat(exist, is(1));
    assertThat(none, is(nullValue()));
  }


  @Test
  @DisplayName("MemberEntity : SELECT PWD TEST : 비밀번호 GET")
  public void testGetPwd() {
    // when
    MemberEntity memberTestEntity =
        MemberEntity.builder().userId("testId").userName("testName").userPwd("testPwd")
            .userNumber("testNumber").userEmail("testEmail").userAddress("testEmail").build();
    String encodedPassword1 = passwordEncoder.encode(memberTestEntity.getUserPwd());
    memberTestEntity.setUserPwd(encodedPassword1);
    em.persist(memberTestEntity);

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
    // when
    MemberEntity memberTestEntity =
        MemberEntity.builder().userId("testId").userName("testName").userPwd("testPwd")
            .userNumber("testNumber").userEmail("testEmail").userAddress("testEmail").build();
    String encodedPassword1 = passwordEncoder.encode(memberTestEntity.getUserPwd());
    memberTestEntity.setUserPwd(encodedPassword1);
    em.persist(memberTestEntity);

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
    // given
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


    // when
    queryFactory.update(memberEntity).set(memberEntity.userPwd, changedPwd)
        .set(memberEntity.userName, changedName).set(memberEntity.userEmail, changedEmail)
        .set(memberEntity.userAddress, changedAddress).set(memberEntity.userNumber, changedNumber)
        .where(memberEntity.userId.eq(user.getUserId())).execute();
    em.flush();
    em.clear();



    // then
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

    // when
    MemberEntity user =
        MemberEntity.builder().userId("userId").userName("userName").userPwd("userPwd")
            .userNumber("userNumber").userEmail("userEmail").userAddress("userEmail").build();
    String encodedPassword = passwordEncoder.encode(user.getUserPwd());
    user.setUserPwd(encodedPassword);
    em.persist(user);


    // then
    Long beforeDelete = queryFactory.select(memberEntity.count()).from(memberEntity).fetch().get(0);
    assertThat(beforeDelete, equalTo(1L));


    // when
    queryFactory.delete(memberEntity).where(memberEntity.userId.eq(user.getUserId())).execute();
    Long afterDelete = queryFactory.select(memberEntity.count()).from(memberEntity).fetch().get(0);
    
    // then
    assertThat(afterDelete, equalTo(0L));

  }



  // 2. Store Entity : StoreDAOImpl
  @Test
  @DisplayName("StoreEntity : PERSIST TEST : 가게등록 성공")
  public void testPersistStoreSuccess() {
    // given
    StoreEntity storeTestEntity =
        StoreEntity.builder().storeName("storeName1").country("country1").city("city1")
            .dong("dong1").type("type1").couponInfo("couponInfo1").ownerId("ownerId1").build();
    
    // when
    em.persist(storeTestEntity);

    // then
    Long count = queryFactory.select(storeEntity.count()).from(storeEntity).fetch().get(0);
    assertThat(count, equalTo(1L));
  }


  @Test
  @DisplayName("StoreEntity : SELECT STORELIST TEST : 가게리스트 출력 성공")
  public void testGetStoreListSuccess() {
    // given
    StoreEntity storeTestEntity =
        StoreEntity.builder().storeName("storeName1").country("country1").city("city1")
            .dong("dong1").type("type1").couponInfo("couponInfo1").ownerId("ownerId1").build();
    em.persist(storeTestEntity);

    // when
    List<StoreEntity> storeInfo = queryFactory.select(storeEntity).from(storeEntity)
        .where(storeEntity.country.eq("country1").and(storeEntity.city.eq("city1"))
            .and(storeEntity.dong.eq("dong1")).and(storeEntity.type.eq("type1")))
        .limit(2).offset(0 * 2).fetch();
    
    // then
    assertThat(storeInfo.size(), equalTo(1));
  }


  @Test
  @DisplayName("StoreEntity : SELECT OWNER ID : 가게 사장 아이디 찾기 성공")
  public void testGetStoreOwnerIdSuccess() {
    // given
    StoreEntity storeTestEntity =
        StoreEntity.builder().storeName("storeName1").country("country1").city("city1")
            .dong("dong1").type("type1").couponInfo("couponInfo1").ownerId("ownerId1").build();
    em.persist(storeTestEntity);

    // when
    String ownerId = queryFactory.select(storeEntity.ownerId).from(storeEntity)
        .where(storeEntity.storeId.eq(3)).fetchFirst();
    
    // then
    assertThat(ownerId, equalTo("ownerId1"));

  }


  // 3. StoreListDTO : Cache
  @Test
  @DisplayName("StoreListDTO : SAVE DTO : 가게 리스트 캐시 저장")
  public void testSaveStoreListDTOSuccess() {
    
    // 추후에

  }


}
