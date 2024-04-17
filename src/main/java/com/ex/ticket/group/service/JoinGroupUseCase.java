package com.ex.ticket.group.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ex.ticket.common.util.UserUtils;
import com.ex.ticket.group.domain.dto.response.GroupDetailResponse;
import com.ex.ticket.group.domain.entity.Group;
import com.ex.ticket.user.domain.entity.AccountRole;
import com.ex.ticket.user.domain.entity.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JoinGroupUseCase {

	private final CommonGroupService commonGroupService;
	private final GroupService groupService;
	private final UserUtils userUtils;


	@Transactional
	public GroupDetailResponse execute(Long groupId) {
		final Long userId = userUtils.getCurrentUserId();
		final User user = userUtils.getCurrentUser();
		final Group group = commonGroupService.findById(groupId);

		user.updateAccountRole(AccountRole.MANAGER);
		return commonGroupService.toGroupDetailResponseExecute(groupService.activateGroupUser(group, userId));
	}

}
