package com.ex.ticket.group.controller;

import com.ex.ticket.common.annotation.MasterAuthorize;
import com.ex.ticket.common.annotation.UserAuthorize;
import com.ex.ticket.common.vo.UserProfileVo;
import com.ex.ticket.group.domain.dto.request.UpdateGroupRequest;
import com.ex.ticket.group.domain.dto.response.GroupDetailResponse;
import com.ex.ticket.group.domain.dto.response.GroupEventProfileResponse;
import com.ex.ticket.group.domain.dto.response.GroupProfileResponse;
import com.ex.ticket.group.domain.dto.response.GroupUserResponse;
import com.ex.ticket.group.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Tag(name = "2. 그룹관리 API(매니저용)")
@RequestMapping("/api/group/manage")
@RestController
@RequiredArgsConstructor
public class GroupController {

	private final JoinGroupUseCase joinGroupUseCase;
	private final RejectGroupUseCase rejectGroupUseCase;
	private final UpdateGroupProfileUseCase updateGroupProfileUseCase;
	private final ReadInviteUsersUseCase readInviteUsersUseCase;
	private final ReadGroupEventUseCase readGroupEventsUseCase;
	private final ReadGroupsUseCase readGroupsUseCase;
	private final ReadGroupUseCase readGroupUseCase;
	private final ReadGroupUserUseCase readGroupUserUseCase;

	@Operation(summary = "내가 속한 그룹 리스트를 가져옵니다.")
	@GetMapping
	@PreAuthorize("hasAuthority('MASTER') or hasAuthority('MANAGER')")
	public List<GroupProfileResponse> getAllGroups() {
		List<GroupProfileResponse> list = readGroupsUseCase.execute();
		log.info("{}", list.stream().toList());
		return readGroupsUseCase.execute();
	}

	@Operation(summary = "그룹에 속한 매니저 리스트를 가져옵니다.")
	@GetMapping("/{groupId}/list")
	@MasterAuthorize
	public GroupUserResponse getAllGroupUsers(@PathVariable Long groupId) {
		return readGroupUserUseCase.execute(groupId);
	}

	@Operation(summary = "고유 아이디에 해당하는 그룹 정보를 가져옵니다.")
	@GetMapping("/{groupId}")
	public GroupDetailResponse getGroupById(@PathVariable Long groupId) {
		return readGroupUseCase.execute(groupId);
	}

	@Operation(summary = "해당 그룹에 가입하지 않은 유저를 이메일로 검색합니다.")
	@GetMapping("/{groupId}/invite/users")
	@MasterAuthorize
	public UserProfileVo getInviteUserListByEmail(
		@PathVariable Long groupId, @RequestParam(value = "email") @Email String email) {
		return readInviteUsersUseCase.execute(groupId, email);
	}

	/* 초대받은 유저 그룹 가입 api */
	@Operation(summary = "초대받은 그룹에 가입을 승인힙니다.")
	@PostMapping("/{groupId}/join")
	@UserAuthorize
	public GroupDetailResponse joinGroup(@PathVariable Long groupId) {
		return joinGroupUseCase.execute(groupId);
	}

	/* 초대받은 유저 그룹 가입 거절 api */
	@Operation(summary = "초대받은 그룹에 가입을 거절합니다.")
	@PostMapping("/{groupId}/reject")
	@UserAuthorize
	public GroupDetailResponse rejectGroup(@PathVariable Long groupId) {
		return rejectGroupUseCase.execute(groupId);
	}

	/* 그룹 정보 업데이트 api (단, 매니저이상부터 가능) */
	@Operation(summary = "그룹 정보를 업데이트 합니다. 매니저 이상부터 가능")
	@PostMapping("/{groupId}/profile")
	@PreAuthorize("hasAuthority('MASTER') or hasAuthority('MANAGER')")
	public GroupDetailResponse patchGroupById(
		@PathVariable Long groupId, @RequestBody @Valid UpdateGroupRequest updateGroupRequest
	) {
		return updateGroupProfileUseCase.execute(groupId, updateGroupRequest);
	}

	@Operation(summary = "해당 그룹에서 관리중인 이벤트 리스트를 가져옵니다.")
	@GetMapping("/{groupId}/events")
	public List<GroupEventProfileResponse> getHostEventsById (
		@PathVariable(name = "groupId") Long groupId
	) {
		return readGroupEventsUseCase.execute(groupId);
	}


}

