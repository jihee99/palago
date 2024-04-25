package com.ex.ticket.event.domain.dto.response;

import com.ex.ticket.common.vo.EventProfileVo;
import com.ex.ticket.event.domain.entity.Event;
import com.ex.ticket.group.domain.entity.Group;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EventProfileResponse {

    private Long groupId;

    private String groupName;

    @JsonUnwrapped private EventProfileVo eventProfileVo;

    public static EventProfileResponse of(Group group, Event event) {
        return EventProfileResponse.builder()
                .groupId(group.getId())
                .groupName(group.getProfile().getName())
                .eventProfileVo(event.toEventProfileVo())
                .build();
    }

}
