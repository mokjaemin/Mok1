package com.ReservationServer1.service.Impl;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.ReservationServer1.DAO.StoreDAO;
import com.ReservationServer1.DAO.JPAImpl.Cache.StoreListRepository;
import com.ReservationServer1.data.DTO.store.StoreDTO;
import com.ReservationServer1.data.DTO.store.StoreListDTO;
import com.ReservationServer1.data.Entity.StoreEntity;
import com.ReservationServer1.service.StoreService;
import com.ReservationServer1.utils.JWTutil;



@Service
public class StoreServiceImpl implements StoreService{
  
  @Value("${jwt.secret}")
  private String secretKey;
  private final Logger logger = LoggerFactory.getLogger(StoreServiceImpl.class);
  private final StoreDAO storeDAO;
  private final StoreListRepository storeListRepository;
  private Long expiredLoginMs = 1000 * 60 * 30l; // 30분
  
  public StoreServiceImpl(StoreDAO storeDAO, StoreListRepository storeListRepository) {
    this.storeDAO = storeDAO;
    this.storeListRepository = storeListRepository;
  }

  @Override
  public String registerStore(StoreDTO storeDTO) {
    logger.info("[StoreService] registerStore(가게 등록) 호출");
    StoreEntity storeEntity = new StoreEntity(storeDTO);
    storeEntity.setStoreId(storeDTO.getId());
    storeDAO.registerStore(storeEntity);
    return "success";
  }

  @Override
  public List<String> printStore(String country, String city, String dong, String type, int page, int size) {
    logger.info("[StoreService] printStore(가게 목록 출력) 호출");
    String address = country+city+dong+type+page+size;
    Optional<StoreListDTO> storeListCache = storeListRepository.findById(address);
    if(storeListCache.isEmpty() == true) {
      List<String> storeList = storeDAO.printStore(country, city, dong, type, page, size);
      StoreListDTO storeListDTO = new StoreListDTO(address, storeList);
      storeListRepository.save(storeListDTO);
      return storeList;
    }
    logger.info("[StoreService] printStore(가게 목록 출력) 캐시에서 호출");
    return storeListCache.get().getStoreList();
  }

  @Override
  public List<String> loginStore(String storeName, String userId) {
    logger.info("[StoreService] loginStore(가게 권한 반환) 호출");
    String ownerId = storeDAO.loginStore(storeName);
    List<String> result = new ArrayList<>();
    if (ownerId.equals(userId)) {
      result.add("OWNER");
      result.add(JWTutil.createJWT(storeName, "OWNER", secretKey, expiredLoginMs));
      return result;
    }
    result.add("USER");
    return result;
  }

}
