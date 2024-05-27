package com.ex.ticket.user.controller;

import com.ex.ticket.auth.model.dto.TokenAndUserResponse;
import com.ex.ticket.auth.service.LogoutUseCase;
import com.ex.ticket.common.PalagoStatic;
import com.ex.ticket.security.CookieHelper;
import com.ex.ticket.user.domain.dto.request.SignUpRequest;
import com.ex.ticket.user.service.LoginUseCase;
import com.ex.ticket.user.service.RefreshUseCase;
import com.ex.ticket.user.service.SignupUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "0. 회원 가입 및 로그인 관련 API")
public class AuthController {

	private final SignupUseCase signupUseCase;
	private final LoginUseCase loginUseCase;
	private final RefreshUseCase refreshUseCase;
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

	@Operation(summary = "refreshToken 용입니다.")
	@PostMapping("/token/refresh")
	public ResponseEntity<TokenAndUserResponse> tokenRefresh(
			@CookieValue(value = "refreshToken", required = false) String refreshTokenCookie,
			@RequestParam(value = "token", required = false, defaultValue = "")
			String refreshToken) {

		// 쿠키 우선시해서 리프레쉬.
		TokenAndUserResponse tokenAndUserResponse = refreshUseCase.execute(refreshTokenCookie != null ? refreshTokenCookie : refreshToken);
//		return ResponseEntity.ok()
//				.headers(cookieHelper.getTokenCookies(tokenAndUserResponse))
//				.body(tokenAndUserResponse);
		return null;
	}

	@Operation(summary = "로그아웃을 합니다.")
	@SecurityRequirement(name = PalagoStatic.ACCESS_TOKEN)
	@GetMapping("/logout")
	public ResponseEntity logoutUser() {
		logoutUseCase.execute();
		return ResponseEntity.ok().headers(cookieHelper.deleteCookies()).body(null);
	}

	// 회원탈퇴

	//
}
