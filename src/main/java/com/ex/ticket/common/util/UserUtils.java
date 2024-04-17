package com.ex.ticket.common.util;

import com.ex.ticket.user.domain.entity.User;
import com.ex.ticket.user.exception.SecurityContextNotFoundException;
import com.ex.ticket.user.exception.UserNotFoundException;
import com.ex.ticket.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserUtils {

	private static SimpleGrantedAuthority anonymous = new SimpleGrantedAuthority("ROLE_ANONYMOUS");
	private static SimpleGrantedAuthority swagger = new SimpleGrantedAuthority("ROLE_SWAGGER");
	private static List<SimpleGrantedAuthority> notUserAuthority = List.of(anonymous, swagger);
	private final UserRepository userRepository;

	public Long getCurrentUserId(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null) {
			throw SecurityContextNotFoundException.EXCEPTION;
		}

		if (authentication.isAuthenticated() && !CollectionUtils.containsAny(authentication.getAuthorities(), notUserAuthority)) {
			User user = userRepository.findByEmail(authentication.getName())
					.orElseThrow(() -> UserNotFoundException.EXCEPTION);
			return user.getUserId();
		}
		// 스웨거 유저일시 익명 유저 취급
		// 익명유저시 userId 0 반환
		return 0L;
	}

	public User getCurrentUser(){
		return userRepository.findById(getCurrentUserId()).orElseThrow(() -> UserNotFoundException.EXCEPTION);
	}

}
