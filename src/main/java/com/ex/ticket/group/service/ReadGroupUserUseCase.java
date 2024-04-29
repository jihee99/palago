package com.ex.ticket.group.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ex.ticket.group.domain.dto.response.GroupUserResponse;
import com.ex.ticket.group.domain.entity.Group;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReadGroupUserUseCase {

	private final CommonGroupService commonGroupService;

	@Transactional
	public GroupUserResponse execute(Long groupId){
		final Group group = commonGroupService.findById(groupId);
		return commonGroupService.toGroupUserResponseExecute(group);
	}
}
