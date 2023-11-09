package com.ReservationServer1.service;

import java.util.HashMap;

import com.ReservationServer1.data.Entity.store.StoreEntity;

public interface AsyncService {
	
	
	void createStoreListCache(String cacheKey, HashMap<String, Short> new_storeList);
	
	void updateStoreListCache(StoreEntity storeEntity);
	
}
