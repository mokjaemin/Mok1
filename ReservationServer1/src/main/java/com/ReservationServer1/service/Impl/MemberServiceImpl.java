package com.ReservationServer1.service.Impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.ReservationServer1.DAO.MemberDAO;
import com.ReservationServer1.data.DTO.LoginDTO;
import com.ReservationServer1.data.DTO.MemberDTO;
import com.ReservationServer1.data.Entity.MemberEntity;
import com.ReservationServer1.service.MemberService;
import com.ReservationServer1.utils.JWTutil;

@Service
public class MemberServiceImpl implements MemberService {

  @Value("${jwt.secret}")
  private String secretKey;
  private final Logger logger = LoggerFactory.getLogger(MemberServiceImpl.class);
  private final MemberDAO memberDAO;
  private Long expiredMs = 1000 * 60 * 60l; // 1시간


  public MemberServiceImpl(MemberDAO memberDAO) {
    this.memberDAO = memberDAO;
  }


  @Override
  public MemberDTO registerMember(MemberDTO member) {
    logger.info("[MemberService] registerMember(회원가입) 호출");
    MemberEntity entity = new MemberEntity(member);
    MemberEntity result = memberDAO.create(entity);
    return result.toDomain();
  }


  @Override
  public String loginMember(String userId, String userPwd) {
    logger.info("[MemberService] loginMember(로그인) 호출");
    LoginDTO loginDTO = new LoginDTO(userId, userPwd);
    MemberEntity result = memberDAO.login(loginDTO);
    return JWTutil.createJWT(userId, "USER", secretKey, expiredMs);
  }



}
