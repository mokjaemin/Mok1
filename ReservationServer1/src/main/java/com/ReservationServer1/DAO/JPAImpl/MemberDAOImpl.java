package com.ReservationServer1.DAO.JPAImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import com.ReservationServer1.DAO.MemberDAO;
import com.ReservationServer1.DAO.JPAImpl.Repository.MemberRepository;
import com.ReservationServer1.data.DTO.LoginDTO;
import com.ReservationServer1.data.Entity.MemberEntity;
import com.ReservationServer1.exception.MemberException;
import jakarta.transaction.Transactional;



@Repository("MemberDAO")
@Transactional
public class MemberDAOImpl implements MemberDAO {

  @Autowired
  private BCryptPasswordEncoder passwordEncoder;
  private final Logger Logger = LoggerFactory.getLogger(MemberDAO.class);
  private MemberRepository memberRepository;
  public MemberDAOImpl(MemberRepository memberRepository) {
    this.memberRepository = memberRepository;
  }


  // 회원등록
  @Override
  public MemberEntity create(MemberEntity memberEntity) {
    Logger.info("[MemberDAO] create(회원가입) 호출");
    if (memberRepository.existsByUserId(memberEntity.getUserId())) {
      throw new MemberException("이미 존재하는 ID입니다: " + memberEntity.getUserId());
    }
    memberEntity.setUserPwd(passwordEncoder.encode(memberEntity.getUserPwd()));
    return memberRepository.save(memberEntity);
  }

  
  
  // 로그인
  public MemberEntity login(LoginDTO loginDTO) {
    Logger.info("[MemberDAO] login(로그인) 호출");
    if (!memberRepository.existsByUserId(loginDTO.getUserId())) {
      throw new MemberException("존재하지 않는 ID입니다. : " + loginDTO.getUserId());
    }
    MemberEntity memberEntity = memberRepository.findByUserId(loginDTO.getUserId());
    if (!passwordEncoder.matches(loginDTO.getUserPwd(), memberEntity.getUserPwd())) {
      throw new MemberException("비밀번호가 일치하지 않습니다.");
    }
    return memberEntity;
  }


}
