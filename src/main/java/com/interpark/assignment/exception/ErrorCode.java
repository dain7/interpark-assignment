package com.interpark.assignment.exception;

public enum ErrorCode {
    CITY_CANNOT_DELETE_EXCEPTION("해당 도시가 지정된 여행이 있을 경우 삭제할 수 없습니다."),
    CITY_NOT_FOUND_EXCEPTION("해당 도시가 존재하지 않습니다."),
    END_DATE_BEFORE_OR_SAME_START_DATE_EXCEPTION("도착 날짜는 시작 날짜보다 뒤어야 합니다."),
    MEMBER_NOT_FOUND_EXCEPTION("해당 유저가 존재하지 않습니다."),
    TRAVEL_NOT_FOUND_EXCEPTION("해당 여행 정보가 존재하지 않습니다.");

    private final String message;

    ErrorCode(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
