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

@Tag(name = "4. 발급 티켓 관리 API (admin)")
@RestController
@RequestMapping("/api/group/event/{eventId}/issuedTickets")
@RequiredArgsConstructor
public class GroupIssuedTicketController {
    private final GetEventTicketItemsUseCase getEventTicketItemsUseCase;

    @Operation(summary = "해당 이벤트의 티켓상품을 모두 조회합니다.", description = "재고 정보가 무조건 공개됩니다.")
    @GetMapping("/list")
    public GetEventTicketItemsResponse getEventTicketItemsForManager(@PathVariable Long eventId) {
        return getEventTicketItemsUseCase.executeForManager(eventId);
    }

    /* 발급 티켓 상태 처리 api */

}
