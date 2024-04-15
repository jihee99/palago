package com.ex.ticket.event.service;

import com.ex.ticket.common.util.UserUtils;
import com.ex.ticket.event.domain.dto.request.CreateEventRequest;
import com.ex.ticket.event.domain.dto.response.EventResponse;
import com.ex.ticket.event.domain.entity.Event;
import com.ex.ticket.event.domain.entity.EventMapper;
import com.ex.ticket.group.domain.entity.Group;
import com.ex.ticket.group.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateEventUseCase {
    private final UserUtils userUtils;
    private final GroupService groupService;
    private final EventService eventService;
    private final EventMapper eventMapper;

    public EventResponse execute(CreateEventRequest createEventRequest) {
        final Long userId = userUtils.getCurrentMemberId();
        System.out.println(userId);
        final Group group  = groupService.findAllByMasterUserId(userId);
        createEventRequest.setGroupId(group.getId());

        // 매니저 이상만 생성 가능
        groupService.validateManagerGroupUser(createEventRequest.getGroupId(), userId);
        final Event event = eventMapper.toEntity(createEventRequest);

        return EventResponse.of(eventService.createEvent(event));
    }
}
