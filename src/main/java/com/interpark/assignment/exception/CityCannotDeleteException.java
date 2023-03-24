package com.interpark.assignment.exception;

public class CityCannotDeleteException extends RuntimeException{
    public CityCannotDeleteException() {
        super(ErrorCode.CITY_CANNOT_DELETE_EXCEPTION.getMessage());
    }
}
