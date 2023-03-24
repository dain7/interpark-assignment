package com.interpark.assignment.exception;

public class EndDateBeforeOrSameStartDateException extends RuntimeException{

    public EndDateBeforeOrSameStartDateException() {
        super(ErrorCode.END_DATE_BEFORE_OR_SAME_START_DATE_EXCEPTION.getMessage());
    }
}
