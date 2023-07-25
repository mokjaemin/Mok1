package com.ReservationServer1.dao;


import static com.ReservationServer1.data.Entity.member.QMemberEntity.memberEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.ReservationServer1.DAO.Impl.MemberDAOImpl;
import com.ReservationServer1.data.DTO.member.LoginDTO;
import com.ReservationServer1.data.DTO.member.ModifyMemberDTO;
import com.ReservationServer1.data.Entity.member.MemberEntity;
import com.ReservationServer1.exception.MessageException;
import com.querydsl.jpa.impl.JPADeleteClause;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import jakarta.persistence.EntityManager;

public class MemberDAOTest {

  @Mock
  private JPAQueryFactory queryFactory;

  @InjectMocks
  private MemberDAOImpl memberDAOImpl;

  @Mock
  private BCryptPasswordEncoder passwordEncoder;

  @Mock
  private EntityManager entityManager;

  @Mock
  private JPAQuery<MemberEntity> jpaQuery;

  @Mock
  private JPAUpdateClause jpaUpdateClause;

  @Mock
  private JPADeleteClause jpaDeleteClause;


  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  // 1. Register Member
  @Test
  @DisplayName("회원 가입 성공")
  public void registerMemberSuccess() {
    // given
    String userId = "testId";
    String userPwd = "testPwd";
    MemberEntity entity = new MemberEntity();
    entity.setUserId(userId);
    entity.setUserPwd(userPwd);
    String encodedPassword = "encodedPassword";


    doReturn(encodedPassword).when(passwordEncoder).encode(userPwd);
    doNothing().when(entityManager).persist(any(MemberEntity.class));
    doReturn(jpaQuery).when(queryFactory).selectOne();
    doReturn(jpaQuery).when(jpaQuery).from(memberEntity);
    doReturn(jpaQuery).when(jpaQuery).where(memberEntity.userId.eq(userId));
    doReturn(jpaQuery).when(jpaQuery).limit(1);
    doReturn(null).when(jpaQuery).fetchOne();

    // when
    String result = memberDAOImpl.registerMember(entity);


    // then
    assertEquals("success", result);
  }

  @Test
  @DisplayName("회원 가입 실패: 이미 존재하는 아이디")
  public void registerMemberFail() {
    // given
    String userId = "testId";
    String userPwd = "testPwd";
    MemberEntity entity = new MemberEntity();
    entity.setUserId(userId);
    entity.setUserPwd(userPwd);
    String encodedPassword = "encodedPassword";


    doReturn(encodedPassword).when(passwordEncoder).encode(userPwd);
    doNothing().when(entityManager).persist(any(MemberEntity.class));
    doReturn(jpaQuery).when(queryFactory).selectOne();
    doReturn(jpaQuery).when(jpaQuery).from(memberEntity);
    doReturn(jpaQuery).when(jpaQuery).where(memberEntity.userId.eq(userId));
    doReturn(jpaQuery).when(jpaQuery).limit(1);
    doReturn(1).when(jpaQuery).fetchOne();


    // then && when
    MessageException message = assertThrows(MessageException.class, () -> {
      memberDAOImpl.registerMember(entity);
    });
    String expected = "이미 존재하는 ID입니다: " + userId;

    assertEquals(expected, message.getMessage());
  }


  // 2. Login Member
  @Test
  @DisplayName("로그인 성공")
  public void loginMemberSuccess() {
    // given
    LoginDTO sample = LoginDTO.sample();
    String encodedPassword = "encodedPassword";

    doReturn(jpaQuery).when(queryFactory).select(memberEntity.userPwd);
    doReturn(jpaQuery).when(jpaQuery).from(memberEntity);
    doReturn(jpaQuery).when(jpaQuery).where(memberEntity.userId.eq(sample.getUserId()));
    doReturn(jpaQuery).when(jpaQuery).limit(1);
    doReturn(encodedPassword).when(jpaQuery).fetchOne();
    doReturn(true).when(passwordEncoder).matches(sample.getUserPwd(), encodedPassword);

    // when
    memberDAOImpl.loginMember(sample);

  }


  @Test
  @DisplayName("로그인 실패 : 존재하지 않는 아이디")
  public void loginMemberFailById() {
    // given
    LoginDTO sample = LoginDTO.sample();

    doReturn(jpaQuery).when(queryFactory).select(memberEntity.userPwd);
    doReturn(jpaQuery).when(jpaQuery).from(memberEntity);
    doReturn(jpaQuery).when(jpaQuery).where(memberEntity.userId.eq(sample.getUserId()));
    doReturn(jpaQuery).when(jpaQuery).limit(1);
    doReturn(null).when(jpaQuery).fetchOne();

    // then && when
    MessageException message = assertThrows(MessageException.class, () -> {
      memberDAOImpl.loginMember(sample);
    });
    String expected = "아이디가 존재하지 않습니다.";
    assertEquals(expected, message.getMessage());
  }

  @Test
  @DisplayName("로그인 실패 : 잘못된 비밀번호")
  public void loginMemberFailByPwd() {
    // given
    LoginDTO sample = LoginDTO.sample();
    String encodedPassword = "encodedPassword";

    doReturn(jpaQuery).when(queryFactory).select(memberEntity.userPwd);
    doReturn(jpaQuery).when(jpaQuery).from(memberEntity);
    doReturn(jpaQuery).when(jpaQuery).where(memberEntity.userId.eq(sample.getUserId()));
    doReturn(jpaQuery).when(jpaQuery).limit(1);
    doReturn(encodedPassword).when(jpaQuery).fetchOne();
    doReturn(false).when(passwordEncoder).matches(sample.getUserPwd(), encodedPassword);

    // then && when
    MessageException message = assertThrows(MessageException.class, () -> {
      memberDAOImpl.loginMember(sample);
    });
    String expected = "비밀번호가 일치하지 않습니다.";
    assertEquals(expected, message.getMessage());
  }



  // 3. Find Pwd Member
  @Test
  @DisplayName("비밀번호 찾기 성공")
  public void findPwdMemberSuccess() {
    // given
    String userId = "testId";
    String userEmail = "testEmial";

    doReturn(jpaQuery).when(queryFactory).select(memberEntity.userEmail);
    doReturn(jpaQuery).when(jpaQuery).from(memberEntity);
    doReturn(jpaQuery).when(jpaQuery).where(memberEntity.userId.eq(userId));
    doReturn(jpaQuery).when(jpaQuery).limit(1);
    doReturn(userEmail).when(jpaQuery).fetchOne();

    // when
    memberDAOImpl.findPwdMember(userId, userEmail);

  }


  @Test
  @DisplayName("비밀번호 찾기 실패 : 존재하지 않는 아이디")
  public void findPwdMemberFailById() {
    // given
    String userId = "testId";
    String userEmail = "testEmial";

    doReturn(jpaQuery).when(queryFactory).select(memberEntity.userEmail);
    doReturn(jpaQuery).when(jpaQuery).from(memberEntity);
    doReturn(jpaQuery).when(jpaQuery).where(memberEntity.userId.eq(userId));
    doReturn(jpaQuery).when(jpaQuery).limit(1);
    doReturn(null).when(jpaQuery).fetchOne();

    // then && when
    MessageException message = assertThrows(MessageException.class, () -> {
      memberDAOImpl.findPwdMember(userId, userEmail);
    });
    String expected = "존재하지 않는 아이디입니다.";
    assertEquals(expected, message.getMessage());
  }

  @Test
  @DisplayName("비밀번호 찾기 실패 : 잘못된 이메일 정보")
  public void findPwdMemberFailByPwd() {
    // given
    String userId = "testId";
    String userEmail = "testEmial";
    String wrongEmail = "wrongEmail";

    doReturn(jpaQuery).when(queryFactory).select(memberEntity.userEmail);
    doReturn(jpaQuery).when(jpaQuery).from(memberEntity);
    doReturn(jpaQuery).when(jpaQuery).where(memberEntity.userId.eq(userId));
    doReturn(jpaQuery).when(jpaQuery).limit(1);
    doReturn(wrongEmail).when(jpaQuery).fetchOne();

    // then && when
    MessageException message = assertThrows(MessageException.class, () -> {
      memberDAOImpl.findPwdMember(userId, userEmail);
    });
    String expected = "이메일 정보가 일치하지 않습니다.";
    assertEquals(expected, message.getMessage());
  }



  // 4. Modify Pwd Member
  @Test
  @DisplayName("비밀번호 수정 성공")
  public void modPwdMemberSuccess() {
    // given
    String userId = "testId";
    String userPwd = "testPwd";
    String encodedPwd = "encodedPwd";

    doReturn(jpaQuery).when(queryFactory).selectOne();
    doReturn(jpaQuery).when(jpaQuery).from(memberEntity);
    doReturn(jpaQuery).when(jpaQuery).where(memberEntity.userId.eq(userId));
    doReturn(jpaQuery).when(jpaQuery).limit(1);
    doReturn(1).when(jpaQuery).fetchOne();

    doReturn(encodedPwd).when(passwordEncoder).encode(userPwd);

    doReturn(jpaUpdateClause).when(queryFactory).update(memberEntity);
    doReturn(jpaUpdateClause).when(jpaUpdateClause).set(memberEntity.userPwd, encodedPwd);
    doReturn(jpaUpdateClause).when(jpaUpdateClause).where(memberEntity.userId.eq(userId));


    // when
    String result = memberDAOImpl.modPwdMember(userId, userPwd);


    // then
    assertEquals(result, "success");
    verify(jpaUpdateClause).execute();
  }

  @Test
  @DisplayName("비밀번호 수정 실패 : 존재하지 않는 아이디")
  public void modPwdMemberFailById() {
    // given
    String userId = "testId";
    String userPwd = "testPwd";

    doReturn(jpaQuery).when(queryFactory).selectOne();
    doReturn(jpaQuery).when(jpaQuery).from(memberEntity);
    doReturn(jpaQuery).when(jpaQuery).where(memberEntity.userId.eq(userId));
    doReturn(jpaQuery).when(jpaQuery).limit(1);
    doReturn(null).when(jpaQuery).fetchOne();



    // then && when
    MessageException message = assertThrows(MessageException.class, () -> {
      memberDAOImpl.modPwdMember(userId, userPwd);
    });
    String expected = "존재하지 않는 아이디 입니다.";
    assertEquals(expected, message.getMessage());

  }



  // 5. Modify Info Member
  @Test
  @DisplayName("회원정보 수정 성공")
  public void modInfoMemberSuccess() {
    // given
    String userId = "testId";
    ModifyMemberDTO sample = ModifyMemberDTO.sample();
    String encodedPwd = "encodedPwd";

    doReturn(jpaQuery).when(queryFactory).selectOne();
    doReturn(jpaQuery).when(jpaQuery).from(memberEntity);
    doReturn(jpaQuery).when(jpaQuery).where(memberEntity.userId.eq(userId));
    doReturn(jpaQuery).when(jpaQuery).limit(1);
    doReturn(1).when(jpaQuery).fetchOne();

    doReturn(encodedPwd).when(passwordEncoder).encode(sample.getUserPwd());

    doReturn(jpaUpdateClause).when(queryFactory).update(memberEntity);
    doReturn(jpaUpdateClause).when(jpaUpdateClause).set(memberEntity.userPwd, encodedPwd);
    doReturn(jpaUpdateClause).when(jpaUpdateClause).set(memberEntity.userAddress,
        sample.getUserAddress());
    doReturn(jpaUpdateClause).when(jpaUpdateClause).set(memberEntity.userEmail,
        sample.getUserEmail());
    doReturn(jpaUpdateClause).when(jpaUpdateClause).set(memberEntity.userName,
        sample.getUserName());
    doReturn(jpaUpdateClause).when(jpaUpdateClause).set(memberEntity.userNumber,
        sample.getUserNumber());
    doReturn(jpaUpdateClause).when(jpaUpdateClause).where(memberEntity.userId.eq(userId));


    // when
    String result = memberDAOImpl.modInfoMember(userId, sample);


    // then
    assertEquals(result, "success");
    verify(jpaUpdateClause).execute();
  }


  @Test
  @DisplayName("회원정보 수정 실패 : 존재하지 않는 아이디")
  public void modInfoMemberFailById() {
    // given
    String userId = "testId";
    ModifyMemberDTO sample = ModifyMemberDTO.sample();

    doReturn(jpaQuery).when(queryFactory).selectOne();
    doReturn(jpaQuery).when(jpaQuery).from(memberEntity);
    doReturn(jpaQuery).when(jpaQuery).where(memberEntity.userId.eq(userId));
    doReturn(jpaQuery).when(jpaQuery).limit(1);
    doReturn(null).when(jpaQuery).fetchOne();


    // then && when
    MessageException message = assertThrows(MessageException.class, () -> {
      memberDAOImpl.modInfoMember(userId, sample);
    });
    String expected = "존재하지 않는 아이디 입니다.";
    assertEquals(expected, message.getMessage());
  }



  // 6. Delete Member
  @Test
  @DisplayName("회원정보 삭제 성공")
  public void delMemberSuccess() {
    // given
    String userId = "testId";
    String userPwd = "testPwd";
    String encodedPwd = "encodedPwd";
    String getPwd = "testPwd";

    doReturn(jpaQuery).when(queryFactory).select(memberEntity.userPwd);
    doReturn(jpaQuery).when(jpaQuery).from(memberEntity);
    doReturn(jpaQuery).when(jpaQuery).where(memberEntity.userId.eq(userId));
    doReturn(jpaQuery).when(jpaQuery).limit(1);
    doReturn(getPwd).when(jpaQuery).fetchOne();

    doReturn(encodedPwd).when(passwordEncoder).encode(userPwd);
    doReturn(true).when(passwordEncoder).matches(getPwd, encodedPwd);

    doReturn(jpaDeleteClause).when(queryFactory).delete(memberEntity);
    doReturn(jpaDeleteClause).when(jpaDeleteClause).where(memberEntity.userId.eq(userId));


    // when
    String result = memberDAOImpl.delMember(userId, userPwd);


    // then
    assertEquals(result, "success");
    verify(jpaDeleteClause).execute();
  }


  @Test
  @DisplayName("회원정보 삭제 실패 : 존재하지 않는 아이디")
  public void delMemberFailById() {
    // given
    String userId = "testId";
    String userPwd = "testPwd";

    doReturn(jpaQuery).when(queryFactory).select(memberEntity.userPwd);
    doReturn(jpaQuery).when(jpaQuery).from(memberEntity);
    doReturn(jpaQuery).when(jpaQuery).where(memberEntity.userId.eq(userId));
    doReturn(jpaQuery).when(jpaQuery).limit(1);
    doReturn(null).when(jpaQuery).fetchOne();


    // then && when
    MessageException message = assertThrows(MessageException.class, () -> {
      memberDAOImpl.delMember(userId, userPwd);
    });
    String expected = "존재하지 않는 아이디 입니다.";
    assertEquals(expected, message.getMessage());
  }


  @Test
  @DisplayName("회원정보 삭제 실패 : 존재하지 않는 아이디")
  public void delMemberFailByPwd() {
    // given
    String userId = "testId";
    String userPwd = "testPwd";
    String encodedPwd = "encodedPwd";
    String getPwd = "wrongPwd";

    doReturn(jpaQuery).when(queryFactory).select(memberEntity.userPwd);
    doReturn(jpaQuery).when(jpaQuery).from(memberEntity);
    doReturn(jpaQuery).when(jpaQuery).where(memberEntity.userId.eq(userId));
    doReturn(jpaQuery).when(jpaQuery).limit(1);
    doReturn(getPwd).when(jpaQuery).fetchOne();

    doReturn(encodedPwd).when(passwordEncoder).encode(userPwd);
    doReturn(false).when(passwordEncoder).matches(getPwd, encodedPwd);


    // then && when
    MessageException message = assertThrows(MessageException.class, () -> {
      memberDAOImpl.delMember(userId, userPwd);
    });
    String expected = "비밀번호가 일치하지 않습니다.";
    assertEquals(expected, message.getMessage());
  }
}
