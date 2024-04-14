package com.ex.ticket.group.service;

import org.springframework.stereotype.Service;

import com.ex.ticket.group.domain.dto.request.UpdateGroupUserRoleRequest;
import com.ex.ticket.group.domain.dto.response.GroupDetailResponse;
import com.ex.ticket.group.domain.entity.Group;
import com.ex.ticket.group.domain.entity.GroupUserRole;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UpdateGroupUserRoleUseCase {
	private final CommonGroupService commonGroupService;
	private final GroupService groupService;

	public GroupDetailResponse execute(Long groupId, UpdateGroupUserRoleRequest updateGroupUserRoleRequest) {
		final Group group = commonGroupService.findById(groupId);
		final Long updateUserId = updateGroupUserRoleRequest.getUserId();

		final GroupUserRole updateUserRole = updateGroupUserRoleRequest.getRole();
		return commonGroupService.toGroupDetailResponseExecute(groupService.updateGroupUserRole(group, updateUserId, updateUserRole));
	}

}
