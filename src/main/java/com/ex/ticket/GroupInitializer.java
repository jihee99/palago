 package com.ex.ticket;

 import com.ex.ticket.event.domain.entity.Event;
 import com.ex.ticket.event.repository.EventRepository;
 import com.ex.ticket.group.domain.entity.Group;
 import com.ex.ticket.group.domain.entity.GroupUser;
 import com.ex.ticket.group.domain.entity.GroupUserRole;
 import com.ex.ticket.group.repository.GroupRepository;
 import com.ex.ticket.group.service.CreateGroupUseCase;
 import lombok.RequiredArgsConstructor;
 import org.springframework.boot.ApplicationArguments;
 import org.springframework.boot.ApplicationRunner;
 import org.springframework.stereotype.Component;

 import java.time.LocalDateTime;
 import java.time.temporal.ChronoUnit;
 import java.util.Set;

 @RequiredArgsConstructor
 @Component
 public class GroupInitializer implements ApplicationRunner {

 	private final GroupRepository groupRepository;
 	private final EventRepository eventRepository;

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
 		// group.getGroupUsers().forEach(GroupUser::activate);
 		// groupRepository.save(group);

		final GroupUser managerGroupUser = toManagerGroupUser(group, 2L);
		group.addGroupUsers(Set.of(managerGroupUser));
		group.getGroupUsers().forEach(GroupUser::activate);
		groupRepository.save(group);

		LocalDateTime currentTime = LocalDateTime.now();
		LocalDateTime thirtyMinutesLater = currentTime.plus(30, ChronoUnit.MINUTES);

		 Event event = Event.builder()
				 .groupId(group.getId())
				 .name("테스트용이벤트")
				 .startAt(thirtyMinutesLater)
				 .runTime(60L)
				 .build();

		 eventRepository.save(event);
 	}

 	public GroupUser toMasterGroupUser(Group group, Long userId) {
 		return GroupUser.builder().userId(userId).group(group).role(GroupUserRole.MASTER).build();
 	}

	 public GroupUser toManagerGroupUser(Group group, Long userId) {
		 return GroupUser.builder().userId(userId).group(group).role(GroupUserRole.MANAGER).build();
	 }

 }
