package com.ReservationServer1.DAO.DB.DBMS.storeInfo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ReservationServer1.data.Entity.store.StoreTimeInfoMapEntity;

public interface StoreTimeInfoMapDB extends JpaRepository<StoreTimeInfoMapEntity, Long> {

}
