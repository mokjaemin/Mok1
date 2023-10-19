package com.ReservationServer1.DAO;

import java.util.List;
import com.ReservationServer1.data.DTO.member.LoginDTO;
import com.ReservationServer1.data.DTO.member.ModifyMemberDTO;
import com.ReservationServer1.data.DTO.member.SearchMemberDTO;
import com.ReservationServer1.data.Entity.member.MemberEntity;

public interface MemberDAO {

	String registerMember(MemberEntity member);

	void loginMember(LoginDTO loginDTO);

	void findPwdMember(String userName, String userEmail);

	String modifyPwdMember(String userId, String userPwd);

	String modifyInfoMember(String userId, ModifyMemberDTO modifyMemberDTO);

	String deleteMember(String userId, String userPwd);

	List<SearchMemberDTO> searchMember(SearchMemberDTO member);
}
