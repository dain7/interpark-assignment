package com.interpark.assignment.exception;

public class CityCannotDeleteException extends RuntimeException{
    public CityCannotDeleteException() {
        super("해당 도시가 지정된 여행이 있을 경우 삭제할 수 없습니다.");
    }
}
