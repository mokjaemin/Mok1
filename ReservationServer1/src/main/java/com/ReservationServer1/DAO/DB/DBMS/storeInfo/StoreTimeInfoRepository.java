package com.ReservationServer1.DAO.DB.DBMS.storeInfo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ReservationServer1.data.Entity.store.StoreTimeInfoEntity;

public interface StoreTimeInfoRepository extends JpaRepository<StoreTimeInfoEntity, Long> {

}
