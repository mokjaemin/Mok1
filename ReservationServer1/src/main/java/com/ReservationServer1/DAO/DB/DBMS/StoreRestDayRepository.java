package com.ReservationServer1.DAO.DB.DBMS;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ReservationServer1.data.Entity.store.StoreRestDaysEntity;

public interface StoreRestDayRepository extends JpaRepository<StoreRestDaysEntity, Long>{
  
  
  // Join N+1 Solution without Query DSL
  // SELECT
  
  // 1. Basic
  // List<StoreRestDaysEntity> findByStoreName(String storeName);

  
  // 2. Left Fetch Join
  // @Query("SELECT DISTINCT srd FROM StoreRestDaysEntity srd LEFT JOIN FETCH srd.childSet srdm WHERE srd.storeName = :storeName")
  // List<StoreRestDaysEntity> findByStoreName(@Param("storeName") String storeName);
  
  
  // 3. Entity Graph
  // @EntityGraph(attributePaths = "childSet")
  // @Query("SELECT srd FROM StoreRestDaysEntity srd WHERE srd.storeName = :storeName")
  // List<StoreRestDaysEntity> findByStoreName(@Param("storeName") String storeName);
  
  
  // DELETE
  void deleteByDaysId(String id);


}
