package com.ex.ticket.common.vo;

import com.ex.ticket.common.annotation.DateFormat;
import com.ex.ticket.ticket.domain.entity.IssuedTicket;
import com.ex.ticket.ticket.domain.entity.IssuedTicketStatus;
import com.ex.ticket.ticket.domain.entity.TicketPayType;
import lombok.Getter;
import lombok.Builder;

import java.time.LocalDateTime;

@Getter
@Builder
public class IssuedTicketInfoVo {

    private final Long issuedTicketId;

    private final String issuedTicketNo;

    private final String uuid;

    private final String ticketName;

    private final TicketPayType payType;

    private final Money ticketPrice;

    @DateFormat
    private final LocalDateTime createdAt;

    @DateFormat
    private final LocalDateTime enteredAt;

    private final IssuedTicketStatus issuedTicketStatus;


    public static IssuedTicketInfoVo from(IssuedTicket issuedTicket) {
        return IssuedTicketInfoVo.builder()
                .issuedTicketId(issuedTicket.getId())
                .issuedTicketNo(issuedTicket.getIssuedTicketNo())
                .uuid(issuedTicket.getUuid())
                .ticketName(issuedTicket.getItemInfo().getTicketName())
                .payType(issuedTicket.getItemInfo().getPayType())
                .ticketPrice(issuedTicket.getItemInfo().getPrice())
                .createdAt(issuedTicket.getCreatedAt())
                .issuedTicketStatus(issuedTicket.getIssuedTicketStatus())
                .enteredAt(issuedTicket.getEnteredAt())
                .build();
    }
}
