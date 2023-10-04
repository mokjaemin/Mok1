package com.ReservationServer1.DTO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.ReservationServer1.data.DTO.member.MemberDTO;

public class CompareToTest {
  
  
  @Test
  @DisplayName("MemberDTO")
  void MemberDTOSample() throws Exception {
    MemberDTO sample1 = MemberDTO.sample();
    MemberDTO sample2 = new MemberDTO(sample1);
    sample2.setUserId("id2");
    List<MemberDTO> list = new ArrayList<>();
    list.add(sample1);
    list.add(sample2);
    Collections.sort(list);
    System.out.println(list);
  }

}
