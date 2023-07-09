package com.ReservationServer1.service.Impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import com.ReservationServer1.DAO.MemberDAO;
import com.ReservationServer1.data.DTO.member.LoginDTO;
import com.ReservationServer1.data.DTO.member.MemberDTO;
import com.ReservationServer1.data.DTO.member.ModifyMemberDTO;
import com.ReservationServer1.data.Entity.member.MemberEntity;
import com.ReservationServer1.service.MemberService;
import com.ReservationServer1.utils.JWTutil;

@Service
public class MemberServiceImpl implements MemberService {

  @Value("${jwt.secret}")
  private String secretKey;

  private final JavaMailSender emailSender;
  private final Environment env;
  private final MemberDAO memberDAO;
  private final Long expiredLoginMs = 1000 * 60 * 30l; // 30분
  private final Long expiredPwdMs = 1000 * 60 * 5l; // 5분


  public MemberServiceImpl(MemberDAO memberDAO, JavaMailSender emailSender, Environment env) {
    this.memberDAO = memberDAO;
    this.emailSender = emailSender;
    this.env = env;
  }


  @Override
  public String registerMember(MemberDTO member) {
    return memberDAO.registerMember(new MemberEntity(member));
  }


  @Override
  public String loginMember(LoginDTO loginDTO) {
    memberDAO.loginMember(loginDTO);
    String token = JWTutil.createJWT(loginDTO.getUserId(), "USER", secretKey, expiredLoginMs);
    return token;
  }

  @Override
  public String findPwdMember(String userId, String userEmail) {
    memberDAO.findPwdMember(userId, userEmail);
    String token = JWTutil.createJWT(userId, "PWD", secretKey, expiredPwdMs);
    SimpleMailMessage message = new SimpleMailMessage();
    message.setFrom(env.getProperty("spring.mail.username"));
    message.setTo(userEmail);
    message.setSubject("비밀번호 인증 메일입니다.");
    message.setText(token);
    emailSender.send(message);
    return token;
  }

  @Override
  public String modPwdMember(String userId, String userPwd) {
    return memberDAO.modPwdMember(userId, userPwd);
  }

  @Override
  public String modInfoMember(String userId, ModifyMemberDTO modifyMemberDTO) {
    return memberDAO.modInfoMember(userId, modifyMemberDTO);
  }

  @Override
  public String delMember(String userId, String userPwd) {
    return memberDAO.delMember(userId, userPwd);
  }


  @Override
  public MemberEntity getMember(String userId) {
    return memberDAO.getMember(userId);
  }



}
