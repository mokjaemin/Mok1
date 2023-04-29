package com.ReservationServer1.DAO.JPAImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import com.ReservationServer1.DAO.StoreRestDayImpl;
import com.ReservationServer1.DAO.JPAImpl.Repository.StoreRestDayRepository;
import com.ReservationServer1.data.Entity.store.StoreRestDayEntity;


@Repository
public class StoreRestDayDAOImpl implements StoreRestDayImpl{
  
  private final Logger logger = LoggerFactory.getLogger(StoreRestDayImpl.class);
  private StoreRestDayRepository storeRestDayRepositoty;
  
  public StoreRestDayDAOImpl(StoreRestDayRepository storeRestDayRepositoty) {
    this.storeRestDayRepositoty = storeRestDayRepositoty;
  }

  @Override
  public void registerDay(StoreRestDayEntity storeRestDayEntity) {
    logger.info("[StoreRestDayDAOImpl] day register(쉬는날 등록) 호출");
    storeRestDayRepositoty.save(storeRestDayEntity);
  }

}
