package com.interpark.assignment.controller;

import com.interpark.assignment.dto.ResponseDto;
import com.interpark.assignment.dto.city.*;
import com.interpark.assignment.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/city")
@RequiredArgsConstructor
public class CityController {

    private final CityService cityService;

    @PostMapping
    public ResponseDto create(
            @RequestHeader("member-id") Long memberId,
            @RequestBody CityRequestDto request
    ) {
        CityCreateResponseDto response = cityService.create(memberId, request);
        return ResponseDto.create(response.getId());
    }

    @PostMapping("/{cityId}")
    public ResponseDto update(
            @PathVariable Long cityId,
            @RequestBody CityRequestDto request
    ) {
        cityService.update(cityId, request);
        return ResponseDto.ok();
    }

    @GetMapping("/{cityId}")
    public ResponseDto get(
            @RequestHeader("member-id") Long memberId,
            @PathVariable Long cityId
    ) {
        CityResponseDto response = cityService.get(cityId);
        return ResponseDto.ok("cities", response);
    }

    @GetMapping
    public ResponseDto getByMember(
            @RequestHeader("member-id") Long memberId
    ) {
        List<CityGetResponseDto> response = cityService.getByMember(memberId);
        return ResponseDto.ok("cities", response);
    }

    @DeleteMapping("/{cityId}")
    public ResponseDto delete(
            @PathVariable Long cityId
    ) {
        cityService.delete(cityId);
        return ResponseDto.ok();
    }
}
