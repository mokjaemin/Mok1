package com.ReservationServer1.service.Impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.ReservationServer1.DAO.StoreRestDayImpl;
import com.ReservationServer1.data.DTO.store.RestDayDTO;
import com.ReservationServer1.data.Entity.store.StoreRestDayEntity;
import com.ReservationServer1.service.StoreRestDayService;


@Service
public class StoreRestDayServiceImpl implements StoreRestDayService{
  
  private final Logger logger = LoggerFactory.getLogger(StoreRestDayService.class);
  private final StoreRestDayImpl ownerDAO;
  
  public StoreRestDayServiceImpl(StoreRestDayImpl ownerDAO) {
    this.ownerDAO = ownerDAO;
  }

  @Override
  public String dayRegister(RestDayDTO restDayDTO, String storeName) {
    logger.info("[StoreRestDayServiceImpl] day register(쉬는날 등록) 호출");
    restDayDTO.setStoreName(storeName);
    StoreRestDayEntity storeRestDayEntity = new StoreRestDayEntity(restDayDTO);
    storeRestDayEntity.setDayId(restDayDTO.getId());
    ownerDAO.registerDay(storeRestDayEntity);
    return "success";
  }

}
