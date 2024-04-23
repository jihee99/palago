package com.ex.ticket.event.service;

import com.ex.ticket.event.domain.entity.Event;
import com.ex.ticket.event.domain.entity.EventBasic;
import com.ex.ticket.event.domain.entity.EventDetail;
import com.ex.ticket.event.domain.entity.EventStatus;
import com.ex.ticket.event.exception.CannotDeleteByIssuedTicketException;
import com.ex.ticket.event.exception.CannotOpenEventException;
import com.ex.ticket.event.exception.UseAnotherApiException;
import com.ex.ticket.event.repository.EventRepository;
import com.ex.ticket.ticket.service.CommonIssuedTicketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventService {

	private final EventRepository eventRepository;
	//TODO
	private final CommonIssuedTicketService commonIssuedTicketService;

	public Event createEvent(Event event) {
		return eventRepository.save(event);
	}

	public Event updateEventBasic(Event event, EventBasic eventBasic) {
		event.setEventBasic(eventBasic);
		return eventRepository.save(event);
	}

	public Event updateEventDetail(Event event, EventDetail eventDetail) {
		event.setEventDetail(eventDetail);
		return eventRepository.save(event);
	}

	public Event openEvent(Event event) {
//		this.validateEventInfoExistence(event);
		this.validateEventBasicExistence(event);
		this.validateEventDetailExistence(event);
		event.open();
		return eventRepository.save(event);
	}


	public Event updateEventStatus(Event event, EventStatus status) {
		if (status == EventStatus.CLOSED) event.close();
		else if (status == EventStatus.PREPARING) event.prepare();
		else throw UseAnotherApiException.EXCEPTION;
		return eventRepository.save(event);
	}

	public void validateEventInfoExistence(Event event) {
		if (!event.hasEventInfo()) throw CannotOpenEventException.EXCEPTION;
	}

	private void validateEventBasicExistence(Event event) {
		if (!event.hasEventBasic() || !event.hasEventPlace())
			throw CannotOpenEventException.EXCEPTION;
	}

	public void validateEventDetailExistence(Event event) {
		if (!event.hasEventDetail()) throw CannotOpenEventException.EXCEPTION;
	}

	public Event deleteEvent(Event event) {
		//TODO
		// 발급된 티켓이 있다면 삭제 불가
		if (commonIssuedTicketService.existsByEventId(event.getId()))
			throw CannotDeleteByIssuedTicketException.EXCEPTION;
		event.deleteSoft();
		return eventRepository.save(event);
	}
}
