package com.ReservationServer1.DAO;

import java.util.List;
import com.ReservationServer1.data.DTO.store.StoreRestDayDTO;
import com.ReservationServer1.data.DTO.store.StoreTimeInfoDTO;

public interface StoreInfoDAO {

  String registerDayOff(StoreRestDayDTO restDayDTO);
  List<String> getDayOff(String storeName);
  String deleteDayOff(StoreRestDayDTO restDayDTO);
  
  String registerTimeInfo(StoreTimeInfoDTO storeTimeInfoDTO);
  StoreTimeInfoDTO getTimeInfo(String storeName);
  String modTimeInfo(StoreTimeInfoDTO storeTimeInfoDTO);
}
