package com.ReservationServer1.DAO.JPAImpl.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ReservationServer1.data.Entity.StoreEntity;

public interface StoreRepository extends JpaRepository<StoreEntity, String>{

}
