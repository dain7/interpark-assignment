package com.interpark.assignment.exception;

public class CityNotFoundException extends RuntimeException{
    public CityNotFoundException() {
        super(ErrorCode.CITY_NOT_FOUND_EXCEPTION.getMessage());
    }
}
