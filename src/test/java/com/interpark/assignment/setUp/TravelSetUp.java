package com.interpark.assignment.setUp;

import com.interpark.assignment.domain.City;
import com.interpark.assignment.domain.Member;
import com.interpark.assignment.domain.Travel;
import com.interpark.assignment.exception.CityNotFoundException;
import com.interpark.assignment.exception.MemberNotFoundException;
import com.interpark.assignment.repository.CityRepository;
import com.interpark.assignment.repository.MemberRepository;
import com.interpark.assignment.repository.TravelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class TravelSetUp {
    @Autowired
    private TravelRepository travelRepository;
    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private MemberRepository memberRepository;

    public Long getTravelId(Long memberId, Long cityId, LocalDate startDate, LocalDate endDate) {
        City city = cityRepository.findById(cityId).orElseThrow(CityNotFoundException::new);
        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        Travel travel = Travel.builder()
                .member(member)
                .city(city)
                .startDate(startDate)
                .endDate(endDate)
                .build();
        travel.updateCity(city);
        return travelRepository.save(travel).getId();
    }
}
