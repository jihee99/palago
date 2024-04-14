package com.ex.ticket.group.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ex.ticket.group.domain.dto.request.UpdateGroupRequest;
import com.ex.ticket.group.domain.dto.response.GroupDetailResponse;
import com.ex.ticket.group.domain.entity.Group;
import com.ex.ticket.group.domain.entity.GroupProfile;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UpdateGroupProfileUseCase {
	private final CommonGroupService commonGroupService;
	private final GroupService groupService;

	@Transactional
	public GroupDetailResponse execute(Long groupId, UpdateGroupRequest updateGroupRequest) {
		final Group group = commonGroupService.findById(groupId);
		return commonGroupService.toGroupDetailResponseExecute(
			groupService.updateGroupProfile(group, toGroupProfile(updateGroupRequest)));
	}

	public GroupProfile toGroupProfile(UpdateGroupRequest updateGroupRequest) {
		return GroupProfile.builder()
			.introduce(updateGroupRequest.getIntroduce())
			.contactEmail(updateGroupRequest.getContactEmail())
			.contactNumber(updateGroupRequest.getContactNumber())
			.build();
	}
}
