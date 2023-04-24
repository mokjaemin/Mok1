package com.ReservationServer1.service;

import com.ReservationServer1.data.DTO.MemberDTO;
import com.ReservationServer1.data.Entity.MemberEntity;


public interface MemberService {

	void registerMember(MemberDTO member);
	String loginMember(String userId, String userPwd);
	String findPwdMember(String userName, String userEmail);
	String modPwdMember(String userId, String userPwd);
}

