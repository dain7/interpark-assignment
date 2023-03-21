package com.interpark.assignment.service;

import com.interpark.assignment.domain.City;
import com.interpark.assignment.domain.User;
import com.interpark.assignment.dto.city.CityRequestDto;
import com.interpark.assignment.dto.city.CityResponseDto;
import com.interpark.assignment.exception.CityCannotDeleteException;
import com.interpark.assignment.exception.CityNotFoundException;
import com.interpark.assignment.repository.CityRepository;
import com.interpark.assignment.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CityService {

    private final CityRepository cityRepository;
    private final UserRepository userRepository;

    @Transactional
    public void create(CityRequestDto request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow();

        cityRepository.save(
                City.builder()
                        .name(request.getName())
                        .user(user)
                        .build()
        );
    }

    @Transactional
    public void update(Long cityId, CityRequestDto request) {
        userRepository.findById(request.getUserId()).orElseThrow();
        City city = cityRepository.findById(cityId).orElseThrow(CityNotFoundException::new);

        city.updateName(request.getName());
    }

    public void delete(Long cityId) {
        City city = cityRepository.findById(cityId).orElseThrow(CityNotFoundException::new);
        if (!city.getTravels().isEmpty()) {
            throw new CityCannotDeleteException();
        }
        cityRepository.delete(city);
    }

    public CityResponseDto get(Long cityId) {
        City city = cityRepository.findById(cityId).orElseThrow(CityNotFoundException::new);

        return CityResponseDto.builder()
                .id(city.getId())
                .name(city.getName())
                .build();
    }
}
