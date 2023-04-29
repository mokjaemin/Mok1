package com.ReservationServer1.DAO;

import com.ReservationServer1.data.DTO.member.LoginDTO;
import com.ReservationServer1.data.DTO.member.ModifyMemberDTO;
import com.ReservationServer1.data.Entity.member.MemberEntity;

public interface MemberDAO {

	MemberEntity create(MemberEntity memberEntity);
	MemberEntity login(LoginDTO loginDTO);
	MemberEntity findPwd(String userName, String userEmail);
	void modPwd(String userId, String userPwd);
	void modInfo(String userId, ModifyMemberDTO modifyMemberDTO);
	void delMember(String userId, String userPwd);
}
