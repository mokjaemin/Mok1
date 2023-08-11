package com.ReservationServer1.DTO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import com.ReservationServer1.data.DTO.store.cache.StoreListDTO;


@ExtendWith(MockitoExtension.class)
public class DTOTest {


  @Test
  @DisplayName("가게 목록 샘플 테스트")
  void StoreListDTOSampleTest() throws Exception {
    // given
    StoreListDTO sample = StoreListDTO.sample();


    // then
    assertEquals(sample.getAddress(), "testAddress");
  }
}
