package com.ReservationServer1.service;

import java.util.List;
import com.ReservationServer1.data.DTO.store.RestDayDTO;

public interface StoreInfoService {

  String registerDayOff(RestDayDTO restDayDTO);
  List<String> getDayOff(String storeName);
  String deleteDayOff(RestDayDTO restDayDTO);
}
