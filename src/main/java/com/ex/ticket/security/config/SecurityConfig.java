package com.ex.ticket.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
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
import org.springframework.security.web.context.SecurityContextHolderFilter;
import org.springframework.web.filter.CorsFilter;

import com.ex.ticket.security.filter.MyFilter3;
import com.ex.ticket.security.jwt.JwtAuthenticationFilter;
import com.ex.ticket.security.jwt.JwtAuthorizationFilter;
import com.ex.ticket.security.jwt.TokenService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final String[] allowedUrls = {"/swagger-ui/**", "/join", "/join/us", "/login", "/login/us", "/error", "/ticket/**"};
	private final CorsFilter corsFilter;


	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	private final AuthenticationConfiguration authenticationConfiguration;

	private final TokenService tokenService;


	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http
			// .addFilterBefore(new MyFilter3(), SecurityContextHolderFilter.class)

			.csrf(CsrfConfigurer<HttpSecurity>::disable)
			.httpBasic((httpBasic) -> httpBasic.disable())

			.sessionManagement(sessionManagement  -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

			.addFilter(corsFilter)
			.formLogin(AbstractHttpConfigurer::disable)

			.addFilterBefore(new JwtAuthenticationFilter(authenticationManager(authenticationConfiguration), tokenService),
				UsernamePasswordAuthenticationFilter.class)

			.addFilterBefore(new JwtAuthorizationFilter(authenticationManager(authenticationConfiguration)),
				BasicAuthenticationFilter.class)

			// AuthenticationManager 을 필수로 전달해줘야함

			.authorizeHttpRequests(requests -> requests
				// .requestMatchers(allowedUrls).permitAll()

				.requestMatchers("/api/mypage/**").hasRole("USER")
				.requestMatchers("/api/group/master/**").hasRole("MASTER")
				.requestMatchers("/api/group/ticket/**").hasAnyRole("MASTER", "MANAGER")
				.requestMatchers("/api/group/event/**").hasAnyRole("MASTER", "MANAGER")
				.requestMatchers("/api/system/**").hasRole("ADMIN")

				// .anyRequest().authenticated()
				.anyRequest().permitAll()
			);

		return http.build();

	}


}
