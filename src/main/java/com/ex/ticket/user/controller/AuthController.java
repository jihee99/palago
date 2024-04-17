package com.ex.ticket.user.controller;

import com.ex.ticket.auth.service.LogoutUseCase;
import com.ex.ticket.common.PalagoStatic;
import com.ex.ticket.security.CookieHelper;
import com.ex.ticket.user.domain.dto.request.SignUpRequest;
import com.ex.ticket.user.service.LoginUseCase;
import com.ex.ticket.user.service.SignupUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "0. 회원 가입 및 로그인 관련 API")
public class AuthController {

	private final SignupUseCase signupUseCase;
	private final LoginUseCase loginUseCase;
	private final LogoutUseCase logoutUseCase;
	private final CookieHelper cookieHelper;

	@Operation(summary = "회원가입")
	@PostMapping("/join")
	public String join(
		SignUpRequest request
	) {
		signupUseCase.execute(request);
		return "redirect:/home";
	}

	// @Operation(summary = "로그인")
	// @PostMapping("/login")
	// public String signIn(@RequestBody SignInRequest request) {
	// 	loginService.execute(request);
	// 	return "redore";
	// 	// return ApiResponse.success(loginService.execute(request));
	// }

	@Operation(summary = "로그아웃을 합니다.")
	@SecurityRequirement(name = PalagoStatic.ACCESS_TOKEN)
	@PostMapping("/logout")
	public ResponseEntity logoutUser() {
		logoutUseCase.execute();
		return ResponseEntity.ok().headers(cookieHelper.deleteCookies()).body(null);
	}


}
