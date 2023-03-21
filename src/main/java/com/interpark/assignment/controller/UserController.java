package com.interpark.assignment.controller;

import com.interpark.assignment.dto.ResponseDto;
import com.interpark.assignment.dto.city.CityCreateDto;
import com.interpark.assignment.dto.user.UserCreateDto;
import com.interpark.assignment.dto.user.UserDto;
import com.interpark.assignment.service.CityService;
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
        UserDto response = userService.signup(request);
        return ResponseDto.ok("user", response);
    }
}
