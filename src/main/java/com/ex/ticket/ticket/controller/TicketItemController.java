package com.ex.ticket.ticket.controller;

import com.ex.ticket.ticket.domain.dto.request.GetEventTicketItemsResponse;
import com.ex.ticket.ticket.service.GetEventTicketItemsUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Tag(name = "4. 티켓 상품 관리 API (그룹용)")
@RestController
@RequestMapping("/api/v1/events/{eventId}/ticketItems")
@RequiredArgsConstructor
public class TicketItemController {

    private final GetEventTicketItemsUseCase getEventTicketItemsUseCase;

    @Operation(summary = "해당 이벤트의 티켓상품을 모두 조회합니다.")
    @GetMapping
    public GetEventTicketItemsResponse getEventTicketItems(@PathVariable Long eventId) {
        return getEventTicketItemsUseCase.execute(eventId);
    }

}
