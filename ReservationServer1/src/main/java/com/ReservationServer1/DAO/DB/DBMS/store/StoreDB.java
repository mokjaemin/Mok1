package com.ReservationServer1.DAO.DB.DBMS.store;



import org.springframework.data.jpa.repository.JpaRepository;
import com.ReservationServer1.data.Entity.store.StoreEntity;

public interface StoreDB extends JpaRepository<StoreEntity, String>{

  StoreEntity findByStoreName(String storeName);
}