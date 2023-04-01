package com.ReservationServer1.service.Impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ReservationServer1.DAO.MemberDAO;
import com.ReservationServer1.data.DTO.MemberDTO;
import com.ReservationServer1.data.Entity.MemberEntity;
import com.ReservationServer1.exception.MemberException;
import com.ReservationServer1.service.MemberService;


@Service("MemberService")
@Transactional
public class MemberServiceImpl implements MemberService{
	
	private final Logger Logger = LoggerFactory.getLogger(MemberService.class);
	
	private MemberDAO memberDAO;
	
	public MemberServiceImpl(MemberDAO memberDAO) {
		this.memberDAO = memberDAO;
	}
	
	
	// 회원등록
	@Override
	public MemberDTO registerMember(MemberDTO member){
		Logger.info("[MemberService] register(회원가입) 호출");
		if(memberDAO.existsById(member.getUserId())) {
			throw new MemberException("Already exsisted : " + member.getUserId());
		}
		MemberEntity memberEntity = new MemberEntity(member);
		MemberEntity memberResult = memberDAO.create(memberEntity);
		return memberResult.toDomain();
	}
	
	// 로그인
	@Override
	public MemberDTO loginMember(String userId, String userPwd){
		Logger.info("[MemberService] login(로그인) 호출");
		if(memberDAO.existsById(userId)) {
			MemberDTO result = memberDAO.infoById(userId).toDomain();
			if(result.getUserPwd().equals(userPwd)) {
				return result;
			}
			throw new MemberException("Pwd is incorrect");
		}
		throw new MemberException("Id is incorrect");
	}
	
	
}
