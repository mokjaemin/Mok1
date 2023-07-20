package com.ReservationServer1.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.HashMap;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.ReservationServer1.DAO.StoreDAO;
import com.ReservationServer1.DAO.Cache.StoreListCache;
import com.ReservationServer1.data.DTO.store.StoreDTO;
import com.ReservationServer1.data.DTO.store.cache.StoreListDTO;
import com.ReservationServer1.data.Entity.store.StoreEntity;
import com.ReservationServer1.service.Impl.StoreServiceImpl;
import com.ReservationServer1.utils.JWTutil;

@ExtendWith(MockitoExtension.class)
public class StoreServiceTest {

  @InjectMocks
  private StoreServiceImpl storeServiceImpl;

  @Mock
  private StoreDAO storeDAO;

  @Mock
  private StoreListCache storeListCache;
  
  @Mock
  private JWTutil jwtUtil;

  private final String secretKey = "test";

  @BeforeEach
  void setup() {
    storeServiceImpl.setTestSecretKey(secretKey);
  }


  // 1. Register Member
  @Test
  @DisplayName("가게 등록 성공")
  void registerStoreSuccess() throws Exception {
    // given
    StoreDTO sample = StoreDTO.sample();
    String response = "success";
    doReturn(response).when(storeDAO).registerStore(any(StoreEntity.class));

    // when
    String result = storeServiceImpl.registerStore(sample);

    // then
    assertThat(result.equals(response));

    // verify
    verify(storeDAO, times(1)).registerStore(any(StoreEntity.class));
    verify(storeDAO).registerStore(eq(new StoreEntity(sample)));
  }

  @Test
  @DisplayName("가게 리스트 출력 : 캐시 미스")
  public void testGetStoreList_WithCacheMiss() {
    // given
    String country = "Country";
    String city = "City";
    String dong = "Dong";
    String type = "Type";
    int page = 1;
    int size = 10;
    String address = country + city + dong + type + page + size;
    HashMap<String, Integer> mockStoreList = new HashMap<>();
    mockStoreList.put("Store1", 10);
    mockStoreList.put("Store2", 20);
    when(storeListCache.findById(anyString())).thenReturn(Optional.empty());
    when(storeDAO.getStoreList(anyString(), anyString(), anyString(), anyString(), anyInt(),
        anyInt())).thenReturn(mockStoreList);



    // when
    HashMap<String, Integer> result =
        storeServiceImpl.getStoreList(country, city, dong, type, page, size);


    // then
    assertEquals(mockStoreList, result);


    // verify (올바른 인자로 입력되었는지 확인)
    verify(storeListCache).save(argThat(storeListDTO -> storeListDTO.getAddress().equals(address)
        && storeListDTO.getStoreList().equals(mockStoreList)));
    verify(storeDAO).getStoreList(eq(country), eq(city), eq(dong), eq(type), eq(page), eq(size));
  }

  @Test
  @DisplayName("가게 리스트 출력 : 캐시 히트")
  public void testGetStoreList_WithCacheHit() {

    // given
    String country = "Country";
    String city = "City";
    String dong = "Dong";
    String type = "Type";
    int page = 1;
    int size = 10;
    String address = country + city + dong + type + page + size;
    HashMap<String, Integer> mockStoreList = new HashMap<>();
    mockStoreList.put("Store1", 10);
    mockStoreList.put("Store2", 20);
    StoreListDTO mockStoreListDTO = new StoreListDTO(address, mockStoreList);
    when(storeListCache.findById(anyString())).thenReturn(Optional.of(mockStoreListDTO));



    // when
    HashMap<String, Integer> result =
        storeServiceImpl.getStoreList(country, city, dong, type, page, size);


    // then
    assertEquals(mockStoreList, result);


    // verify 호출 되지 않았는지 확인
    verify(storeListCache, never()).save(any(StoreListDTO.class));
    verify(storeDAO, never()).getStoreList(anyString(), anyString(), anyString(), anyString(),
        anyInt(), anyInt());
  }

  @Test
  @DisplayName("가게 로그인 성공 : 권한 토큰 반환")
  public void loginStoreTestSuccess() {
    // given
    String userId = "testId";
    doReturn(userId).when(storeDAO).loginStore(anyInt());
    
    // when
    String result = storeServiceImpl.loginStore(0, userId);
    
    // then
    String resultId = JWTutil.getUserId(result, secretKey);
    String userRole = JWTutil.getUserRole(result, secretKey);
    assertThat(userId.equals(resultId));
    assertThat(userRole.equals("OWNER"));
    
    // verify 
    verify(storeDAO, times(1)).loginStore(anyInt());
    verify(storeDAO).loginStore(eq(0));
  }
  
  
  @Test
  @DisplayName("가게 로그인 실패")
  public void loginStoreTestFail() {
    // given
    String userId = "testId";
    String wrongId = "wrongId";
    doReturn(wrongId).when(storeDAO).loginStore(anyInt());
    
    // when
    String result = storeServiceImpl.loginStore(0, userId);
    
    // then
    assertEquals(result, "user");
    
    // verify 
    verify(storeDAO, times(1)).loginStore(anyInt());
    verify(storeDAO).loginStore(0);
  }

}
