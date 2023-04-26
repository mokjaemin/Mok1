package com.ReservationServer1.DAO.JPAImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.ReservationServer1.DAO.StoreDAO;
import com.ReservationServer1.DAO.JPAImpl.Repository.StoreRepository;
import com.ReservationServer1.data.Entity.StoreEntity;


@Repository("StoreDAO")
@Transactional
public class StoreDAOImpl implements StoreDAO{
  
  private final Logger logger = LoggerFactory.getLogger(StoreDAO.class);
  private StoreRepository storeRepository;

  public StoreDAOImpl(StoreRepository storeRepository) {
    this.storeRepository = storeRepository;
  }

  @Override
  public void registerStore(StoreEntity storeEntity) {
    logger.info("[StoreDAO] registerStore(가게 등록) 호출");
    storeRepository.save(storeEntity);
  }

}
