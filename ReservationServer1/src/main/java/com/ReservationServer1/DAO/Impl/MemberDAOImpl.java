package com.ReservationServer1.DAO.Impl;

import static com.ReservationServer1.data.Entity.member.QMemberEntity.memberEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.ReservationServer1.DAO.MemberDAO;
import com.ReservationServer1.data.DTO.member.LoginDTO;
import com.ReservationServer1.data.DTO.member.ModifyMemberDTO;
import com.ReservationServer1.data.Entity.member.MemberEntity;
import com.ReservationServer1.exception.MessageException;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;



@Repository("MemberDAO")
@Transactional
public class MemberDAOImpl implements MemberDAO {

  private final BCryptPasswordEncoder passwordEncoder;
  private final JPAQueryFactory queryFactory;
  private final EntityManager entityManager;

  public MemberDAOImpl(BCryptPasswordEncoder passwordEncoder, JPAQueryFactory queryFactory,
      EntityManager entityManager) {
    this.passwordEncoder = passwordEncoder;
    this.queryFactory = queryFactory;
    this.entityManager = entityManager;
  }


  // 회원등록
  @Override
  public String registerMember(MemberEntity entity) {
    String user_id = entity.getUserId();
    Integer exist_id = queryFactory.selectOne().from(memberEntity)
        .where(memberEntity.userId.eq(user_id)).fetchFirst();
    if (exist_id != null) {
      throw new MessageException("이미 존재하는 ID입니다: " + user_id);
    }
    entity.setUserPwd(passwordEncoder.encode(entity.getUserPwd()));
    entityManager.persist(entity);
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
    Integer exist_id = queryFactory.selectOne().from(memberEntity)
        .where(memberEntity.userId.eq(userId)).fetchFirst();
    if (exist_id == null) {
      throw new MessageException("존재하지 않는 아이디 입니다.");
    }
    String encoded_pwd = passwordEncoder.encode(userPwd);
    queryFactory.update(memberEntity).set(memberEntity.userPwd, encoded_pwd)
    .where(memberEntity.userId.eq(userId)).execute();
    return "success";
  }


  // 회원정보 수정
  @Override
  public String modInfoMember(String userId, ModifyMemberDTO modifyMemberDTO) {
    Integer exist_id = queryFactory.selectOne().from(memberEntity)
        .where(memberEntity.userId.eq(userId)).fetchFirst();
    if (exist_id == null) {
      throw new MessageException("존재하지 않는 아이디 입니다.");
    }
    String encoded_pwd = passwordEncoder.encode(modifyMemberDTO.getUserPwd());
    queryFactory.update(memberEntity).set(memberEntity.userPwd, encoded_pwd)
        .set(memberEntity.userName, modifyMemberDTO.getUserName())
        .set(memberEntity.userEmail, modifyMemberDTO.getUserEmail())
        .set(memberEntity.userAddress, modifyMemberDTO.getUserAddress())
        .set(memberEntity.userNumber, modifyMemberDTO.getUserNumber())
        .where(memberEntity.userId.eq(userId)).execute();
    return "success";
  }


  // 회원정보 삭제
  @Override
  public String delMember(String userId, String userPwd) {
    String get_pwd = queryFactory.select(memberEntity.userPwd).from(memberEntity)
        .where(memberEntity.userId.eq(userId)).fetchFirst();
    if (get_pwd == null) {
      throw new MessageException("존재하지 않는 아이디 입니다.");
    }
    String encoded_pwd = passwordEncoder.encode(userPwd);
    if (!passwordEncoder.matches(get_pwd, encoded_pwd)) {
      throw new MessageException("비밀번호가 일치하지 않습니다.");
    }
    queryFactory.delete(memberEntity).where(memberEntity.userId.eq(userId)).execute();
    return "success";
  }

}
