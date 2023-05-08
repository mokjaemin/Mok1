package com.ReservationServer1.DAO;

import java.util.List;
import com.ReservationServer1.data.DTO.store.RestDayDTO;
import com.ReservationServer1.data.Entity.store.StoreRestDaysEntity;

public interface StoreInfoDAO {

  void registerDay(RestDayDTO restDayDTO, String storeName);
  List<String> getRestDays(String storeName);
}
