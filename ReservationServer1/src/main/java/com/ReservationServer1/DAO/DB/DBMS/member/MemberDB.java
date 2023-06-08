package com.ReservationServer1.DAO.DB.DBMS.member;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ReservationServer1.data.Entity.member.MemberEntity;

public interface MemberDB extends JpaRepository<MemberEntity, String>{
	Boolean existsByUserId(String userId);
	MemberEntity findByUserId(String userId);
}
