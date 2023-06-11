package com.ReservationServer1.service.Impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import org.springframework.stereotype.Service;
import com.ReservationServer1.DAO.StoreInfoDAO;
import com.ReservationServer1.data.DTO.store.StoreRestDayDTO;
import com.ReservationServer1.data.DTO.store.StoreTableInfoDTO;
import com.ReservationServer1.data.DTO.store.StoreTimeInfoDTO;
import com.ReservationServer1.data.Entity.store.StoreTableInfoEntity;
import com.ReservationServer1.service.StoreInfoService;


@Service
public class StoreInfoServiceImpl implements StoreInfoService {

  private static final String DIR = "/Users/mokjaemin/Desktop/Mok1/storeTable";
  private final StoreInfoDAO storeInfoDAO;

  public StoreInfoServiceImpl(StoreInfoDAO ownerDAO) {
    this.storeInfoDAO = ownerDAO;
  }

  @Override
  public String registerDayOff(StoreRestDayDTO restDayDTO) {
    return storeInfoDAO.registerDayOff(restDayDTO);
  }

  @Override
  public List<String> getDayOff(String storeName) {
    return storeInfoDAO.getDayOff(storeName);
  }

  @Override
  public String deleteDayOff(StoreRestDayDTO restDayDTO) {
    return storeInfoDAO.deleteDayOff(restDayDTO);
  }

  @Override
  public String registerTimeInfo(StoreTimeInfoDTO timeInfoDTO) {
    return storeInfoDAO.registerTimeInfo(timeInfoDTO);
  }

  @Override
  public StoreTimeInfoDTO getTimeInfo(String storeName) {
    return storeInfoDAO.getTimeInfo(storeName);
  }

  @Override
  public String modTimeInfo(StoreTimeInfoDTO timeInfoDTO) {
    return storeInfoDAO.modTimeInfo(timeInfoDTO);
  }

  @Override
  public String deleteTimeInfo(String storeName) {
    return storeInfoDAO.deleteTimeInfo(storeName);
  }

  @Override
  public String registerTableInfo(StoreTableInfoDTO storeTableInfoDTO) {
    try {
      File uploadDir = new File(DIR);
      if (!uploadDir.exists()) {
        uploadDir.mkdirs();
      }
      String filename = storeTableInfoDTO.getStoreName();
      Path filePath = Path.of(DIR, filename+".png");
      Files.copy(storeTableInfoDTO.getTableImage().getInputStream(), filePath,
          StandardCopyOption.REPLACE_EXISTING);
      StoreTableInfoEntity storeTableInfoEntity = StoreTableInfoEntity.builder()
          .storeName(storeTableInfoDTO.getStoreName())
          .count(storeTableInfoDTO.getCount())
          .imageURL(filePath.toString()).build();
      return storeInfoDAO.registerTableInfo(storeTableInfoEntity);
    } catch (IOException e) {
      return "File upload failed";
    }
  }
}
