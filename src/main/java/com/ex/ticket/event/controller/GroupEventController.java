package com.ex.ticket.event.controller;

import com.ex.ticket.event.domain.dto.request.CreateEventRequest;
import com.ex.ticket.event.domain.dto.request.UpdateEventBasicRequest;
import com.ex.ticket.event.domain.dto.request.UpdateEventDetailRequest;
import com.ex.ticket.event.domain.dto.request.UpdateEventStatusRequest;
import com.ex.ticket.event.domain.dto.response.EventProfileResponse;
import com.ex.ticket.event.domain.dto.response.EventResponse;
import com.ex.ticket.event.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@Tag(name = "3. 이벤트 관리 API (그룹용)")
@RequestMapping("/api/group/event")
@RequiredArgsConstructor
public class GroupEventController {

	private final CreateEventUseCase createEventUseCase;
 	private final UpdateEventBasicUseCase updateEventBasicUseCase;
 	private final UpdateEventDetailUseCase updateEventDetailUseCase;
 	private final UpdateEventStatusUseCase updateEventStatusUseCase;
	private final OpenEventUseCase openEventUseCase;
	private final DeleteEventUseCase deleteEventUseCase;
	private final ReadGroupUserEventListUseCase readGroupUserEventListUseCase;

	@Operation(summary = "자신이 관리 중인 이벤트 리스트를 가져옵니다.")
	@GetMapping
	public List<EventProfileResponse> getAllEventByUser() {
		return readGroupUserEventListUseCase.execute();
	}

	@Operation(summary = "전시 기본 정보를 등록하여, 새로운 이벤트를 생성합니다.")
	@PostMapping("/register")
	public EventResponse createEvent(@RequestBody @Valid CreateEventRequest createEventRequest) {
		return createEventUseCase.execute(createEventRequest);
	}

	@Operation(summary = "전시 정보를 수정합니다.")
	@PostMapping("/{eventId}/basic")
	public EventResponse updateEventBasic(
		@PathVariable Long eventId,
		@RequestBody @Valid UpdateEventBasicRequest updateEventBasicRequest) {
		return updateEventBasicUseCase.execute(eventId, updateEventBasicRequest);
	}

	@Operation(summary = "전시 상세 정보를 수정합니다.")
	@PostMapping("/{eventId}/details")
	public EventResponse updateEventDetail(
		@PathVariable Long eventId,
		@RequestBody @Valid UpdateEventDetailRequest updateEventDetailRequest) {
		return updateEventDetailUseCase.execute(eventId, updateEventDetailRequest);
	}

	@Operation(summary = "전시를 오픈 상태로 변경합니다.")
	@GetMapping("/{eventId}/open")
	public EventResponse updateEventStatus(@PathVariable Long eventId) {
		return openEventUseCase.execute(eventId);
	}

	@Operation(summary = "전시 상태를 변경합니다. (OPEN 제외)")
	@PostMapping("/{eventId}/status")
	public EventResponse updateEventStatus(
		@PathVariable Long eventId,
		@RequestBody @Valid UpdateEventStatusRequest updateEventDetailRequest) {
		System.out.println("================= updateEventStatus ================= ");
		return updateEventStatusUseCase.execute(eventId, updateEventDetailRequest);
	}

	@Operation(summary = "이벤트를 삭제합니다.")
	@GetMapping("/{eventId}/delete")
	public EventResponse deleteEvent(@PathVariable Long eventId) {
		return deleteEventUseCase.execute(eventId);
	}



}
