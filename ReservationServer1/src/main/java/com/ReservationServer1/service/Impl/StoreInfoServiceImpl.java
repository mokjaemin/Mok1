package com.ReservationServer1.service.Impl;

import java.util.List;
import org.springframework.stereotype.Service;
import com.ReservationServer1.DAO.StoreInfoDAO;
import com.ReservationServer1.data.DTO.store.StoreRestDayDTO;
import com.ReservationServer1.data.DTO.store.StoreTimeInfoDTO;
import com.ReservationServer1.service.StoreInfoService;


@Service
public class StoreInfoServiceImpl implements StoreInfoService{
  
  private final StoreInfoDAO storeInfoDAO;
  
  public StoreInfoServiceImpl(StoreInfoDAO ownerDAO) {
    this.storeInfoDAO = ownerDAO;
  }

  @Override
  public String registerDayOff(StoreRestDayDTO restDayDTO) {
    return storeInfoDAO.registerDayOff(restDayDTO);
  }

  @Override
  public List<String> getDayOff(String storeName) {
    return storeInfoDAO.getDayOff(storeName);
  }

  @Override
  public String deleteDayOff(StoreRestDayDTO restDayDTO) {
    return storeInfoDAO.deleteDayOff(restDayDTO);
  }

  @Override
  public String registerTimeInfo(StoreTimeInfoDTO timeInfoDTO) {
    return storeInfoDAO.registerTimeInfo(timeInfoDTO);
  }

  @Override
  public StoreTimeInfoDTO getTimeInfo(String storeName) {
    return storeInfoDAO.getTimeInfo(storeName);
  }

  @Override
  public String modTimeInfo(StoreTimeInfoDTO timeInfoDTO) {
    return storeInfoDAO.modTimeInfo(timeInfoDTO);
  }

}
