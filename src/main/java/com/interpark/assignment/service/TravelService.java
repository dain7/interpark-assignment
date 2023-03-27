package com.interpark.assignment.service;

import com.interpark.assignment.domain.City;
import com.interpark.assignment.domain.Member;
import com.interpark.assignment.domain.Travel;
import com.interpark.assignment.dto.travel.*;
import com.interpark.assignment.exception.CityNotFoundException;
import com.interpark.assignment.exception.EndDateBeforeOrSameStartDateException;
import com.interpark.assignment.exception.MemberNotFoundException;
import com.interpark.assignment.exception.TravelNotFoundException;
import com.interpark.assignment.repository.CityRepository;
import com.interpark.assignment.repository.MemberRepository;
import com.interpark.assignment.repository.TravelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TravelService {

    private final MemberRepository memberRepository;
    private final TravelRepository travelRepository;
    private final CityRepository cityRepository;

    @Transactional
    public TravelCreateResponseDto create(Long memberId, TravelRequestDto request) {
        City city = cityRepository.findById(request.getCityId()).orElseThrow(CityNotFoundException::new);
        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);

        if (!isEndDateAfterStartDate(request.getStartDate(), request.getEndDate())) {
            throw new EndDateBeforeOrSameStartDateException();
        }

        Travel travel = Travel.builder()
                .member(member)
                .city(city)
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .build();
        travel.updateCity(city);
        travelRepository.save(travel);

        return TravelCreateResponseDto.builder()
                .id(travel.getId())
                .build();
    }

    @Transactional
    public void update(Long travelId, TravelRequestDto request) {
        Travel travel = travelRepository.findById(travelId).orElseThrow(TravelNotFoundException::new);
        City city = cityRepository.findById(request.getCityId()).orElseThrow(CityNotFoundException::new);

        if (!isEndDateAfterStartDate(request.getStartDate(), request.getEndDate())) {
            throw new EndDateBeforeOrSameStartDateException();
        }

        travel.updateDate(request.getStartDate(), request.getEndDate());
        travel.updateCity(city);
    }

    @Transactional
    public void delete(Long travelId) {
        Travel travel = travelRepository.findById(travelId).orElseThrow(TravelNotFoundException::new);
        travel.removeCity();
        travelRepository.delete(travel);
    }

    public TravelResponseDto get(Long travelId) {
        Travel travel = travelRepository.findByIdWithCity(travelId).orElseThrow(TravelNotFoundException::new);
        return TravelResponseDto.builder()
                .id(travel.getId())
                .cityName(travel.getCity().getName())
                .startDate(travel.getStartDate())
                .endDate(travel.getEndDate())
                .build();
    }

    public boolean isEndDateAfterStartDate(LocalDate startDate, LocalDate endDate) {
        return startDate.compareTo(endDate) < 0;
    }
}
