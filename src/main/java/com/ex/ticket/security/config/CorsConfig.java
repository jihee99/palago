package com.ex.ticket.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

//	@Bean
//	public CorsFilter corsFilter() {
//		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//		CorsConfiguration config = new CorsConfiguration();
//
//		config.setAllowCredentials(true);
//		config.addAllowedOrigin("*");
//		config.addAllowedHeader("*");
//		config.addAllowedMethod("*");
//
//		source.registerCorsConfiguration("/**", config);
//		return new CorsFilter(source);
//	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		ArrayList<String> allowedOriginPatterns = new ArrayList<>();
		allowedOriginPatterns.add("http://localhost:8080");
		allowedOriginPatterns.add("http://localhost:8080");
//		if (!springEnvironmentHelper.isProdProfile()) {
//			allowedOriginPatterns.add("http://localhost:3000");
//			allowedOriginPatterns.add("http://localhost:5173");
//		}
		String[] patterns = allowedOriginPatterns.toArray(String[]::new);
		registry.addMapping("/**")
				.allowedMethods("*")
				.allowedOriginPatterns(patterns)
				.exposedHeaders("Set-Cookie")
				.allowCredentials(true);
	}
}
