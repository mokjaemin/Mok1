package com.ReservationServer1.DAO.JPAImpl.Repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ReservationServer1.data.Entity.store.StoreRestDaysEntity;

public interface StoreRestDayRepository extends JpaRepository<StoreRestDaysEntity, Long>{

  Optional<StoreRestDaysEntity> findByStoreName(String storeName);
}
