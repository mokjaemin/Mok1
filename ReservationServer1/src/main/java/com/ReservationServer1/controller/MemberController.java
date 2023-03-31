package com.ReservationServer1.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ReservationServer1.data.Member;
import com.ReservationServer1.service.MemberService;


@RestController("MemberController")
@RequestMapping("/member")
public class MemberController {
	
	
	private MemberService memberService;
	
	public MemberController(MemberService memberService) {
		this.memberService = memberService;
	}
	
	
	// 회원등록
	@PostMapping
	public String register(@RequestBody Member member) {
		return memberService.registerMember(member);
	}
	
}
