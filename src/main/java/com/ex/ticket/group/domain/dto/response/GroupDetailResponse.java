package com.ex.ticket.group.domain.dto.response;

import java.util.ArrayList;
import java.util.List;

import com.ex.ticket.common.vo.GroupInfoVo;
import com.ex.ticket.common.vo.GroupUserVo;
import com.ex.ticket.group.domain.entity.Group;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GroupDetailResponse {

	@Schema(description = "그룹 정보")
	@JsonUnwrapped
	private final GroupInfoVo groupInfo;

	@Schema(description = "마스터 유저의 정보")
	private final GroupUserVo masterUser;

	@Schema(description = "그룹 유저 정보")
	private final List<GroupUserVo> groupUsers;

	public static GroupDetailResponse of(Group group, List<GroupUserVo> groupUserVoSet) {
		GroupDetailResponseBuilder builder = GroupDetailResponse.builder();
		List<GroupUserVo> groupUserVoList = new ArrayList<>();
		groupUserVoSet.forEach(
				groupUserVo -> {
				if (groupUserVo.getUserInfoVo().getUserId().equals(group.getMasterUserId())) {
					builder.masterUser(groupUserVo);
				} else {
					groupUserVoList.add(groupUserVo);
				}
			});

		return builder.groupInfo(GroupInfoVo.from(group))
			.groupUsers(groupUserVoList)
			.build();
	}

}
