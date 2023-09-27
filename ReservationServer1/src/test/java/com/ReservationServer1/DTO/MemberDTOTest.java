package com.ReservationServer1.DTO;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import com.ReservationServer1.data.DTO.member.MemberDTO;

@ExtendWith(MockitoExtension.class)
public class MemberDTOTest {
  
  
  @Test
  @DisplayName("MemberDTO sample Test")
  void registerMemberSuccess() throws Exception {
     
    MemberDTO sample1 = MemberDTO.sample();
    MemberDTO sample2 = MemberDTO.sample();
    assertTrue(sample1 == sample2);
    assertTrue(sample1.equals(sample2));
  }


  
}
