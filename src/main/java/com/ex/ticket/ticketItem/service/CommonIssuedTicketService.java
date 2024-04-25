package com.ex.ticket.ticketItem.service;

import com.ex.ticket.ticketItem.domain.entity.IssuedTicket;
import com.ex.ticket.ticketItem.domain.entity.IssuedTickets;
import com.ex.ticket.ticketItem.repository.IssuedTicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommonIssuedTicketService {

    private final IssuedTicketRepository issuedTicketRepository;

    public IssuedTicket save(IssuedTicket issuedTicket) {
        return issuedTicketRepository.save(issuedTicket);
    }

    public Boolean existsByEventId(Long eventId) {
        return issuedTicketRepository.existsByEventId(eventId);
    }

    public Long countPaidTicket(Long userId, Long itemId) {
        return issuedTicketRepository.countPaidTickets(userId, itemId);
    }

    public IssuedTickets findOrderIssuedTickets(Long orderId) {
        return IssuedTickets.from(issuedTicketRepository.findAllByOrderId(orderId));
    }
}
