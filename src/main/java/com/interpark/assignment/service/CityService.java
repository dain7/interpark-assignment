package com.interpark.assignment.service;

import com.interpark.assignment.domain.City;
import com.interpark.assignment.dto.city.CityCreateDto;
import com.interpark.assignment.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CityService {

    private final CityRepository cityRepository;

    @Transactional
    public void create(CityCreateDto request) {
        cityRepository.save(
                City.builder()
                        .name(request.getName())
                        .build()
        );
    }
}
