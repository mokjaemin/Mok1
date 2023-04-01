package com.ReservationServer1.service;

import com.ReservationServer1.data.DTO.MemberDTO;


public interface MemberService {

	MemberDTO registerMember(MemberDTO member);
	MemberDTO loginMember(String userId, String userPwd);
}

