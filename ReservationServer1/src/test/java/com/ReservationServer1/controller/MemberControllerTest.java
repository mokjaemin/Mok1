package com.ReservationServer1.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.ReservationServer1.data.DTO.member.FindPwdDTO;
import com.ReservationServer1.data.DTO.member.LoginDTO;
import com.ReservationServer1.data.DTO.member.MemberDTO;
import com.ReservationServer1.data.DTO.member.MemberTokenResultDTO;
import com.ReservationServer1.data.DTO.member.ModifyMemberDTO;
import com.ReservationServer1.data.DTO.member.SearchMemberDTO;
import com.ReservationServer1.service.MemberService;
import com.ReservationServer1.utils.JWTutil;
import com.google.gson.Gson;

@WebMvcTest(MemberController.class)
public class MemberControllerTest {

	@MockBean
	private MemberService memberService;

	@Autowired
	private MockMvc mockMvc;

	@Test
	@DisplayName("회원 가입 성공")
	@WithMockUser
	void registerMemberSuccess() throws Exception {
		// given
		MemberDTO sample = MemberDTO.getSample();
		String response = "success";
		doReturn(response).when(memberService).registerMember(any(MemberDTO.class));

		// when
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/member").with(csrf())
				.contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(sample)));

		// then
		resultActions.andExpect(status().isOk()).andExpect(MockMvcResultMatchers.content().string(equalTo(response)));
	}

	@Test
	@DisplayName("회원 가입 실패 : MemberDTO 잘못된 입력")
	@WithMockUser
	void registerMemberFail() throws Exception {
		// given
		MemberDTO sample = new MemberDTO();

		// when
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/member").with(csrf())
				.contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(sample)));

		// then
		resultActions.andExpect(status().isBadRequest());
	}

	// 2. Login Member
	@Test
	@DisplayName("로그인 성공")
	@WithMockUser
	void loginMemberSuccess() throws Exception {
		// given
		LoginDTO sample = LoginDTO.getSample();
		MemberTokenResultDTO result = new MemberTokenResultDTO("access", "refresh");
		doReturn(result).when(memberService).loginMember(any(LoginDTO.class));

		// when
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/member/login").with(csrf())
				.contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(sample)));

		// then
		resultActions.andExpect(status().isOk());
	}

	@Test
	@DisplayName("로그인 실패 : LoginDTO 잘못된 입력")
	@WithMockUser
	void loginMemberFail() throws Exception {
		// given
		LoginDTO sample = new LoginDTO();

		// when
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/member/login").with(csrf())
				.contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(sample)));

		// then
		resultActions.andExpect(status().isBadRequest());
	}

	// 3. Find PWD Member
	@Test
	@DisplayName("비밀번호 찾기 인증 성공")
	@WithMockUser
	void findPwdMemberSuccess() throws Exception {
		// given
		FindPwdDTO sample = FindPwdDTO.getSample();
		String token = JWTutil.createJWT(sample.getUserId(), "PWD", "test", 10);
		doReturn(token).when(memberService).findPwdMember(anyString(), anyString());

		// when
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/member/auth/pwd").with(csrf())
				.contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(sample)));

		// then
		resultActions.andExpect(status().isOk()).andExpect(MockMvcResultMatchers.content().string(equalTo(token)));
	}

	@Test
	@DisplayName("비밀번호 찾기 인증 실패 : FindPwdDTO 잘못된 입력")
	@WithMockUser
	void findPwdMemberFail() throws Exception {
		// given
		FindPwdDTO sample = new FindPwdDTO();

		// when
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/member/auth/pwd").with(csrf())
				.contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(sample)));

		// then
		resultActions.andExpect(status().isBadRequest());
	}

	// 4. Modify Pwd
	@Test
	@DisplayName("비밀번호 변경 성공")
	@WithMockUser
	void updatePwdMemberSuccess() throws Exception {
		// given
		String response = "success";
		String jsonBody = "{\"userPwd\": \"userPwd\"}";
		doReturn(response).when(memberService).modifyPwdMember(anyString(), anyString());

		// when
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.put("/member/pwd").with(csrf())
				.contentType(MediaType.APPLICATION_JSON).content(jsonBody));

		// then
		resultActions.andExpect(status().isOk()).andExpect(MockMvcResultMatchers.content().string(equalTo(response)));
	}

	// 5. Modify Info
	@Test
	@DisplayName("회원정보 변경 성공")
	@WithMockUser
	void updateInfoMemberSuccess() throws Exception {
		// given
		ModifyMemberDTO sample = ModifyMemberDTO.getSample();
		String response = "success";
		doReturn(response).when(memberService).modifyInfoMember(anyString(), any(ModifyMemberDTO.class));

		// when
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.put("/member/info").with(csrf())
				.contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(sample)));

		// then
		resultActions.andExpect(status().isOk()).andExpect(MockMvcResultMatchers.content().string(equalTo(response)));
	}

	@Test
	@DisplayName("회원정보 변경 실패 : ModifyMemberDTO 잘못된 입력")
	@WithMockUser
	void updateInfoMemberFail() throws Exception {
		// given
		ModifyMemberDTO sample = new ModifyMemberDTO();

		// when
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.put("/member/info").with(csrf())
				.contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(sample)));

		// then
		resultActions.andExpect(status().isBadRequest());
	}

	// 6. Delete Member
	@Test
	@DisplayName("회원정보 삭제 성공")
	@WithMockUser(username = "userId")
	void deleteMemberSuccess() throws Exception {
		// given
		String response = "success";
		String jsonBody = "{\"userPwd\": \"userPwd\"}";
		doReturn(response).when(memberService).deleteMember(anyString(), anyString());

		// when
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.delete("/member").with(csrf())
				.contentType(MediaType.APPLICATION_JSON).content(jsonBody));

		// then
		resultActions.andExpect(status().isOk()).andExpect(MockMvcResultMatchers.content().string(equalTo(response)));
	}

	@Test
	@DisplayName("회원정보 검색 성공")
	@WithMockUser(username = "userId")
	void searchMemberSuccess() throws Exception {
		// given
		List<SearchMemberDTO> response = new ArrayList<>();
		SearchMemberDTO sample = SearchMemberDTO.getSample();
		doReturn(response).when(memberService).searchMember(any(SearchMemberDTO.class));

		// when
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/member/search").with(csrf())
				.contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(sample)));

		// then
		resultActions.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().json(new Gson().toJson(response)));
	}

	@Test
	@DisplayName("회원정보 검색 실패 : SearchMemberDTO 잘못된 입력")
	@WithMockUser(username = "userId")
	void searchMemberFail() throws Exception {
		// given
		SearchMemberDTO sample = SearchMemberDTO.builder().userId("1").build();

		// when
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/member/search").with(csrf())
				.contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(sample)));

		// then
		resultActions.andExpect(status().isBadRequest());
	}

}
