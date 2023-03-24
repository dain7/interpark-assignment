package com.interpark.assignment.exception;

public class TravelNotFoundException extends RuntimeException{
    public TravelNotFoundException() {
        super("해당 여행 정보가 존재하지 않습니다.");
    }
}
