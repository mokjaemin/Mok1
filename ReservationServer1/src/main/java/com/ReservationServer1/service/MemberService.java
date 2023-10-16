package com.ReservationServer1.service;

import java.util.List;
import com.ReservationServer1.data.DTO.member.LoginDTO;
import com.ReservationServer1.data.DTO.member.MemberDTO;
import com.ReservationServer1.data.DTO.member.ModifyMemberDTO;
import com.ReservationServer1.data.DTO.member.SearchMemberDTO;


public interface MemberService {

	String registerMember(MemberDTO member);
	String loginMember(LoginDTO loginDTO);
	String findPwdMember(String userName, String userEmail);
	String modPwdMember(String userId, String userPwd);
	String modInfoMember(String userId, ModifyMemberDTO modifyMemberDTO);
	String delMember(String userId, String userPwd);
	List<SearchMemberDTO> searchMember(SearchMemberDTO member);
}

