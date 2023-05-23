package com.ReservationServer1.DAO;

import java.util.List;
import com.ReservationServer1.data.DTO.store.RestDayDTO;

public interface StoreInfoDAO {

  String registerDayOff(RestDayDTO restDayDTO);
  List<String> getDayOff(String storeName);
  String deleteDayOff(RestDayDTO restDayDTO);
}
