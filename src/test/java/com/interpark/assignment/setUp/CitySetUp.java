package com.interpark.assignment.setUp;

import com.interpark.assignment.domain.City;
import com.interpark.assignment.domain.Member;
import com.interpark.assignment.exception.MemberNotFoundException;
import com.interpark.assignment.repository.CityRepository;
import com.interpark.assignment.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CitySetUp {
    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private MemberRepository memberRepository;

    public Long getCityId(Long memberId, String name) {
        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        City city = City.builder()
                .member(member)
                .name(name)
                .build();
        return cityRepository.save(city).getId();
    }
}
