package com.ex.ticket.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ex.ticket.user.domain.entity.User;
import com.ex.ticket.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

// localhot:port/login >> 로그인 요청이 올 때 동작한다.

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		System.out.println(email);
		System.out.println("PrincipalDetailsService의 loadUserByUsername");

		User user = userRepository.findByEmail(email).orElseThrow();
		user.login();

		userRepository.save(user);
		return new PrincipalDetails(user);
	}

}
