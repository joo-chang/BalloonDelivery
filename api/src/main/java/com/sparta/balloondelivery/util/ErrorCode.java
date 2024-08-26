package com.sparta.balloondelivery.util;

import lombok.Getter;

@Getter
public enum ErrorCode {
    // 모니터링 대상 에러
    SERVER_ERROR("INTERNAL_SERVER_ERROR", "일시적인 오류가 발생했습니다. 잠시 후 다시 시도해주세요."),

    // 비즈니스 로직 에러 (모니터링 대상 아님)
    INVALID_PARAMETER("요청한 값이 올바르지 않습니다."),
    ENTITY_NOT_FOUND("존재하지 않는 엔티티입니다."),
    AUTHENTICATION_FAIL("인증 정보가 올바르지 않습니다."),
    AUTHORIZATION_FAIL("권한이 없습니다."),
    INCORRECT_USER("사용자 정보가 올바르지 않습니다."),
    DATA_LENGTH_OVER_EXCEPTION("데이터 길이가 초과되었습니다."),
    JWT_NOTFOUND("토큰이 존재하지 않습니다."),
    EXIST_USER("이미 존재하는 사용자입니다."),
    INVALID_CREDENTIALS("아이디나 비밀번호가 올바르지 않습니다."),
    INVALID_ROLE("유효하지 않은 권한입니다."),
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
