package com.ReservationServer1.service.Impl;


import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.ReservationServer1.DAO.StoreDAO;
import com.ReservationServer1.DAO.DB.Cache.StoreListCache;
import com.ReservationServer1.data.DTO.store.StoreDTO;
import com.ReservationServer1.data.DTO.store.cache.StoreListDTO;
import com.ReservationServer1.data.Entity.store.StoreEntity;
import com.ReservationServer1.service.StoreService;
import com.ReservationServer1.utils.JWTutil;



@Service
public class StoreServiceImpl implements StoreService {

  @Value("${jwt.secret}")
  private String secretKey;
  private final StoreDAO storeDAO;
  private final StoreListCache storeListCache;
  private final Long expiredLoginMs = 1000 * 60 * 30l; // 30분

  public StoreServiceImpl(StoreDAO storeDAO, StoreListCache storeListCache) {
    this.storeDAO = storeDAO;
    this.storeListCache = storeListCache;
  }

  @Override
  public String registerStore(StoreDTO storeDTO) {
    return storeDAO.registerStore(new StoreEntity(storeDTO));
  }

  @Override
  public List<String> getStoreList(String country, String city, String dong, String type, int page,
      int size) {
    String address = country + city + dong + type + page + size;
    Optional<StoreListDTO> storeList = storeListCache.findById(address);
    if (storeList.isEmpty() == true) {
      List<String> new_storeList = storeDAO.getStoreList(country, city, dong, type, page, size);
      StoreListDTO storeListDTO = new StoreListDTO(address, new_storeList);
      storeListCache.save(storeListDTO);
      return new_storeList;
    }
    return storeList.get().getStoreList();
  }

  @Override
  public String loginStore(String storeName, String userId) {
    if (storeDAO.loginStore(storeName).equals(userId)) {
      return JWTutil.createJWT(storeName, "OWNER", secretKey, expiredLoginMs);
    }
    return "user";
  }

}
