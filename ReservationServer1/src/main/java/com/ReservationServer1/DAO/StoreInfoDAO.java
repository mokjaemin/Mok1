package com.ReservationServer1.DAO;

import java.util.List;
import com.ReservationServer1.data.DTO.store.StoreRestDayDTO;
import com.ReservationServer1.data.DTO.store.StoreTimeInfoDTO;
import com.ReservationServer1.data.Entity.store.StoreFoodsInfoEntity;
import com.ReservationServer1.data.Entity.store.StoreTableInfoEntity;

public interface StoreInfoDAO {

  String registerDayOff(StoreRestDayDTO restDayDTO);
  List<String> getDayOff(String storeName);
  String deleteDayOff(StoreRestDayDTO restDayDTO);
  
  String registerTimeInfo(StoreTimeInfoDTO storeTimeInfoDTO);
  StoreTimeInfoDTO getTimeInfo(String storeName);
  String modTimeInfo(StoreTimeInfoDTO storeTimeInfoDTO);
  String deleteTimeInfo(String storeName);
  
  String registerTableInfo(StoreTableInfoEntity storeTableInfoEntity);
  String modTableInfo(StoreTableInfoEntity storeTableInfoEntity);
  String deleteTableInfo(String storeName);
  
  String registerFoodsInfo(StoreFoodsInfoEntity storeFoodsInfoEntity);
  List<StoreFoodsInfoEntity> getFoodsInfo(String storeName);
  String modFoodsInfo(StoreFoodsInfoEntity storeFoodsInfoEntity);
  String deleteFoodsInfo(String storeName, String foodName);
}
