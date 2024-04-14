package com.ex.ticket.common.vo;

import com.ex.ticket.group.domain.entity.GroupUser;
import com.ex.ticket.group.domain.entity.GroupUserRole;
import com.ex.ticket.user.domain.entity.User;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GroupUserVo {
	@JsonUnwrapped
	private final UserInfoVo userInfoVo;
	private final GroupUserRole role;
	private final Boolean active;

	public static GroupUserVo from(User user, GroupUser groupUser) {
		return GroupUserVo.builder()
			.userInfoVo(user.toUserInfoVo())
			.active(groupUser.getActive())
			.role(groupUser.getRole())
			.build();
	}

	public static GroupUserVo from(UserInfoVo userInfoVo, GroupUser groupUser) {
		return GroupUserVo.builder()
			.userInfoVo(userInfoVo)
			.active(groupUser.getActive())
			.role(groupUser.getRole())
			.build();
	}

}
