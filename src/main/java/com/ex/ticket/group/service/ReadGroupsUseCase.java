package com.ex.ticket.group.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ex.ticket.common.util.UserUtils;
import com.ex.ticket.group.domain.dto.response.GroupProfileResponse;
import com.ex.ticket.user.domain.entity.User;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class ReadGroupsUseCase {
    private final UserUtils userUtils;
    private final GroupService groupService;


    @Transactional(readOnly = true)
    public List<GroupProfileResponse> execute() {
        final User user = userUtils.getCurrentMember();
        final Long userId = user.getUserId();

        return groupService.findAllByGroupUsers_UserId(userId)
            .stream()
            .map(group -> GroupProfileResponse.of(group, userId))
            .collect(Collectors.toList());

    }

}
