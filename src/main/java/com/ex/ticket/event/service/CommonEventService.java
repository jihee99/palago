package com.ex.ticket.event.service;

import com.ex.ticket.event.domain.entity.Event;
import com.ex.ticket.event.domain.entity.EventStatus;
import com.ex.ticket.event.exception.EventNotFoundException;
import com.ex.ticket.event.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommonEventService {
    private final EventRepository eventRepository;

    public List<Event> findAllByOrderByCreatedAtDesc(){
        return eventRepository.findAllByOrderByCreatedAtDesc();
    }

    public Event findById(Long eventId) {
        return eventRepository
                .findById(eventId)
                .orElseThrow(() -> EventNotFoundException.EXCEPTION);
    }

    public List<Event> findAllByGroupId(Long groupId) {
        return eventRepository.findAllByGroupId(groupId);
    }

    public List<Event> findAllByGroupIdIn(List<Long> groupId) {
        return eventRepository.findAllByGroupIdIn(groupId);
    }

    public List<Event> queryEventsByGroupIdIn(List<Long> groupId) {
        return eventRepository.queryEventsByGroupIdIn(groupId);
    }

    public List<Event> queryEventsByStatus(EventStatus status) {
        return eventRepository.queryEventsByStatus(status);
    }

    public List<Event> findAllByIds(List<Long> ids) {
        return eventRepository.findAllByIdIn(ids);
    }

}
