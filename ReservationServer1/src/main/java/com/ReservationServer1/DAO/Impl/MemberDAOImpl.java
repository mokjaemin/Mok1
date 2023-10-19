package com.ReservationServer1.DAO.Impl;

import static com.ReservationServer1.data.Entity.member.QMemberEntity.memberEntity;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ReservationServer1.DAO.MemberDAO;
import com.ReservationServer1.data.DTO.member.LoginDTO;
import com.ReservationServer1.data.DTO.member.ModifyMemberDTO;
import com.ReservationServer1.data.DTO.member.SearchMemberDTO;
import com.ReservationServer1.data.Entity.member.MemberEntity;
import com.ReservationServer1.exception.ExistIDException;
import com.ReservationServer1.exception.NoInformationException;
import com.ReservationServer1.exception.NotCoincideEmailException;
import com.ReservationServer1.exception.NotCoincidePwdException;
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

	@Override
	public String registerMember(MemberEntity member) {
		
		String userId = member.getUserId();
		Integer existId = queryFactory.selectOne().from(memberEntity).where(memberEntity.userId.eq(userId))
				.fetchFirst();
		if (existId != null) {
			throw new ExistIDException();
		}
		member.setUserPwd(passwordEncoder.encode(member.getUserPwd()));
		entityManager.persist(member);
		return "success";
	}

	public void loginMember(LoginDTO loginDTO) {
		
		String getPwd = queryFactory.select(memberEntity.userPwd).from(memberEntity)
				.where(memberEntity.userId.eq(loginDTO.getUserId())).fetchFirst();
		if (getPwd == null) {
			throw new NoInformationException();
		}
		if (!passwordEncoder.matches(loginDTO.getUserPwd(), getPwd)) {
			throw new NotCoincidePwdException();
		}
	}

	public void findPwdMember(String userId, String userEmail) {
		
		String getEmail = queryFactory.select(memberEntity.userEmail).from(memberEntity)
				.where(memberEntity.userId.eq(userId)).fetchFirst();
		if (getEmail == null) {
			throw new NoInformationException();
		}
		// 이메일 정보 불일치
		if (!getEmail.equals(getEmail)) {
			throw new NotCoincideEmailException();
		}
	}

	// 비밀번호 수정
	@Override
	public String modifyPwdMember(String userId, String userPwd) {

		Integer existId = queryFactory.selectOne().from(memberEntity).where(memberEntity.userId.eq(userId))
				.fetchFirst();
		if (existId == null) {
			throw new NoInformationException();
		}
		String encodedPwd = passwordEncoder.encode(userPwd);
		queryFactory.update(memberEntity).set(memberEntity.userPwd, encodedPwd).where(memberEntity.userId.eq(userId))
				.execute();
		return "success";
	}

	@Override
	public String modifyInfoMember(String userId, ModifyMemberDTO modifyMemberDTO) {
		
		Integer existId = queryFactory.selectOne().from(memberEntity).where(memberEntity.userId.eq(userId))
				.fetchFirst();
		if (existId == null) {
			throw new NoInformationException();
		}
		// 정보 수정
		String encoded_pwd = passwordEncoder.encode(modifyMemberDTO.getUserPwd());
		queryFactory.update(memberEntity).set(memberEntity.userPwd, encoded_pwd)
				.set(memberEntity.userName, modifyMemberDTO.getUserName())
				.set(memberEntity.userEmail, modifyMemberDTO.getUserEmail())
				.set(memberEntity.userAddress, modifyMemberDTO.getUserAddress())
				.set(memberEntity.userNumber, modifyMemberDTO.getUserNumber()).where(memberEntity.userId.eq(userId))
				.execute();
		return "success";
	}

	
	@Override
	public String deleteMember(String userId, String userPwd) {

		String getPwd = queryFactory.select(memberEntity.userPwd).from(memberEntity)
				.where(memberEntity.userId.eq(userId)).fetchFirst();
		
		if (getPwd == null) {
			throw new NoInformationException();
		}
		
		if (!passwordEncoder.matches(userPwd, getPwd)) {
			throw new NotCoincidePwdException();
		}

		queryFactory.delete(memberEntity).where(memberEntity.userId.eq(userId)).execute();
		return "success";
	}

	@Override
	public List<SearchMemberDTO> searchMember(SearchMemberDTO member) {

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

		List<SearchMemberDTO> memberList = queryFactory
				.select(Projections.fields(SearchMemberDTO.class, memberEntity.userId, memberEntity.userName,
						memberEntity.userNumber, memberEntity.userAddress, memberEntity.userEmail))
				.from(memberEntity)
				.where(eqUserId(member.getUserId()), eqUserName(member.getUserName()),
						eqUserNumber(member.getUserNumber()), eqUserAddress(member.getUserAddress()),
						eqUserEmail(member.getUserEmail()))
				.fetch();

		
		return memberList.stream().sorted(
				Comparator.comparing((SearchMemberDTO dto) -> dto.getUserId()).thenComparing(dto -> dto.getUserName()))
				.collect(Collectors.toList());
	}

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
