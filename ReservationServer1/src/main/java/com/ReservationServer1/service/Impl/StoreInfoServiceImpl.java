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


  public StoreInfoServiceImpl(StoreInfoDAO storeInfoDAO) {
    this.storeInfoDAO = storeInfoDAO;
  }

  @Override
  public String registerDayOff(StoreRestDayDTO restDayDTO) {
    return storeInfoDAO.registerDayOff(restDayDTO);
  }

  @Override
  public List<String> getDayOff(int storeId) {
    return storeInfoDAO.getDayOff(storeId);
  }

  //
  @Override
  public String deleteDayOff(int storeId) {
    return storeInfoDAO.deleteDayOff(storeId);
  }

  @Override
  public String registerTimeInfo(StoreTimeInfoDTO timeInfoDTO) {
    return storeInfoDAO.registerTimeInfo(timeInfoDTO);
  }

  @Override
  public StoreTimeInfoDTO getTimeInfo(int storeId) {
    return storeInfoDAO.getTimeInfo(storeId);
  }

  @Override
  public String modTimeInfo(StoreTimeInfoDTO timeInfoDTO) {
    return storeInfoDAO.modTimeInfo(timeInfoDTO);
  }

  @Override
  public String deleteTimeInfo(int storeId) {
    return storeInfoDAO.deleteTimeInfo(storeId);
  }


  @Override
  public String registerTableInfo(StoreTableInfoDTO storeTableInfoDTO) {
    try {
      File uploadDir = new File(DIR_TABLE);
      if (!uploadDir.exists()) {
        uploadDir.mkdirs();
      }
      int storeId = storeTableInfoDTO.getStoreId();
      Path filePath = Path.of(DIR_TABLE, String.valueOf(storeId) + ".png");
      Files.copy(storeTableInfoDTO.getTableImage().getInputStream(), filePath,
          StandardCopyOption.REPLACE_EXISTING);
      StoreTableInfoEntity storeTableInfoEntity =
          StoreTableInfoEntity.builder().storeId(storeTableInfoDTO.getStoreId())
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
      int storeId = storeTableInfoDTO.getStoreId();
      Path filePath = Path.of(DIR_TABLE, String.valueOf(storeId) + ".png");
      Files.copy(storeTableInfoDTO.getTableImage().getInputStream(), filePath,
          StandardCopyOption.REPLACE_EXISTING);
      StoreTableInfoEntity storeTableInfoEntity =
          StoreTableInfoEntity.builder().storeId(storeTableInfoDTO.getStoreId())
              .count(storeTableInfoDTO.getCount()).imageURL(filePath.toString()).build();
      return storeInfoDAO.modTableInfo(storeTableInfoEntity);
    } catch (IOException e) {
      return "File Upload Failed";
    }
  }


  @Override
  public String deleteTableInfo(int storeId) {
    try {
      Path filePath = Path.of(DIR_TABLE, String.valueOf(storeId) + ".png");
      Files.delete(filePath);
      return storeInfoDAO.deleteTableInfo(storeId);
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
      int storeId = storeFoodsInfoDTO.getStoreId();
      String foodName = storeFoodsInfoDTO.getFoodName();
      Path filePath = Path.of(DIR_FOODS, String.valueOf(storeId) + "_" + foodName + ".png");
      Files.copy(storeFoodsInfoDTO.getFoodImage().getInputStream(), filePath,
          StandardCopyOption.REPLACE_EXISTING);
      StoreFoodsInfoEntity storeFoodsInfoEntity = StoreFoodsInfoEntity.builder()
          .storeId(storeFoodsInfoDTO.getStoreId()).foodName(storeFoodsInfoDTO.getFoodName())
          .foodDescription(storeFoodsInfoDTO.getFoodDescription()).imageURL(filePath.toString())
          .foodPrice(storeFoodsInfoDTO.getFoodPrice()).build();
      return storeInfoDAO.registerFoodsInfo(storeFoodsInfoEntity);
    } catch (IOException e) {
      return "File Upload Failed";
    }
  }

  @Override
  public List<StoreFoodsInfoResultDTO> getFoodsInfo(int storeId) {
    try {
      List<StoreFoodsInfoResultDTO> result = new ArrayList<>();
      List<StoreFoodsInfoEntity> storeFoodsInfoEntity = storeInfoDAO.getFoodsInfo(storeId);
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
      int storeId = storeFoodsInfoDTO.getStoreId();
      String foodName = storeFoodsInfoDTO.getFoodName();
      Path filePath = Path.of(DIR_FOODS, String.valueOf(storeId) + "_" + foodName + ".png");
      Files.copy(storeFoodsInfoDTO.getFoodImage().getInputStream(), filePath,
          StandardCopyOption.REPLACE_EXISTING);
      StoreFoodsInfoEntity storeFoodsInfoEntity = StoreFoodsInfoEntity.builder()
          .storeId(storeFoodsInfoDTO.getStoreId()).foodName(storeFoodsInfoDTO.getFoodName())
          .foodDescription(storeFoodsInfoDTO.getFoodDescription()).imageURL(filePath.toString())
          .foodPrice(storeFoodsInfoDTO.getFoodPrice()).build();
      return storeInfoDAO.modFoodsInfo(storeFoodsInfoEntity);
    } catch (IOException e) {
      return "File Upload Failed";
    }
  }

  @Override
  public String deleteFoodsInfo(int storeId, String foodName) {
    try {
      Path filePath = Path.of(DIR_FOODS, String.valueOf(storeId) + "_" + foodName + ".png");
      Files.delete(filePath);
      return storeInfoDAO.deleteFoodsInfo(storeId, foodName);
    } catch (IOException e) {
      return "File Delete Failed";
    }
  }
}
