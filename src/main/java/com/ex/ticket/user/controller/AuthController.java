package com.ex.ticket.user.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ex.ticket.user.domain.dto.request.SignInRequest;
import com.ex.ticket.user.domain.dto.request.SignUpRequest;
import com.ex.ticket.user.service.LoginService;
import com.ex.ticket.user.service.SignUpService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "0. 회원 가입 및 로그인 관련 API")
public class AuthController {

	private final SignUpService signUpService;

	private final LoginService loginService;

	@Operation(summary = "회원가입")
	@PostMapping("/join/us")
	public String join(
		SignUpRequest request
	) {
		signUpService.execute(request);
		return "joinForm";
	}

	// @Operation(summary = "로그인")
	// @PostMapping("/login")
	// public String signIn(@RequestBody SignInRequest request) {
	// 	loginService.execute(request);
	// 	return "redore";
	// 	// return ApiResponse.success(loginService.execute(request));
	// }


}
