package com.interpark.assignment.exception;

public class DuplicateNameException extends RuntimeException{

    public DuplicateNameException() {
        super(ErrorCode.DUPLICATE_NAME_EXCEPTION.getMessage());
    }
}
