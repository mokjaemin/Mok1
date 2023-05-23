package com.ReservationServer1.DAO.Impl;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.ReservationServer1.DAO.StoreDAO;
import com.ReservationServer1.DAO.DB.DBMS.StoreRepository;
import com.ReservationServer1.data.Entity.store.StoreEntity;
import com.ReservationServer1.exception.MessageException;


@Repository("StoreDAO")
@Transactional
public class StoreDAOImpl implements StoreDAO {

  private final Logger logger = LoggerFactory.getLogger(StoreDAO.class);
  private final StoreRepository storeRepository;

  public StoreDAOImpl(StoreRepository storeRepository) {
    this.storeRepository = storeRepository;
  }

  @Override
  public String registerStore(StoreEntity storeEntity) {
    logger.info("[StoreDAO] registerStore(가게 등록) 호출");
    storeRepository.save(storeEntity);
    return "success";
  }

  @Override
  public List<String> getStoreList(String country, String city, String dong, String type, int page,
      int size) {
    logger.info("[StoreDAO] printStore(가게 목록 출력) 호출");
    Pageable pageable = PageRequest.of(page, size);
    List<StoreEntity> storeEntityList = storeRepository.findByCountryAndCityAndDongAndType(country, city, dong, type, pageable);
    List<String> result = new ArrayList<>();
    for (StoreEntity entity : storeEntityList) {
      result.add(entity.getStoreName());
    }
    return result;
  }

  @Override
  public String loginStore(String storeName) {
    logger.info("[StoreDAO] loginStore(가게 권한 반환) 호출");
    StoreEntity storeInfo = storeRepository.findByStoreName(storeName);
    if(storeInfo == null) {
      throw new MessageException("존재하지 않는 가게입니다.");
    }
    return storeInfo.getOwnerId();
  }

}
