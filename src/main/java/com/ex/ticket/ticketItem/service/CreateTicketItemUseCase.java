package com.ex.ticket.ticketItem.service;

import com.ex.ticket.ticketItem.domain.dto.request.CreateTicketItemRequest;
import com.ex.ticket.ticketItem.domain.dto.response.TicketItemResponse;
import com.ex.ticket.ticketItem.domain.entity.TicketItem;
import com.ex.ticket.ticketItem.domain.mapper.TicketItemMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateTicketItemUseCase {
    private final TicketItemService ticketItemService;
    private final TicketItemMapper ticketItemMapper;

    public TicketItemResponse execute(CreateTicketItemRequest createTicketItemRequest, Long eventId) {
//        Event event = commonEventService.findById(eventId);
//        Group group = commonGroupService.findById(event.getGroupId());

        TicketItem ticketItem = ticketItemService.createTicketItem(ticketItemMapper.toTicketItem(createTicketItemRequest, eventId));
        return TicketItemResponse.from(ticketItem, true);
    }
}
