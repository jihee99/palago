package com.ex.ticket.event.domain.dto.response;

import com.ex.ticket.common.vo.EventBasicVo;
import com.ex.ticket.common.vo.EventDetailVo;
import com.ex.ticket.event.domain.entity.Event;
import com.ex.ticket.event.domain.entity.EventStatus;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EventResponse {

	private Long eventId;

	private Long groupId;

	private EventStatus status;

	@JsonUnwrapped
	private EventBasicVo eventBasic;

	@JsonUnwrapped
	private EventDetailVo eventDetail;

	public static EventResponse of(Event event) {
		return EventResponse.builder()
			.eventId(event.getId())
			.groupId(event.getGroupId())
			.eventBasic(EventBasicVo.from(event))
			.eventDetail(EventDetailVo.from(event))
			.status(event.getStatus())
			.build();
	}
}
