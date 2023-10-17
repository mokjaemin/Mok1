package com.ReservationServer1.DAO.Impl;

import static com.ReservationServer1.data.Entity.member.QMemberEntity.memberEntity;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.ReservationServer1.DAO.MemberDAO;
import com.ReservationServer1.data.DTO.member.LoginDTO;
import com.ReservationServer1.data.DTO.member.ModifyMemberDTO;
import com.ReservationServer1.data.DTO.member.SearchMemberDTO;
import com.ReservationServer1.data.Entity.member.MemberEntity;
import com.ReservationServer1.exception.member.BadEmailException;
import com.ReservationServer1.exception.member.BadPwdException;
import com.ReservationServer1.exception.member.ExistIDException;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
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
    // ID 존재 여부 확인
    Integer exist_id = queryFactory.selectOne().from(memberEntity)
        .where(memberEntity.userId.eq(user_id)).fetchFirst();
    // 이미 존재하는 아이디 처리
    if (exist_id != null) {
      throw new ExistIDException(true);
    }
    // 회원정보 저장
    entity.setUserPwd(passwordEncoder.encode(entity.getUserPwd()));
    entityManager.persist(entity);
    return "success";
  }



  // 로그인
  public void loginMember(LoginDTO loginDTO) {
    // 비밀번호 조회
    String get_pwd = queryFactory.select(memberEntity.userPwd).from(memberEntity)
        .where(memberEntity.userId.eq(loginDTO.getUserId())).fetchFirst();
    // ID 존재하지 않음
    if (get_pwd == null) {
      throw new ExistIDException(false);
    }
    // 비밀번호 불일치
    if (!passwordEncoder.matches(loginDTO.getUserPwd(), get_pwd)) {
      throw new BadPwdException();
    }
  }


  // 비밀번호 찾기
  public void findPwdMember(String userId, String userEmail) {
    // Email 조회
    String get_email = queryFactory.select(memberEntity.userEmail).from(memberEntity)
        .where(memberEntity.userId.eq(userId)).fetchFirst();
    // 존재하지 않는 아이디
    if (get_email == null) {
      throw new ExistIDException(false);
    }
    // 이메일 정보 불일치
    if (!get_email.equals(userEmail)) {
      throw new BadEmailException();
    }
  }


  // 비밀번호 수정
  @Override
  public String modPwdMember(String userId, String userPwd) {
    // 아이디 존재 여부 확인
    Integer exist_id = queryFactory.selectOne().from(memberEntity)
        .where(memberEntity.userId.eq(userId)).fetchFirst();
    if (exist_id == null) {
      throw new ExistIDException(false);
    }
    // 비밀번호 수정
    String encoded_pwd = passwordEncoder.encode(userPwd);
    queryFactory.update(memberEntity).set(memberEntity.userPwd, encoded_pwd)
        .where(memberEntity.userId.eq(userId)).execute();
    return "success";
  }


  // 회원정보 수정
  @Override
  public String modInfoMember(String userId, ModifyMemberDTO modifyMemberDTO) {
    // 아이디 존재 여부 확인
    Integer exist_id = queryFactory.selectOne().from(memberEntity)
        .where(memberEntity.userId.eq(userId)).fetchFirst();
    if (exist_id == null) {
      throw new ExistIDException(false);
    }
    // 정보 수정
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
    // 비밀번호 조회
    String getPwd = queryFactory.select(memberEntity.userPwd).from(memberEntity)
        .where(memberEntity.userId.eq(userId)).fetchFirst();
    // 존재하지 않는 아이디
    if (getPwd == null) {
      throw new ExistIDException(false);
    }
    // 비밀번호 불일치
    if (!passwordEncoder.matches(userPwd, getPwd)) {
      throw new BadPwdException();
    }
    // 회원정보 삭제
    queryFactory.delete(memberEntity).where(memberEntity.userId.eq(userId)).execute();
    return "success";
  }


  @Override
  public List<SearchMemberDTO> searchMember(SearchMemberDTO member) {

    // 아이디가 존재한다면 아이디로 조회
    if (member.getUserId() != null) {
      SearchMemberDTO dto = queryFactory
          .select(Projections.fields(SearchMemberDTO.class,
              Expressions.asString(member.getUserId()).as("userId"), memberEntity.userName,
              memberEntity.userNumber, memberEntity.userAddress, memberEntity.userEmail))
          .from(memberEntity).where(eqUserId(member.getUserId())).fetchFirst();
      List<SearchMemberDTO> result = new ArrayList<>();
      result.add(dto);
      return result;
    }


    // 동적 쿼리 생성 및 결과 반환
    List<SearchMemberDTO> result = queryFactory
        .select(
            Projections.fields(SearchMemberDTO.class, memberEntity.userId, memberEntity.userName,
                memberEntity.userNumber, memberEntity.userAddress, memberEntity.userEmail))
        .from(memberEntity)
        .where(eqUserId(member.getUserId()), eqUserName(member.getUserName()),
            eqUserNumber(member.getUserNumber()), eqUserAddress(member.getUserAddress()),
            eqUserEmail(member.getUserEmail()))
        .orderBy(memberEntity.userId.asc(), memberEntity.userName.asc())
        .fetch();


    return result;
  }

  // 동적 쿼리 메서드
  private BooleanExpression eqUserId(String userId) {
    if (StringUtils.isEmpty(userId)) {
      return null;
    }
    return memberEntity.userId.eq(userId);
  }

  private BooleanExpression eqUserName(String userName) {
    if (StringUtils.isEmpty(userName)) {
      return null;
    }
    return memberEntity.userName.eq(userName);
  }

  private BooleanExpression eqUserNumber(String userNumber) {
    if (StringUtils.isEmpty(userNumber)) {
      return null;
    }
    return memberEntity.userNumber.eq(userNumber);
  }

  private BooleanExpression eqUserAddress(String userAddress) {
    if (StringUtils.isEmpty(userAddress)) {
      return null;
    }
    return memberEntity.userAddress.eq(userAddress);
  }

  private BooleanExpression eqUserEmail(String userEmail) {
    if (StringUtils.isEmpty(userEmail)) {
      return null;
    }
    return memberEntity.userEmail.eq(userEmail);
  }

}
