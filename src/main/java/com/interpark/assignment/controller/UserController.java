package com.interpark.assignment.controller;

import com.interpark.assignment.dto.ResponseDto;
import com.interpark.assignment.dto.user.UserCreateDto;
import com.interpark.assignment.dto.user.UserResponseDto;
import com.interpark.assignment.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseDto signup(
            @RequestBody UserCreateDto request
    ) {
        UserResponseDto response = userService.signup(request);
        return ResponseDto.ok("user", response);
    }
}
