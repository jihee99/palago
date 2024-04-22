package com.ex.ticket.event.repository;


import com.ex.ticket.event.domain.entity.Event;
import com.ex.ticket.event.domain.entity.EventStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface EventRepository extends JpaRepository<Event, Long> {
	List<Event> findAllByGroupIdOrderByCreatedAt(Long groupId);

	List<Event> findAllByGroupIdIn(List<Long> groupId);

	List<Event> queryEventsByGroupIdIn(List<Long> groupId);

	List<Event> queryEventsByStatus(EventStatus status);

	List<Event> findAllByOrderByCreatedAtDesc();

	List<Event> findAllByIdIn(List<Long> ids);

	// List<Event> queryEventsByKeyword(String keyword);
}
