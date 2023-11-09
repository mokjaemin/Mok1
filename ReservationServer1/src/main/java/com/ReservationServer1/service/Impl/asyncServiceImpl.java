package com.ReservationServer1.service.Impl;

import java.util.HashMap;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.ReservationServer1.DAO.Cache.StoreListCache;
import com.ReservationServer1.data.DTO.store.cache.StoreListDTO;
import com.ReservationServer1.data.Entity.store.StoreEntity;
import com.ReservationServer1.service.AsyncService;

@Service
public class asyncServiceImpl implements AsyncService{
	
	private final StoreListCache storeListCache;
	
	public asyncServiceImpl(StoreListCache storeListCache) {
		this.storeListCache = storeListCache;
	}

	
	@Async("threadPoolTaskExecutor")
	@Override
	public void createStoreListCache(String cacheKey, HashMap<String, Short> new_storeList) {
		StoreListDTO storeListDTO = new StoreListDTO(cacheKey, new_storeList);
		storeListCache.save(storeListDTO);
	}
	
	@Async("threadPoolTaskExecutor")
	@Override
	public void updateStoreListCache(StoreEntity storeEntity) {
		// 수정 작업
	}

}
