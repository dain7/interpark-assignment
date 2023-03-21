package com.interpark.assignment.exception;

public class CityNotFoundException extends RuntimeException{
    public CityNotFoundException() {
        super("해당 도시가 존재하지 않습니다.");
    }
}
