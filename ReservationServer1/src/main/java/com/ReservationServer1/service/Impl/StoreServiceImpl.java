package com.ReservationServer1.service.Impl;

import java.util.HashMap;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ReservationServer1.DAO.StoreDAO;
import com.ReservationServer1.DAO.Cache.StoreListCache;
import com.ReservationServer1.data.StoreType;
import com.ReservationServer1.data.DTO.store.StoreDTO;
import com.ReservationServer1.data.DTO.store.cache.StoreListDTO;
import com.ReservationServer1.data.Entity.store.StoreEntity;
import com.ReservationServer1.service.StoreService;
import com.ReservationServer1.utils.JWTutil;

@Service
public class StoreServiceImpl implements StoreService {

	@Value("${jwt.secret}")
	private String secretKey;
	private final StoreDAO storeDAO;
	private final Long expiredLoginMs = 1000 * 60 * 30l; // 30분

	public StoreServiceImpl(StoreDAO storeDAO) {
		this.storeDAO = storeDAO;
	}

	@Override
	public String registerStore(StoreDTO storeDTO) {
		return storeDAO.registerStore(new StoreEntity(storeDTO));
	}

	@Override
	public HashMap<String, Short> getStoreList(String country, String city, String dong, StoreType type, int page,
			int size) {

		return storeDAO.getStoreList(country, city, dong, type, page, size);
	}

	@Override
	public String loginStore(short storeId, String userId) {
		String result = storeDAO.loginStore(storeId);
		if (result != null && result.equals(userId)) {
			return JWTutil.createJWT(String.valueOf(storeId), "OWNER", secretKey, expiredLoginMs);
		}
		return "user";
	}

}
