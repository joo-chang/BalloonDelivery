package com.sparta.balloondelivery.util;

import lombok.Getter;

@Getter
public enum SuccessCode {

    // 성공 코드
    SUCCESS("요청이 성공적으로 처리되었습니다."),
    LOGIN_SUCCESS("로그인에 성공했습니다."),
    LOGOUT_SUCCESS("로그아웃에 성공했습니다."),
    REGISTER_SUCCESS("회원가입에 성공했습니다."),
    UPDATE_SUCCESS("정보 수정에 성공했습니다."),
    DELETE_SUCCESS("정보 삭제에 성공했습니다."),
    ADD_SUCCESS("정보 추가에 성공했습니다."),
    FIND_SUCCESS("정보 조회에 성공했습니다."),
    SEND_SUCCESS("메시지 전송에 성공했습니다."),
    CANCEL_SUCCESS("예약 취소에 성공했습니다."),
    PAY_SUCCESS("결제에 성공했습니다."),
    REFUND_SUCCESS("환불에 성공했습니다."),
    DELIVERY_SUCCESS("배송에 성공했습니다."),
    ;


    private final String status;
    private final String successMsg;

    SuccessCode(String successMsg) {
        this.status = "SUCCESS";
        this.successMsg = successMsg;
    }

    SuccessCode(String status, String successMsg) {
        this.status = status;
        this.successMsg = successMsg;
    }

    public String getSuccessMsg(Object... arg) {
        return String.format(successMsg, arg);
    }
}
