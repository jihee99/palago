package com.ex.ticket.event.controller;

import com.ex.ticket.event.domain.dto.response.EventDetailResponse;
import com.ex.ticket.event.domain.dto.response.EventResponse;
import com.ex.ticket.event.service.ReadEventDetailUseCase;
import com.ex.ticket.event.service.ReadEventsUseCase;
import com.ex.ticket.event.service.SearchEventsUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// @SecurityRequirement(name = "access-token")
@RestController
@Tag(name = "3. 이벤트 관련 API (사용자용)")
@RequestMapping("/api/v1/events")
@RequiredArgsConstructor
public class EventController {

	private final ReadEventsUseCase readEventsUseCase;
	private final ReadEventDetailUseCase readEventDetailUseCase;
	private final SearchEventsUseCase searchEventsUseCase;

	@Operation(summary = "전시를 최신순으로 가져옵니다.")
	@GetMapping("/list")
	public List<EventResponse> getAllOpenEventByUser() {
		return readEventsUseCase.execute();
	}

	@Operation(summary = "전시 상세 정보를 가져옵니다.")
	@GetMapping("/{eventId}")
	public EventDetailResponse getEventDetailById(@PathVariable Long eventId) {
		return readEventDetailUseCase.execute(eventId);
	}


	// @Operation(summary = "전시 제목을 키워드로 검색하여 최신순으로 가져옵니다.")
	// @GetMapping("/search")
	// public List<EventResponse> getAllOpenEventByUser(
	// 	@RequestParam(required = false) String keyword,
	// 	@ParameterObject @PageableDefault(size = 10) Pageable pageable) {
	// 	return searchEventsUseCase.execute(keyword, pageable);
	// }


}
