package com.ex.ticket;

import com.ex.ticket.group.domain.entity.Group;
import com.ex.ticket.group.domain.entity.GroupUser;
import com.ex.ticket.group.domain.entity.GroupUserRole;
import com.ex.ticket.group.repository.GroupRepository;
import com.ex.ticket.group.service.CreateGroupUseCase;
import com.ex.ticket.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Set;

@RequiredArgsConstructor
@Component
public class GroupInitializer implements ApplicationRunner {

	private final GroupRepository groupRepository;
	private final UserRepository userRepository;

	private final CreateGroupUseCase createGroupUseCase;
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
		final GroupUser masterGroupUser = toMasterGroupUser(group, 3L);
		group.addGroupUsers(Set.of(masterGroupUser));
		group.getGroupUsers().forEach(GroupUser::activate);
		groupRepository.save(group);

	}

	public GroupUser toMasterGroupUser(Group group, Long userId) {
		return GroupUser.builder().userId(userId).group(group).role(GroupUserRole.MASTER).build();
	}

}
