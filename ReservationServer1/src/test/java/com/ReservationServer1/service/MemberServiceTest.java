package com.ReservationServer1.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import com.ReservationServer1.DAO.Impl.MemberDAOImpl;
import com.ReservationServer1.data.DTO.member.LoginDTO;
import com.ReservationServer1.data.DTO.member.MemberDTO;
import com.ReservationServer1.data.DTO.member.ModifyMemberDTO;
import com.ReservationServer1.data.Entity.member.MemberEntity;
import com.ReservationServer1.service.Impl.MemberServiceImpl;
import com.ReservationServer1.utils.JWTutil;

@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {

  @InjectMocks
  private MemberServiceImpl memberServiceImpl;

  @Mock
  private MemberDAOImpl memberDAOImpl;

  @Mock
  private Environment env;

  @Mock
  private JavaMailSender emailSender;


  private final String secretKey = "test";

  @BeforeEach
  void setup() {
    memberServiceImpl.setTestSecretKey(secretKey);
  }


  // 1. Register Member
  @Test
  @DisplayName("회원 가입 성공")
  void registerMemberSuccess() throws Exception {
    // given
    MemberDTO sample = MemberDTO.sample();
    MemberEntity entity = new MemberEntity(sample);
    String response = "success";
    doReturn(response).when(memberDAOImpl).registerMember(entity);

    // when
    String result = memberServiceImpl.registerMember(sample);

    // then
    assertThat(result.equals(response));

    // verify
    verify(memberDAOImpl, times(1)).registerMember(any(MemberEntity.class));
  }


  // 2. Login Member
  @Test
  @DisplayName("로그인 성공")
  void loginMemberSuccess() throws Exception {
    // given
    LoginDTO sample = LoginDTO.sample();

    // when
    String result = memberServiceImpl.loginMember(sample);

    // then
    String userId = JWTutil.getUserId(result, secretKey);
    String userRole = JWTutil.getUserRole(result, secretKey);
    assertThat(userId.equals(sample.getUserId()));
    assertThat(userRole.equals("USER"));

    // verify
    verify(memberDAOImpl, times(1)).loginMember(any(LoginDTO.class));
  }

  // 3. Find Pwd Member
  @Test
  @DisplayName("비밀번호 찾기 성공")
  void findPwdMemberSuccess() throws Exception {
    // given
    String sampleId = "testId";
    String sampleEmail = "testEmail";
    SimpleMailMessage message = new SimpleMailMessage();
    message.setFrom(env.getProperty("spring.mail.username"));
    message.setTo(sampleEmail);
    message.setSubject("비밀번호 인증 메일입니다.");

    // when
    String result = memberServiceImpl.findPwdMember(sampleId, sampleEmail);
    message.setText(result);

    // then
    String userId = JWTutil.getUserId(result, secretKey);
    String userRole = JWTutil.getUserRole(result, secretKey);
    assertThat(userId.equals(sampleId));
    assertThat(userRole.equals("PWD"));

    // verify
    verify(memberDAOImpl, times(1)).findPwdMember(sampleId, sampleEmail);
    verify(emailSender, times(1)).send(message);
  }



  // 4. Modify Pwd Member
  @Test
  @DisplayName("비밀번호 수정 성공")
  void modPwdMemberSuccess() throws Exception {
    // given
    String userId = "testId";
    String userPwd = "testPwd";
    String response = "success";
    doReturn(response).when(memberDAOImpl).modPwdMember(userId, userPwd);

    // when
    String result = memberServiceImpl.modPwdMember(userId, userPwd);

    // then
    assertThat(result.equals(response));

    // verify
    verify(memberDAOImpl, times(1)).modPwdMember(userId, userPwd);
  }


  // 5. Modify Info Member
  @Test
  @DisplayName("회원정보 수정 성공")
  void modInfoMemberSuccess() throws Exception {
    // given
    String userId = "testId";
    ModifyMemberDTO sample = ModifyMemberDTO.sample();
    String response = "success";
    doReturn(response).when(memberDAOImpl).modInfoMember(userId, sample);

    // when
    String result = memberServiceImpl.modInfoMember(userId, sample);

    // then
    assertThat(result.equals(response));

    // verify
    verify(memberDAOImpl, times(1)).modInfoMember(userId, sample);
  }

  // 6. Delete Member
  @Test
  @DisplayName("회원정보 삭제 성공")
  void delMemberSuccess() throws Exception {
    // given
    String userId = "testId";
    String userPwd = "testPwd";
    String response = "success";
    doReturn(response).when(memberDAOImpl).delMember(userId, userPwd);

    // when
    String result = memberServiceImpl.delMember(userId, userPwd);

    // then
    assertThat(result.equals(response));

    // verify
    verify(memberDAOImpl, times(1)).delMember(userId, userPwd);
  }


}
