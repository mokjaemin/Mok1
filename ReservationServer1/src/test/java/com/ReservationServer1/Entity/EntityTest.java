package com.ReservationServer1.Entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import com.ReservationServer1.data.DTO.member.MemberDTO;
import com.ReservationServer1.data.DTO.store.StoreDTO;
import com.ReservationServer1.data.Entity.member.MemberEntity;
import com.ReservationServer1.data.Entity.store.StoreEntity;

@ExtendWith(MockitoExtension.class)
public class EntityTest {


  @Test
  @DisplayName("StoreEntity : toStoreDTO")
  void StoreEntitytoStoreDTOTest() throws Exception {

    StoreEntity entity = StoreEntity.builder().storeName("storeName").ownerId("ownerId")
        .country("country").city("city").dong("dong").type("type").couponInfo("couponInfo").build();

    StoreDTO dto = entity.toStoreDTO();
    assertEquals(dto.getStoreName(), entity.getStoreName());
    assertEquals(dto.getOwnerId(), entity.getOwnerId());
    assertEquals(dto.getCountry(), entity.getCountry());
    assertEquals(dto.getCity(), entity.getCity());
    assertEquals(dto.getDong(), entity.getDong());
    assertEquals(dto.getType(), entity.getType());
    assertEquals(dto.getCouponInfo(), entity.getCouponInfo());
  }


  @Test
  @DisplayName("MemberEntity : toMemberDTO")
  void MemberEntitytoStoreDTOTest() throws Exception {

    MemberEntity entity = MemberEntity.builder().userId("userId").userName("userName")
        .userPwd("userPwd").userNumber("userNumber").userAddress("userAddress").build();

    MemberDTO dto = MemberEntity.toMemberDTO(entity);
    assertEquals(dto.getUserName(), entity.getUserName());
    assertEquals(dto.getUserId(), entity.getUserId());
    assertEquals(dto.getUserPwd(), entity.getUserPwd());
    assertEquals(dto.getUserNumber(), entity.getUserNumber());
    assertEquals(dto.getUserAddress(), entity.getUserAddress());
  }
}
