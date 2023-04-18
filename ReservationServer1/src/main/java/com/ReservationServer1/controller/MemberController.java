package com.ReservationServer1.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ReservationServer1.data.DTO.LoginDTO;
import com.ReservationServer1.data.DTO.MemberDTO;
import com.ReservationServer1.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;


@RestController("MemberController")
@RequestMapping("/member")
public class MemberController {
	
    private final Logger logger = LoggerFactory.getLogger(MemberController.class);
  
    
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
        @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
	public ResponseEntity<MemberDTO> register(@Valid @RequestBody MemberDTO member){
	    logger.info("[MemberController] register(회원가입) 호출");
		MemberDTO response = memberService.registerMember(member);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	
	@PostMapping("/login")
    @Operation(summary = "로그인 요청", description = "로그인을 요청합니다.", tags = { "Member Controller" })
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
        @ApiResponse(responseCode = "404", description = "NOT FOUND"),
        @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
	public ResponseEntity<MemberDTO> login(@RequestBody LoginDTO loginDTO){
		logger.info("[MemberController] register(회원가입) 호출");
		MemberDTO response = memberService.loginMember(loginDTO.getUserId(), loginDTO.getUserPwd());
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	
}
