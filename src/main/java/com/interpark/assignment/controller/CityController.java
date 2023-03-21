package com.interpark.assignment.controller;

import com.interpark.assignment.dto.ResponseDto;
import com.interpark.assignment.dto.city.CityRequestDto;
import com.interpark.assignment.dto.city.CityResponseDto;
import com.interpark.assignment.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/city")
@RequiredArgsConstructor
public class CityController {

    private final CityService cityService;

    @PostMapping
    public ResponseDto create(
            @RequestBody CityRequestDto request
    ) {
        cityService.create(request);
        return ResponseDto.ok();
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
            @PathVariable Long cityId
    ) {
        cityService.get(cityId);
        return ResponseDto.ok();
    }

    @DeleteMapping("/{cityId}")
    public ResponseDto delete(
            @PathVariable Long cityId
    ) {
        CityResponseDto response = cityService.get(cityId);
        return ResponseDto.ok("city", response);
    }
}
