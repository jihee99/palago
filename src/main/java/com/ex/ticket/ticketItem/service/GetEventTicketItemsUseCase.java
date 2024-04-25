package com.ex.ticket.ticketItem.service;

import com.ex.ticket.ticketItem.domain.dto.request.GetEventTicketItemsResponse;
import com.ex.ticket.ticketItem.domain.mapper.TicketItemMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetEventTicketItemsUseCase {

    private final TicketItemMapper ticketItemMapper;

    public GetEventTicketItemsResponse execute(Long eventId) {
        return ticketItemMapper.toGetEventTicketItemsResponse(eventId, false);
    }

    public GetEventTicketItemsResponse executeForManager(Long eventId) {
        return ticketItemMapper.toGetEventTicketItemsResponse(eventId, true);
    }
}