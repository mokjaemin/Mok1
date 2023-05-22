package com.ReservationServer1.DAO.JPAImpl.Cache;

import org.springframework.data.repository.CrudRepository;
import com.ReservationServer1.data.DTO.store.cache.StoreListDTO;

public interface StoreListCache extends CrudRepository<StoreListDTO, String> {
  
}
