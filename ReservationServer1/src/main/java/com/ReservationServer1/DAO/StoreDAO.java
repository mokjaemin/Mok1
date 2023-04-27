package com.ReservationServer1.DAO;

import java.util.List;
import com.ReservationServer1.data.Entity.StoreEntity;

public interface StoreDAO {
  void registerStore(StoreEntity storeEntity);
  List<String> printStore(String country, String city, String dong, String type, int page, int size);
}
