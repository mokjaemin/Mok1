package com.ReservationServer1.DAO.Impl;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.ReservationServer1.DAO.MemberDAO;
import com.ReservationServer1.DAO.DB.DBMS.MemberRepository;
import com.ReservationServer1.data.DTO.member.LoginDTO;
import com.ReservationServer1.data.DTO.member.ModifyMemberDTO;
import com.ReservationServer1.data.Entity.member.MemberEntity;
import com.ReservationServer1.exception.MessageException;




@Repository("MemberDAO")
@Transactional
public class MemberDAOImpl implements MemberDAO {

  private final BCryptPasswordEncoder passwordEncoder;
  private final MemberRepository memberRepository;

  public MemberDAOImpl(MemberRepository memberRepository, BCryptPasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
    this.memberRepository = memberRepository;
  }


  // 회원등록
  @Override
  public String registerMember(MemberEntity memberEntity) {
    if (memberRepository.existsByUserId(memberEntity.getUserId())) {
      throw new MessageException("이미 존재하는 ID입니다: " + memberEntity.getUserId());
    }
    memberEntity.setUserPwd(passwordEncoder.encode(memberEntity.getUserPwd()));
    memberRepository.save(memberEntity);
    return "success";
  }



  // 로그인
  public void loginMember(LoginDTO loginDTO) {
    MemberEntity memberEntity = memberRepository.findByUserId(loginDTO.getUserId());
    if (memberEntity == null) {
      throw new MessageException("아이디가 존재하지 않습니다.");
    }
    if(!passwordEncoder.matches(loginDTO.getUserPwd(), memberEntity.getUserPwd())){
      throw new MessageException("비밀번호가 일치하지 않습니다.");
    }
  }


  // 비밀번호 찾기
  public MemberEntity findPwdMember(String userId, String userEmail) {
    MemberEntity memberEntity = memberRepository.findByUserId(userId);
    if (memberEntity == null) {
      throw new MessageException("존재하지 않는 아이디입니다.");
    }
    if(!memberEntity.getUserEmail().equals(userEmail)){
      throw new MessageException("이메일 정보가 일치하지 않습니다.");
    }
    return memberEntity;
  }


  // 비밀번호 수정
  @Override
  public String modPwdMember(String userId, String userPwd) {
    String encoded_pwd = passwordEncoder.encode(userPwd);
    MemberEntity memberEntity = memberRepository.findByUserId(userId);
    if (memberEntity == null) {
      throw new MessageException("아이디 오류 발생");
    }
    memberEntity.setUserPwd(encoded_pwd);
    return "success";
  }


  // 회원정보 수정
  @Override
  public String modInfoMember(String userId, ModifyMemberDTO modifyMemberDTO) {
    String encoded_pwd = passwordEncoder.encode(modifyMemberDTO.getUserPwd());
    MemberEntity memberEntity = memberRepository.findByUserId(userId);
    if (memberEntity == null) {
      throw new MessageException("아이디 오류 발생");
    }
    memberEntity.setUserPwd(encoded_pwd);
    memberEntity.setUserName(modifyMemberDTO.getUserName());
    memberEntity.setUserEmail(modifyMemberDTO.getUserEmail());
    memberEntity.setUserAddress(modifyMemberDTO.getUserAddress());
    memberEntity.setUserNumber(modifyMemberDTO.getUserNumber());
    return "success";
  }
  
  
  // 회원정보 삭제
  @Override
  public String delMember(String userId, String userPwd) {
    MemberEntity memberEntity = memberRepository.findByUserId(userId);
    if (!passwordEncoder.matches(userPwd, memberEntity.getUserPwd())) {
      throw new MessageException("비밀번호가 일치하지 않습니다.");
    }
    memberRepository.deleteById(userId);
    return "success";
  }


}
