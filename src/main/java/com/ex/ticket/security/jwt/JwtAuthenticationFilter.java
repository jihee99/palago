	package com.ex.ticket.security.jwt;

	import java.io.IOException;
	import java.util.Date;
	import java.util.stream.Collectors;

	import javax.crypto.SecretKey;

	import org.springframework.security.authentication.AuthenticationManager;
	import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
	import org.springframework.security.core.Authentication;
	import org.springframework.security.core.AuthenticationException;
	import org.springframework.security.core.GrantedAuthority;
	import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

	import com.ex.ticket.auth.PrincipalDetails;
	import com.ex.ticket.common.PalagoStatic;
	import com.ex.ticket.security.SecretHolder;
	import com.ex.ticket.security.handler.CustomAuthenticationSuccessHandler;
	import com.ex.ticket.user.domain.dto.request.SignInRequest;
	import com.ex.ticket.user.domain.entity.User;
	import com.ex.ticket.user.repository.UserRepository;
	import com.fasterxml.jackson.databind.ObjectMapper;

	import io.jsonwebtoken.Claims;
	import io.jsonwebtoken.Jwts;
	import io.jsonwebtoken.SignatureAlgorithm;
	import jakarta.servlet.FilterChain;
	import jakarta.servlet.ServletException;
	import jakarta.servlet.ServletInputStream;
	import jakarta.servlet.http.HttpServletRequest;
	import jakarta.servlet.http.HttpServletResponse;
	import lombok.RequiredArgsConstructor;
	import lombok.extern.slf4j.Slf4j;

	@RequiredArgsConstructor
	@Slf4j
	public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

		private final AuthenticationManager authenticationManager;
		private final TokenService tokenService;

		private final UserRepository userRepository;

		private final CustomAuthenticationSuccessHandler successHandler;

		@Override
		public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException
		{
			System.out.println("JwtAuthenticationFilter 로그인 시도 중 ");

			try {

				// 1. username, password 받기

				SignInRequest signInRequest = getSignInRequest(request.getInputStream());

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
			// System.out.println(principalDetails.getUsername());
			// String authoritiesAsString = principalDetails.getAuthorities().stream()
			// 	.map(GrantedAuthority::getAuthority)
			// 	.collect(Collectors.joining(", "));
			// System.out.println("Authorities: " + authoritiesAsString);

			// String grantedAuthority = authResult.getAuthorities().stream().findAny().orElseThrow().toString();

			String grantedAuthority = authResult.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.findAny()
				.orElseThrow()
				.toString();

			log.info("authResult 로 가져와볼것이다.");
			log.info("{} {}", ((PrincipalDetails)authResult.getPrincipal()).getUsername(), grantedAuthority);
			// String access = tokenService.generateAccessToken(authResult.getPrincipal().toString(), grantedAuthority);

			String access = tokenService.generateAccessToken(principalDetails.getUsername(), grantedAuthority);

			response.addHeader(PalagoStatic.AUTH_HEADER, PalagoStatic.BEARER + access);
			System.out.println("---success authentication---");
			successHandler.onAuthenticationSuccess(request, response, authResult);
		}

		@Override
		protected void unsuccessfulAuthentication(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException failed)
			throws IOException, ServletException {
			super.unsuccessfulAuthentication(request, response, failed);
		}


	}
