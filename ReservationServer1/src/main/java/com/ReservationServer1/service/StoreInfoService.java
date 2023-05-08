package com.ReservationServer1.service;

import java.util.List;
import com.ReservationServer1.data.DTO.store.RestDayDTO;

public interface StoreInfoService {

  String dayRegister(RestDayDTO restDayDTO, String storeName);
  List<String> dayInfo(String storeName);
}
