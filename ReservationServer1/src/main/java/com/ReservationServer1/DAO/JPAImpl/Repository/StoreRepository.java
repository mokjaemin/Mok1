package com.ReservationServer1.DAO.JPAImpl.Repository;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ReservationServer1.data.Entity.store.StoreEntity;

public interface StoreRepository extends JpaRepository<StoreEntity, String>{

  
  List<StoreEntity> findByCountryAndCityAndDongAndType(String country, String city, String dong, String type, Pageable pageable);
  StoreEntity findByStoreName(String storeName);
}
