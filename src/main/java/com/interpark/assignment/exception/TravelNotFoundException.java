package com.interpark.assignment.exception;

public class TravelNotFoundException extends RuntimeException{
    public TravelNotFoundException() {
        super(ErrorCode.TRAVEL_NOT_FOUND_EXCEPTION.getMessage());
    }
}
