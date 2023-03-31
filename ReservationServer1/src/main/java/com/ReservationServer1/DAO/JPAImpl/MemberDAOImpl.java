package com.ReservationServer1.DAO.JPAImpl;

import org.springframework.stereotype.Repository;

import com.ReservationServer1.DAO.MemberDAO;
import com.ReservationServer1.DAO.JPAImpl.Entity.MemberEntity;
import com.ReservationServer1.DAO.JPAImpl.Repository.MemberRepository;
import com.ReservationServer1.data.Member;
import com.google.gson.Gson;

@Repository("MemberDAO")
public class MemberDAOImpl implements MemberDAO{

	
	private MemberRepository memberRepository;
	
	public MemberDAOImpl(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}
	
	// 회원등록
	@Override
	public String create(Member member) {
		if(memberRepository.existsById(member.getUserId())) {
			return "fail : " + member.getUserId() + " is Aleady existed.";
		}
		memberRepository.save(new MemberEntity(member));
		return "success : registering has been succeed.";
	}
}
