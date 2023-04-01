package com.ReservationServer1.DAO.JPAImpl.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ReservationServer1.data.Entity.MemberEntity;


public interface MemberRepository extends JpaRepository<MemberEntity, String>{
	MemberEntity findAllByUserId(String userId);
}
