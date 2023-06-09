package com.ReservationServer1.service;

import com.ReservationServer1.data.DTO.member.LoginDTO;
import com.ReservationServer1.data.DTO.member.MemberDTO;
import com.ReservationServer1.data.DTO.member.ModifyMemberDTO;
import com.ReservationServer1.data.Entity.member.MemberEntity;


public interface MemberService {

	String registerMember(MemberDTO member);
	String loginMember(LoginDTO loginDTO);
	String findPwdMember(String userName, String userEmail);
	String modPwdMember(String userId, String userPwd);
	String modInfoMember(String userId, ModifyMemberDTO modifyMemberDTO);
	String delMember(String userId, String userPwd);
	
	MemberEntity getMember(String userId);
}

