package com.ex.ticket.event.service;

import com.ex.ticket.common.util.UserUtils;
import com.ex.ticket.event.domain.dto.response.EventProfileResponse;
import com.ex.ticket.event.domain.entity.EventMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReadGroupUserEventListUseCase {

    private final UserUtils userUtils;
    private final EventMapper eventMapper;

    public List<EventProfileResponse> execute() {
        final Long userId = userUtils.getCurrentUserId();
        return eventMapper.toEventProfileResponseList(userId);
    }

}
