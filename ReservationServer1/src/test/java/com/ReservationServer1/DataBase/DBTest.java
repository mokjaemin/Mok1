package com.ReservationServer1.DataBase;


import static com.ReservationServer1.data.Entity.member.QMemberEntity.memberEntity;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import com.ReservationServer1.data.Entity.member.MemberEntity;
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

  MemberEntity memberTestEntity = new MemberEntity();

  private String existIdError = "jakarta.persistence.EntityExistsException: "
      + "A different object with the same identifier value was already associated with the session : "
      + "[com.ReservationServer1.data.Entity.member.MemberEntity#testId]";

  @BeforeEach
  void init() {
    em = testEntityManager.getEntityManager();
    passwordEncoder = new BCryptPasswordEncoder();
    queryFactory = new JPAQueryFactory(em);


    // Member Entity Sample
    memberTestEntity =
        MemberEntity.builder().userId("testId").userName("testName").userPwd("testPwd")
            .userNumber("testNumber").userEmail("testEmail").userAddress("testEmail").build();
    String encodedPassword = passwordEncoder.encode(memberTestEntity.getUserPwd());
    memberTestEntity.setUserPwd(encodedPassword);
    em.persist(memberTestEntity);
  }



  // 1. Member Entity

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
    assertThat(count, equalTo(2L));
  }

  @Test
  @DisplayName("MemberEntity : PERSIST TEST : 회원가입 실패 : EXIST ID")
  public void testPersistMemberFail() {
    // given
    MemberEntity user =
        MemberEntity.builder().userId("testId").userName("userName").userPwd("userPwd")
            .userNumber("userNumber").userEmail("userEmail").userAddress("userEmail").build();
    String encodedPassword = passwordEncoder.encode(user.getUserPwd());
    user.setUserPwd(encodedPassword);

    // when
    EntityExistsException exception = assertThrows(EntityExistsException.class, () -> {
      em.persist(user);
    });

    // then
    assertEquals(exception.toString(), existIdError);
    Long count = queryFactory.select(memberEntity.count()).from(memberEntity).fetch().get(0);
    assertThat(count, equalTo(1L));
  }


  @Test
  @DisplayName("MemberEntity : EXIST TEST : 아이디 존재 여부")
  public void testExsistId() {
    // when
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
  @DisplayName("MemberEntity : GET PWD TEST : 비밀번호 GET")
  public void testGetPwd() {
    // when
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
  @DisplayName("MemberEntity : GET EMAIL TEST : 비밀번호 GET")
  public void testGetEmail() {
    // when
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


    MemberEntity updatedUser = em.find(MemberEntity.class, user.getUserId());
    System.out.println(updatedUser.toString());


    // then
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
    assertThat(beforeDelete, equalTo(2L));
    
    
    // when
    queryFactory.delete(memberEntity).where(memberEntity.userId.eq(user.getUserId())).execute();
    Long afterDelete = queryFactory.select(memberEntity.count()).from(memberEntity).fetch().get(0);
    assertThat(afterDelete, equalTo(1L));
    
  }

}
