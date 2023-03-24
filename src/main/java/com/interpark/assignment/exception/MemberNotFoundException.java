package com.interpark.assignment.exception;

public class MemberNotFoundException extends RuntimeException{
    public MemberNotFoundException() {
        super(ErrorCode.MEMBER_NOT_FOUND_EXCEPTION.getMessage());
    }
}
