package com.ReservationServer1.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Base64;
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
import com.ReservationServer1.service.Impl.StoreInfoServiceImpl;


@ExtendWith(MockitoExtension.class)
public class StoreInfoServiceTest {


  @InjectMocks
  private StoreInfoServiceImpl storeInfoServiceImpl;

  @Mock
  private StoreInfoDAO storeInfoDAO;


  private static final String DIR_TABLE = "/Users/mokjaemin/Desktop/Mok1/storeTable";
  private static final String DIR_FOODS = "/Users/mokjaemin/Desktop/Mok1/storeFoods";


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
    assertThat(result.equals(response));

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
    assertThat(result.equals(response));

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
    assertThat(result.equals(response));

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
    assertThat(result.equals(response));

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
    assertThat(result.equals(response));

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
    assertThat(result.equals(response));

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
    assertThat(result.equals(response));

    // verify
    verify(storeInfoDAO, times(1)).deleteTimeInfo(anyInt());
    verify(storeInfoDAO).deleteTimeInfo(storeId);
  }


  // 8. Register Table Info
  @Test
  @DisplayName("가게 테이블 정보 등록 성공")
  void registerTableInfoSuccess() throws Exception {

    // given
    StoreTableInfoDTO sample = StoreTableInfoDTO.sample();
    MockMultipartFile tableImageFile =
        new MockMultipartFile("tableImage", "table-image.png", "image/png", new byte[0]);
    sample.setTableImage(tableImageFile);
    Path filePath = Path.of(DIR_TABLE, String.valueOf(sample.getStoreId()) + ".png");
    StoreTableInfoEntity entity = StoreTableInfoEntity.builder().storeId(sample.getStoreId())
        .count(sample.getCount()).imageURL(filePath.toString()).build();


    String response = "success";
    doReturn(response).when(storeInfoDAO).registerTableInfo(entity);


    // when
    String result = storeInfoServiceImpl.registerTableInfo(sample);

    // then
    assertThat(result.equals(response));

    // verify
    verify(storeInfoDAO, times(1)).registerTableInfo(any(StoreTableInfoEntity.class));
    verify(storeInfoDAO).registerTableInfo(entity);
  }


  // 9. Modify Table Info
  @Test
  @DisplayName("가게 테이블 정보 수정 성공")
  void modTableInfoSuccess() throws Exception {

    // given
    StoreTableInfoDTO sample = StoreTableInfoDTO.sample();
    MockMultipartFile tableImageFile =
        new MockMultipartFile("tableImage", "table-image.png", "image/png", new byte[0]);
    sample.setTableImage(tableImageFile);

    Path filePath = Path.of(DIR_TABLE, String.valueOf(sample.getStoreId()) + ".png");
    StoreTableInfoEntity entity = StoreTableInfoEntity.builder().storeId(sample.getStoreId())
        .count(sample.getCount()).imageURL(filePath.toString()).build();


    String response = "success";
    doReturn(response).when(storeInfoDAO).modTableInfo(entity);

    // when
    String result = storeInfoServiceImpl.modTableInfo(sample);

    // then
    assertThat(result.equals(response));

    // verify
    verify(storeInfoDAO, times(1)).modTableInfo(any(StoreTableInfoEntity.class));
    verify(storeInfoDAO).modTableInfo(entity);


  }

  // 10. Delete Table Info
  @Test
  @DisplayName("가게 테이블 정보 삭제 성공")
  void deleteTableInfoSuccess() throws Exception {
    // given
    int storeId = StoreTableInfoDTO.sample().getStoreId();
    String response = "success";
    doReturn(response).when(storeInfoDAO).deleteTableInfo(storeId);

    // when
    String result = storeInfoServiceImpl.deleteTableInfo(storeId);

    // then
    assertThat(result.equals(response));

    // verify
    verify(storeInfoDAO, times(1)).deleteTableInfo(anyInt());
    verify(storeInfoDAO).deleteTableInfo(storeId);
  }


  // 11. Register Foods Info
  @Test
  @DisplayName("가게 음식 정보 등록 성공")
  void registerFoodsInfoSuccess() throws Exception {

    // given
    StoreFoodsInfoDTO sample = StoreFoodsInfoDTO.sample();
    MockMultipartFile tableImageFile =
        new MockMultipartFile("tableImage", "table-image.png", "image/png", new byte[0]);
    sample.setFoodImage(tableImageFile);
    Path filePath = Path.of(DIR_FOODS,
        String.valueOf(sample.getStoreId()) + "_" + sample.getFoodName() + ".png");
    StoreFoodsInfoEntity entity = new StoreFoodsInfoEntity(sample);
    entity.setImageURL(filePath.toString());


    String response = "success";
    when(storeInfoDAO.registerFoodsInfo(any(StoreFoodsInfoEntity.class))).thenReturn(response);


    // when
    String result = storeInfoServiceImpl.registerFoodsInfo(sample);

    // then
    assertThat(result.equals(response));

    // verify
    verify(storeInfoDAO, times(1)).registerFoodsInfo(any(StoreFoodsInfoEntity.class));
    verify(storeInfoDAO).registerFoodsInfo(entity);
  }


  // 12. Modify Foods Info
  @Test
  @DisplayName("가게 음식 정보 수정 성공")
  void modFoodsInfoSuccess() throws Exception {

    // given
    StoreFoodsInfoDTO sample = StoreFoodsInfoDTO.sample();
    MockMultipartFile tableImageFile =
        new MockMultipartFile("tableImage", "table-image.png", "image/png", new byte[0]);
    sample.setFoodImage(tableImageFile);
    Path filePath = Path.of(DIR_FOODS,
        String.valueOf(sample.getStoreId()) + "_" + sample.getFoodName() + ".png");
    StoreFoodsInfoEntity entity = new StoreFoodsInfoEntity(sample);
    entity.setImageURL(filePath.toString());


    String response = "success";
    doReturn(response).when(storeInfoDAO).modFoodsInfo(entity);


    // when
    String result = storeInfoServiceImpl.modFoodsInfo(sample);

    // then
    assertThat(result.equals(response));

    // verify
    verify(storeInfoDAO, times(1)).modFoodsInfo(any(StoreFoodsInfoEntity.class));
    verify(storeInfoDAO).modFoodsInfo(entity);
  }


  // 13. Get Foods Info
  @Test
  @DisplayName("가게 음식 정보 수정 성공")
  void getFoodsInfoSuccess() throws Exception {

    // given
    List<StoreFoodsInfoResultDTO> result = new ArrayList<>();
    List<StoreFoodsInfoEntity> storeFoodsInfoEntity = new ArrayList<>();
    StoreFoodsInfoDTO sample = StoreFoodsInfoDTO.sample();
    StoreFoodsInfoEntity entity = new StoreFoodsInfoEntity(sample);
    Path filePath = Path.of(DIR_FOODS,
        String.valueOf(sample.getStoreId()) + "_" + sample.getFoodName() + ".png");
    entity.setImageURL(filePath.toString());
    storeFoodsInfoEntity.add(entity);
    doReturn(storeFoodsInfoEntity).when(storeInfoDAO).getFoodsInfo(sample.getStoreId());
    for (StoreFoodsInfoEntity now : storeFoodsInfoEntity) {
      Path now_filePath = Path.of(now.getImageURL());
      byte[] imageBytes = Files.readAllBytes(now_filePath);
      String encoded_image = Base64.getEncoder().encodeToString(imageBytes);
      StoreFoodsInfoResultDTO dto = entity.toStoreFoodsInfoResultDTO();
      dto.setEncoded_image(encoded_image);
      result.add(dto);
    }


    // when
    List<StoreFoodsInfoResultDTO> response = storeInfoServiceImpl.getFoodsInfo(sample.getStoreId());
    
    //then
    assertThat(result.equals(response));

    // verify
    verify(storeInfoDAO, times(1)).getFoodsInfo(anyInt());
    verify(storeInfoDAO).getFoodsInfo(sample.getStoreId());

  }



  // 14. Delete Foods Info
  @Test
  @DisplayName("가게 테이블 정보 삭제 성공")
  void deleteFoodsInfoSuccess() throws Exception {
    // given
    int storeId = StoreFoodsInfoDTO.sample().getStoreId();
    String foodName = StoreFoodsInfoDTO.sample().getFoodName();
    String response = "success";
    doReturn(response).when(storeInfoDAO).deleteFoodsInfo(storeId, foodName);

    // when
    String result = storeInfoServiceImpl.deleteFoodsInfo(storeId, foodName);

    // then
    assertThat(result.equals(response));

    // verify
    verify(storeInfoDAO, times(1)).deleteFoodsInfo(anyInt(), anyString());
    verify(storeInfoDAO).deleteFoodsInfo(storeId, foodName);
  }

}
