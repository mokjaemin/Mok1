package com.ReservationServer1.DAO;

import com.ReservationServer1.data.DTO.LoginDTO;
import com.ReservationServer1.data.Entity.MemberEntity;

public interface MemberDAO {

	MemberEntity create(MemberEntity memberEntity);
	MemberEntity login(LoginDTO loginDTO);
}
