package com.ReservationServer1.DAO.Impl;

import java.util.List;
import java.util.stream.Collectors;
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

  private final StoreRepository storeRepository;

  public StoreDAOImpl(StoreRepository storeRepository) {
    this.storeRepository = storeRepository;
  }

  @Override
  public String registerStore(StoreEntity storeEntity) {
    storeRepository.save(storeEntity);
    return "success";
  }

  @Override
  public List<String> getStoreList(String country, String city, String dong, String type, int page,int size) {
    Pageable pageable = PageRequest.of(page, size);
    List<StoreEntity> storeEntityList = storeRepository.findByCountryAndCityAndDongAndType(country, city, dong, type, pageable);
    return storeEntityList.stream().distinct().map(StoreEntity::getStoreName).sorted().collect(Collectors.toList());
  }

  @Override
  public String loginStore(String storeName) {
    StoreEntity storeInfo = storeRepository.findByStoreName(storeName);
    if(storeInfo == null) {
      throw new MessageException("존재하지 않는 가게입니다.");
    }
    return storeInfo.getOwnerId();
  }

}
