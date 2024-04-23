package com.ex.ticket.ticket.repository;

import com.ex.ticket.ticket.domain.entity.TicketItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<TicketItem, Long> {

}
