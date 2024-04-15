package com.ex.ticket.event.service;

import com.ex.ticket.event.domain.dto.request.UpdateEventBasicRequest;
import com.ex.ticket.event.domain.dto.response.EventResponse;
import com.ex.ticket.event.domain.entity.Event;
import com.ex.ticket.event.domain.entity.EventBasic;
import com.ex.ticket.event.domain.entity.EventMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateEventBasicUseCase {
	private final EventService eventService;
	private final CommonEventService commonEventService;
	private final EventMapper eventMapper;

	@Transactional
	public EventResponse execute(Long eventId, UpdateEventBasicRequest updateEventBasicRequest) {
		final Event event = commonEventService.findById(eventId);
		EventBasic eventBasic = eventMapper.toEventBasic(updateEventBasicRequest);

		return EventResponse.of(eventService.updateEventBasic(event, eventBasic));
	}
}
