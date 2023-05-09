package com.ReservationServer1.DAO.JPAImpl;


import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import com.ReservationServer1.DAO.StoreInfoDAO;
import com.ReservationServer1.DAO.JPAImpl.Repository.StoreRestDayMapRepository;
import com.ReservationServer1.DAO.JPAImpl.Repository.StoreRestDayRepository;
import com.ReservationServer1.data.DTO.store.RestDayDTO;
import com.ReservationServer1.data.Entity.store.StoreRestDaysEntity;
import com.ReservationServer1.data.Entity.store.StoreRestDaysMapEntity;


@Repository
public class StoreInfoDAOImpl implements StoreInfoDAO {

  private final Logger logger = LoggerFactory.getLogger(StoreInfoDAO.class);
  private StoreRestDayRepository storeRestDayRepositoty;
  private StoreRestDayMapRepository storeRestDayMapRepositoty;

  public StoreInfoDAOImpl(StoreRestDayRepository storeRestDayRepositoty,
      StoreRestDayMapRepository storeRestDayMapRepositoty) {
    this.storeRestDayRepositoty = storeRestDayRepositoty;
    this.storeRestDayMapRepositoty = storeRestDayMapRepositoty;
  }

  @Override
  public void registerDay(RestDayDTO restDayDTO, String storeName) {
    logger.info("[StoreRestDayDAOImpl] day register(쉬는날 등록) 호출");
    StoreRestDaysEntity parent = new StoreRestDaysEntity(storeName);
    storeRestDayRepositoty.save(parent);
    Set<StoreRestDaysMapEntity> childs = new LinkedHashSet<>();
    Set<String> keys = restDayDTO.getDate().keySet();
    for (String key : keys) {
      StoreRestDaysMapEntity child =
          new StoreRestDaysMapEntity(restDayDTO.getDate().get(key), parent);
      storeRestDayMapRepositoty.save(child);
      childs.add(child);
    }
    parent.setChildSet(childs);
  }


  @Override
  public List<String> getRestDays(String storeName) {
    logger.info("[StoreRestDayDAOImpl] get rest days(쉬는날 반환) 호출");
    List<String> result = new ArrayList<>();
    List<StoreRestDaysEntity> check = storeRestDayRepositoty.findByStoreName(storeName);
    if (check.isEmpty() == false) {
      for (StoreRestDaysEntity srde : check) {
        for (StoreRestDaysMapEntity srdme : srde.getChildSet()) {
          result.add(srdme.getDate());
        }
      }
    }
    return result;
  }

}
