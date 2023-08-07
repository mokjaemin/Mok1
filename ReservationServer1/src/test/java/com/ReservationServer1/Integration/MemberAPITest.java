package com.ReservationServer1.Integration;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.ReservationServer1.data.DTO.member.FindPwdDTO;
import com.ReservationServer1.data.DTO.member.LoginDTO;
import com.ReservationServer1.data.DTO.member.MemberDTO;
import com.ReservationServer1.data.DTO.member.ModifyMemberDTO;
import com.ReservationServer1.utils.JWTutil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import jakarta.transaction.Transactional;


@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Transactional
class MemberAPITest {

  @Autowired
  protected MockMvc mockMvc;

  @Value("${jwt.secret}")
  private String secretKey;


  @Test
  @DisplayName("MemberAPI : Register Member Success")
  public void registerMemberSuccess() throws Exception {
    // given
    MemberDTO sample = MemberDTO.sample();
    String response = "success";

    // when
    ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/member")
        .with(csrf()).contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(sample)));

    // then
    resultActions.andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().string(equalTo(response)));
  }


  @Test
  @DisplayName("MemberAPI : Register Member Fail : 이미 존재하는 아이디")
  public void registerMemberFail() throws Exception {
    // given
    MemberDTO sample = MemberDTO.sample();
    String response = "이미 존재하는 ID입니다: " + sample.getUserId();

    // 회원 가입
    RegisterMember(sample);

    // when
    ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/member")
        .with(csrf()).contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(sample)));

    // then
    resultActions.andExpect(status().isBadRequest()).andExpect(result -> {
      String responseBody = result.getResponse().getContentAsString();
      String utf8ResponseBody =
          new String(responseBody.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
      JsonNode jsonNode = new ObjectMapper().readTree(utf8ResponseBody);
      String errorMessage = jsonNode.get("message").asText();
      assertEquals(errorMessage, response);
    });
  }



  @Test
  @DisplayName("MemberAPI : Login Member Success")
  void loginMemberSuccess() throws Exception {
    // given
    MemberDTO sample1 = MemberDTO.sample();
    LoginDTO sample2 = new LoginDTO();
    sample2.setUserId(sample1.getUserId());
    sample2.setUserPwd(sample1.getUserPwd());

    // 회원가입
    RegisterMember(sample1);

    // when & then
    MvcResult mvcResult = mockMvc
        .perform(MockMvcRequestBuilders.post("/member/login").with(csrf())
            .contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(sample2)))
        .andExpect(status().isOk()).andReturn();

    // 토큰 확인
    String response = mvcResult.getResponse().getContentAsString();
    assertTrue(JWTutil.isExpired(response, secretKey) == false);
    assertTrue(JWTutil.getUserId(response, secretKey).equals(sample2.getUserId()));
    assertTrue(JWTutil.getUserRole(response, secretKey).equals("USER"));
  }


  @Test
  @DisplayName("MemberAPI : Login Member Fail : 존재하지 않는 아이디")
  void loginMemberFail1() throws Exception {
    // given
    LoginDTO sample1 = LoginDTO.sample();
    String response = "아이디가 존재하지 않습니다.";


    // when
    ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/member/login")
        .with(csrf()).contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(sample1)));

    // then
    resultActions.andExpect(status().isBadRequest()).andExpect(result -> {
      String responseBody = result.getResponse().getContentAsString();
      String utf8ResponseBody =
          new String(responseBody.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
      JsonNode jsonNode = new ObjectMapper().readTree(utf8ResponseBody);
      String errorMessage = jsonNode.get("message").asText();
      assertEquals(errorMessage, response);
    });
  }



  @Test
  @DisplayName("MemberAPI : Login Member Fail : 비밀번호 불일치")
  void loginMemberFail2() throws Exception {
    // given
    MemberDTO sample1 = MemberDTO.sample();
    LoginDTO sample2 = new LoginDTO();
    sample2.setUserId(sample1.getUserId());
    sample2.setUserPwd("Wrong PWD");
    String response = "비밀번호가 일치하지 않습니다.";

    // 회원가입
    RegisterMember(sample1);


    // when
    ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/member/login")
        .with(csrf()).contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(sample2)));

    // then
    resultActions.andExpect(status().isBadRequest()).andExpect(result -> {
      String responseBody = result.getResponse().getContentAsString();
      String utf8ResponseBody =
          new String(responseBody.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
      JsonNode jsonNode = new ObjectMapper().readTree(utf8ResponseBody);
      String errorMessage = jsonNode.get("message").asText();
      assertEquals(errorMessage, response);
    });
  }

  @Test
  @DisplayName("MemberAPI : Find Pwd Success")
  public void findPwdMemberSuccess() throws Exception {
    // given
    MemberDTO sample = MemberDTO.sample();
    sample.setUserEmail("bamer@naver.com");
    FindPwdDTO info = new FindPwdDTO();
    info.setUserId(sample.getUserId());
    info.setUserEmail(sample.getUserEmail());


    // 회원가입
    RegisterMember(sample);


    // when & then
    MvcResult mvcResult = mockMvc
        .perform(MockMvcRequestBuilders.post("/member/auth/pwd").with(csrf())
            .contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(info)))
        .andExpect(status().isOk()).andReturn();

    // 토큰 확인
    String response = mvcResult.getResponse().getContentAsString();
    assertTrue(JWTutil.isExpired(response, secretKey) == false);
    assertTrue(JWTutil.getUserId(response, secretKey).equals(info.getUserId()));
    assertTrue(JWTutil.getUserRole(response, secretKey).equals("PWD"));
  }


  @Test
  @DisplayName("MemberAPI : Find Pwd Fail : 존재하지 않는 아이디")
  public void findPwdMemberFail1() throws Exception {
    // given
    FindPwdDTO info = FindPwdDTO.sample();
    String response = "존재하지 않는 아이디입니다.";

    // when
    ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/member/auth/pwd")
        .with(csrf()).contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(info)));

    // then
    resultActions.andExpect(status().isBadRequest()).andExpect(result -> {
      String responseBody = result.getResponse().getContentAsString();
      String utf8ResponseBody =
          new String(responseBody.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
      JsonNode jsonNode = new ObjectMapper().readTree(utf8ResponseBody);
      String errorMessage = jsonNode.get("message").asText();
      assertEquals(errorMessage, response);
    });
  }


  @Test
  @DisplayName("MemberAPI : Find Pwd Fail : 이메일 불일치")
  public void findPwdMemberFail2() throws Exception {
    // given
    MemberDTO sample = MemberDTO.sample();
    sample.setUserEmail("bamer@naver.com");
    FindPwdDTO info = new FindPwdDTO();
    info.setUserId(sample.getUserId());
    info.setUserEmail("wrongEmail");
    String response = "이메일 정보가 일치하지 않습니다.";


    // 회원가입
    RegisterMember(sample);

    // when
    ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/member/auth/pwd")
        .with(csrf()).contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(info)));

    // then
    resultActions.andExpect(status().isBadRequest()).andExpect(result -> {
      String responseBody = result.getResponse().getContentAsString();
      String utf8ResponseBody =
          new String(responseBody.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
      JsonNode jsonNode = new ObjectMapper().readTree(utf8ResponseBody);
      String errorMessage = jsonNode.get("message").asText();
      assertEquals(errorMessage, response);
    });
  }


  @Test
  @DisplayName("MemberAPI : Modify Pwd Success")
  @WithMockUser(username = "id", roles = "PWD")
  public void modPwdMemberSuccess() throws Exception {
    // given
    MemberDTO sample = MemberDTO.sample();
    String jsonBody = "{\"userPwd\": \"changedPwd\"}";
    String response = "success";

    // 회원가입
    RegisterMember(sample);


    // when
    ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.put("/member/pwd")
        .with(csrf()).contentType(MediaType.APPLICATION_JSON).content(jsonBody));

    // then
    resultActions.andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().string(equalTo(response)));
  }


  @Test
  @DisplayName("MemberAPI : Modify Pwd Fail : 존재하지 않는 아이디")
  @WithMockUser(username = "wrongId", roles = "PWD")
  public void modPwdMemberFail() throws Exception {
    // given
    String jsonBody = "{\"userPwd\": \"changedPwd\"}";
    String response = "존재하지 않는 아이디 입니다.";

    // when
    ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.put("/member/pwd")
        .with(csrf()).contentType(MediaType.APPLICATION_JSON).content(jsonBody));

    // then
    resultActions.andExpect(status().isBadRequest()).andExpect(result -> {
      String responseBody = result.getResponse().getContentAsString();
      String utf8ResponseBody =
          new String(responseBody.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
      JsonNode jsonNode = new ObjectMapper().readTree(utf8ResponseBody);
      String errorMessage = jsonNode.get("message").asText();
      assertEquals(errorMessage, response);
    });
  }


  @Test
  @DisplayName("MemberAPI : Modify Info Success")
  @WithMockUser(username = "id", roles = "USER")
  public void modInfoMemberSuccess() throws Exception {
    // given
    MemberDTO sample = MemberDTO.sample();
    ModifyMemberDTO changedSample = ModifyMemberDTO.sample();
    String response = "success";

    // 회원가입
    RegisterMember(sample);


    // when
    ResultActions resultActions =
        mockMvc.perform(MockMvcRequestBuilders.put("/member/info").with(csrf())
            .contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(changedSample)));

    // then
    resultActions.andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().string(equalTo(response)));
  }


  @Test
  @DisplayName("MemberAPI : Modify Info Fail : 존재하지 않는 아이디")
  @WithMockUser(username = "wrongId", roles = "USER")
  public void modInfoMemberFail() throws Exception {
    // given
    ModifyMemberDTO changedSample = ModifyMemberDTO.sample();
    String response = "존재하지 않는 아이디 입니다.";


    // when
    ResultActions resultActions =
        mockMvc.perform(MockMvcRequestBuilders.put("/member/info").with(csrf())
            .contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(changedSample)));

    // then
    resultActions.andExpect(status().isBadRequest()).andExpect(result -> {
      String responseBody = result.getResponse().getContentAsString();
      String utf8ResponseBody =
          new String(responseBody.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
      JsonNode jsonNode = new ObjectMapper().readTree(utf8ResponseBody);
      String errorMessage = jsonNode.get("message").asText();
      assertEquals(errorMessage, response);
    });
  }

  @Test
  @DisplayName("MemberAPI : Delete Info Success")
  @WithMockUser(username = "id", roles = "USER")
  public void deleteInfoMemberSuccess() throws Exception {
    // given
    MemberDTO sample = MemberDTO.sample();
    String jsonBody = "{\"userPwd\": \"pwd\"}";
    String response = "success";

    // 회원가입
    RegisterMember(sample);


    // when
    ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.delete("/member")
        .with(csrf()).contentType(MediaType.APPLICATION_JSON).content(jsonBody));

    // then
    resultActions.andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().string(equalTo(response)));
  }

  @Test
  @DisplayName("MemberAPI : Delete Info Fail : 존재하지 않는 아이디")
  @WithMockUser(username = "wrongId", roles = "USER")
  public void deleteInfoMemberFail1() throws Exception {
    // given
    String jsonBody = "{\"userPwd\": \"pwd\"}";
    String response = "존재하지 않는 아이디 입니다.";


    // when
    ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.delete("/member")
        .with(csrf()).contentType(MediaType.APPLICATION_JSON).content(jsonBody));

    // then
    resultActions.andExpect(status().isBadRequest()).andExpect(result -> {
      String responseBody = result.getResponse().getContentAsString();
      String utf8ResponseBody =
          new String(responseBody.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
      JsonNode jsonNode = new ObjectMapper().readTree(utf8ResponseBody);
      String errorMessage = jsonNode.get("message").asText();
      assertEquals(errorMessage, response);
    });
  }


  @Test
  @DisplayName("MemberAPI : Delete Info Fail : 비밀번호 불일치")
  @WithMockUser(username = "id", roles = "USER")
  public void deleteInfoMemberFail2() throws Exception {
    // given
    MemberDTO sample = MemberDTO.sample();
    String jsonBody = "{\"userPwd\": \"wrongPwd\"}";
    String response = "비밀번호가 일치하지 않습니다.";

    // 회원가입
    RegisterMember(sample);


    // when
    ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.delete("/member")
        .with(csrf()).contentType(MediaType.APPLICATION_JSON).content(jsonBody));

    // then
    resultActions.andExpect(status().isBadRequest()).andExpect(result -> {
      String responseBody = result.getResponse().getContentAsString();
      String utf8ResponseBody =
          new String(responseBody.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
      JsonNode jsonNode = new ObjectMapper().readTree(utf8ResponseBody);
      String errorMessage = jsonNode.get("message").asText();
      assertEquals(errorMessage, response);
    });
  }


  // 회원가입 메서드
  public void RegisterMember(MemberDTO member) throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.post("/member").with(csrf())
        .contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(member)));
  }
}
