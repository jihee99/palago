package com.ex.ticket.event.domain.entity;

import com.ex.ticket.common.annotation.Mapper;
import com.ex.ticket.event.domain.dto.request.CreateEventRequest;
import com.ex.ticket.event.domain.dto.request.UpdateEventBasicRequest;
import com.ex.ticket.event.domain.dto.request.UpdateEventDetailRequest;
import com.ex.ticket.event.domain.dto.response.EventDetailResponse;
import com.ex.ticket.event.domain.dto.response.EventResponse;
import com.ex.ticket.event.service.CommonEventService;
import com.ex.ticket.group.domain.entity.Group;
import com.ex.ticket.group.service.CommonGroupService;
import com.ex.ticket.group.service.GroupService;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
@RequiredArgsConstructor
public class EventMapper {

	private final GroupService groupService;
	private final CommonGroupService commonGroupService;

	private final CommonEventService commonEventService;

	public Event toEntity(CreateEventRequest createEventRequest) {
		return Event.builder()
				.groupId(createEventRequest.getGroupId())
				.name(createEventRequest.getName())
				.startAt(createEventRequest.getStartAt())
				.runTime(createEventRequest.getRunTime())
				.build();
	}

	public List<EventResponse> toEventResponse() {
		return commonEventService.findAllByOrderByCreatedAtDesc()
				.stream()
				.map(EventResponse::of)
				.collect(Collectors.toList());
	}

	public EventDetail toEventDetail(UpdateEventDetailRequest updateEventDetailRequest) {
		return EventDetail.builder()
				.content(updateEventDetailRequest.getContent())
				.build();
	}

	public EventDetailResponse toEventDetailResponse(Group group, Event event) {
		return EventDetailResponse.of(group, event);
	}

	public EventBasic toEventBasic(UpdateEventBasicRequest updateEventBasicRequest) {
		return EventBasic.builder()
				.name(updateEventBasicRequest.getName())
				.runTime(updateEventBasicRequest.getRunTime())
				.startAt(updateEventBasicRequest.getStartAt())
				.build();
	}


}
