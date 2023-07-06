package com.ReservationServer1.DAO;

import java.util.List;
import com.ReservationServer1.data.DTO.store.StoreRestDayDTO;
import com.ReservationServer1.data.DTO.store.StoreTimeInfoDTO;
import com.ReservationServer1.data.Entity.store.StoreFoodsInfoEntity;
import com.ReservationServer1.data.Entity.store.StoreTableInfoEntity;

public interface StoreInfoDAO {

  String registerDayOff(StoreRestDayDTO restDayDTO);
  List<String> getDayOff(int storeId);
  String deleteDayOff(StoreRestDayDTO restDayDTO);
  
  String registerTimeInfo(StoreTimeInfoDTO storeTimeInfoDTO);
  StoreTimeInfoDTO getTimeInfo(int storeId);
  String modTimeInfo(StoreTimeInfoDTO storeTimeInfoDTO);
  String deleteTimeInfo(int storeId);

  String registerTableInfo(StoreTableInfoEntity storeTableInfoEntity);
  String modTableInfo(StoreTableInfoEntity storeTableInfoEntity);
  String deleteTableInfo(int storeId);
  
  String registerFoodsInfo(StoreFoodsInfoEntity storeFoodsInfoEntity);
  List<StoreFoodsInfoEntity> getFoodsInfo(int storeId);
  String modFoodsInfo(StoreFoodsInfoEntity storeFoodsInfoEntity);
  String deleteFoodsInfo(int storeId, String foodName);
}
