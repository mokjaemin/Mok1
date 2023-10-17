package com.ReservationServer1.service;

import java.util.HashMap;
import com.ReservationServer1.data.StoreType;
import com.ReservationServer1.data.DTO.store.StoreDTO;

public interface StoreService {
  String registerStore(StoreDTO storeDTO);
  HashMap<String, Short> getStoreList(String country, String city, String dong, StoreType type, int page, int size);
  String loginStore(short storeId, String userId);
}
