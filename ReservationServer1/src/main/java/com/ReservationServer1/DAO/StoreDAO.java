package com.ReservationServer1.DAO;

import java.util.List;
import com.ReservationServer1.data.Entity.store.StoreEntity;

public interface StoreDAO {
  String registerStore(StoreEntity storeEntity);
  List<String> getStoreList(String country, String city, String dong, String type, int page, int size);
  String loginStore(String storeName);
}
