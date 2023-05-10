package com.ReservationServer1.DAO.JPAImpl.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import com.ReservationServer1.data.Entity.store.StoreRestDaysMapEntity;

public interface StoreRestDayMapRepository extends JpaRepository<StoreRestDaysMapEntity, Long>{

}
