package com.ex.ticket.ticketItem.domain.entity;

import com.ex.ticket.common.vo.Money;
import com.ex.ticket.ticketItem.exception.*;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;

@Table(name = "tbl_ticket_item")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TicketItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ticket_item_id")
	@Comment("티켓 아이디")
	private Long id;

	@Enumerated(EnumType.STRING)
	@Comment("결제 타입 (무료|유료)")
	private TicketPayType payType;

	private String name;

	private String description;

	private Money price;

	@Comment("재고")
	private Long quantity;

	@Comment("공급량")
	private Long supplyCount;

	@Comment("구매 제한 수량")
	private Long purchaseLimit;

	@Enumerated(EnumType.STRING)
	private TicketType type;

//	@Embedded
//	private AccountInfo accountInfo;

	@Comment("판매 가능 여부")
	private Boolean isSellable;

	@Comment("판매 가능 시간")
	private LocalDateTime saleStartAt;

	@Comment("판매 종료 시간")
	private LocalDateTime saleEndAt;

	@Enumerated(EnumType.STRING)
	@ColumnDefault(value = "'VALID'")
	@Comment("티켓 상태")
	private TicketItemStatus ticketItemStatus = TicketItemStatus.VALID;

	private Long eventId;

	@Builder
	public TicketItem(
			TicketPayType payType,
			String name,
			String description,
			Money price,
			Long quantity,
			Long supplyCount,
			Long purchaseLimit,
			TicketType type,
			Boolean isSellable,
			LocalDateTime saleStartAt,
			LocalDateTime saleEndAt,
			Long eventId) {
		this.payType = payType;
		this.name = name;
		this.description = description;
		this.price = price;
		this.quantity = quantity;
		this.supplyCount = supplyCount;
		this.purchaseLimit = purchaseLimit;
		this.type = type;
		this.isSellable = isSellable;
		this.saleStartAt = saleStartAt;
		this.saleEndAt = saleEndAt;
		this.eventId = eventId;
	}


	public Boolean isQuantityReduced() {
		return !this.quantity.equals(this.supplyCount);
	}

	public void reduceQuantity(Long quantity) {
		if (this.quantity < 0) {
			 throw TicketItemQuantityException.EXCEPTION;
		}
		validEnoughQuantity(quantity);
		this.quantity = this.quantity - quantity;
	}

	public void validEnoughQuantity(Long quantity) {
		if (this.quantity < quantity) {
			// throw TicketItemQuantityLackException.EXCEPTION;
		}
	}

	public void validPurchaseLimit(Long quantity) {
		if (isPurchaseLimitExceed(quantity)) {
			 throw TicketPurchaseLimitException.EXCEPTION;
		}
	}

	public Boolean isPurchaseLimitExceed(Long quantity) {
		return this.purchaseLimit < quantity;
	}


	public Boolean isSold() {
		return quantity < supplyCount;
	}

	public Boolean isQuantityLeft() {
		return quantity > 0;
	}

	public void validateEventId(Long eventId) {
		if (!this.getEventId().equals(eventId)) {
			throw InvalidTicketItemException.EXCEPTION;
		}
	}

	public void deleteTicketItem() {
		// 재고 감소된 티켓상품은 삭제 불가
		if (this.isQuantityReduced()) {
			throw ForbiddenTicketItemDeleteException.EXCEPTION;
		}
		this.ticketItemStatus = TicketItemStatus.DELETED;
	}

	public static final Long MINIMUM_PAYMENT_WON = 1000L;
	public void validateTicketPayType() {

 		if (payType == TicketPayType.PRICE_TICKET) {
			// 유료티켓은 무조건 선착순 + 1000원 이상
			 if (type != TicketType.FIRST_COME_FIRST_SERVED) {
				throw InvalidTicketTypeException.EXCEPTION;
			 }
			if (price.isLessThan(Money.wons(MINIMUM_PAYMENT_WON))) {
				throw InvalidTicketPriceException.EXCEPTION;
			}
		} else {
			 // 무료 티켓은 0
			 if (!price.equals(Money.ZERO)) {
				throw InvalidTicketPriceException.EXCEPTION;
			 }
		}
	}

	public Boolean isFCFS() {
		return this.type.isFCFS();
	}
}
