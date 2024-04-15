package com.ex.ticket.event.service;

import com.ex.ticket.event.domain.dto.request.UpdateEventDetailRequest;
import com.ex.ticket.event.domain.dto.response.EventResponse;
import com.ex.ticket.event.domain.entity.Event;
import com.ex.ticket.event.domain.entity.EventMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateEventDetailUseCase {

	private final EventService eventService;
	private final CommonEventService commonEventService;
	private final EventMapper eventMapper;

	@Transactional
	public EventResponse execute(Long eventId, UpdateEventDetailRequest updateEventDetailRequest) {
		final Event event = commonEventService.findById(eventId);
		return EventResponse.of(
			eventService.updateEventDetail(
				event, eventMapper.toEventDetail(updateEventDetailRequest)));
	}
}
