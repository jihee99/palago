package com.ex.ticket.ticket.domain.mapper;

import com.ex.ticket.common.annotation.Mapper;
import com.ex.ticket.common.vo.Money;
import com.ex.ticket.event.domain.entity.Event;
import com.ex.ticket.event.service.CommonEventService;
import com.ex.ticket.ticket.domain.dto.request.CreateTicketItemRequest;
import com.ex.ticket.ticket.domain.dto.request.GetEventTicketItemsResponse;
import com.ex.ticket.ticket.domain.dto.response.TicketItemResponse;
import com.ex.ticket.ticket.domain.entity.TicketItem;
import com.ex.ticket.ticket.service.CommonTicketItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Mapper
@RequiredArgsConstructor
public class TicketItemMapper {

    private final CommonTicketItemService commonTicketItemService;

    private final CommonEventService commonEventService;

    public TicketItem toTicketItem(CreateTicketItemRequest createTicketItemRequest, Long eventId) {

        return TicketItem.builder()
                .payType(createTicketItemRequest.getPayType())
                .name(createTicketItemRequest.getName())
                .description(createTicketItemRequest.getDescription())
                .price(Money.wons(createTicketItemRequest.getPrice()))
                .quantity(createTicketItemRequest.getSupplyCount())
                .supplyCount(createTicketItemRequest.getSupplyCount())
                .purchaseLimit(createTicketItemRequest.getPurchaseLimit())
                .type(createTicketItemRequest.getApproveType())
                .isSellable(true)
                .eventId(eventId)
                .build();
    }

    @Transactional(readOnly = true)
    public GetEventTicketItemsResponse toGetEventTicketItemsResponse(Long eventId, Boolean isAdmin) {

        Event event = commonEventService.findById(eventId);
        List<TicketItem> ticketItems = commonTicketItemService.findAllByEventId(event.getId());
        return GetEventTicketItemsResponse.from(
                ticketItems.stream()
                        .map(ticketItem -> TicketItemResponse.from(ticketItem, isAdmin))
                        .toList());
    }

//    @Transactional(readOnly = true)
//    public GetAppliedOptionGroupsResponse toGetAppliedOptionGroupsResponse(Long eventId) {
//
//        Event event = eventAdaptor.findById(eventId);
//        List<TicketItem> ticketItems = ticketItemAdaptor.findAllByEventId(event.getId());
//        List<AppliedOptionGroupResponse> appliedOptionGroups = new ArrayList<>();
//        ticketItems.forEach(
//                ticketItem ->
//                        appliedOptionGroups.add(
//                                AppliedOptionGroupResponse.from(
//                                        ticketItem,
//                                        ticketItem.getItemOptionGroups().stream()
//                                                .map(ItemOptionGroup::getOptionGroup)
//                                                .toList()
//                                                .stream()
//                                                .map(OptionGroupResponse::from)
//                                                .toList())));
//        return GetAppliedOptionGroupsResponse.from(appliedOptionGroups);
//    }

}
