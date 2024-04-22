package com.ex.ticket.group.service;

import com.ex.ticket.event.service.CommonEventService;
import com.ex.ticket.group.domain.dto.response.GroupEventProfileResponse;
import com.ex.ticket.group.domain.entity.Group;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReadGroupEventUseCase{
    private final CommonGroupService commonGroupService;

      private final CommonEventService commonEventService;

    public List<GroupEventProfileResponse> execute(Long groupId) {
        Group group = commonGroupService.findById(groupId);
//        List<Event> events = commonEventService.findAllByGroupId(groupId);

         return commonEventService.findAllByGroupId(groupId)
                 .stream().map(event -> GroupEventProfileResponse.of(group, event))
                 .toList();
    }
}
