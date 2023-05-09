package com.ReservationServer1.DAO;

import java.util.List;
import com.ReservationServer1.data.DTO.store.RestDayDTO;
import com.ReservationServer1.data.Entity.store.StoreRestDaysEntity;

public interface StoreInfoDAO {

  void postDayOff(RestDayDTO restDayDTO);
  List<String> getDayOff(String storeName);
  void deleteDayOff(RestDayDTO restDayDTO);
}
