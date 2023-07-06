package com.ReservationServer1.service;

import java.util.HashMap;
import com.ReservationServer1.data.DTO.store.StoreDTO;

public interface StoreService {
  String registerStore(StoreDTO storeDTO);
  HashMap<String, Integer> getStoreList(String country, String city, String dong, String type, int page, int size);
  String loginStore(int storeId, String userId);
}
