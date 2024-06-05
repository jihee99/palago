package com.ex.ticket.security.filter;

import com.ex.ticket.auth.PrincipalDetails;
import com.ex.ticket.common.PalagoStatic;
import com.ex.ticket.security.jwt.AccessTokenInfo;
import com.ex.ticket.security.jwt.TokenService;
import com.ex.ticket.user.domain.entity.User;
import com.ex.ticket.user.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.util.WebUtils;

import java.io.IOException;


public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
	// 권한이나 인증이 필요한 특정 주소를 요청했을 때 authorization filter 타게 되어있음

//	경우 1. Access Token , Refresh Token 모두 만료 -> 에러, 재로그인 요청
//	경우2. Access Token 만료, Refresh Token 유효 -> Refresh Token 검증하여 (서버 DB에 저장되어 토큰과 클라이언트에 저장되어있는 토큰을 비요하여) Access Token 발급
//	경우3. Access Token 유효, Refresh Token 만료 -> Access Token 검증하여 Refresh Token 재발급(access token이 유효하다 ==이미 인증되었다 -> 바로 refresh token 재발급)
//	경우4. Access Token , Refresh Token 모두 유효 -> 정상처리

	private final TokenService tokenService;
	private final UserRepository userRepository;
	public JwtAuthorizationFilter(AuthenticationManager authenticationManager, TokenService tokenService, UserRepository userRepository) {
		super(authenticationManager);
		this.tokenService = tokenService;
		this.userRepository = userRepository;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws
		IOException,
		ServletException {

		// header가 있는지 확인
//		if(isNullToken(request)) {
//			chain.doFilter(request, response);
//			return;
//		}

		// jwt 토큰을 검증해서 정상적인 사용자인지 확인
//		String jwtToken = request.getHeader(PalagoStatic.AUTH_HEADER).replace(PalagoStatic.BEARER, "");

		String token = resolveToken(request);
		System.out.println("============ resolve token ============");
		System.out.println(token);

		if (token != null) {
			Authentication authentication = getAuthentication(token);
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}

//		AccessTokenInfo accessTokenInfo = tokenService.parseAccessToken(token);
//
//		if(accessTokenInfo != null) {
//			System.out.println("token 있다");
//			System.out.println(accessTokenInfo.getEmail() + "  " + accessTokenInfo.getRole());
//
//			User user = userRepository.findByEmail(accessTokenInfo.getEmail()).orElseThrow();
//			PrincipalDetails principalDetails = new PrincipalDetails(user);
//
//			// Jwt 토큰 서명을 통해서 서명이 정상이면 authentication 객체를 만들어준다.
//			Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails, "", principalDetails.getAuthorities());
//
//			SecurityContextHolder.getContext().setAuthentication(authentication);
//		}

		chain.doFilter(request, response);

	}


	private boolean isNullToken(HttpServletRequest request) {
		String jwtHeader = request.getHeader(PalagoStatic.AUTH_HEADER);
		System.out.println("header : "+ jwtHeader);

		if (jwtHeader == null || !jwtHeader.startsWith(PalagoStatic.BEARER)) {
			return true;
		}

		return false;
	}

	private String resolveToken(HttpServletRequest request) {
		// 쿠키방식 지원
		Cookie accessTokenCookie = WebUtils.getCookie(request, "accessToken");
		if (accessTokenCookie != null) {
			System.out.println(accessTokenCookie.getValue());
			return accessTokenCookie.getValue();
		}

		// 기존 jwt 방식 지원
		String rawHeader = request.getHeader(PalagoStatic.AUTH_HEADER);

		if (rawHeader != null
				&& rawHeader.length() > PalagoStatic.BEARER.length()
				&& rawHeader.startsWith(PalagoStatic.BEARER)) {
			return rawHeader.substring(PalagoStatic.BEARER.length());
		}
		return null;
	}

	public Authentication getAuthentication(String token) {
		AccessTokenInfo accessTokenInfo = tokenService.parseAccessToken(token);

		User user = userRepository.findByEmail(accessTokenInfo.getEmail()).orElseThrow();
		UserDetails userDetails = new PrincipalDetails(user);
		System.out.println(userDetails.getUsername() + " " + userDetails.getAuthorities());
		return new UsernamePasswordAuthenticationToken(
				userDetails, "user", userDetails.getAuthorities());
	}

}
