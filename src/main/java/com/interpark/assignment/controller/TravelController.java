package com.interpark.assignment.controller;

import com.interpark.assignment.dto.ResponseDto;
import com.interpark.assignment.dto.travel.*;
import com.interpark.assignment.service.TravelService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/travel")
@RequiredArgsConstructor
public class TravelController {

    private final TravelService travelService;

    @PostMapping
    public ResponseDto create(
            @RequestBody TravelRequestDto request
    ) {
        TravelCreateResponseDto response = travelService.create(request);
        return ResponseDto.create(response.getId());
    }

    @PostMapping("/{travelId}")
    public ResponseDto update(
            @PathVariable Long travelId,
            @RequestBody TravelRequestDto request
    ) {
        travelService.update(travelId, request);
        return ResponseDto.ok();
    }

    @DeleteMapping("/{travelId}")
    public ResponseDto delete(
            @PathVariable Long travelId
    ) {
        travelService.delete(travelId);
        return ResponseDto.ok();
    }

    @GetMapping("/{travelId}")
    public ResponseDto get(
            @PathVariable Long travelId
    ) {
        TravelResponseDto response = travelService.get(travelId);
        return ResponseDto.ok("travel", response);
    }
}
