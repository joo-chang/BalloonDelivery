package com.sparta.balloondelivery.util;

import lombok.Getter;

@Getter
public enum ErrorCode {
    // 모니터링 대상 에러
    SERVER_ERROR("INTERNAL_SERVER_ERROR", "일시적인 오류가 발생했습니다. 잠시 후 다시 시도해주세요."),

    // 비즈니스 로직 에러 (모니터링 대상 아님)
    INVALID_PARAMETER("요청한 값이 올바르지 않습니다."),
    AUTHENTICATION_FAIL("인증 정보가 올바르지 않습니다."),
    AUTHORIZATION_FAIL("권한이 없습니다."),
    INCORRECT_USER("사용자 정보가 올바르지 않습니다."),
    USER_NOT_FOUND("사용자 정보가 존재하지 않습니다."),
    DATA_LENGTH_OVER_EXCEPTION("데이터 길이가 초과되었습니다."),
    EXIST_USER("이미 존재하는 사용자입니다."),
    INVALID_CREDENTIALS("아이디나 비밀번호가 올바르지 않습니다."),
    INVALID_ROLE("유효하지 않은 권한입니다."),
    INVALID_ADMIN_TOKEN("관리자 암호가 올바르지 않습니다."),
    NOT_EXIST_USER("존재하지 않는 사용자입니다."),
    NO_PERMISSION("권한이 없습니다."),
    EXIST_ADDRESS("이미 주소가 존재합니다."),
    JWT_NOT_FOUND("토큰이 존재하지 않습니다."),
    PAYMENT_NOT_FOUND("결제 정보가 존재하지 않습니다."),
    ORDER_NOT_FOUND("주문 정보가 존재하지 않습니다."),
    PAYMENT_CANCEL_FAILED("결제 취소에 실패했습니다."),
    ORDER_CANNOT_BE_CANCELED("주문이 진행 중이어서 취소할 수 없습니다."),
    ADDRESS_LIMIT("배송지는 최대 10개까지만 등록 가능합니다."),
    ;


    private final String status;
    private final String errorMsg;

    ErrorCode(String errorMsg) {
        this.status = "BAD_REQUEST";
        this.errorMsg = errorMsg;
    }

    ErrorCode(String status, String errorMsg) {
        this.status = status;
        this.errorMsg = errorMsg;
    }

    public String getErrorMsg(Object... arg) {
        return String.format(errorMsg, arg);
    }
}
