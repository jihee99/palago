package com.ex.ticket.ticketItem.repository;

import com.ex.ticket.ticketItem.domain.entity.TicketItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<TicketItem, Long> {

}
