package com.ReservationServer1.service;

import java.util.List;
import com.ReservationServer1.data.DTO.store.StoreRestDayDTO;
import com.ReservationServer1.data.DTO.store.StoreTimeInfoDTO;

public interface StoreInfoService {

  String registerDayOff(StoreRestDayDTO restDayDTO);
  List<String> getDayOff(String storeName);
  String deleteDayOff(StoreRestDayDTO restDayDTO);
  
  String registerTimeInfo(StoreTimeInfoDTO timeInfoDTO);
  StoreTimeInfoDTO getTimeInfo(String storeName);
  String modTimeInfo(StoreTimeInfoDTO timeInfoDTO);
  String deleteTimeInfo(String storeName);
}
