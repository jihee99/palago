package com.ex.ticket.ticketItem.domain.entity;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TicketPayType {

    // 무료티켓
    FREE_TICKET("FREE_TICKET", "무료"),
    // 유료티켓
    PRICE_TICKET("PRICE_TICKET", "유료");

    private String value;

    @JsonValue
    private String kr;
}
