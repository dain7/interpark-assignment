package com.interpark.assignment.controller;

import com.interpark.assignment.dto.ResponseDto;
import com.interpark.assignment.dto.member.MemberCreateDto;
import com.interpark.assignment.dto.member.MemberResponseDto;
import com.interpark.assignment.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping
    public ResponseDto signup(
            @RequestBody MemberCreateDto request
    ) {
        MemberResponseDto response = memberService.signup(request);
        return ResponseDto.create(response.getId());
    }
}
