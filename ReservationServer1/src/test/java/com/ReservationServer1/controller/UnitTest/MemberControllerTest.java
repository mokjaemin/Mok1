package com.ReservationServer1.controller.UnitTest;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.ReservationServer1.controller.MemberController;
import com.ReservationServer1.data.DTO.MemberDTO;
import com.ReservationServer1.service.Impl.MemberServiceImpl;
import com.google.gson.Gson;



@WebMvcTest(MemberController.class)
//@AutoConfigureWebMvc // 이 어노테이션을 통해 MockMvc를 Builder 없이 주입받을 수 있음
public class MemberControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  MemberServiceImpl memberServiceImpl;


  @Test
  @DisplayName("회원가입 테스트")
  void getProductTest() throws Exception {

	  
    // given
	MemberDTO sample = MemberDTO.sample();	
    given(memberServiceImpl.registerMember(sample)).willReturn(sample);
    Gson gson = new Gson();
    String content = gson.toJson(sample);

    
    // when
    ResultActions actions = mockMvc.perform(post("/member")
			.content(content)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON));
			
    
    // then
    ResultActions realResult = actions.andExpect(status().isOk())
			.andExpect(jsonPath("$.userId").exists())
			.andExpect(jsonPath("$.userPwd").exists())
			.andExpect(jsonPath("$.userName").exists())
			.andExpect(jsonPath("$.userNumber").exists())
			.andExpect(jsonPath("$.userAddress").exists())
			.andExpect(jsonPath("$.userEmail").exists())
			.andDo(print());
    verify(memberServiceImpl).registerMember(sample);
  }

}
