package com.rental.domain.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    // Car 관련 예외
    CAR_NOT_FOUND("요청하신 ID의 차량을 찾을 수 없습니다."),
    DUPLICATE_CAR("이미 존재하는 차량입니다."),
    INVALID_CAR_STATUS("잘못된 차량 상태입니다."),

    // Category 관련 예외
    CATEGORY_NOT_FOUND("카테고리를 찾을 수 없습니다."),
    DUPLICATE_CATEGORY("이미 존재하는 카테고리입니다."),
    CATEGORY_REQUIRED("최소 하나의 카테고리가 필요합니다."),

    // 공통 예외
    INVALID_INPUT_VALUE("잘못된 입력값입니다."),
    INTERNAL_SERVER_ERROR("서버 오류가 발생했습니다.");

    private final String message;

    ErrorCode(String message) {
        this.message = message;
    }
}