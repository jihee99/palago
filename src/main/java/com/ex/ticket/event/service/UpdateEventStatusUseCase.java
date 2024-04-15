package com.ex.ticket.event.service;

import com.ex.ticket.event.domain.dto.request.UpdateEventStatusRequest;
import com.ex.ticket.event.domain.dto.response.EventResponse;
import com.ex.ticket.event.domain.entity.Event;
import com.ex.ticket.event.domain.entity.EventStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UpdateEventStatusUseCase {
	private final EventService eventService;
	private final CommonEventService commonEventService;

	public EventResponse execute(Long eventId, UpdateEventStatusRequest updateEventStatusRequest) {
		final Event event = commonEventService.findById(eventId);
		final EventStatus status = updateEventStatusRequest.getStatus();

		return EventResponse.of(eventService.updateEventStatus(event, status));
	}
}
