package com.ReservationServer1.service.Impl;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ReservationServer1.DAO.StoreInfoDAO;
import com.ReservationServer1.data.DTO.store.StoreFoodsInfoDTO;
import com.ReservationServer1.data.DTO.store.StoreFoodsInfoResultDTO;
import com.ReservationServer1.data.DTO.store.StoreRestDayDTO;
import com.ReservationServer1.data.DTO.store.StoreTableInfoDTO;
import com.ReservationServer1.data.DTO.store.StoreTimeInfoDTO;
import com.ReservationServer1.data.Entity.store.StoreFoodsInfoEntity;
import com.ReservationServer1.data.Entity.store.StoreTableInfoEntity;
import com.ReservationServer1.data.Entity.store.StoreTimeInfoEntity;
import com.ReservationServer1.service.StoreInfoService;

@Service
public class StoreInfoServiceImpl implements StoreInfoService {

	private final StoreInfoDAO storeInfoDAO;

	public StoreInfoServiceImpl(StoreInfoDAO storeInfoDAO) {
		this.storeInfoDAO = storeInfoDAO;
	}

	@Override
	public String registerDayOff(StoreRestDayDTO restDayDTO) {
		return storeInfoDAO.registerDayOff(restDayDTO);
	}

	@Override
	public List<String> getDayOff(short storeId) {
		return storeInfoDAO.getDayOff(storeId);
	}

	//
	@Override
	public String deleteDayOff(short storeId) {
		return storeInfoDAO.deleteDayOff(storeId);
	}

	@Override
	public String registerTimeInfo(StoreTimeInfoDTO timeInfoDTO) {
		return storeInfoDAO.registerTimeInfo(timeInfoDTO);
	}

	@Override
	public StoreTimeInfoEntity getTimeInfo(short storeId) {
		return storeInfoDAO.getTimeInfo(storeId);
	}

	@Override
	public String updateTimeInfo(StoreTimeInfoDTO timeInfoDTO) {
		return storeInfoDAO.updateTimeInfo(timeInfoDTO);
	}

	@Override
	public String deleteTimeInfo(short storeId) {
		return storeInfoDAO.deleteTimeInfo(storeId);
	}

	@Override
	public String registerTableInfo(StoreTableInfoDTO storeTableInfoDTO) {
		try {
			StoreTableInfoEntity tableInfo = StoreTableInfoEntity.builder()
					.storeId(storeTableInfoDTO.getStoreId()).count(storeTableInfoDTO.getCount())
					.tableImage(storeTableInfoDTO.getTableImage().getBytes()).build();
			return storeInfoDAO.registerTableInfo(tableInfo);
		} catch (IOException e) {
			return "FILE UPLOAD FAIL";
		}
	}

	@Override
	public String updateTableInfo(StoreTableInfoDTO storeTableInfoDTO) {
		try {
			StoreTableInfoEntity tableInfo = StoreTableInfoEntity.builder()
					.storeId(storeTableInfoDTO.getStoreId()).count(storeTableInfoDTO.getCount())
					.tableImage(storeTableInfoDTO.getTableImage().getBytes()).build();
			return storeInfoDAO.updateTableInfo(tableInfo);
		} catch (IOException e) {
			return "FILE UPLOAD FAIL";
		}
	}

	@Override
	public String deleteTableInfo(short storeId) {
		return storeInfoDAO.deleteTableInfo(storeId);
	}

	@Override
	public String registerFoodsInfo(StoreFoodsInfoDTO storeFoodsInfoDTO) {
		try {
			StoreFoodsInfoEntity foodsInfo = StoreFoodsInfoEntity.builder()
					.storeId(storeFoodsInfoDTO.getStoreId()).foodName(storeFoodsInfoDTO.getFoodName())
					.foodDescription(storeFoodsInfoDTO.getFoodDescription())
					.foodImage(storeFoodsInfoDTO.getFoodImage().getBytes()).foodPrice(storeFoodsInfoDTO.getFoodPrice())
					.build();
			return storeInfoDAO.registerFoodsInfo(foodsInfo);
		} catch (IOException e) {
			return "FILE UPLOAD FAIL";
		}
	}

	@Override
	public List<StoreFoodsInfoResultDTO> getFoodsInfo(short storeId) {

		return storeInfoDAO.getFoodsInfo(storeId).stream().map(foodsInfo -> {
			String encoded_image = Base64.getEncoder().encodeToString(foodsInfo.getFoodImage());
			StoreFoodsInfoResultDTO foodsInfoDTO = foodsInfo.toStoreFoodsInfoResultDTO();
			foodsInfoDTO.setEncoded_image(encoded_image);
			return foodsInfoDTO;
		}).collect(Collectors.toList());

	}

	@Override
	public String updateFoodsInfo(StoreFoodsInfoDTO storeFoodsInfoDTO) {
		try {
			StoreFoodsInfoEntity foodsInfo = StoreFoodsInfoEntity.builder()
					.storeId(storeFoodsInfoDTO.getStoreId()).foodName(storeFoodsInfoDTO.getFoodName())
					.foodDescription(storeFoodsInfoDTO.getFoodDescription())
					.foodImage(storeFoodsInfoDTO.getFoodImage().getBytes()).foodPrice(storeFoodsInfoDTO.getFoodPrice())
					.build();
			return storeInfoDAO.updateFoodsInfo(foodsInfo);
		} catch (IOException e) {
			return "FILE UPLOAD FAIL";
		}
	}

	@Override
	public String deleteFoodsInfo(short storeId, String foodName) {
		return storeInfoDAO.deleteFoodsInfo(storeId, foodName);
	}
}
