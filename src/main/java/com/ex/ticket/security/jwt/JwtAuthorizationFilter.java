package com.ex.ticket.security.jwt;

import com.ex.ticket.auth.PrincipalDetails;
import com.ex.ticket.common.PalagoStatic;
import com.ex.ticket.user.domain.entity.User;
import com.ex.ticket.user.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;


public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
	// 권한이나 인증이 필요한 특정 주소를 요청했을 때 authorization filter 타게 되어있음

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

		System.out.println("인증이나 권한이 필요한 주소가 요청됨");

		// header가 있는지 확인
		if(isNullToken(request)) {
			chain.doFilter(request, response);
			return;
		}

		// jwt 토큰을 검증해서 정상적인 사용자인지 확인
		String jwtToken = request.getHeader(PalagoStatic.AUTH_HEADER).replace(PalagoStatic.BEARER, "");


		AccessTokenInfo accessTokenInfo = tokenService.parseAccessToken(jwtToken);

		if(accessTokenInfo != null) {
			System.out.println("token 있다");
			System.out.println(accessTokenInfo.getEmail() + "  " + accessTokenInfo.getRole());

			User user = userRepository.findByEmail(accessTokenInfo.getEmail()).orElseThrow();
			PrincipalDetails principalDetails = new PrincipalDetails(user);

			// Jwt 토큰 서명을 통해서 서명이 정상이면 authentication 객체를 만들어준다.
			Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails, "", principalDetails.getAuthorities());

			SecurityContextHolder.getContext().setAuthentication(authentication);
		}

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

}
