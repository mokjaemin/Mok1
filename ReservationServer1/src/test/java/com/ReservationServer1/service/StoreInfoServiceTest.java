package com.ReservationServer1.service;


import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import com.ReservationServer1.DAO.StoreInfoDAO;
import com.ReservationServer1.data.DTO.store.StoreFoodsInfoDTO;
import com.ReservationServer1.data.DTO.store.StoreFoodsInfoResultDTO;
import com.ReservationServer1.data.DTO.store.StoreRestDayDTO;
import com.ReservationServer1.data.DTO.store.StoreTableInfoDTO;
import com.ReservationServer1.data.DTO.store.StoreTimeInfoDTO;
import com.ReservationServer1.data.Entity.store.StoreFoodsInfoEntity;
import com.ReservationServer1.data.Entity.store.StoreTableInfoEntity;
import com.ReservationServer1.exception.MessageException;
import com.ReservationServer1.service.Impl.StoreInfoServiceImpl;


@ExtendWith(MockitoExtension.class)
public class StoreInfoServiceTest {


  @InjectMocks
  private StoreInfoServiceImpl storeInfoServiceImpl;

  @Mock
  private StoreInfoDAO storeInfoDAO;


  private static final String DIR_TEST = "/Users/mokjaemin/Desktop/Mok1/test";



  // 1. Register Member
  @Test
  @DisplayName("가게 쉬는날 등록 성공")
  void registerDayOffSuccess() throws Exception {
    // given
    StoreRestDayDTO sample = StoreRestDayDTO.sample();
    String response = "success";
    doReturn(response).when(storeInfoDAO).registerDayOff(any(StoreRestDayDTO.class));

    // when
    String result = storeInfoServiceImpl.registerDayOff(sample);

    // then
    assertEquals(result, response);

    // verify
    verify(storeInfoDAO, times(1)).registerDayOff(any(StoreRestDayDTO.class));
    verify(storeInfoDAO).registerDayOff(sample);
  }

  // 2. Get Day Off
  @Test
  @DisplayName("가게 쉬는날 출력 성공")
  void getDayOffSuccess() throws Exception {
    // given
    int storeId = 1;
    List<String> response = new ArrayList<>();
    response.add("1월1일");
    response.add("1월2일");
    doReturn(response).when(storeInfoDAO).getDayOff(anyInt());

    // when
    List<String> result = storeInfoServiceImpl.getDayOff(storeId);

    // then
    assertEquals(result, response);

    // verify
    verify(storeInfoDAO, times(1)).getDayOff(anyInt());
    verify(storeInfoDAO).getDayOff(storeId);
  }

  // 3. Delete Day Off
  @Test
  @DisplayName("가게 쉬는날 삭제 성공")
  void deleteDayOffSuccess() throws Exception {
    // given
    int storeId = 1;
    String response = "success";
    doReturn(response).when(storeInfoDAO).deleteDayOff(anyInt());

    // when
    String result = storeInfoServiceImpl.deleteDayOff(storeId);

    // then
    assertEquals(result, response);

    // verify
    verify(storeInfoDAO, times(1)).deleteDayOff(anyInt());
    verify(storeInfoDAO).deleteDayOff(storeId);
  }


  // 4. Register Time Info
  @Test
  @DisplayName("가게 시간정보 등록 성공")
  void registerTimeInfoSuccess() throws Exception {
    // given
    StoreTimeInfoDTO sample = StoreTimeInfoDTO.sample();
    String response = "success";
    doReturn(response).when(storeInfoDAO).registerTimeInfo(any(StoreTimeInfoDTO.class));

    // when
    String result = storeInfoServiceImpl.registerTimeInfo(sample);

    // then
    assertEquals(result, response);

    // verify
    verify(storeInfoDAO, times(1)).registerTimeInfo(any(StoreTimeInfoDTO.class));
    verify(storeInfoDAO).registerTimeInfo(sample);
  }

  // 5. Get Time Info
  @Test
  @DisplayName("가게 시간정보 출력 성공")
  void getTimeInfoSuccess() throws Exception {
    // given
    StoreTimeInfoDTO response = StoreTimeInfoDTO.sample();
    int storeId = response.getStoreId();
    doReturn(response).when(storeInfoDAO).getTimeInfo(anyInt());

    // when
    StoreTimeInfoDTO result = storeInfoServiceImpl.getTimeInfo(storeId);

    // then
    assertEquals(result, response);

    // verify
    verify(storeInfoDAO, times(1)).getTimeInfo(anyInt());
    verify(storeInfoDAO).getTimeInfo(storeId);
  }


  // 6. Modify Time Info
  @Test
  @DisplayName("가게 시간정보 등록 성공")
  void modTimeInfoSuccess() throws Exception {
    // given
    StoreTimeInfoDTO sample = StoreTimeInfoDTO.sample();
    String response = "success";
    doReturn(response).when(storeInfoDAO).modTimeInfo(any(StoreTimeInfoDTO.class));

    // when
    String result = storeInfoServiceImpl.modTimeInfo(sample);

    // then
    assertEquals(result, response);

    // verify
    verify(storeInfoDAO, times(1)).modTimeInfo(any(StoreTimeInfoDTO.class));
    verify(storeInfoDAO).modTimeInfo(sample);
  }

  // 7. Delete Time Info
  @Test
  @DisplayName("가게 시간정보 삭제 성공")
  void deleteTimeInfoSuccess() throws Exception {
    // given
    int storeId = 1;
    String response = "success";
    doReturn(response).when(storeInfoDAO).deleteTimeInfo(anyInt());

    // when
    String result = storeInfoServiceImpl.deleteTimeInfo(storeId);

    // then
    assertEquals(result, response);

    // verify
    verify(storeInfoDAO, times(1)).deleteTimeInfo(anyInt());
    verify(storeInfoDAO).deleteTimeInfo(storeId);
  }


  // 8. Register Table Info
  @Test
  @DisplayName("가게 테이블 정보 등록 성공")
  void registerTableInfoSuccess() throws Exception {

    // given
    String response = "success";
    StoreTableInfoDTO sample = StoreTableInfoDTO.sample();
    MockMultipartFile tableImageFile =
        new MockMultipartFile("tableImage", "table-image.png", "image/png", new byte[0]);
    sample.setTableImage(tableImageFile);
    sample.setStoreId(-1);

    Path filePath = Path.of(DIR_TEST, String.valueOf(sample.getStoreId()) + ".png");
    StoreTableInfoEntity entity = StoreTableInfoEntity.builder().storeId(sample.getStoreId())
        .count(sample.getCount()).imageURL(filePath.toString()).build();
    doReturn(response).when(storeInfoDAO).registerTableInfo(entity);

    // when
    storeInfoServiceImpl.setDirTable(DIR_TEST);
    String result = storeInfoServiceImpl.registerTableInfo(sample);

    // then
    assertEquals(result, response);

    // verify
    verify(storeInfoDAO, times(1)).registerTableInfo(any(StoreTableInfoEntity.class));
    verify(storeInfoDAO).registerTableInfo(entity);

    File uploadDir = new File(DIR_TEST);
    assertTrue(uploadDir.exists());
    File[] files = uploadDir.listFiles();
    if (files != null) {
      for (File file : files) {
        file.delete();
      }
    }
    uploadDir.delete();
  }


//  @Test
//  @DisplayName("가게 테이블 정보 등록 실패 : 파일 복사 오류 : 권한 없는 경로")
//  void registerTableInfoFail_FileCopyError() throws Exception {
//    // given
//    String response = "File upload failed";
//    StoreTableInfoDTO sample = StoreTableInfoDTO.sample();
//    MockMultipartFile tableImageFile =
//        new MockMultipartFile("tableImage", "table-image.png", "image/png", new byte[0]);
//    sample.setStoreId(-1);
//    sample.setTableImage(tableImageFile);
//
//    String invalidDir = "/invalid/directory";
//    storeInfoServiceImpl.setDirTable(invalidDir);
//
//    // when
//    String result = storeInfoServiceImpl.registerTableInfo(sample);
//
//    // then
//    assertEquals(response, result);
//
//    // verify
//    verify(storeInfoDAO, never()).registerTableInfo(any(StoreTableInfoEntity.class));
//  }



  // 9. Modify Table Info
  @Test
  @DisplayName("가게 테이블 정보 수정 성공")
  void modTableInfoSuccess() throws Exception {

    // given
    StoreTableInfoDTO sample = StoreTableInfoDTO.sample();
    MockMultipartFile tableImageFile =
        new MockMultipartFile("tableImage", "table-image.png", "image/png", new byte[0]);
    sample.setTableImage(tableImageFile);
    Path filePath = Path.of(DIR_TEST, String.valueOf(sample.getStoreId()) + ".png");
    StoreTableInfoEntity entity = StoreTableInfoEntity.builder().storeId(sample.getStoreId())
        .count(sample.getCount()).imageURL(filePath.toString()).build();



    String response = "success";
    storeInfoServiceImpl.setDirTable(DIR_TEST);
    doReturn(response).when(storeInfoDAO).modTableInfo(entity);



    // when
    String result = storeInfoServiceImpl.modTableInfo(sample);



    // then
    assertEquals(result, response);



    // verify
    verify(storeInfoDAO, times(1)).modTableInfo(any(StoreTableInfoEntity.class));
    verify(storeInfoDAO).modTableInfo(entity);

    // 폴더 생성 여부 체크 및 삭제
    File uploadDir = new File(DIR_TEST);
    assertTrue(uploadDir.exists());
    File[] files = uploadDir.listFiles();
    if (files != null) {
      for (File file : files) {
        file.delete();
      }
    }
    uploadDir.delete();

  }


//  @Test
//  @DisplayName("가게 테이블 정보 수정 실패 : 파일 복사 오류 : 권한 없는 경로")
//  void modTableInfoFail_FileCopyError() throws Exception {
//    // given
//    String response = "File upload failed";
//    StoreTableInfoDTO sample = StoreTableInfoDTO.sample();
//    MockMultipartFile tableImageFile =
//        new MockMultipartFile("tableImage", "table-image.png", "image/png", new byte[0]);
//    sample.setStoreId(-1);
//    sample.setTableImage(tableImageFile);
//
//    String invalidDir = "/invalid/directory";
//    storeInfoServiceImpl.setDirTable(invalidDir);
//
//    // when
//    String result = storeInfoServiceImpl.modTableInfo(sample);
//
//    // then
//    assertEquals(response, result);
//
//    // verify
//    verify(storeInfoDAO, never()).registerTableInfo(any(StoreTableInfoEntity.class));
//  }


  // 10. Delete Table Info
  @Test
  @DisplayName("가게 테이블 정보 삭제 성공")
  void deleteTableInfoSuccess() throws Exception {
    // given

    // 1. 사진 생성
    StoreTableInfoDTO sample = StoreTableInfoDTO.sample();
    MockMultipartFile tableImageFile =
        new MockMultipartFile("tableImage", "table-image.png", "image/png", new byte[0]);
    sample.setStoreId(-1);
    sample.setTableImage(tableImageFile);

    // 2. 폴더 생성 및 저장
    File uploadDir = new File(DIR_TEST);
    if (!uploadDir.exists()) {
      uploadDir.mkdirs();
    }
    int storeId = sample.getStoreId();
    Path filePath = Path.of(DIR_TEST, String.valueOf(storeId) + ".png");
    Files.copy(sample.getTableImage().getInputStream(), filePath,
        StandardCopyOption.REPLACE_EXISTING);


    String response = "success";
    doReturn(response).when(storeInfoDAO).deleteTableInfo(storeId);


    // when - 삭제
    storeInfoServiceImpl.setDirTable(DIR_TEST);
    String result = storeInfoServiceImpl.deleteTableInfo(storeId);

    // then
    assertEquals(result, response);

    // verify
    verify(storeInfoDAO, times(1)).deleteTableInfo(anyInt());
    verify(storeInfoDAO).deleteTableInfo(storeId);

    // 폴더 생성 여부 체크 및 삭제
    File[] files = uploadDir.listFiles();
    if (files != null) {
      for (File file : files) {
        file.delete();
      }
    }
    uploadDir.delete();
  }


  @Test
  @DisplayName("가게 테이블 정보 삭제 실패 : 사진 없음, 잘못된 경로")
  void deleteTableInfoFail() throws Exception {
    // given
    int storeId = StoreTableInfoDTO.sample().getStoreId();
    String response = "File Deleted failed";

    // when - 삭제
    storeInfoServiceImpl.setDirTable(DIR_TEST);
    String result = storeInfoServiceImpl.deleteTableInfo(storeId);

    // then
    assertEquals(result, response);

    // verify
    verify(storeInfoDAO, never()).deleteTableInfo(anyInt());

    // 폴더 생성 여부 체크 및 삭제
    File uploadDir = new File(DIR_TEST);
    File[] files = uploadDir.listFiles();
    if (files != null) {
      for (File file : files) {
        file.delete();
      }
    }
    uploadDir.delete();
  }



  // 11. Register Foods Info
  @Test
  @DisplayName("가게 음식 정보 등록 성공")
  void registerFoodsInfoSuccess() throws Exception {

    // given
    // 사진 생성
    StoreFoodsInfoDTO sample = StoreFoodsInfoDTO.sample();
    MockMultipartFile tableImageFile =
        new MockMultipartFile("tableImage", "table-image.png", "image/png", new byte[0]);
    sample.setFoodImage(tableImageFile);


    String response = "success";
    when(storeInfoDAO.registerFoodsInfo(any(StoreFoodsInfoEntity.class))).thenReturn(response);


    // when
    storeInfoServiceImpl.setDirFoods(DIR_TEST);
    String result = storeInfoServiceImpl.registerFoodsInfo(sample);

    // then
    assertEquals(result, response);

    // verify
    verify(storeInfoDAO, times(1)).registerFoodsInfo(any(StoreFoodsInfoEntity.class));


    // 폴더 생성 여부 체크 및 삭제
    File uploadDir = new File(DIR_TEST);
    File[] files = uploadDir.listFiles();
    if (files != null) {
      for (File file : files) {
        file.delete();
      }
    }
    uploadDir.delete();
  }


//  @Test
//  @DisplayName("가게 음식 정보 등록 실패 : 잘못된 경로")
//  void registerFoodsInfoFail() throws Exception {
//
//    // given
//    // 사진 생성
//    StoreFoodsInfoDTO sample = StoreFoodsInfoDTO.sample();
//    MockMultipartFile tableImageFile =
//        new MockMultipartFile("tableImage", "table-image.png", "image/png", new byte[0]);
//    sample.setFoodImage(tableImageFile);
//    String response = "File Upload Failed";
//
//
//    // when
//    String invalidDir = "/invalid/directory";
//    storeInfoServiceImpl.setDirFoods(invalidDir);
//    String result = storeInfoServiceImpl.registerFoodsInfo(sample);
//
//    // then
//    assertEquals(result, response);
//
//    // verify
//    verify(storeInfoDAO, never()).registerFoodsInfo(any(StoreFoodsInfoEntity.class));
//
//  }



  // 12. Modify Foods Info
  @Test
  @DisplayName("가게 음식 정보 수정 성공")
  void modFoodsInfoSuccess() throws Exception {

    // given
    StoreFoodsInfoDTO sample = StoreFoodsInfoDTO.sample();
    MockMultipartFile tableImageFile =
        new MockMultipartFile("tableImage", "table-image.png", "image/png", new byte[0]);
    sample.setFoodImage(tableImageFile);
    Path filePath = Path.of(DIR_TEST,
        String.valueOf(sample.getStoreId()) + "_" + sample.getFoodName() + ".png");
    StoreFoodsInfoEntity entity = new StoreFoodsInfoEntity(sample);
    entity.setImageURL(filePath.toString());


    String response = "success";
    doReturn(response).when(storeInfoDAO).modFoodsInfo(entity);


    // when
    storeInfoServiceImpl.setDirFoods(DIR_TEST);
    String result = storeInfoServiceImpl.modFoodsInfo(sample);

    // then
    assertEquals(result, response);

    // verify
    verify(storeInfoDAO, times(1)).modFoodsInfo(any(StoreFoodsInfoEntity.class));
    verify(storeInfoDAO).modFoodsInfo(entity);

    // 폴더 생성 여부 체크 및 삭제
    File uploadDir = new File(DIR_TEST);
    File[] files = uploadDir.listFiles();
    if (files != null) {
      for (File file : files) {
        file.delete();
      }
    }
    uploadDir.delete();
  }



//  @Test
//  @DisplayName("가게 음식 정보 수정 실패 : 잘못된 경로")
//  void updateFoodsInfoFail() throws Exception {
//
//    // given
//    // 사진 생성
//    StoreFoodsInfoDTO sample = StoreFoodsInfoDTO.sample();
//    MockMultipartFile tableImageFile =
//        new MockMultipartFile("tableImage", "table-image.png", "image/png", new byte[0]);
//    sample.setFoodImage(tableImageFile);
//    String response = "File Upload Failed";
//
//
//    // when
//    String invalidDir = "/invalid/directory";
//    storeInfoServiceImpl.setDirFoods(invalidDir);
//    String result = storeInfoServiceImpl.modFoodsInfo(sample);
//
//    // then
//    assertEquals(result, response);
//
//    // verify
//    verify(storeInfoDAO, never()).modFoodsInfo(any(StoreFoodsInfoEntity.class));
//
//  }

  // 13. Get Foods Info
  @Test
  @DisplayName("가게 음식 정보 출력 성공")
  void getFoodsInfoSuccess() throws Exception {
    // given
    // 1. 사진 생성
    StoreFoodsInfoDTO sample = StoreFoodsInfoDTO.sample();
    MockMultipartFile foodImageFile =
        new MockMultipartFile("foodImage", "food-image.png", "image/png", new byte[10]);
    sample.setFoodImage(foodImageFile);


    // 2. 폴더 생성 및 저장
    File uploadDir = new File(DIR_TEST);
    if (!uploadDir.exists()) {
      uploadDir.mkdirs();
    }
    int storeId = sample.getStoreId();
    String foodName = sample.getFoodName();
    Path filePath = Path.of(DIR_TEST, String.valueOf(storeId) + "_" + foodName + ".png");
    Files.copy(sample.getFoodImage().getInputStream(), filePath,
        StandardCopyOption.REPLACE_EXISTING);


    // 3. DAO 설정
    List<StoreFoodsInfoEntity> response = new ArrayList<>();
    StoreFoodsInfoEntity entity = new StoreFoodsInfoEntity();
    entity.setImageURL(filePath.toString());
    response.add(entity);
    doReturn(response).when(storeInfoDAO).getFoodsInfo(storeId);


    // when
    storeInfoServiceImpl.setDirFoods(DIR_TEST);
    List<StoreFoodsInfoResultDTO> result = storeInfoServiceImpl.getFoodsInfo(storeId);


    // then
    assertThat(result.size(), is(1));


    // 폴더 생성 여부 체크 및 삭제
    File[] files = uploadDir.listFiles();
    if (files != null) {
      for (File file : files) {
        file.delete();
      }
    }
    uploadDir.delete();
  }



  @Test
  @DisplayName("가게 음식 정보 출력 실패 : 잘못된 사진")
  void getFoodsInfoFail() throws Exception {

    // given
    // 1. 사진 생성
    StoreFoodsInfoDTO sample = StoreFoodsInfoDTO.sample();
    MockMultipartFile tableImageFile =
        new MockMultipartFile("tableImage", "table-image.png", "image/png", new byte[0]);
    sample.setFoodImage(tableImageFile);


    // 2. 폴더 생성 및 저장
    File uploadDir = new File(DIR_TEST);
    if (!uploadDir.exists()) {
      uploadDir.mkdirs();
    }
    int storeId = sample.getStoreId();
    Path filePath = Path.of(DIR_TEST, String.valueOf(storeId) + ".png");
    Files.copy(sample.getFoodImage().getInputStream(), filePath,
        StandardCopyOption.REPLACE_EXISTING);


    // 3. DAO 설정
    List<StoreFoodsInfoEntity> response = new ArrayList<>();
    StoreFoodsInfoEntity entity = new StoreFoodsInfoEntity();
    entity.setImageURL(DIR_TEST + String.valueOf(storeId) + "_" + sample.getFoodName() + ".png");
    response.add(entity);
    doReturn(response).when(storeInfoDAO).getFoodsInfo(storeId);



    // when
    storeInfoServiceImpl.setDirFoods(DIR_TEST);

    // then && when
    MessageException message = assertThrows(MessageException.class, () -> {
      storeInfoServiceImpl.getFoodsInfo(storeId);
    });
    String expected = "해당 음식점 사진에 문제가 존재합니다.";

    assertEquals(expected, message.getMessage());


  }


  // 14. Delete Foods Info
  @Test
  @DisplayName("가게 테이블 정보 삭제 성공")
  void deleteFoodsInfoSuccess() throws Exception {
    // given
    // 1. 사진 생성
    StoreFoodsInfoDTO sample = StoreFoodsInfoDTO.sample();
    MockMultipartFile tableImageFile =
        new MockMultipartFile("tableImage", "table-image.png", "image/png", new byte[0]);
    sample.setFoodImage(tableImageFile);


    // 2. 폴더 생성 및 저장
    File uploadDir = new File(DIR_TEST);
    if (!uploadDir.exists()) {
      uploadDir.mkdirs();
    }
    int storeId = sample.getStoreId();
    String foodName = sample.getFoodName();
    Path filePath = Path.of(DIR_TEST, String.valueOf(storeId) + "_" + foodName + ".png");
    Files.copy(sample.getFoodImage().getInputStream(), filePath,
        StandardCopyOption.REPLACE_EXISTING);


    String response = "success";
    doReturn(response).when(storeInfoDAO).deleteFoodsInfo(storeId, foodName);


    // when
    storeInfoServiceImpl.setDirFoods(DIR_TEST);
    String result = storeInfoServiceImpl.deleteFoodsInfo(storeId, foodName);


    // then
    assertEquals(result, response);

    // verify
    verify(storeInfoDAO, times(1)).deleteFoodsInfo(anyInt(), anyString());
    verify(storeInfoDAO).deleteFoodsInfo(storeId, foodName);


    // 폴더 삭제
    File[] files = uploadDir.listFiles();
    if (files != null) {
      for (File file : files) {
        file.delete();
      }
    }
    uploadDir.delete();
  }


  @Test
  @DisplayName("가게 테이블 정보 삭제 실패 : 잘못된 경로")
  void deleteFoodsInfoFail() throws Exception {
    // given
    String response = "File Delete Failed";


    // when
    storeInfoServiceImpl.setDirFoods("invalid route");
    String result = storeInfoServiceImpl.deleteFoodsInfo(0, "");


    // then
    assertEquals(result, response);

    // verify
    verify(storeInfoDAO, times(0)).deleteFoodsInfo(anyInt(), anyString());
  }

}
