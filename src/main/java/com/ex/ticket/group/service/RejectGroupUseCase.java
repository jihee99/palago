package com.ex.ticket.group.service;

import org.springframework.stereotype.Service;

import com.ex.ticket.common.util.UserUtils;
import com.ex.ticket.group.domain.dto.response.GroupDetailResponse;
import com.ex.ticket.group.domain.entity.Group;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RejectGroupUseCase {
	private final CommonGroupService commonGroupService;
	private final GroupService groupService;
	private final UserUtils userUtils;

	public GroupDetailResponse execute(Long groupId) {
		final Long userId = userUtils.getCurrentUserId();
		final Group group = commonGroupService.findById(groupId);

		return commonGroupService.toGroupDetailResponseExecute(groupService.removeGroupUser(group, userId));
	}
}
