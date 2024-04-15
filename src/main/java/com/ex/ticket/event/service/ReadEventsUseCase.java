package com.ex.ticket.event.service;

import com.ex.ticket.event.domain.dto.response.EventResponse;
import com.ex.ticket.event.domain.entity.EventMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReadEventsUseCase {
	private final EventMapper eventMapper;

	public List<EventResponse> execute() {
		return eventMapper.toEventResponse();
	}

}
