package com.interpark.assignment.repository.custom;

import com.interpark.assignment.domain.Travel;

import java.util.Optional;

public interface TravelRepositoryCustom {
    Optional<Travel> findByIdWithCity(Long travelId);
}
