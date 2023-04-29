package com.ReservationServer1.service;

import com.ReservationServer1.data.DTO.store.RestDayDTO;

public interface StoreRestDayService {

  String dayRegister(RestDayDTO restDayDTO, String storeName);
}
