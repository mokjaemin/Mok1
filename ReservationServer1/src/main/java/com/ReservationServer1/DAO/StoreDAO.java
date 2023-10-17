package com.ReservationServer1.DAO;

import java.util.HashMap;
import com.ReservationServer1.data.StoreType;
import com.ReservationServer1.data.Entity.store.StoreEntity;

public interface StoreDAO {
  String registerStore(StoreEntity storeEntity);
  HashMap<String, Short> getStoreList(String country, String city, String dong, StoreType type, int page, int size);
  String loginStore(short storeId);
}
