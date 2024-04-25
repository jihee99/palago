package com.ex.ticket.ticketItem.service;

import com.ex.ticket.ticketItem.domain.dto.request.GetEventTicketItemsResponse;
import com.ex.ticket.ticketItem.domain.mapper.TicketItemMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteTicketItemUseCase {

    private final TicketItemMapper ticketItemMapper;
    private final TicketItemService ticketItemService;

    public GetEventTicketItemsResponse execute(Long eventId, Long ticketItemId) {

        ticketItemService.deleteTicketItem(eventId, ticketItemId);

        return ticketItemMapper.toGetEventTicketItemsResponse(eventId, true);
    }
}
