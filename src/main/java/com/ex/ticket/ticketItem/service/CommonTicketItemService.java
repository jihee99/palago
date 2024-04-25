package com.ex.ticket.ticketItem.service;

import com.ex.ticket.ticketItem.domain.entity.TicketItem;
import com.ex.ticket.ticketItem.domain.entity.TicketItemStatus;
import com.ex.ticket.ticketItem.exception.TicketItemNotFoundException;
import com.ex.ticket.ticketItem.repository.TicketItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommonTicketItemService {

    private final TicketItemRepository ticketItemRepository;
    public TicketItem save(TicketItem ticketItem) {
        return ticketItemRepository.save(ticketItem);
    }

    public List<TicketItem> findAllByEventId(Long eventId) {
        return ticketItemRepository.findAllByEventIdAndTicketItemStatus(eventId, TicketItemStatus.VALID);
    }

    public TicketItem queryTicketItem(Long ticketItemId) {
        return ticketItemRepository
                .findByIdAndTicketItemStatus(ticketItemId, TicketItemStatus.VALID)
                .orElseThrow(() -> TicketItemNotFoundException.EXCEPTION);
    }

}
