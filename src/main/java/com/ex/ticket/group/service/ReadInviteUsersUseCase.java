package com.ex.ticket.group.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ex.ticket.common.vo.UserProfileVo;
import com.ex.ticket.group.domain.entity.Group;
import com.ex.ticket.group.exception.AlreadyJoinedGroupException;
import com.ex.ticket.user.domain.entity.User;
import com.ex.ticket.user.service.CommonUserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReadInviteUsersUseCase {

    private final CommonGroupService commonGroupService;
    private final CommonUserService commonUserService;

    @Transactional(readOnly = true)
    public UserProfileVo execute(Long groupId, String email) {
        return toGroupInviteUserList(groupId, email);
    }

    public UserProfileVo toGroupInviteUserList(Long groupId, String email) {
        final Group group = commonGroupService.findById(groupId);
        final User inviteUser = commonUserService.queryUserByEmail(email);
        if (group.hasGroupUserId(inviteUser.getUserId())) {
            throw AlreadyJoinedGroupException.EXCEPTION;
        }
        return inviteUser.toUserProfileVo();
    }
}
