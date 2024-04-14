package com.ex.ticket.group.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ex.ticket.group.domain.dto.response.GroupEventProfileResponse;
import com.ex.ticket.group.domain.entity.Group;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReadGroupEventUseCase{
    private final CommonGroupService commonGroupService;

     // private final CommonEventService commonEventService;

    public List<GroupEventProfileResponse> execute(Long groupId) {
        Group group = commonGroupService.findById(groupId);
        //TODO
        return null;
        // return commonEventService.findAllByGroupId(groupId)
        //         .stream().map(event -> GroupEventProfileResponse.of(group, event))
        //         .toList();
    }
}
