package com.ex.ticket.event.domain.dto.response;

import com.ex.ticket.common.vo.EventBasicVo;
import com.ex.ticket.common.vo.EventDetailVo;
import com.ex.ticket.common.vo.GroupInfoVo;
import com.ex.ticket.event.domain.entity.Event;
import com.ex.ticket.event.domain.entity.EventStatus;
import com.ex.ticket.group.domain.entity.Group;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EventDetailResponse {
	private EventStatus status;
	private GroupInfoVo group;
	@JsonUnwrapped
	private EventBasicVo eventBasicVo;
	@JsonUnwrapped
	private EventDetailVo eventDetailVo;
	public static EventDetailResponse of(Group group, Event event) {
		return EventDetailResponse.builder()
			.eventBasicVo(event.toEventBasicVo())
			.eventDetailVo(event.toEventDetailVo())
			.group(group.toGroupInfoVo())
			.status(event.getStatus())
			.build();
	}
}
