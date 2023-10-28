package com.ReservationServer1.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.env.Environment;

import com.ReservationServer1.DAO.MemberDAO;
import com.ReservationServer1.data.DTO.member.MemberDTO;
import com.ReservationServer1.data.DTO.member.ModifyMemberDTO;
import com.ReservationServer1.data.Entity.member.MemberEntity;
import com.ReservationServer1.service.Impl.MemberServiceImpl;

@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {

	@InjectMocks
	private MemberServiceImpl memberServiceImpl;

	@Mock
	private MemberDAO memberDAO;

	@Mock
	private Environment env;

	// 1. Register Member
	@Test
	@DisplayName("회원 가입 성공")
	void registerMemberSuccess() throws Exception {
		// given
		MemberDTO sample = MemberDTO.getSample();
		String response = "success";
		doReturn(response).when(memberDAO).registerMember(any(MemberEntity.class));

		// when
		String result = memberServiceImpl.registerMember(sample);

		// then
		assertThat(result.equals(response));

		// verify
		verify(memberDAO, times(1)).registerMember(any(MemberEntity.class));
	}

	// 4. Modify Pwd Member
	@Test
	@DisplayName("비밀번호 수정 성공")
	void modifyPwdMemberSuccess() throws Exception {
		// given
		String userId = "testId";
		String userPwd = "testPwd";
		String response = "success";
		doReturn(response).when(memberDAO).modifyPwdMember(anyString(), anyString());

		// when
		String result = memberServiceImpl.modifyPwdMember(userId, userPwd);

		// then
		assertThat(result.equals(response));

		// verify
		verify(memberDAO, times(1)).modifyPwdMember(userId, userPwd);
	}

	// 5. Modify Info Member
	@Test
	@DisplayName("회원정보 수정 성공")
	void modifyInfoMemberSuccess() throws Exception {
		// given
		String userId = "testId";
		ModifyMemberDTO sample = ModifyMemberDTO.getSample();
		String response = "success";
		doReturn(response).when(memberDAO).modifyInfoMember(anyString(), any(ModifyMemberDTO.class));

		// when
		String result = memberServiceImpl.modifyInfoMember(userId, sample);

		// then
		assertThat(result.equals(response));

		// verify
		verify(memberDAO, times(1)).modifyInfoMember(userId, sample);
	}

	// 6. Delete Member
	@Test
	@DisplayName("회원정보 삭제 성공")
	void deleteMemberSuccess() throws Exception {
		// given
		String userId = "testId";
		String userPwd = "testPwd";
		String response = "success";
		doReturn(response).when(memberDAO).deleteMember(anyString(), anyString());

		// when
		String result = memberServiceImpl.deleteMember(userId, userPwd);

		// then
		assertThat(result.equals(response));

		// verify
		verify(memberDAO, times(1)).deleteMember(userId, userPwd);
	}

}
