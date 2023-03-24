package com.interpark.assignment.service;

import com.interpark.assignment.domain.City;
import com.interpark.assignment.domain.Member;
import com.interpark.assignment.dto.city.*;
import com.interpark.assignment.exception.CityCannotDeleteException;
import com.interpark.assignment.exception.CityNotFoundException;
import com.interpark.assignment.exception.MemberNotFoundException;
import com.interpark.assignment.repository.CityRepository;
import com.interpark.assignment.repository.MemberRepository;
import com.interpark.assignment.repository.SearchLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CityService {

    private final CityRepository cityRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public CityCreateResponseDto create(CityRequestDto request) {
        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(MemberNotFoundException::new);

        City city = City.builder()
                .name(request.getName())
                .member(member)
                .build();
        cityRepository.save(city);

        return CityCreateResponseDto.builder()
                .id(city.getId())
                .build();
    }

    @Transactional
    public void update(Long cityId, CityUpdateRequestDto request) {
        City city = cityRepository.findById(cityId).orElseThrow(CityNotFoundException::new);
        city.updateName(request.getName());
    }

    @Transactional
    public void delete(Long cityId) {
        City city = cityRepository.findById(cityId).orElseThrow(CityNotFoundException::new);

        if (!city.getTravels().isEmpty()) {
            throw new CityCannotDeleteException();
        }

        cityRepository.delete(city);
    }

    public CityResponseDto get(Long memberId, Long cityId) {
        City city = cityRepository.findById(cityId).orElseThrow(CityNotFoundException::new);
        memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);

        return CityResponseDto.builder()
                .id(city.getId())
                .name(city.getName())
                .build();
    }

    public List<CityGetResponseDto> getByMember(Long memberId) {
        List<City> cities = cityRepository.getCitiesByMember(memberId);

        List<CityGetResponseDto> response = new ArrayList<>();
        for (City city : cities) {
            response.add(
                    CityGetResponseDto.builder()
                            .id(city.getId())
                            .name(city.getName())
                            .build()
            );
        }
        return response;
    }
}
