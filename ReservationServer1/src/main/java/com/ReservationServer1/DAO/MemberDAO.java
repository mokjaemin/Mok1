package com.ReservationServer1.DAO;

import com.ReservationServer1.data.Entity.MemberEntity;

public interface MemberDAO {

	MemberEntity create(MemberEntity memberEntity);
	Boolean existsById(String userId);
	MemberEntity infoById(String userId);
}
