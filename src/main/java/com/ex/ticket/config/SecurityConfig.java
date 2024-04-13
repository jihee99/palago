package com.ex.ticket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final String[] allowedUrls = {"/swagger-ui/**", "/join", "/join/us", "/login", "/login/us", "/error", "/ticket/**"};


	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}


	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.csrf(CsrfConfigurer<HttpSecurity>::disable)
			.httpBasic((httpBasic) -> httpBasic.disable())

			.sessionManagement(sessionManagement  -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

			.formLogin(formLogin -> formLogin
				.loginPage("/login")
				.loginProcessingUrl("/login/us")
				.defaultSuccessUrl("/")
			)

			.authorizeHttpRequests(requests -> requests
				// .requestMatchers(allowedUrls).permitAll()

				.requestMatchers("/mypage/**").authenticated()
				.requestMatchers("/group/master/**").hasRole("MASTER")
				.requestMatchers("/group/ticket/**").hasAnyRole("MASTER", "MANAGER")
				.requestMatchers("/group/event/**").hasAnyRole("MASTER", "MANAGER")
				.requestMatchers("/system/**").hasRole("ADMIN")

				// .anyRequest().authenticated()
				.anyRequest().permitAll()
			);

		return http.build();

	}

}
