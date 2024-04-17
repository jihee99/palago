package com.ex.ticket.event.service;

import com.ex.ticket.common.util.UserUtils;
import com.ex.ticket.event.domain.dto.response.EventDetailResponse;
import com.ex.ticket.event.domain.entity.Event;
import com.ex.ticket.event.domain.entity.EventMapper;
import com.ex.ticket.event.exception.EventNotOpenException;
import com.ex.ticket.group.domain.entity.Group;
import com.ex.ticket.group.service.CommonGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReadEventDetailUseCase {

	private final UserUtils userUtils;
	private final CommonGroupService commonGroupService;
	private final CommonEventService commonEventService;
	private final EventMapper eventMapper;

	public EventDetailResponse execute(Long eventId) {
		final Event event = commonEventService.findById(eventId);
		final Group group = commonGroupService.findById(event.getGroupId());
		final Long userId = userUtils.getCurrentUserId();
		// 호스트 유저가 아닐 경우 준비 상태일 때 조회할 수 없음
		if (event.isPreparing() && !group.isActiveGroupUserId(userId)) {
			throw EventNotOpenException.EXCEPTION;
		}
		return eventMapper.toEventDetailResponse(group, event);
	}
}
