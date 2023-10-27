package com.ReservationServer1.DAO.Cache;

import org.springframework.data.repository.CrudRepository;

import com.ReservationServer1.data.DTO.member.cache.MemberLoginListDTO;

public interface MemberLoginListCache extends CrudRepository<MemberLoginListDTO, String> {

}
