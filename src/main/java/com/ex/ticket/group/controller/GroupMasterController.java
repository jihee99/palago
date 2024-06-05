package com.ex.ticket.group.controller;

import com.ex.ticket.common.annotation.MasterAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ex.ticket.common.domain.ResultMap;
import com.ex.ticket.group.domain.dto.request.CreateGroupRequest;
import com.ex.ticket.group.domain.dto.request.InviteGroupRequest;
import com.ex.ticket.group.domain.dto.request.UpdateGroupUserRoleRequest;
import com.ex.ticket.group.domain.dto.response.GroupDetailResponse;
import com.ex.ticket.group.domain.dto.response.GroupResponse;
import com.ex.ticket.group.service.CreateGroupUseCase;
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
@MasterAuthorize
@RequiredArgsConstructor
public class GroupMasterController {
	private final CreateGroupUseCase createGroupUseCase;
	private final InviteGroupUseCase inviteGroupUseCase;
	private final UpdateGroupUserRoleUseCase updateGroupUserRoleUseCase;

	@Operation(summary = "그룹 간편 생성. 그룹을 생성한 유저는 마스터 사용자가 됩니다.")
	@PostMapping
	public GroupResponse createGroup(@RequestBody @Valid CreateGroupRequest createEventRequest) {
		return createGroupUseCase.execute(createEventRequest);
	}

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

	/* 그룹 유저의 권한을 변경하는 api (단, 마스터만 가능) */
	@Operation(summary = "그룹 유저의 권한을 변경합니다. 마스터 이상만 가능합니다.")
	@PostMapping("/{groupId}/role")
	public GroupDetailResponse patchGroupUserRole(
		@PathVariable Long groupId,
		@RequestBody @Valid UpdateGroupUserRoleRequest updateGroupUserRoleRequest
	) {
		return updateGroupUserRoleUseCase.execute(groupId, updateGroupUserRoleRequest);
	}

	/* 그룹 삭제 api */

}
