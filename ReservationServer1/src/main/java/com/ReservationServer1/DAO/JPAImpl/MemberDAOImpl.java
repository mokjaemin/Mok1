package com.ReservationServer1.DAO.JPAImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.ReservationServer1.DAO.MemberDAO;
import com.ReservationServer1.DAO.JPAImpl.Repository.MemberRepository;
import com.ReservationServer1.data.Entity.MemberEntity;
import com.ReservationServer1.service.MemberService;



@Repository("MemberDAO") 
public class MemberDAOImpl implements MemberDAO{

	private final Logger Logger = LoggerFactory.getLogger(MemberService.class);
	
	private MemberRepository memberRepository;
	
	public MemberDAOImpl(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}
	
	
	// 회원등록
	@Override
	public MemberEntity create(MemberEntity memberEntity){
		Logger.info("[MemberDAO] create(회원가입) 호출");
		memberRepository.save(memberEntity);
		return memberEntity;
	}
	
	// 아이디 존재 확인
	public Boolean existsById(String userId){
		Logger.info("[MemberDAO] existsById(아이디 존재여부) 호출");
		return memberRepository.existsById(userId);
	}

	// 아이디에 맞는 정보 반환
	@Override
	public MemberEntity infoById(String userId){
		Logger.info("[MemberDAO] infoById(아이디에 맞는 회원정보 반환) 호출");
		MemberEntity result = memberRepository.findAllByUserId(userId);
		return result;
	}
}
