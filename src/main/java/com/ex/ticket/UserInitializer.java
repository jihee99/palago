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
			.email("user1@test.com")
			.password(passwordEncoder.encode("1234"))
			.name("테스트사용자1")
			.accountRole(AccountRole.USER)
			.accountState(AccountState.NORMAL)
			.build();

	   User manager = User.builder()
		   // .username("test1")test1
		   .email("manager1@test.com")
		   .password(passwordEncoder.encode("1234"))
		   .name("테스트매니저1")
		   .accountRole(AccountRole.MANAGER)
		   .accountState(AccountState.NORMAL)
		   .build();

	   User master = User.builder()
		   // .username("test1")test1
		   .email("master1@test.com")
		   .password(passwordEncoder.encode("1234"))
		   .name("테스트마스터1")
		   .accountRole(AccountRole.MASTER)
		   .accountState(AccountState.NORMAL)
		   .build();

	   User admin = User.builder()
		   // .username("test1")test1
		   .email("admin@system.com")
		   .password(passwordEncoder.encode("1234"))
		   .name("관리자")
		   .accountRole(AccountRole.ADMIN)
		   .accountState(AccountState.NORMAL)
		   .build();

	   userRepository.save(user);
	   userRepository.save(manager);
	   userRepository.save(master);
	   userRepository.save(admin);

   }
}
