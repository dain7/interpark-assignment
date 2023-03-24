package com.interpark.assignment.exception;

public class EndDateBeforeOrSameStartDateException extends RuntimeException{

    public EndDateBeforeOrSameStartDateException() {
        super("도착 날짜는 시작 날짜보다 뒤어야 합니다.");
    }
}
