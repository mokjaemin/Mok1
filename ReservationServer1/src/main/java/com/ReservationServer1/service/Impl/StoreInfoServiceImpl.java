package com.ReservationServer1.service.Impl;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.ReservationServer1.DAO.StoreInfoDAO;
import com.ReservationServer1.data.DTO.store.RestDayDTO;
import com.ReservationServer1.service.StoreInfoService;


@Service
public class StoreInfoServiceImpl implements StoreInfoService{
  
  private final Logger logger = LoggerFactory.getLogger(StoreInfoService.class);
  private final StoreInfoDAO storeInfoDAO;
  
  public StoreInfoServiceImpl(StoreInfoDAO ownerDAO) {
    this.storeInfoDAO = ownerDAO;
  }

  @Override
  public String postDayOff(RestDayDTO restDayDTO) {
    logger.info("[StoreRestDayServiceImpl] post Day Off(쉬는날 등록) 호출");
    storeInfoDAO.postDayOff(restDayDTO);
    return "success";
  }

  @Override
  public List<String> getDayOff(String storeName) {
    logger.info("[StoreRestDayServiceImpl] get Day off(쉬는날 반환) 호출");
    List<String> result = storeInfoDAO.getDayOff(storeName);
    return result;
  }

  @Override
  public String deleteDayOff(RestDayDTO restDayDTO) {
    logger.info("[StoreRestDayServiceImpl] delete Day Off(쉬는날 삭제) 호출");
    storeInfoDAO.deleteDayOff(restDayDTO);
    return "success";
  }

}