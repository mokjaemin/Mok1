package com.ReservationServer1.service.Impl;

import org.springframework.stereotype.Service;

import com.ReservationServer1.DAO.MemberDAO;
import com.ReservationServer1.data.Member;
import com.ReservationServer1.service.MemberService;


@Service("MemberService")
public class MemberServiceImpl implements MemberService{
	
	private MemberDAO memberDAO;
	
	public MemberServiceImpl(MemberDAO memberDAO) {
		this.memberDAO = memberDAO;
	}
	
	
	// 회원등록
	@Override
	public String registerMember(Member member) {
		return memberDAO.create(member);
	}
	
	
}
