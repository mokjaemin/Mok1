package com.ReservationServer1.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ReservationServer1.data.DTO.member.FindPwdDTO;
import com.ReservationServer1.data.DTO.member.LoginDTO;
import com.ReservationServer1.data.DTO.member.MemberDTO;
import com.ReservationServer1.data.DTO.member.MemberTokenResultDTO;
import com.ReservationServer1.data.DTO.member.ModifyMemberDTO;
import com.ReservationServer1.data.DTO.member.SearchMemberDTO;
import com.ReservationServer1.service.MemberService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController("MemberController")
@RequestMapping("/member")
public class MemberController {

	private final MemberService memberService;

	public MemberController(MemberService memberService) {
		this.memberService = memberService;
	}

	@PostMapping
	@Operation(summary = "회원 등록 요청", description = "회원 정보가 등록됩니다.", tags = { "Member Controller" })
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = MemberDTO.class))),
			@ApiResponse(responseCode = "400", description = "BAD REQUEST"),
			@ApiResponse(responseCode = "404", description = "NOT FOUND"),
			@ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR") })
	public ResponseEntity<String> registerMember(@Valid @RequestBody MemberDTO member) {
		return ResponseEntity.status(HttpStatus.OK).body(memberService.registerMember(member));
	}

	@PostMapping("/login")
	@Operation(summary = "로그인 요청", description = "로그인을 요청합니다.", tags = { "Member Controller" })
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = LoginDTO.class))),
			@ApiResponse(responseCode = "400", description = "BAD REQUEST"),
			@ApiResponse(responseCode = "404", description = "NOT FOUND"),
			@ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR") })
	public ResponseEntity<MemberTokenResultDTO> loginMember(@Valid @RequestBody LoginDTO loginDTO) {
		return ResponseEntity.status(HttpStatus.OK).body(memberService.loginMember(loginDTO));
	}

	@GetMapping("/reLogin")
	@Operation(summary = "로그인 요청", description = "로그인을 요청합니다.", tags = { "Member Controller" })
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = LoginDTO.class))),
			@ApiResponse(responseCode = "400", description = "BAD REQUEST"),
			@ApiResponse(responseCode = "404", description = "NOT FOUND"),
			@ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR") })
	public ResponseEntity<String> reLoginMember(Authentication authentication) {
		return ResponseEntity.status(HttpStatus.OK).body(memberService.reLoginMember(authentication.getName()));
	}

	@PostMapping("/auth/pwd")
	@Operation(summary = "비밀번호 찾기 요청", description = "비밀번호 찾기를 요청합니다.", tags = { "Member Controller" })
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = FindPwdDTO.class))),
			@ApiResponse(responseCode = "400", description = "BAD REQUEST"),
			@ApiResponse(responseCode = "404", description = "NOT FOUND"),
			@ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR") })
	public ResponseEntity<String> findPwdMember(@Valid @RequestBody FindPwdDTO findPwdDTO) {
		return ResponseEntity.status(HttpStatus.OK)
				.body(memberService.findPwdMember(findPwdDTO.getUserId(), findPwdDTO.getUserEmail()));
	}

	@PutMapping("/pwd")
	@Operation(summary = "비밀번호 수정 요청", description = "비밀번호 수정을 요청합니다.", tags = { "Member Controller" })
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "400", description = "BAD REQUEST"),
			@ApiResponse(responseCode = "404", description = "NOT FOUND"),
			@ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR") })
	public ResponseEntity<String> updatePwdMember(@Valid @RequestBody String userPwd, Authentication authentication)
			throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode jsonNode = objectMapper.readTree(userPwd);
		String new_userPwd = jsonNode.get("userPwd").asText();
		return ResponseEntity.status(HttpStatus.OK)
				.body(memberService.modifyPwdMember(authentication.getName(), new_userPwd));
	}

	@PutMapping("/info")
	@Operation(summary = "회원정보 수정 요청", description = "회원정보 수정을 요청합니다.", tags = { "Member Controller" })
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = ModifyMemberDTO.class))),
			@ApiResponse(responseCode = "400", description = "BAD REQUEST"),
			@ApiResponse(responseCode = "404", description = "NOT FOUND"),
			@ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR") })
	public ResponseEntity<String> updateInfoMember(@Valid @RequestBody ModifyMemberDTO modifyMemberDTO,
			Authentication authentication) {
		return ResponseEntity.status(HttpStatus.OK)
				.body(memberService.modifyInfoMember(authentication.getName(), modifyMemberDTO));
	}

	@DeleteMapping
	@Operation(summary = "회원정보 삭제 요청", description = "회원정보 삭제를 요청합니다.", tags = { "Member Controller" })
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "OK"),
			@ApiResponse(responseCode = "400", description = "BAD REQUEST"),
			@ApiResponse(responseCode = "404", description = "NOT FOUND"),
			@ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR") })
	public ResponseEntity<String> deleteMember(@Valid @RequestBody String userPwd, Authentication authentication)
			throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode jsonNode = objectMapper.readTree(userPwd);
		String new_userPwd = jsonNode.get("userPwd").asText();
		return ResponseEntity.status(HttpStatus.OK)
				.body(memberService.deleteMember(authentication.getName(), new_userPwd));
	}

	@PostMapping("/search")
	@Operation(summary = "회원 정보 검색 요청", description = "회원 정보가 검색됩니다.", tags = { "Member Controller" })
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = MemberDTO.class))),
			@ApiResponse(responseCode = "400", description = "BAD REQUEST"),
			@ApiResponse(responseCode = "404", description = "NOT FOUND"),
			@ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR") })
	public ResponseEntity<List<SearchMemberDTO>> searchMember(@Valid @RequestBody SearchMemberDTO member) {
		return ResponseEntity.status(HttpStatus.OK).body(memberService.searchMember(member));
	}

}
