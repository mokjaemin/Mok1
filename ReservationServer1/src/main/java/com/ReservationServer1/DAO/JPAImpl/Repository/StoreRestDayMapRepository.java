package com.ReservationServer1.DAO.JPAImpl.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.ReservationServer1.data.Entity.store.StoreRestDaysMapEntity;

public interface StoreRestDayMapRepository extends JpaRepository<StoreRestDaysMapEntity, Long>{


  @Modifying // Update Or Delete 일때 사용, Native 사용시
  @Query(value = "DELETE FROM StoreRestDaysMapEntity s WHERE s.storeRestDaysEntity.storeName = :storeName AND s.date = :date", nativeQuery = true)
  void deleteByStoreNameAndDate(@Param("storeName") String storeName, @Param("date") String date);

}
