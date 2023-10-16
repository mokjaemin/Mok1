package com.ReservationServer1.DAO;

import java.util.List;
import com.ReservationServer1.data.DTO.member.LoginDTO;
import com.ReservationServer1.data.DTO.member.ModifyMemberDTO;
import com.ReservationServer1.data.DTO.member.SearchMemberDTO;
import com.ReservationServer1.data.Entity.member.MemberEntity;

public interface MemberDAO {

	String registerMember(MemberEntity memberEntity);
	void loginMember(LoginDTO loginDTO);
	void findPwdMember(String userName, String userEmail);
	String modPwdMember(String userId, String userPwd);
	String modInfoMember(String userId, ModifyMemberDTO modifyMemberDTO);
	String delMember(String userId, String userPwd);
	List<SearchMemberDTO> searchMember(SearchMemberDTO member);
}
