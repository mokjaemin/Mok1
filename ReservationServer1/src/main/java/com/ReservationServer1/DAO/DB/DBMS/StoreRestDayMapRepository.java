package com.ReservationServer1.DAO.DB.DBMS;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.ReservationServer1.data.Entity.store.StoreRestDaysMapEntity;

public interface StoreRestDayMapRepository extends JpaRepository<StoreRestDaysMapEntity, Long> {

  @Modifying
  @Query(value = "DELETE srdm FROM store_rest_days_map srdm JOIN store_rest_days srd ON srdm.days_id = srd.days_id WHERE srd.store_name = :storeName AND srdm.date = :date", nativeQuery = true)
  void deleteByStoreNameAndDate(@Param("storeName") String storeName, @Param("date") String date);
  
  @Query(value = "SELECT srdm.days_id FROM store_rest_days_map srdm WHERE srdm.date = :date", nativeQuery = true)
  String findDaysIdByDate(@Param("date") String date);
  
  @Query(value = "SELECT COUNT(*) FROM store_rest_days_map srdm WHERE srdm.days_id = :daysId", nativeQuery = true)
  int findCountByDaysId(@Param("daysId") String daysId);
}
