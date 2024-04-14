package com.ex.ticket.group.domain.dto.response;


import com.ex.ticket.common.vo.GroupInfoVo;
import com.ex.ticket.group.domain.entity.Group;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GroupResponse {
	@Schema(description = "그룹 프로필")
	@JsonUnwrapped
	private final GroupInfoVo profile;

	@Schema(description = "마스터 유저의 고유 아이디")
	private final Long masterUserId;


	public static GroupResponse of(Group group) {
		return GroupResponse.builder()
			.profile(GroupInfoVo.from(group))
			.masterUserId(group.getMasterUserId())
			.build();
	}
}
