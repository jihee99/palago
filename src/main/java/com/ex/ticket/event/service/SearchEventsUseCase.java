package com.ex.ticket.event.service;

import com.ex.ticket.event.domain.entity.EventMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SearchEventsUseCase {

	private final EventMapper eventMapper;

	// public List<EventResponse> execute(String keyword, Pageable pageable) {
	// 	return eventMapper.toEventResponseByKeyword(keyword);
	// }
}
