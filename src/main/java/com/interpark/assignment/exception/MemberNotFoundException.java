package com.interpark.assignment.exception;

public class MemberNotFoundException extends RuntimeException{
    public MemberNotFoundException() {
        super("해당 유저가 존재하지 않습니다.");
    }
}
