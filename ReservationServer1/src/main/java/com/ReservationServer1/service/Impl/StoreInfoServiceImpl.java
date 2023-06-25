package com.ReservationServer1.service.Impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import org.springframework.stereotype.Service;
import com.ReservationServer1.DAO.StoreInfoDAO;
import com.ReservationServer1.data.DTO.store.StoreFoodsInfoDTO;
import com.ReservationServer1.data.DTO.store.StoreFoodsInfoResultDTO;
import com.ReservationServer1.data.DTO.store.StoreRestDayDTO;
import com.ReservationServer1.data.DTO.store.StoreTableInfoDTO;
import com.ReservationServer1.data.DTO.store.StoreTimeInfoDTO;
import com.ReservationServer1.data.Entity.store.StoreFoodsInfoEntity;
import com.ReservationServer1.data.Entity.store.StoreTableInfoEntity;
import com.ReservationServer1.exception.MessageException;
import com.ReservationServer1.service.StoreInfoService;


@Service
public class StoreInfoServiceImpl implements StoreInfoService {

  private static final String DIR_TABLE = "/Users/mokjaemin/Desktop/Mok1/storeTable";
  private static final String DIR_FOODS = "/Users/mokjaemin/Desktop/Mok1/storeFoods";
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
      File uploadDir = new File(DIR_TABLE);
      if (!uploadDir.exists()) {
        uploadDir.mkdirs();
      }
      String filename = storeTableInfoDTO.getStoreName();
      Path filePath = Path.of(DIR_TABLE, filename + ".png");
      Files.copy(storeTableInfoDTO.getTableImage().getInputStream(), filePath,
          StandardCopyOption.REPLACE_EXISTING);
      StoreTableInfoEntity storeTableInfoEntity =
          StoreTableInfoEntity.builder().storeName(storeTableInfoDTO.getStoreName())
              .count(storeTableInfoDTO.getCount()).imageURL(filePath.toString()).build();
      return storeInfoDAO.registerTableInfo(storeTableInfoEntity);
    } catch (IOException e) {
      return "File upload failed";
    }
  }

  @Override
  public String modTableInfo(StoreTableInfoDTO storeTableInfoDTO) {
    try {
      File uploadDir = new File(DIR_TABLE);
      if (!uploadDir.exists()) {
        uploadDir.mkdirs();
      }
      String filename = storeTableInfoDTO.getStoreName();
      Path filePath = Path.of(DIR_TABLE, filename + ".png");
      Files.copy(storeTableInfoDTO.getTableImage().getInputStream(), filePath,
          StandardCopyOption.REPLACE_EXISTING);
      StoreTableInfoEntity storeTableInfoEntity =
          StoreTableInfoEntity.builder().storeName(storeTableInfoDTO.getStoreName())
              .count(storeTableInfoDTO.getCount()).imageURL(filePath.toString()).build();
      return storeInfoDAO.modTableInfo(storeTableInfoEntity);
    } catch (IOException e) {
      return "File Upload Failed";
    }
  }

  @Override
  public String deleteTableInfo(String storeName) {
    try {
      Path filePath = Path.of(DIR_TABLE, storeName + ".png");
      Files.delete(filePath);
      return storeInfoDAO.deleteTableInfo(storeName);
    } catch (IOException e) {
      return "File Delete Failed";
    }
  }

  @Override
  public String registerFoodsInfo(StoreFoodsInfoDTO storeFoodsInfoDTO) {
    try {
      File uploadDir = new File(DIR_FOODS);
      if (!uploadDir.exists()) {
        uploadDir.mkdirs();
      }
      String filename = storeFoodsInfoDTO.getStoreName();
      String foodName = storeFoodsInfoDTO.getFoodName();
      Path filePath = Path.of(DIR_FOODS, filename + "_" + foodName + ".png");
      Files.copy(storeFoodsInfoDTO.getFoodImage().getInputStream(), filePath,
          StandardCopyOption.REPLACE_EXISTING);
      StoreFoodsInfoEntity storeFoodsInfoEntity = StoreFoodsInfoEntity.builder()
          .storeName(storeFoodsInfoDTO.getStoreName()).foodName(storeFoodsInfoDTO.getFoodName())
          .foodDescription(storeFoodsInfoDTO.getFoodDescription()).imageURL(filePath.toString())
          .build();
      return storeInfoDAO.registerFoodsInfo(storeFoodsInfoEntity);
    } catch (IOException e) {
      return "File Upload Failed";
    }
  }

  @Override
  public List<StoreFoodsInfoResultDTO> getFoodsInfo(String storeName) {
    try {
      List<StoreFoodsInfoResultDTO> result = new ArrayList<>();
      List<StoreFoodsInfoEntity> storeFoodsInfoEntity = storeInfoDAO.getFoodsInfo(storeName);
      for (StoreFoodsInfoEntity entity : storeFoodsInfoEntity) {
        Path filePath = Path.of(entity.getImageURL());
        byte[] imageBytes = Files.readAllBytes(filePath);
        String encoded_image = Base64.getEncoder().encodeToString(imageBytes);
        StoreFoodsInfoResultDTO dto = entity.toStoreFoodsInfoResultDTO();
        dto.setEncoded_image(encoded_image);
        result.add(dto);
      }
      return result;
    } catch (IOException e) {
      throw new MessageException("해당 음식점 사진에 문제가 존재합니다.");
    }
  }

  @Override
  public String modFoodsInfo(StoreFoodsInfoDTO storeFoodsInfoDTO) {
    try {
      File uploadDir = new File(DIR_FOODS);
      if (!uploadDir.exists()) {
        uploadDir.mkdirs();
      }
      String filename = storeFoodsInfoDTO.getStoreName();
      String foodName = storeFoodsInfoDTO.getFoodName();
      Path filePath = Path.of(DIR_FOODS, filename + "_" + foodName + ".png");
      Files.copy(storeFoodsInfoDTO.getFoodImage().getInputStream(), filePath,
          StandardCopyOption.REPLACE_EXISTING);
      StoreFoodsInfoEntity storeFoodsInfoEntity = StoreFoodsInfoEntity.builder()
          .storeName(storeFoodsInfoDTO.getStoreName()).foodName(storeFoodsInfoDTO.getFoodName())
          .foodDescription(storeFoodsInfoDTO.getFoodDescription()).imageURL(filePath.toString())
          .build();
      return storeInfoDAO.modFoodsInfo(storeFoodsInfoEntity);
    } catch (IOException e) {
      return "File Upload Failed";
    }
  }

  @Override
  public String deleteFoodsInfo(String storeName, String foodName) {
    try {
      Path filePath = Path.of(DIR_FOODS, storeName + "_" + foodName + ".png");
      Files.delete(filePath);
      return storeInfoDAO.deleteFoodsInfo(storeName, foodName);
    } catch (IOException e) {
      return "File Delete Failed";
    }
  }
}
