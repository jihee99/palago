package com.ex.ticket.group.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ex.ticket.group.domain.dto.response.GroupDetailResponse;
import com.ex.ticket.group.domain.entity.Group;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReadGroupUseCase {

    private final GroupService groupService;
    private final CommonGroupService commonGroupService;

    @Transactional(readOnly = true)
    public GroupDetailResponse execute(Long groupId) {
        final Group group = commonGroupService.findById(groupId);
        return commonGroupService.toGroupDetailResponseExecute(group);
    }

}
