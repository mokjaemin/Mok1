//package com.ReservationServer1.controller;
//
//import static org.hamcrest.CoreMatchers.equalTo;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.doReturn;
//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.ResultActions;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//import com.ReservationServer1.data.DTO.member.FindPwdDTO;
//import com.ReservationServer1.data.DTO.member.LoginDTO;
//import com.ReservationServer1.data.DTO.member.MemberDTO;
//import com.ReservationServer1.data.DTO.member.ModifyMemberDTO;
//import com.ReservationServer1.service.MemberService;
//import com.ReservationServer1.utils.JWTutil;
//import com.google.gson.Gson;
//
//
//@WebMvcTest(MemberController.class)
//public class MemberControllerTest {
//  
//  
//
//
//  @MockBean
//  private MemberService memberService;
//
//  @Autowired
//  private MockMvc mockMvc;
//
//
//
//  // 1. Register Member
//  @Test
//  @DisplayName("회원 가입 성공")
//  @WithMockUser
//  void registerMemberSuccess() throws Exception {
//    // given
//    MemberDTO sample = MemberDTO.sample();
//    String response = "success";
//    doReturn(response).when(memberService).registerMember(any(MemberDTO.class));
//
//    // when
//    ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/member")
//        .with(csrf()).contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(sample)));
//
//    // then
//    resultActions.andExpect(status().isOk())
//        .andExpect(MockMvcResultMatchers.content().string(equalTo(response)));
//  }
//
//  @Test
//  @DisplayName("회원 가입 실패 : MemberDTO 잘못된 입력")
//  @WithMockUser
//  void registerMemberFail() throws Exception {
//    // given
//    MemberDTO sample = new MemberDTO();
//
//    // when
//    ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/member")
//        .with(csrf()).contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(sample)));
//
//    // then
//    resultActions.andExpect(status().isBadRequest());
//  }
//
//
//
//  // 2. Login Member
//  @Test
//  @DisplayName("로그인 성공")
//  @WithMockUser
//  void loginMemberSuccess() throws Exception {
//    // given
//    LoginDTO sample = LoginDTO.sample();
//    String token = JWTutil.createJWT(sample.getUserId(), "USER", "test", 10);
//    doReturn(token).when(memberService).loginMember(any(LoginDTO.class));
//
//    // when
//    ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/member/login")
//        .with(csrf()).contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(sample)));
//
//    // then
//    resultActions.andExpect(status().isOk())
//        .andExpect(MockMvcResultMatchers.content().string(equalTo(token)));
//  }
//
//  @Test
//  @DisplayName("로그인 실패 : LoginDTO 잘못된 입력")
//  @WithMockUser
//  void loginMemberFail() throws Exception {
//    // given
//    LoginDTO sample = new LoginDTO();
//
//    // when
//    ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/member/login")
//        .with(csrf()).contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(sample)));
//
//    // then
//    resultActions.andExpect(status().isBadRequest());
//  }
//
//
//  // 3. Find PWD Member
//  @Test
//  @DisplayName("비밀번호 찾기 인증 성공")
//  @WithMockUser
//  void findPwdMemberSuccess() throws Exception {
//    // given
//    FindPwdDTO sample = FindPwdDTO.sample();
//    String token = JWTutil.createJWT(sample.getUserId(), "PWD", "test", 10);
//    doReturn(token).when(memberService).findPwdMember(anyString(),
//        anyString());
//
//    // when
//    ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/member/auth/pwd")
//        .with(csrf()).contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(sample)));
//
//    // then
//    resultActions.andExpect(status().isOk())
//        .andExpect(MockMvcResultMatchers.content().string(equalTo(token)));
//  }
//
//
//  @Test
//  @DisplayName("비밀번호 찾기 인증 실패 : FindPwdDTO 잘못된 입력")
//  @WithMockUser
//  void findPwdMemberFail() throws Exception {
//    // given
//    FindPwdDTO sample = new FindPwdDTO();
//
//    // when
//    ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/member/auth/pwd")
//        .with(csrf()).contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(sample)));
//
//    // then
//    resultActions.andExpect(status().isBadRequest());
//  }
//
//  // 4. Modify Pwd
//  @Test
//  @DisplayName("비밀번호 변경 성공")
//  @WithMockUser
//  void modPwdMemberSuccess() throws Exception {
//    // given
//    String response = "success";
//    String jsonBody = "{\"userPwd\": \"userPwd\"}";
//    doReturn(response).when(memberService).modPwdMember(anyString(), anyString());
//
//
//    // when
//    ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.put("/member/pwd")
//        .with(csrf()).contentType(MediaType.APPLICATION_JSON).content(jsonBody));
//
//    // then
//    resultActions.andExpect(status().isOk())
//        .andExpect(MockMvcResultMatchers.content().string(equalTo(response)));
//  }
//
//
//  // 5. Modify Info
//  @Test
//  @DisplayName("회원정보 변경 성공")
//  @WithMockUser
//  void modInfoMemberSuccess() throws Exception {
//    // given
//    ModifyMemberDTO sample = ModifyMemberDTO.sample();
//    String response = "success";
//    doReturn(response).when(memberService).modInfoMember(anyString(), any(ModifyMemberDTO.class));
//
//
//    // when
//    ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.put("/member/info")
//        .with(csrf()).contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(sample)));
//
//    // then
//    resultActions.andExpect(status().isOk())
//        .andExpect(MockMvcResultMatchers.content().string(equalTo(response)));
//  }
//
//  @Test
//  @DisplayName("회원정보 변경 실패 : 잘못된 ModifyMemberDTO 입력")
//  @WithMockUser
//  void modInfoMemberFail() throws Exception {
//    // given
//    ModifyMemberDTO sample = new ModifyMemberDTO();
//    String response = "success";
//    doReturn(response).when(memberService).modInfoMember(anyString(), any(ModifyMemberDTO.class));
//
//
//    // when
//    ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.put("/member/info")
//        .with(csrf()).contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(sample)));
//
//    // then
//    resultActions.andExpect(status().isBadRequest());
//  }
//
//
//  // 6. Delete Member
//  @Test
//  @DisplayName("회원정보 삭제 성공")
//  @WithMockUser(username = "userId")
//  void deleteMemberSuccess() throws Exception {
//    // given
//    String response = "success";
//    String jsonBody = "{\"userPwd\": \"userPwd\"}";
//    doReturn(response).when(memberService).delMember(anyString(), anyString());
//
//
//    // when
//    ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.delete("/member")
//        .with(csrf()).contentType(MediaType.APPLICATION_JSON).content(jsonBody));
//
//    // then
//    resultActions.andExpect(status().isOk())
//        .andExpect(MockMvcResultMatchers.content().string(equalTo(response)));
//  }
//
//}
