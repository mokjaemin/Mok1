package com.ReservationServer1.DAO.Impl;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import static com.ReservationServer1.data.Entity.member.QMemberEntity.memberEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.ReservationServer1.DAO.MemberDAO;
import com.ReservationServer1.DAO.DB.DBMS.MemberRepository;
import com.ReservationServer1.data.DTO.member.LoginDTO;
import com.ReservationServer1.data.DTO.member.ModifyMemberDTO;
import com.ReservationServer1.data.Entity.member.MemberEntity;
import com.ReservationServer1.exception.MessageException;
import com.querydsl.jpa.impl.JPAQueryFactory;



@Repository("MemberDAO")
@Transactional
public class MemberDAOImpl implements MemberDAO {

  private final BCryptPasswordEncoder passwordEncoder;
  private final MemberRepository memberRepository;
  private final JPAQueryFactory queryFactory;

  public MemberDAOImpl(MemberRepository memberRepository, BCryptPasswordEncoder passwordEncoder,
      JPAQueryFactory queryFactory) {
    this.passwordEncoder = passwordEncoder;
    this.memberRepository = memberRepository;
    this.queryFactory = queryFactory;
  }


  // 회원등록
  @Override
  public String registerMember(MemberEntity memberEntity) {
    String user_id = memberEntity.getUserId();
    if (memberRepository.existsByUserId(user_id)) {
      throw new MessageException("이미 존재하는 ID입니다: " + user_id);
    }
    memberEntity.setUserPwd(passwordEncoder.encode(memberEntity.getUserPwd()));
    memberRepository.save(memberEntity);
    return "success";
  }



  // 로그인
  public void loginMember(LoginDTO loginDTO) {
    String get_pwd = queryFactory.select(memberEntity.userPwd).from(memberEntity)
        .where(memberEntity.userId.eq(loginDTO.getUserId())).fetchFirst();
    if (get_pwd == null) {
      throw new MessageException("아이디가 존재하지 않습니다.");
    }
    if (!passwordEncoder.matches(loginDTO.getUserPwd(), get_pwd)) {
      throw new MessageException("비밀번호가 일치하지 않습니다.");
    }
  }


  // 비밀번호 찾기
  public void findPwdMember(String userId, String userEmail) {
    String get_email = queryFactory.select(memberEntity.userEmail).from(memberEntity)
        .where(memberEntity.userId.eq(userId)).fetchFirst();
    if (get_email == null) {
      throw new MessageException("존재하지 않는 아이디입니다.");
    }
    if (!get_email.equals(userEmail)) {
      throw new MessageException("이메일 정보가 일치하지 않습니다.");
    }
  }


  // 비밀번호 수정
  @Override
  public String modPwdMember(String userId, String userPwd) {
    MemberEntity memberEntity = memberRepository.findByUserId(userId);
    if (memberEntity == null) {
      throw new MessageException("존재하지 않는 아이디입니다.");
    }
    String encoded_pwd = passwordEncoder.encode(userPwd);
    memberEntity.setUserPwd(encoded_pwd);
    return "success";
  }


  // 회원정보 수정
  @Override
  public String modInfoMember(String userId, ModifyMemberDTO modifyMemberDTO) {
    MemberEntity memberEntity = memberRepository.findByUserId(userId);
    if (memberEntity == null) {
      throw new MessageException("아이디 오류 발생");
    }
    String encoded_pwd = passwordEncoder.encode(modifyMemberDTO.getUserPwd());
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
    String get_pwd = queryFactory.select(memberEntity.userPwd).from(memberEntity)
        .where(memberEntity.userId.eq(userId)).fetchFirst();
    if (!passwordEncoder.matches(userPwd, get_pwd)) {
      throw new MessageException("비밀번호가 일치하지 않습니다.");
    }
    memberRepository.deleteById(userId);
    return "success";
  }


}
