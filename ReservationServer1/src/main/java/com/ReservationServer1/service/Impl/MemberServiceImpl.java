package com.ReservationServer1.service.Impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import com.ReservationServer1.DAO.MemberDAO;
import com.ReservationServer1.data.DTO.LoginDTO;
import com.ReservationServer1.data.DTO.MemberDTO;
import com.ReservationServer1.data.DTO.ModifyMemberDTO;
import com.ReservationServer1.data.Entity.MemberEntity;
import com.ReservationServer1.service.MemberService;
import com.ReservationServer1.utils.JWTutil;

@Service
public class MemberServiceImpl implements MemberService {

  @Value("${jwt.secret}")
  private String secretKey;
  
  private final JavaMailSender emailSender;
  private final Environment env;
  
  private final Logger logger = LoggerFactory.getLogger(MemberServiceImpl.class);
  private final MemberDAO memberDAO;
  private Long expiredLoginMs = 1000 * 60 * 30l; // 30분
  private Long expiredPwdMs = 1000 * 60 * 5l; // 5분


  public MemberServiceImpl(MemberDAO memberDAO, JavaMailSender emailSender, Environment env) {
    this.memberDAO = memberDAO;
    this.emailSender = emailSender;
    this.env = env;
  }


  @Override
  public void registerMember(MemberDTO member) {
    logger.info("[MemberService] registerMember(회원가입) 호출");
    MemberEntity entity = new MemberEntity(member);
    memberDAO.create(entity);
  }


  @Override
  public String loginMember(String userId, String userPwd) {
    logger.info("[MemberService] loginMember(로그인) 호출");
    LoginDTO loginDTO = new LoginDTO(userId, userPwd);
    memberDAO.login(loginDTO);
    String token = JWTutil.createJWT(userId, "USER", secretKey, expiredLoginMs);
    return token;
  }
  
  @Override
  public String findPwdMember(String userId, String userEmail) {
    logger.info("[MemberService] findPwdMember(비밀번호 찾기) 호출");
    MemberEntity result = memberDAO.findPwd(userId, userEmail);
    
    String token = JWTutil.createJWT(result.getUserId(), "PWD", secretKey, expiredPwdMs);
    
    // 이메일 전송
    SimpleMailMessage message = new SimpleMailMessage();
    message.setFrom(env.getProperty("spring.mail.username"));
    message.setTo(result.getUserEmail());
    message.setSubject("비밀번호 인증 메일입니다.");
    message.setText(token);
    emailSender.send(message);
    return token;
  }
  
  @Override
  public String modPwdMember(String userId, String userPwd) {
    logger.info("[MemberService] modPwdMember(비밀번호 수정) 호출");
    memberDAO.modPwd(userId, userPwd);
    return "success";
  }
  
  @Override
  public String modInfoMember(String userId, ModifyMemberDTO modifyMemberDTO) {
    logger.info("[MemberService] modInfoMember(회원정보 수정) 호출");
    memberDAO.modInfo(userId, modifyMemberDTO);
    return "success";
  }



}
