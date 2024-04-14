package com.ex.ticket.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.SecurityContextHolderFilter;
import org.springframework.web.filter.CorsFilter;

import com.ex.ticket.security.filter.MyFilter3;

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


	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.addFilterBefore(new MyFilter3(), SecurityContextHolderFilter.class)

			.csrf(CsrfConfigurer<HttpSecurity>::disable)
			.httpBasic((httpBasic) -> httpBasic.disable())

			.sessionManagement(sessionManagement  -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

			.addFilter(corsFilter)
			.formLogin(AbstractHttpConfigurer::disable)
			// .formLogin(formLogin -> formLogin
			// 	.loginPage("/login")
			// 	.loginProcessingUrl("/login/us")
			// 	.defaultSuccessUrl("/")
			// )

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