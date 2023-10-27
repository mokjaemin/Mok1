package com.ReservationServer1.service;

import java.util.List;

import com.ReservationServer1.data.DTO.member.LoginDTO;
import com.ReservationServer1.data.DTO.member.MemberDTO;
import com.ReservationServer1.data.DTO.member.MemberTokenResultDTO;
import com.ReservationServer1.data.DTO.member.ModifyMemberDTO;
import com.ReservationServer1.data.DTO.member.SearchMemberDTO;

public interface MemberService {

	String registerMember(MemberDTO member);

	MemberTokenResultDTO loginMember(LoginDTO loginDTO);
	
	String reLoginMember(String userId);

	String findPwdMember(String userName, String userEmail);

	String modifyPwdMember(String userId, String userPwd);

	String modifyInfoMember(String userId, ModifyMemberDTO modifyMemberDTO);

	String deleteMember(String userId, String userPwd);

	List<SearchMemberDTO> searchMember(SearchMemberDTO member);
}
