package com.interpark.assignment.repository.custom;

import com.interpark.assignment.domain.City;

import java.util.List;

public interface CityRepositoryCustom {
    List<City> getCitiesByMember(Long memberId);
}
