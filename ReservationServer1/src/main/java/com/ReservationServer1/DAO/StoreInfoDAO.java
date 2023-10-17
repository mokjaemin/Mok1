package com.ReservationServer1.DAO;

import java.util.List;
import com.ReservationServer1.data.DTO.store.StoreRestDayDTO;
import com.ReservationServer1.data.DTO.store.StoreTimeInfoDTO;
import com.ReservationServer1.data.Entity.store.StoreFoodsInfoEntity;
import com.ReservationServer1.data.Entity.store.StoreTableInfoEntity;
import com.ReservationServer1.data.Entity.store.StoreTimeInfoEntity;

public interface StoreInfoDAO {

  String registerDayOff(StoreRestDayDTO restDayDTO);
  List<String> getDayOff(short storeId);
  String deleteDayOff(short storeId);
  
  String registerTimeInfo(StoreTimeInfoDTO storeTimeInfoDTO);
  StoreTimeInfoEntity getTimeInfo(short storeId);
  String modTimeInfo(StoreTimeInfoDTO storeTimeInfoDTO);
  String deleteTimeInfo(short storeId);

  String registerTableInfo(StoreTableInfoEntity storeTableInfoEntity);
  String modTableInfo(StoreTableInfoEntity storeTableInfoEntity);
  String deleteTableInfo(short storeId);
  
  String registerFoodsInfo(StoreFoodsInfoEntity storeFoodsInfoEntity);
  List<StoreFoodsInfoEntity> getFoodsInfo(short storeId);
  String modFoodsInfo(StoreFoodsInfoEntity storeFoodsInfoEntity);
  String deleteFoodsInfo(short storeId, String foodName);
}
