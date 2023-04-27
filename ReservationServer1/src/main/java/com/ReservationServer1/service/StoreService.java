package com.ReservationServer1.service;

import java.util.List;
import com.ReservationServer1.data.DTO.store.StoreDTO;

public interface StoreService {
  String registerStore(StoreDTO storeDTO);
  List<String> printStore(String country, String city, String dong, String type, int page, int size);
}
