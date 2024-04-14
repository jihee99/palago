package com.ex.ticket.group.service;

import org.springframework.stereotype.Service;

import com.ex.ticket.group.domain.dto.request.InviteGroupRequest;
import com.ex.ticket.group.domain.dto.response.GroupDetailResponse;
import com.ex.ticket.group.domain.entity.Group;
import com.ex.ticket.group.domain.entity.GroupUser;
import com.ex.ticket.group.domain.entity.GroupUserRole;
import com.ex.ticket.user.domain.entity.User;
import com.ex.ticket.user.service.CommonUserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InviteGroupUseCase {

	private final CommonGroupService commonGroupService;
	private final GroupService groupService;
	private final CommonUserService commonUserService;
	//TODO
	// private final GroupUserInvitationEmailService invitationEmailService;

	public GroupDetailResponse execute(Long groupId, InviteGroupRequest inviteGroupRequest) {
		final Group group = commonGroupService.findById(groupId);
		final User inviteUser = commonUserService.queryUserByEmail(inviteGroupRequest.getEmail());
		final Long invitedUserId = inviteUser.getUserId();
		final GroupUserRole role = inviteGroupRequest.getRole();

		final GroupUser groupUser = toGroupUser(groupId, invitedUserId, role);
//		 invitationEmailService.execute(inviteMember.toEmailUserInfo(), group.toGroupProfileVo().getName(), role);

		return commonGroupService.toGroupDetailResponseExecute(groupService.inviteGroupUser(group, groupUser));
	}

	public GroupUser toGroupUser(Long groupId, Long userId, GroupUserRole role) {
		final Group group = commonGroupService.findById(groupId);
		return GroupUser.builder().userId(userId).group(group).role(role).build();
	}


}
