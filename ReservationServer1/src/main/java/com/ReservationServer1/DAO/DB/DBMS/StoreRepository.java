package com.ReservationServer1.DAO.DB.DBMS;



import org.springframework.data.jpa.repository.JpaRepository;
import com.ReservationServer1.data.Entity.store.StoreEntity;

public interface StoreRepository extends JpaRepository<StoreEntity, String>{

  StoreEntity findByStoreName(String storeName);
}
