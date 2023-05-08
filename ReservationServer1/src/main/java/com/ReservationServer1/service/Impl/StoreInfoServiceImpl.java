package com.ReservationServer1.service.Impl;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.ReservationServer1.DAO.StoreInfoDAO;
import com.ReservationServer1.data.DTO.store.RestDayDTO;
import com.ReservationServer1.data.Entity.store.StoreRestDaysEntity;
import com.ReservationServer1.data.Entity.store.StoreRestDaysMapEntity;
import com.ReservationServer1.service.StoreInfoService;


@Service
public class StoreInfoServiceImpl implements StoreInfoService{
  
  private final Logger logger = LoggerFactory.getLogger(StoreInfoService.class);
  private final StoreInfoDAO storeInfoDAO;
  
  public StoreInfoServiceImpl(StoreInfoDAO ownerDAO) {
    this.storeInfoDAO = ownerDAO;
  }

  @Override
  public String dayRegister(RestDayDTO restDayDTO, String storeName) {
    logger.info("[StoreRestDayServiceImpl] day register(쉬는날 등록) 호출");
    storeInfoDAO.registerDay(restDayDTO, storeName);
    return "success";
  }

  @Override
  public List<String> dayInfo(String storeName) {
    logger.info("[StoreRestDayServiceImpl] get rest days(쉬는날 반환) 호출");
    List<String> result = storeInfoDAO.getRestDays(storeName);
    return result;
  }

}
