package com.ex.ticket.group.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ex.ticket.common.vo.GroupUserVo;
import com.ex.ticket.common.vo.UserInfoVo;
import com.ex.ticket.group.domain.dto.response.GroupDetailResponse;
import com.ex.ticket.group.domain.dto.response.GroupUserResponse;
import com.ex.ticket.group.domain.entity.Group;
import com.ex.ticket.group.domain.entity.GroupUser;
import com.ex.ticket.group.domain.entity.GroupUserRole;
import com.ex.ticket.group.exception.GroupNotFoundException;
import com.ex.ticket.group.repository.GroupRepository;
import com.ex.ticket.user.domain.entity.User;
import com.ex.ticket.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommonGroupService {

	private final UserRepository userRepository;
	private final GroupRepository groupRepository;

	public Group findById(Long groupId) {
		return groupRepository.findById(groupId).orElseThrow(() -> GroupNotFoundException.EXCEPTION);
	}

	public GroupUser toGroupUser(Long groupId, Long userId, GroupUserRole role) {
		final Group group = groupRepository.findById(groupId).orElseThrow(() -> GroupNotFoundException.EXCEPTION);
		return GroupUser.builder().userId(userId).group(group).role(role).build();
	}

	public GroupDetailResponse toGroupDetailResponseExecute(Group group) {
		final List<Long> userIds = group.getGroupUser_UserIds();
		final List<User> userList = userRepository.findAllByUserIdIn(userIds);
		final Map<Long, User> userMap =
			userList.stream().collect(Collectors.toMap(User::getUserId, user -> user));
		final List<GroupUserVo> groupUserVoList = new ArrayList<>();

		for (Long userId : userIds) {
			final User user = userMap.get(userId);
			if (user != null) {
				final UserInfoVo userInfoVo = user.toUserInfoVo();
				final GroupUser groupUser = group.getGroupUserByUserId(userId);
				final GroupUserVo groupUserVo = GroupUserVo.from(userInfoVo, groupUser);
				groupUserVoList.add(groupUserVo);
			}
		}
		return GroupDetailResponse.of(group, groupUserVoList);
	}

	public GroupUserResponse toGroupUserResponseExecute(Group group){
		final List<Long> userIds = group.getGroupUser_UserIds();
		final List<User> userList = userRepository.findAllByUserIdIn(userIds);
		final Map<Long, User> userMap =
			userList.stream().collect(Collectors.toMap(User::getUserId, user -> user));
		final List<GroupUserVo> groupUserVoList = new ArrayList<>();

		for (Long userId : userIds) {
			final User user = userMap.get(userId);
			if (user != null) {
				final UserInfoVo userInfoVo = user.toUserInfoVo();
				final GroupUser groupUser = group.getGroupUserByUserId(userId);
				final GroupUserVo groupUserVo = GroupUserVo.from(userInfoVo, groupUser);
				groupUserVoList.add(groupUserVo);
			}
		}

		return GroupUserResponse.of(group, groupUserVoList);
	}

}
