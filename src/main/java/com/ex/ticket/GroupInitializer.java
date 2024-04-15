package com.ex.ticket;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.ex.ticket.group.domain.entity.Group;
import com.ex.ticket.group.repository.GroupRepository;
import com.ex.ticket.user.domain.entity.AccountRole;
import com.ex.ticket.user.domain.entity.AccountState;
import com.ex.ticket.user.domain.entity.User;
import com.ex.ticket.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class GroupInitializer implements ApplicationRunner {

	private final GroupRepository groupRepository;

	@Override
	public void run(ApplicationArguments args) {

	   Group group = Group.builder()
		   .name("테스트 그룹")
		   .introduce("테스트용 그룹입니다.")
		   .contactEmail("master1@test.com")
		   .contactNumber("010-1234-1234")
		   .masterUserId(3L)
		   .build();

	   groupRepository.save(group);


	}
}
