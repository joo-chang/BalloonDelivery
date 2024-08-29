package com.sparta.balloondelivery.exception;

import com.sparta.balloondelivery.util.ApiResponse;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
@Order(1)
public class AuthExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        // 필드별로 에러 메시지를 수집
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        // 에러 메시지를 필드 이름 없이 단순히 문자열로 변환
        String errorMessages = errors.values().stream()
                .collect(Collectors.joining(", "));

        //TODO: 에러 메시지 JSON 형태로 변환
        return ApiResponse.fail(HttpStatus.BAD_REQUEST.name(), errorMessages);
    }
}
