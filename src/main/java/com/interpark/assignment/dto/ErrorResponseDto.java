package com.interpark.assignment.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ErrorResponseDto {

    private final LocalDateTime timestamp = LocalDateTime.now();

    private final String message;

    private final int status;

    @Builder
    public ErrorResponseDto(String message, int status) {
        this.message = message;
        this.status = status;
    }
}
