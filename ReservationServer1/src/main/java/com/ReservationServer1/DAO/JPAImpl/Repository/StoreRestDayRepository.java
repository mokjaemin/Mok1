package com.ReservationServer1.DAO.JPAImpl.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import com.ReservationServer1.data.Entity.store.StoreRestDaysEntity;

public interface StoreRestDayRepository extends JpaRepository<StoreRestDaysEntity, Long>{
  
  // Join N+1 Solution without Query DSL
  
  // 1. Basic
  // List<StoreRestDaysEntity> findByStoreName(String storeName);

  
  // 2. Left Fetch Join
  // @Query("SELECT DISTINCT srd FROM StoreRestDaysEntity srd LEFT JOIN FETCH srd.childSet srdm WHERE srd.storeName = :storeName")
  // List<StoreRestDaysEntity> findByStoreName(@Param("storeName") String storeName);
  
  
  // 3. Entity Graph
   @EntityGraph(attributePaths = "childSet")
   @Query("SELECT srd FROM StoreRestDaysEntity srd WHERE srd.storeName = :storeName")
   List<StoreRestDaysEntity> findByStoreName(@Param("storeName") String storeName);


}
