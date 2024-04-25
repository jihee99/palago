package com.ex.ticket.ticketItem.domain.dto.request;

import com.ex.ticket.ticketItem.domain.dto.response.TicketItemResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class GetEventTicketItemsResponse {

    @Schema(description = "티켓상품 리스트")
    private List<TicketItemResponse> ticketItems;

    public static GetEventTicketItemsResponse from(List<TicketItemResponse> ticketItems) {

        return GetEventTicketItemsResponse.builder().ticketItems(ticketItems).build();
    }
}
