package com.interpark.assignment.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDto {

    private int status = 200;
    private String message = "ok";
    private LocalDateTime timestamp = LocalDateTime.now();
    private Map<String, Object> data = new HashMap<>();

    private ResponseDto(String name, Object data) {
        this.data.put(name, data);
    }

    private ResponseDto(Long id) {
        this.data.put("id", id);
    }

    public static ResponseDto ok() {
        return new ResponseDto();
    }

    public static ResponseDto ok(String name, Object data) {
        return new ResponseDto(name, data);
    }

    public static ResponseDto create(Long id) {
        return new ResponseDto(id);
    }
}
