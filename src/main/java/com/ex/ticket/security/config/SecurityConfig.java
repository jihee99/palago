package com.ex.ticket.security.config;

import com.ex.ticket.security.CookieHelper;
import com.ex.ticket.security.filter.JwtAuthenticationFilter;
import com.ex.ticket.security.filter.JwtAuthorizationFilter;
import com.ex.ticket.security.filter.JwtExceptionFilter;
import com.ex.ticket.security.handler.CustomAuthenticationSuccessHandler;
import com.ex.ticket.security.jwt.TokenService;
import com.ex.ticket.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final String[] allowedUrls = {"/swagger-ui/**", "/join", "/join/us", "/login/us", "/login", "/logout", "/error", "/event/**"};

	private final UserRepository userRepository;

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	private final AuthenticationConfiguration authenticationConfiguration;

	private final TokenService tokenService;
	private final CookieHelper cookieHelper;

	@Bean
	public CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler() {
		return new CustomAuthenticationSuccessHandler(tokenService);
	}


	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http
			.csrf(CsrfConfigurer<HttpSecurity>::disable)
			.httpBasic((httpBasic) -> httpBasic.disable())
			.sessionManagement(sessionManagement  -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

//			.addFilter(corsFilter)
			.formLogin(AbstractHttpConfigurer::disable)
			.logout(AbstractHttpConfigurer::disable)
			.authorizeHttpRequests(requests -> requests
				.requestMatchers(allowedUrls).permitAll()

				.requestMatchers("/api/mypage/**").hasAuthority("USER")
				.requestMatchers("/api/group/master/**").hasAuthority("MASTER")
				.requestMatchers("/api/group/manage/**").hasAnyAuthority("MASTER", "MANAGER")
				.requestMatchers("/api/group/ticket/**").hasAnyAuthority("MASTER", "MANAGER")
				.requestMatchers("/api/group/event/**").hasAnyAuthority("MASTER", "MANAGER")
				.requestMatchers("/api/system/**").hasAuthority("ADMIN")

				// .anyRequest().authenticated()
				.anyRequest().permitAll()
			)


			.addFilterBefore(new JwtAuthenticationFilter(authenticationManager(authenticationConfiguration), tokenService, userRepository, cookieHelper),
				UsernamePasswordAuthenticationFilter.class)

			.addFilterBefore(new JwtAuthorizationFilter(authenticationManager(authenticationConfiguration), tokenService, userRepository),
				BasicAuthenticationFilter.class)

			.addFilterBefore(new JwtExceptionFilter(new ObjectMapper()), JwtAuthorizationFilter.class);

		return http.build();

	}



}
