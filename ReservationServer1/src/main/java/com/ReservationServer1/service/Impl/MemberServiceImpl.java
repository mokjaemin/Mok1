package com.ReservationServer1.service.Impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import com.ReservationServer1.DAO.MemberDAO;
import com.ReservationServer1.data.DTO.member.LoginDTO;
import com.ReservationServer1.data.DTO.member.MemberDTO;
import com.ReservationServer1.data.DTO.member.ModifyMemberDTO;
import com.ReservationServer1.data.DTO.member.SearchMemberDTO;
import com.ReservationServer1.data.Entity.member.MemberEntity;
import com.ReservationServer1.service.MemberService;
import com.ReservationServer1.utils.JWTutil;

@Service
public class MemberServiceImpl implements MemberService {

	@Value("${jwt.secret}")
	private String secretKey;
	private final JavaMailSender emailSender;
	private final MemberDAO memberDAO;
	private final Environment env;
	private final Long expiredLoginMs = 1000 * 60 * 30l; // 30분
	private final Long expiredFindPwdMs = 1000 * 60 * 5l; // 5분

	public MemberServiceImpl(MemberDAO memberDAO, JavaMailSender emailSender, Environment env) {
		this.memberDAO = memberDAO;
		this.emailSender = emailSender;
		this.env = env;
	}

	public void setTestSecretKey(String secretKey) {
		if (secretKey != null) {
			return;
		}
		this.secretKey = secretKey;
	}

	@Override
	public String registerMember(MemberDTO member) {
		return memberDAO.registerMember(new MemberEntity(member));
	}

	@Override
	public String loginMember(LoginDTO loginDTO) {
		memberDAO.loginMember(loginDTO);
		String token = JWTutil.createJWT(loginDTO.getUserId(), "USER", secretKey, expiredLoginMs);
		return token;
	}

	@Override
	public String findPwdMember(String userId, String userEmail) {
		memberDAO.findPwdMember(userId, userEmail);
		String token = JWTutil.createJWT(userId, "PWD", secretKey, expiredFindPwdMs);
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(env.getProperty("spring.mail.username"));
		message.setTo(userEmail);
		message.setSubject("비밀번호 인증 메일입니다.");
		message.setText(token);
		emailSender.send(message);
		return token;
	}

	@Override
	public String modifyPwdMember(String userId, String userPwd) {
		return memberDAO.modifyPwdMember(userId, userPwd);
	}

	@Override
	public String modifyInfoMember(String userId, ModifyMemberDTO modifyMemberDTO) {
		return memberDAO.modifyInfoMember(userId, modifyMemberDTO);
	}

	@Override
	public String deleteMember(String userId, String userPwd) {
		return memberDAO.deleteMember(userId, userPwd);
	}

	@Override
	public List<SearchMemberDTO> searchMember(SearchMemberDTO member) {
		return memberDAO.searchMember(member);
	}

}
