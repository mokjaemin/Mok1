package com.ReservationServer1.service;

import java.util.List;
import com.ReservationServer1.data.DTO.store.StoreFoodsInfoDTO;
import com.ReservationServer1.data.DTO.store.StoreFoodsInfoResultDTO;
import com.ReservationServer1.data.DTO.store.StoreRestDayDTO;
import com.ReservationServer1.data.DTO.store.StoreTableInfoDTO;
import com.ReservationServer1.data.DTO.store.StoreTimeInfoDTO;

public interface StoreInfoService {

  // DAY OFF
  String registerDayOff(StoreRestDayDTO restDayDTO);

  List<String> getDayOff(int storeId);

  String deleteDayOff(int storeId);


  // TIME INFO
  String registerTimeInfo(StoreTimeInfoDTO timeInfoDTO);

  StoreTimeInfoDTO getTimeInfo(int storeId);

  String modTimeInfo(StoreTimeInfoDTO timeInfoDTO);

  String deleteTimeInfo(int storeId);


  // TABLE INFO
  String registerTableInfo(StoreTableInfoDTO storeTableInfoDTO);

  String modTableInfo(StoreTableInfoDTO storeTableInfoDTO);

  String deleteTableInfo(int storeId);
  
  
  // FOODS INFO
   String registerFoodsInfo(StoreFoodsInfoDTO storeFoodsInfoDTO);

   List<StoreFoodsInfoResultDTO> getFoodsInfo(int storeId);

   String modFoodsInfo(StoreFoodsInfoDTO storeFoodsInfoDTO);

   String deleteFoodsInfo(int storeId, String foodName);
}
