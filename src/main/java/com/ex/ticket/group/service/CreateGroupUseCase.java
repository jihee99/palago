package com.ex.ticket.group.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ex.ticket.common.util.UserUtils;
import com.ex.ticket.group.domain.dto.request.CreateGroupRequest;
import com.ex.ticket.group.domain.dto.response.GroupResponse;
import com.ex.ticket.group.domain.entity.Group;
import com.ex.ticket.group.domain.entity.GroupUser;
import com.ex.ticket.group.domain.entity.GroupUserRole;
import com.ex.ticket.user.domain.entity.AccountRole;
import com.ex.ticket.user.domain.entity.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateGroupUseCase {

	private final UserUtils userUtils;
	private final GroupService groupService;
	private final CommonGroupService commonGroupService;

	@Transactional
	public GroupResponse execute(CreateGroupRequest createGroupRequest) {
		// 존재하는 유저인지 검증
		final User user = userUtils.getCurrentMember();
		final Long id = user.getUserId();
		// 호스트 생성
		final Group group = groupService.createGroup( Group.toEntity(createGroupRequest, id));
		// 생성한 유저를 마스터 권한으로 등록
		final GroupUser masterGroupUser = toMasterGroupUser(group.getId(), id);

		// 즉시 활성화
		user.updateAccountRole(AccountRole.MASTER);
		masterGroupUser.activate();
		return GroupResponse.of(groupService.addGroupUser(group, masterGroupUser));
	}


	public GroupUser toMasterGroupUser(Long groupId, Long userId) {
		final Group group = commonGroupService.findById(groupId);
		return GroupUser.builder().userId(userId).group(group).role(GroupUserRole.MASTER).build();
	}
}
