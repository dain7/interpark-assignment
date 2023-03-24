package com.interpark.assignment.exception;

public enum ErrorCode {
    CITY_CANNOT_DELETE_EXCEPTION("해당 도시가 지정된 여행이 있을 경우 삭제할 수 없습니다.");

    private final String message;

    ErrorCode(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
