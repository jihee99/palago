package com.ex.ticket.ticketItem.controller;

import com.ex.ticket.ticketItem.domain.dto.request.CreateTicketItemRequest;
import com.ex.ticket.ticketItem.domain.dto.request.GetEventTicketItemsResponse;
import com.ex.ticket.ticketItem.domain.dto.response.TicketItemResponse;
import com.ex.ticket.ticketItem.service.CreateTicketItemUseCase;
import com.ex.ticket.ticketItem.service.DeleteTicketItemUseCase;
import com.ex.ticket.ticketItem.service.GetEventTicketItemsUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@Tag(name = "4. 티켓 상품 관리 API (그룹용)")
@RestController
@RequestMapping("/api/group/event/{eventId}/ticketItems")
@RequiredArgsConstructor
public class TicketItemController {

    private final CreateTicketItemUseCase createTicketItemUseCase;
    private final GetEventTicketItemsUseCase getEventTicketItemsUseCase;

    private final DeleteTicketItemUseCase deleteTicketItemUseCase;

    @Operation(summary = "이벤트에 속하는 티켓 상품을 생성합니다.")
    @PostMapping()
    public TicketItemResponse createTicketItem(
            @PathVariable Long eventId,
            @RequestBody @Valid CreateTicketItemRequest createTicketItemRequest) {
        return createTicketItemUseCase.execute(createTicketItemRequest, eventId);
    }

    @Operation(summary = "해당 이벤트의 티켓상품을 모두 조회합니다.")
    @GetMapping
    public GetEventTicketItemsResponse getEventTicketItems(
            @PathVariable(name = "eventId") Long eventId
    ) {
        return getEventTicketItemsUseCase.execute(eventId);
    }

    @Operation(summary = "해당 티켓상품을 삭제합니다.")
    @PostMapping("/{ticketItemId}")
    public GetEventTicketItemsResponse deleteTicketItem(
            @PathVariable Long eventId, @PathVariable Long ticketItemId) {
        return deleteTicketItemUseCase.execute(eventId, ticketItemId);
    }

}
