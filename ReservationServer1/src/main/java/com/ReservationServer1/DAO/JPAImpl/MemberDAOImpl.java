package com.ReservationServer1.DAO.JPAImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.ReservationServer1.DAO.MemberDAO;
import com.ReservationServer1.DAO.JPAImpl.Repository.MemberRepository;
import com.ReservationServer1.data.DTO.member.LoginDTO;
import com.ReservationServer1.data.DTO.member.ModifyMemberDTO;
import com.ReservationServer1.data.Entity.member.MemberEntity;
import com.ReservationServer1.exception.MemberException;




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


  // 비밀번호 찾기
  public MemberEntity findPwd(String userId, String userEmail) {
    Logger.info("[MemberDAO] findId(비밀번호 찾기) 호출");
    MemberEntity memberEntity = memberRepository.findByUserId(userId);
    if (memberEntity == null) {
      throw new MemberException("해당 아이디가 존재하지 않습니다.");
    }
    if (!memberEntity.getUserEmail().equals(userEmail)) {
      throw new MemberException("이메일이 일치하지 않습니다.");
    }
    return memberEntity;
  }


  // 비밀번호 수정
  @Override
  public void modPwd(String userId, String userPwd) {
    Logger.info("[MemberDAO] modPwd(비밀번호 수정) 호출");
    String encoded_pwd = passwordEncoder.encode(userPwd);
    MemberEntity memberEntity = memberRepository.findByUserId(userId);
    if (memberEntity == null) {
      throw new MemberException("아이디 오류 발생");
    }
    memberEntity.setUserPwd(encoded_pwd);
  }


  // 회원정보 수정
  @Override
  public void modInfo(String userId, ModifyMemberDTO modifyMemberDTO) {
    Logger.info("[MemberDAO] modInfo(회원정보 수정) 호출");
    String encoded_pwd = passwordEncoder.encode(modifyMemberDTO.getUserPwd());
    MemberEntity memberEntity = memberRepository.findByUserId(userId);
    if (memberEntity == null) {
      throw new MemberException("아이디 오류 발생");
    }
    memberEntity.setUserPwd(encoded_pwd);
    memberEntity.setUserName(modifyMemberDTO.getUserName());
    memberEntity.setUserEmail(modifyMemberDTO.getUserEmail());
    memberEntity.setUserAddress(modifyMemberDTO.getUserAddress());
    memberEntity.setUserNumber(modifyMemberDTO.getUserNumber());
  }
  
  
  @Override
  public void delMember(String userId, String userPwd) {
    Logger.info("[MemberDAO] delMember(회원정보 삭제) 호출");
    System.out.println(userId);
    System.out.println(userPwd);
    MemberEntity memberEntity = memberRepository.findByUserId(userId);
    if (!passwordEncoder.matches(userPwd, memberEntity.getUserPwd())) {
      throw new MemberException("비밀번호가 일치하지 않습니다.");
    }
    memberRepository.deleteById(userId);
  }


}
