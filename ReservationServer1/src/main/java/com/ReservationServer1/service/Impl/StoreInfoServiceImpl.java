package com.ReservationServer1.service.Impl;

import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.ReservationServer1.DAO.StoreInfoDAO;
import com.ReservationServer1.data.DTO.store.RestDayDTO;
import com.ReservationServer1.service.StoreInfoService;


@Service
public class StoreInfoServiceImpl implements StoreInfoService{
  
  private final StoreInfoDAO storeInfoDAO;
  
  public StoreInfoServiceImpl(StoreInfoDAO ownerDAO) {
    this.storeInfoDAO = ownerDAO;
  }

  @Override
  public String registerDayOff(RestDayDTO restDayDTO) {
    return storeInfoDAO.registerDayOff(restDayDTO);
  }

  @Override
  public List<String> getDayOff(String storeName) {
    return storeInfoDAO.getDayOff(storeName);
  }

  @Override
  public String deleteDayOff(RestDayDTO restDayDTO) {
    return storeInfoDAO.deleteDayOff(restDayDTO);
  }

}
