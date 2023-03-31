package com.ReservationServer1.DAO.UnitTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.ReservationServer1.data.Member;
import com.fasterxml.jackson.databind.ObjectMapper;




@SpringBootTest
@AutoConfigureMockMvc
public class MemberReigsterTest {
	
	
	@Autowired
	private MockMvc mockmvc; // HTTP 통신 

	
	@Autowired
	private ObjectMapper objectMapper; // JSON 변경
	
	
	@BeforeEach
    public void init() {
        // delete test 해줘야 함. 
    }
	
	
	@DisplayName("회원 가입 성공")
    @Test
    void signUpSuccess() throws Exception {
        // given
        Member sample = Member.sample(); // 회원가입 정보 샘플
        String content = objectMapper.writeValueAsString(sample); // 회원가입 정보를 JSON으로 변경
        
        // when
        ResultActions resultActions = mockmvc.perform(post("/member") // Post : member
        		.content(content) // Body로 Content를 담아서
        		.contentType(MediaType.APPLICATION_JSON) // BODY는 JSON
        		.accept(MediaType.APPLICATION_JSON)); // Controller는 Client로부터 JSON을 받는지
        
        
		// then 
		ResultActions mvcResult = resultActions.andExpect(status().isOk()) // 잘전송 되었는지
				.andExpect(content().string("success : registering has been succeed."))
				.andDo(print());
    }
    
	
    @DisplayName("회원 가입 실패")
    @Test
    void signUpFail() throws Exception {
        // given
        Member sample = Member.sample(); // 회원가입 정보 샘플
        String content = objectMapper.writeValueAsString(sample); // 회원가입 정보를 JSON으로 변경
        
        
        // when
        ResultActions resultActions = mockmvc.perform(post("/member") // Post : member
        		.content(content) // Body로 Content를 담아서
        		.contentType(MediaType.APPLICATION_JSON) // BODY는 JSON
        		.accept(MediaType.APPLICATION_JSON)); // Controller는 Client로부터 JSON을 받는지
        
        
		// then 
		ResultActions mvcResult = resultActions.andExpect(status().isOk()) // 잘전송 되었는지
				.andExpect(content().string("fail : " + sample.getUserId() + " is Aleady existed."))
				.andDo(print());
    }
    
}
