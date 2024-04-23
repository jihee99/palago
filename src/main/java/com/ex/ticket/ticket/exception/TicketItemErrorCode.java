package com.ex.ticket.ticket.exception;

import com.ex.ticket.common.annotation.ExplainError;
import com.ex.ticket.common.domain.BaseErrorCode;
import com.ex.ticket.common.domain.ErrorReason;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.lang.reflect.Field;
import java.util.Objects;

@Getter
@AllArgsConstructor
public enum
TicketItemErrorCode implements BaseErrorCode {
    @ExplainError("요청에서 보내준 티켓 상품 id 값이 올바르지 않을 때 발생하는 오류입니다.")
    TICKET_ITEM_NOT_FOUND(HttpStatus.NOT_FOUND, 4000, "티켓 아이템을 찾을 수 없습니다."),
    @ExplainError("설정할수 없는 티켓 가격일때 발생하는 오류입니다.")
    INVALID_TICKET_PRICE(HttpStatus.BAD_REQUEST, 4001, "설정할 수 없는 티켓 가격입니다."),
    @ExplainError("주문 및 승인 요청 시 티켓 상품 재고보다 많은 양을 주문 시 발생하는 오류입니다.")
    TICKET_ITEM_QUANTITY_LESS_THAN_ZERO(HttpStatus.BAD_REQUEST, 4002, "티켓 아이템 재고가 0보다 작을 수 없습니다."),
    @ExplainError("옵션을 적용할 상품이 해당 이벤트 소속이 아닐 때 발생하는 오류입니다.")
    INVALID_TICKET_ITEM(HttpStatus.BAD_REQUEST, 4003, "해당 이벤트 소속의 티켓이 아닙니다."),
    TICKET_ITEM_PURCHASE_LIMIT(HttpStatus.BAD_REQUEST, 4004, "해당 티켓상품 최대 구매 가능 갯수를 넘었습니다."),
    @ExplainError("이미 재고가 감소되어 티켓상품 삭제가 불가능할 경우 발생하는 오류입니다.")
    FORBIDDEN_TICKET_ITEM_DELETE(HttpStatus.BAD_REQUEST, 4006, "티켓상품 삭제가 불가능한 상태입니다."),
    @ExplainError("티켓 지불방식과 승인방식이 불가능한 조합일때 발생하는 오류입니다.")
    INVALID_TICKET_TYPE(HttpStatus.BAD_REQUEST, 4007, "잘못된 티켓 승인타입입니다.");

    private HttpStatus httpStatus;
    private int errorCode;
    private String errorMessage;

    @Override
    public ErrorReason getErrorReason() {
        System.out.println(errorMessage);
        return ErrorReason.builder().errorCode(errorCode).errorMessage(errorMessage).build();
    }

    @Override
    public String getExplainError() throws NoSuchFieldException {
        Field field = this.getClass().getField(this.name());
        ExplainError annotation = field.getAnnotation(ExplainError.class);
        return Objects.nonNull(annotation) ? annotation.value() : this.getErrorMessage();
    }
}
