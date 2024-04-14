package com.ex.ticket.group.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ex.ticket.common.domain.ResultMap;
import com.ex.ticket.group.domain.dto.request.InviteGroupRequest;
import com.ex.ticket.group.domain.dto.response.GroupDetailResponse;
import com.ex.ticket.group.service.InviteGroupUseCase;
import com.ex.ticket.group.service.UpdateGroupUserRoleUseCase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Tag(name = "2. 그룹관리 API(마스터용)")
@RequestMapping("/api/group/master")
@RestController
@RequiredArgsConstructor
public class GroupMasterController {

	private final InviteGroupUseCase inviteGroupUseCase;
	private final UpdateGroupUserRoleUseCase updateGroupUserRoleUseCase;


	@SneakyThrows
	@Operation(summary = "멤버를 그룹 유저로 초대합니다.")
	@PostMapping("/{groupId}/invite")
	public ResultMap inviteGroup(
		@PathVariable Long groupId, @RequestBody @Valid InviteGroupRequest inviteGroupRequest
	){
		ResultMap result = new ResultMap();
		inviteGroupUseCase.execute(groupId, inviteGroupRequest);
		return result;
	}

}
