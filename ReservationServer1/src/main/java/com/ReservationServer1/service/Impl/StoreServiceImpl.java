package com.ReservationServer1.service.Impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.ReservationServer1.DAO.StoreDAO;
import com.ReservationServer1.data.DTO.store.StoreDTO;
import com.ReservationServer1.data.Entity.StoreEntity;
import com.ReservationServer1.service.StoreService;



@Service
public class StoreServiceImpl implements StoreService{
  
  private final Logger logger = LoggerFactory.getLogger(StoreServiceImpl.class);
  private final StoreDAO storeDAO;
  
  public StoreServiceImpl(StoreDAO storeDAO) {
    this.storeDAO = storeDAO;
  }

  @Override
  public String registerStore(StoreDTO storeDTO) {
    logger.info("[StoreService] registerStore(가게 등록) 호출");
    StoreEntity storeEntity = new StoreEntity(storeDTO);
    storeEntity.setStoreId(storeDTO.getId());
    storeDAO.registerStore(storeEntity);
    return "success";
  }

}
