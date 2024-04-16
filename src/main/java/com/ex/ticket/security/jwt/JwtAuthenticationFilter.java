package com.ex.ticket.security.jwt;

import com.ex.ticket.auth.PrincipalDetails;
import com.ex.ticket.auth.model.dto.TokenAndUserResponse;
import com.ex.ticket.common.PalagoStatic;
import com.ex.ticket.security.CookieHelper;
import com.ex.ticket.user.domain.dto.request.SignInRequest;
import com.ex.ticket.user.domain.entity.User;
import com.ex.ticket.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Map;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private final AuthenticationManager authenticationManager;
	private final TokenService tokenService;

	private final UserRepository userRepository;

//	private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

	private final CookieHelper cookieHelper;

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
		throws AuthenticationException
	{
		System.out.println("JwtAuthenticationFilter 로그인 시도 중 ");
		try {

			// 1. username, password 받기
			SignInRequest signInRequest = getSignInRequest(request.getInputStream());

			// ** refreshToken 이 없다면? 추가 하기

			// username, password 를 통해서 인증 객체 생성
			// 2. 로그인 시도 > authenticationManager 로 로그인 시도 > PrincipalDetailsService 호출 후 loadByUsername 자동 호출
			UsernamePasswordAuthenticationToken authenticationToken = createUsernamePasswordAuthenticationToken(signInRequest);

			// PrincipalDetailsService 의 loadByUsername 함수 실행
			Authentication authentication = authenticationManager.authenticate(authenticationToken);

			// 3. PrincipalDetails 를 세션에 담고
			// authentication 객체가 session 영역에 저장됨
			// PrincipalDetails principalDetails = (PrincipalDetails)authentication.getPrincipal();

			// 4. Jwt 토큰을 만들어서 응답
			return authentication;

		} catch (IOException e) {
			e.printStackTrace();
			// throw new RuntimeException(e);
			return super.attemptAuthentication(request, response);
		}

	}

	private SignInRequest getSignInRequest(ServletInputStream inputStream) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(inputStream, SignInRequest.class);
	}

	private UsernamePasswordAuthenticationToken createUsernamePasswordAuthenticationToken(SignInRequest signInRequest) {
		return new UsernamePasswordAuthenticationToken(
			signInRequest.getUsername(), signInRequest.getPassword());
	}

	@Override
	public void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws
		ServletException,
		IOException {

		PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();

		System.out.println(principalDetails.getUsername());
		User loginUser = userRepository.findById(principalDetails.getUserId()).orElseThrow();

		String grantedAuthority = authResult.getAuthorities().stream()
			.map(GrantedAuthority::getAuthority)
			.findAny()
			.orElseThrow()
			.toString();

//		String access = tokenService.generateAccessToken(authResult.getPrincipal().toString(), grantedAuthority);

		String access = tokenService.generateAccessToken(principalDetails.getUsername(), grantedAuthority);
		String refresh = tokenService.generateRefreshToken(principalDetails.getUsername());

		TokenAndUserResponse tokenAndUserResponse = TokenAndUserResponse.builder()
				.user(loginUser)
				.accessToken(access)
				.accessTokenAge(tokenService.getAccessTokenTTlSecond())
				.refreshTokenAge(tokenService.getRefreshTokenTTlSecond())
				.refreshToken(refresh)
				.build();
		// 쿠키 생성 및 HTTP 헤더에 추가
		Map<String, ResponseCookie> cookies = cookieHelper.getTokenCookies(tokenAndUserResponse);
		response.addHeader(PalagoStatic.AUTH_HEADER, PalagoStatic.BEARER + access);

		response.addHeader(HttpHeaders.SET_COOKIE, cookies.get(PalagoStatic.ACCESS_TOKEN).toString());
		response.addHeader(HttpHeaders.SET_COOKIE, cookies.get(PalagoStatic.REFRESH_TOKEN).toString());

		response.sendRedirect("/api/group/event");
		System.out.println("---success authentication---");

//		customAuthenticationSuccessHandler.onAuthenticationSuccess(request, response, authResult);
//			this.getSuccessHandler().onAuthenticationSuccess(request, response, authResult);
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request,
		HttpServletResponse response, AuthenticationException failed)
		throws IOException, ServletException {
		super.unsuccessfulAuthentication(request, response, failed);
	}


}
