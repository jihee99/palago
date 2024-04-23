package com.ex.ticket.ticket.service;

import com.ex.ticket.ticket.domain.entity.IssuedTicket;
import com.ex.ticket.ticket.domain.entity.IssuedTickets;
import com.ex.ticket.ticket.repository.IssuedTicketRepository;
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
