package com.ReservationServer1.service.UnitTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import com.ReservationServer1.DAO.MemberDAO;
import com.ReservationServer1.data.DTO.MemberDTO;
import com.ReservationServer1.data.Entity.MemberEntity;
import com.ReservationServer1.service.Impl.MemberServiceImpl;



@ExtendWith(MockitoExtension.class)
public class MemberServiceUnitTest {
	
	
	
	  @InjectMocks
	  private MemberServiceImpl memberService;
	
	  @Mock
	  private MemberDAO memberDAO;
	  
	  @Test
	  @DisplayName("회원가입 테스트")
	  public void saveProductTest() {
		  
		    
		  //given
		  MemberDTO sampleDTO = MemberDTO.sample();
		  MemberEntity sampleEntity = new MemberEntity(sampleDTO);
		  Mockito.when(memberDAO.create(any(MemberEntity.class))).thenReturn(sampleEntity);

		  // when
		  MemberDTO testDTO = memberService.registerMember(sampleDTO);
		    
		  // then
		  Assertions.assertEquals(testDTO.getUserId(), sampleDTO.getUserId());
		  Assertions.assertEquals(testDTO.getUserPwd(), sampleDTO.getUserPwd());
		  Assertions.assertEquals(testDTO.getUserName(), sampleDTO.getUserName());
		  Assertions.assertEquals(testDTO.getUserEmail(), sampleDTO.getUserEmail());
		  Assertions.assertEquals(testDTO.getUserAddress(), sampleDTO.getUserAddress());
		  Assertions.assertEquals(testDTO.getUserNumber(), sampleDTO.getUserNumber());
		  verify(memberDAO).create(any(MemberEntity.class));
	  }
	  
	  
}
