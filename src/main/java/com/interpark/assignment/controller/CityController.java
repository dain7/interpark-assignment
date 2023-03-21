package com.interpark.assignment.controller;

import com.interpark.assignment.dto.ResponseDto;
import com.interpark.assignment.dto.city.CityCreateDto;
import com.interpark.assignment.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/city")
@RequiredArgsConstructor
public class CityController {

    private final CityService cityService;

    @PostMapping
    public ResponseDto create(
            @RequestBody CityCreateDto request
    ) {
        cityService.create(request);
        return ResponseDto.ok();
    }
}
