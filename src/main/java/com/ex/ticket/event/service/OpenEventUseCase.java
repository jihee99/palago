package com.ex.ticket.event.service;

import com.ex.ticket.event.domain.dto.response.EventResponse;
import com.ex.ticket.event.domain.entity.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OpenEventUseCase {
	private final EventService eventService;
	private final CommonEventService commonEventService;

	public EventResponse execute(Long eventId) {

		System.out.println("==============오픈할거임============");
		final Event event = commonEventService.findById(eventId);
		return EventResponse.of(eventService.openEvent(event));
	}
}
