package com.ex.ticket.common.vo;

import com.ex.ticket.event.domain.entity.Event;
import com.ex.ticket.event.domain.entity.EventBasic;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class EventBasicVo {

    private String name;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime startAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime endAt;
    private Long runTime;
    private String address;

    public static EventBasicVo from(Event event) {
        EventBasic eventBasic = event.getEventBasic();
        if (eventBasic == null) {
            return EventBasicVo.builder().build();
        }
        return EventBasicVo.builder()
                .name(eventBasic.getName())
                .startAt(eventBasic.getStartAt())
                .endAt(event.getEndAt())
                .runTime(eventBasic.getRunTime())
                .address(eventBasic.getAddress())
                .build();
    }
}
