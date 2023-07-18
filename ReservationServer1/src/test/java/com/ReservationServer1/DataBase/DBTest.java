package com.ReservationServer1.DataBase;

import static org.assertj.core.api.Assertions.assertThat;
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
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManager;

@DataJpaTest
@ActiveProfiles("test")
public class DBTest {


  @Autowired
  TestEntityManager testEntityManager;

  EntityManager em;

  BCryptPasswordEncoder passwordEncoder;

  MemberEntity testEntity = new MemberEntity();

  @BeforeEach
  void init() {
    em = testEntityManager.getEntityManager();
    passwordEncoder = new BCryptPasswordEncoder();
    testEntity = MemberEntity.builder().userId("testId").userName("testName").userPwd("testPwd")
        .userNumber("testNumber").userEmail("testEmail").userAddress("testEmail").build();
    String encodedPassword = passwordEncoder.encode(testEntity.getUserPwd());
    testEntity.setUserPwd(encodedPassword);
    em.persist(testEntity);
  }


  // 1. Member DAO

  //
  @Test
  @DisplayName("회원 가입 성공")
  public void testRegisterMemberSuccess() {
    // given
    MemberEntity user =
        MemberEntity.builder().userId("userId").userName("userName").userPwd("userPwd")
            .userNumber("userNumber").userEmail("userEmail").userAddress("userEmail").build();
    String encodedPassword = passwordEncoder.encode(user.getUserPwd());
    user.setUserPwd(encodedPassword);


    // when
    em.persist(user);


    // then
  }

  @Test
  @DisplayName("회원 가입 실패 : 이미 존재하는 아이디")
  public void testRegisterMemberFail() {
    // given
    MemberEntity user =
        MemberEntity.builder().userId("testId").userName("userName").userPwd("userPwd")
            .userNumber("userNumber").userEmail("userEmail").userAddress("userEmail").build();
    String encodedPassword = passwordEncoder.encode(user.getUserPwd());
    user.setUserPwd(encodedPassword);
    String error = "jakarta.persistence.EntityExistsException: "
        + "A different object with the same identifier value was already associated with the session : "
        + "[com.ReservationServer1.data.Entity.member.MemberEntity#testId]";

    // when
    EntityExistsException exception = assertThrows(EntityExistsException.class, () -> {
      em.persist(user);
    });
    // exist 추가


    // then
    assertThat(exception.toString()).isEqualTo(error);
  }

}
