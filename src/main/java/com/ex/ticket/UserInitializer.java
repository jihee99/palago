package com.ex.ticket;

import java.time.LocalDateTime;


import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.ex.ticket.user.domain.entity.AccountRole;
import com.ex.ticket.user.domain.entity.AccountState;
import com.ex.ticket.user.domain.entity.User;
import com.ex.ticket.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class UserInitializer implements ApplicationRunner {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

   @Override
   public void run(ApplicationArguments args) {

		User user = User.builder()
			// .username("test1")test1
			.email("user-test1@test.com")
			.password(passwordEncoder.encode("1234"))
			.name("테스트사용자1")
			.accountRole(AccountRole.USER)
			.accountState(AccountState.NORMAL)
			.build();


	   userRepository.save(user);

   }
}
