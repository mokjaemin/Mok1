package com.ReservationServer1.service;

import java.util.List;
import com.ReservationServer1.data.DTO.store.StoreFoodsInfoDTO;
import com.ReservationServer1.data.DTO.store.StoreFoodsInfoResultDTO;
import com.ReservationServer1.data.DTO.store.StoreRestDayDTO;
import com.ReservationServer1.data.DTO.store.StoreTableInfoDTO;
import com.ReservationServer1.data.DTO.store.StoreTimeInfoDTO;
import com.ReservationServer1.data.Entity.store.StoreTimeInfoEntity;

public interface StoreInfoService {

	// DAY OFF
	String registerDayOff(StoreRestDayDTO restDayDTO);

	List<String> getDayOff(short storeId);

	String deleteDayOff(short storeId);

	// TIME INFO
	String registerTimeInfo(StoreTimeInfoDTO timeInfoDTO);

	StoreTimeInfoEntity getTimeInfo(short storeId);

	String updateTimeInfo(StoreTimeInfoDTO timeInfoDTO);

	String deleteTimeInfo(short storeId);

	// TABLE INFO
	String registerTableInfo(StoreTableInfoDTO storeTableInfoDTO);

	String updateTableInfo(StoreTableInfoDTO storeTableInfoDTO);

	String deleteTableInfo(short storeId);

	// FOODS INFO
	String registerFoodsInfo(StoreFoodsInfoDTO storeFoodsInfoDTO);

	List<StoreFoodsInfoResultDTO> getFoodsInfo(short storeId);

	String updateFoodsInfo(StoreFoodsInfoDTO storeFoodsInfoDTO);

	String deleteFoodsInfo(short storeId, String foodName);
}
