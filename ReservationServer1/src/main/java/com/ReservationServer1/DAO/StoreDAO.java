package com.ReservationServer1.DAO;

import java.util.HashMap;
import com.ReservationServer1.data.Entity.store.StoreEntity;

public interface StoreDAO {
  String registerStore(StoreEntity storeEntity);
  HashMap<String, Integer> getStoreList(String country, String city, String dong, String type, int page, int size);
  String loginStore(int storeId);
}
