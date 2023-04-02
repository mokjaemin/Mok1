package com.ReservationServer1.service;

import com.ReservationServer1.data.DTO.MemberDTO;
import com.ReservationServer1.data.Entity.MemberEntity;


public interface MemberService {

	MemberDTO registerMember(MemberDTO member);
	MemberDTO loginMember(String userId, String userPwd);
}

