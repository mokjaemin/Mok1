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
		  MemberDTO sample = MemberDTO.sample();
		  Mockito.when(memberDAO.existsById(sample.getUserId())).thenReturn(false);
		  Mockito.when(memberDAO.create(any(MemberEntity.class))).thenReturn(any(MemberEntity.class));


		    
		  // when
		  MemberDTO testDTO = memberService.registerMember(sample);
	
		    
		  // then
		  Assertions.assertEquals(testDTO.getUserId(), sample.getUserId());
		  Assertions.assertEquals(testDTO.getUserPwd(), sample.getUserPwd());
		  Assertions.assertEquals(testDTO.getUserName(), sample.getUserName());
		  Assertions.assertEquals(testDTO.getUserEmail(), sample.getUserEmail());
		  Assertions.assertEquals(testDTO.getUserAddress(), sample.getUserAddress());
		  Assertions.assertEquals(testDTO.getUserNumber(), sample.getUserNumber());
		  verify(memberDAO).create(any(MemberEntity.class));
	  }
	  
	  
}
